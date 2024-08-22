package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Modify")
public class Modify extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPDATE_QUERY = "UPDATE admin_dashboard SET full_name = ?, address = ?, mobile_no = ?, email = ?, account_type = ?, balance = ?, dob = ? WHERE mobile_no = ?";

    public Modify() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mobileNo = request.getParameter("mobileNo");
        String address = request.getParameter("address");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");
        double balance = Double.parseDouble(request.getParameter("balance"));
        String dob = request.getParameter("dob");

        try (Connection con = Database_Connector.connect();
             PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {

            ps.setString(1, fullName);
            ps.setString(2, address);
            ps.setString(3, mobileNo);
            ps.setString(4, email);
            ps.setString(5, accountType);
            ps.setDouble(6, balance);
            ps.setString(7, dob);
            ps.setString(8, mobileNo); // Match by mobile number

            int count = ps.executeUpdate();

            if (count > 0) {
                forwardWithAlert(request, response, "Success", "Data Updated Successfully", "success");
            } else {
                forwardWithAlert(request, response, "Failed", "Failed to update data. (Possible Reason: Credential Not Exist)", "error");
            }

        } catch (SQLException se) {
            handleSQLException(request, response, se);
        } catch (Exception e) {
            handleOtherException(request, response, e);
        }
    }

    private void forwardWithAlert(HttpServletRequest request, HttpServletResponse response, String title, String message, String icon) throws ServletException, IOException {
        request.setAttribute("title", title);
        request.setAttribute("message", message);
        request.setAttribute("icon", icon);
        request.setAttribute("forward", "Admin.html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }

    private void handleSQLException(HttpServletRequest request, HttpServletResponse response, SQLException e) throws ServletException, IOException {
        e.printStackTrace();
        forwardWithAlert(request, response, "Error", "Database error: " + e.getMessage(), "error");
    }

    private void handleOtherException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        e.printStackTrace();
        forwardWithAlert(request, response, "Error", "Unexpected error: " + e.getMessage(), "error");
    }
}
