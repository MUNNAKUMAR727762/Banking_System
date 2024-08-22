package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Customer_Login_Page")
public class Customer_Login_Page extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PASSWORD_QUERY = "SELECT email, isnewuser FROM password_management WHERE account=? AND password=?";
    private static final String CUSTOMER_QUERY = "SELECT * FROM admin_dashboard WHERE account=? AND email=?";

    public Customer_Login_Page() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement passwordStmt = null;
        PreparedStatement customerStmt = null;
        ResultSet passwordRs = null;
        ResultSet customerRs = null;

        try {
            conn = Database_Connector.connect();
        } catch (Database_Connector.DatabaseConnectionException e) {
            forwardWithError(request, response, "Database connection error");
            return;
        }

        try {
            // Check credentials in password_management table
            passwordStmt = conn.prepareStatement(PASSWORD_QUERY);
            passwordStmt.setString(1, account);
            passwordStmt.setString(2, password);
            passwordRs = passwordStmt.executeQuery();

            if (passwordRs.next()) {
                int isNewUser = passwordRs.getInt("isnewuser");
                String email = passwordRs.getString("email");

                // Fetch customer details from admin_dashboard
                customerStmt = conn.prepareStatement(CUSTOMER_QUERY);
                customerStmt.setString(1, account);
                customerStmt.setString(2, email);
                customerRs = customerStmt.executeQuery();

                if (customerRs.next()) {
                    // Retrieve customer details
                    String fullName = customerRs.getString("full_name");
                    String address = customerRs.getString("address");
                    String mobileNo = customerRs.getString("mobile_no");
                    String accountType = customerRs.getString("account_type");
                    double balance = customerRs.getDouble("balance");
                    String dobStr = customerRs.getString("dob");
                    String idProffNumber = customerRs.getString("id_proff_number");
                    String idProff = customerRs.getString("id_proff");

                    // Convert dob string to Date object
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.parse(dobStr); // Just to validate the date, not needed for storing in session

                    // Store customer details in session
                    HttpSession session = request.getSession();
                    session.setAttribute("fullName", fullName);
                    session.setAttribute("account", account);
                    session.setAttribute("address", address);
                    session.setAttribute("mobileNo", mobileNo);
                    session.setAttribute("email", email);
                    session.setAttribute("accountType", accountType);
                    session.setAttribute("balance", balance);
                    session.setAttribute("idProff", idProff);
                    session.setAttribute("idProffNumber", idProffNumber);
                    session.setAttribute("dob", dobStr);

                    // Redirect to PersonalInformation servlet
                    response.sendRedirect("PersonalInformation");
                } else {
                    forwardWithError(request, response, "Customer details not found");
                }
            } else {
                forwardWithError(request, response, "Invalid account number or password");
            }
        } catch (SQLException | ParseException e) {
            forwardWithError(request, response, "Error: " + e.getMessage());
        } finally {
            try {
                if (passwordRs != null) passwordRs.close();
                if (customerRs != null) customerRs.close();
                if (passwordStmt != null) passwordStmt.close();
                if (customerStmt != null) customerStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("status", "error");
        request.setAttribute("message", message);
        request.setAttribute("forward", "Admin.html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }
}
