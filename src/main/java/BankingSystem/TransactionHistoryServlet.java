package BankingSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import BankingSystem.Database_Connector.DatabaseConnectionException;

@WebServlet("/TransactionHistoryServlet")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user details from session
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("account");

        // Retrieve the number of days from the request parameters
        String daysParam = request.getParameter("days");
        int days = daysParam != null ? Integer.parseInt(daysParam) : 7;

       

        // Initialize list to hold transaction details
        List<Map<String, String>> transactions = new ArrayList<>();

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = Database_Connector.connect();
            // Prepare SQL statement to fetch transaction history
            String sql = "SELECT * FROM transaction_history WHERE account_number = ? AND transaction_date >= ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, accountNumber);

            // Calculate the date limit based on the number of days
            LocalDateTime dateLimit = LocalDateTime.now().minusDays(days);
            pstmt.setTimestamp(2, Timestamp.valueOf(dateLimit));

            ResultSet rs = pstmt.executeQuery();

            // Collect the transaction data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (rs.next()) {
                Map<String, String> transaction = new HashMap<>();
                transaction.put("date", rs.getTimestamp("transaction_date").toLocalDateTime().format(formatter));
                transaction.put("transactionType", rs.getString("transaction_type"));
                transaction.put("amount", String.valueOf(rs.getDouble("amount")));
                transaction.put("balance", String.valueOf(rs.getDouble("balance")));
                transactions.add(transaction);
            }

            // Construct JSON response manually
            StringBuilder json = new StringBuilder("[");
            for (Map<String, String> transaction : transactions) {
                json.append("{");
                json.append("\"date\":\"").append(transaction.get("date")).append("\",");
                json.append("\"transactionType\":\"").append(transaction.get("transactionType")).append("\",");
                json.append("\"amount\":").append(transaction.get("amount")).append(",");
                json.append("\"balance\":").append(transaction.get("balance"));
                json.append("},");
            }
            if (!transactions.isEmpty()) {
                json.deleteCharAt(json.length() - 1); // Remove the trailing comma
            }
            json.append("]");

            // Set content type and send JSON response
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(json.toString());
            out.flush();

            // Close the database connection
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        } catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}