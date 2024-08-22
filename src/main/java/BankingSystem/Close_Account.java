package BankingSystem;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/Close_Account")
public class Close_Account extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("full_name");
        String accountNumber = request.getParameter("account_number");
        String mobileNumber = request.getParameter("mobile_number");

        Connection connection = null;
        try {
            connection = Database_Connector.connect();
        } catch (Database_Connector.DatabaseConnectionException e) {
            e.printStackTrace();
        }

        String status;
        String message;

        if (connection != null) {
            try {
                String deleteAccountQuery = "DELETE FROM admin_dashboard WHERE full_name=? AND Account=? AND Mobile_no=? AND balance=0";
                PreparedStatement deleteAccountStatement = connection.prepareStatement(deleteAccountQuery);
                deleteAccountStatement.setString(1, fullName);
                deleteAccountStatement.setString(2, accountNumber);
                deleteAccountStatement.setString(3, mobileNumber);

                int rowsAffected = deleteAccountStatement.executeUpdate();

                if (rowsAffected > 0) {
                    String deletePasswordQuery = "DELETE FROM password_management WHERE username=? OR email=?";
                    PreparedStatement deletePasswordStatement = connection.prepareStatement(deletePasswordQuery);
                    deletePasswordStatement.setString(1, fullName);
                    deletePasswordStatement.setString(2, mobileNumber);

                    deletePasswordStatement.executeUpdate();
                    deletePasswordStatement.close();

                    status = "success";
                    message = "Account deleted successfully.";
                } else {
                    status = "error";
                    message = "Unable to delete account. Please check your details or ensure balance is 0.";
                }

                deleteAccountStatement.close();

            } catch (SQLException e) {
                status = "error";
                message = "Database error: " + e.getMessage();
            } finally {
                Database_Connector.closeConnection(connection);
            }
        } else {
            status = "error";
            message = "Failed to establish database connection.";
        }

        request.setAttribute("status", status);
        request.setAttribute("message", message);
        request.setAttribute("forward", "C_Dashboard.html");

        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }
}
