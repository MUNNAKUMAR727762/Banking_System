package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CHECK_BALANCE_QUERY = "SELECT balance FROM admin_dashboard WHERE full_name = ? AND mobile_no = ?";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admin_dashboard WHERE full_name = ? AND mobile_no = ?";
    private static final String DELETE_PASSWORD_QUERY = "DELETE FROM password_management WHERE username = ?";
    
    public Delete() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String mobileNo = request.getParameter("mobileNo");

        try (Connection con = Database_Connector.connect();
             PreparedStatement psCheckBalance = con.prepareStatement(CHECK_BALANCE_QUERY);
             PreparedStatement psDeleteAdmin = con.prepareStatement(DELETE_ADMIN_QUERY);
             PreparedStatement psDeletePassword = con.prepareStatement(DELETE_PASSWORD_QUERY)) {

            // Check balance
            psCheckBalance.setString(1, fullName);
            psCheckBalance.setString(2, mobileNo);
            ResultSet rs = psCheckBalance.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance == 0) {
                    // Delete record from admin_dashboard table
                    psDeleteAdmin.setString(1, fullName);
                    psDeleteAdmin.setString(2, mobileNo);
                    int countAdmin = psDeleteAdmin.executeUpdate();

                    // Delete record from password_management table
                    psDeletePassword.setString(1, fullName);
                    int countPassword = psDeletePassword.executeUpdate();

                    if (countAdmin > 0 && countPassword > 0) {
                        // Deletion from both tables successful
                        forwardWithSuccess(request, response, "Account Data Deleted Successfully");
                    } else {
                        // Deletion failed
                        forwardWithError(request, response, "Failed To Delete (possible reason wrong Credentials)");
                    }
                } else {
                    // Account has balance, deletion not allowed
                    forwardWithError(request, response, "Account has balance, deletion not allowed");
                }
            } else {
                // Account not found
                forwardWithError(request, response, "Account not found");
            }
        } catch (SQLException se) {
            forwardWithError(request, response, "SQL Exception occurred: " + se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            forwardWithError(request, response, "Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("status", "error");
        request.setAttribute("message", message);
        request.setAttribute("forward", "Admin.html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }

    private void forwardWithSuccess(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("status", "success");
        request.setAttribute("message", message);
        request.setAttribute("forward", "Admin.html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }
}
