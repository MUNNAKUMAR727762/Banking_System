package BankingSystem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Reset_Password")
public class Reset_Password extends HttpServlet {
    private Connection conn;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        conn = null;
        try {
            conn = Database_Connector.connect();
        } catch (Database_Connector.DatabaseConnectionException e) {
            e.printStackTrace();
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String status;
        String message;

        if (isUserExists(username, email)) {
            if (updatePasswordAndIsNewUser(username, password)) {
                status = "success";
                message = "Password updated successfully.";
            } else {
                status = "error";
                message = "Entered wrong credentials.";
            }
        } else {
            if (createNewUser(username, email, password)) {
                status = "success";
                message = "Account created successfully.";
            } else {
                status = "error";
                message = "Error creating account.";
            }
        }

        request.setAttribute("status", status);
        request.setAttribute("message", message);

        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }

    private boolean isUserExists(String username, String email) {
        try {
            String query = "SELECT * FROM password_management WHERE username = ? OR email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if username or email exists
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Assume error, treat as user exists
        }
    }

    private boolean updatePasswordAndIsNewUser(String username, String password) {
        try {
            String updateQuery = "UPDATE password_management SET password = ?, isnewuser = 1 WHERE username = ? OR email = ?";
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, password);
            statement.setString(2, username);
            statement.setString(3, username); // Update based on username or email

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createNewUser(String username, String email, String password) {
        try {
            String insertQuery = "INSERT INTO password_management (username, email, password, isnewuser) VALUES (?, ?, ?, 1)";
            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Returns true if insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
