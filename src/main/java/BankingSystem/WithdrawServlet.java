package BankingSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String withdrawAmountStr = request.getParameter("withdrawAmount");
        double withdrawAmount;

        // Validate the withdrawal amount
        try {
            withdrawAmount = Double.parseDouble(withdrawAmountStr);
        } catch (NumberFormatException e) {
            sendAlert(response, "Error", "Invalid withdrawal amount!", "error", "C_Dashboard.html");
            return;
        }

        // Retrieve user details from session
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("fullName");
        String email = (String) session.getAttribute("email");
        String accountNumber = (String) session.getAttribute("account");
        Double currentBalance = (Double) session.getAttribute("balance");

        if (accountNumber == null || currentBalance == null) {
            sendAlert(response, "Error", "Session expired", "error", "index.html");
            return;
        }

        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement updateBalanceStmt = null;
        PreparedStatement insertTransactionStmt = null;

        try {
            // Connect to the database
            conn = Database_Connector.connect();

            // Check if the account number exists and retrieve current balance
            checkStmt = conn.prepareStatement("SELECT balance FROM admin_dashboard WHERE account = ?");
            checkStmt.setString(1, accountNumber);
            ResultSet checkResult = checkStmt.executeQuery();

            if (!checkResult.next()) {
                sendAlert(response, "Error", "Account number does not exist!", "error", "C_Dashboard.html");
                return;
            }

            double balance = checkResult.getDouble("balance");

            // Check if withdrawal amount is valid
            if (withdrawAmount > 0 && withdrawAmount <= balance) {
                // Deduct withdrawal amount from the balance
                double newBalance = balance - withdrawAmount;

                // Update the balance in the database
                updateBalanceStmt = conn.prepareStatement("UPDATE admin_dashboard SET balance = ? WHERE account = ?");
                updateBalanceStmt.setDouble(1, newBalance);
                updateBalanceStmt.setString(2, accountNumber);
                updateBalanceStmt.executeUpdate();

                // Insert transaction history record for withdrawal
                insertTransactionStmt = conn.prepareStatement(
                        "INSERT INTO transaction_history (account_number, transaction_type, amount, balance, transaction_date) VALUES (?, 'Withdrawal', ?, ?, CURRENT_TIMESTAMP)"
                );
                insertTransactionStmt.setString(1, accountNumber);
                insertTransactionStmt.setDouble(2, withdrawAmount);
                insertTransactionStmt.setDouble(3, newBalance);
                insertTransactionStmt.executeUpdate();

                // Update the session attribute 'balance' with the new balance
                session.setAttribute("balance", newBalance);

                // Send withdrawal confirmation email
                Emailing.sendWithdrawalConfirmationEmail(fullName, email, accountNumber, withdrawAmountStr, newBalance);

                // Display success message with user's name and account number
                sendAlert(response, "Success", "Withdrawal successful!<br>Name: " + fullName + "<br>Account Number: " + accountNumber, "success", "C_Dashboard.html");
            } else {
                // Invalid withdrawal amount
                sendAlert(response, "Error", "Insufficient balance or invalid amount!", "error", "C_Dashboard.html");
            }
        } catch (SQLException e) {
            handleSQLException(response, e);
        } catch (Exception e) {
            handleOtherException(response, e);
        } finally {
            // Close database resources
            try {
                if (checkStmt != null) checkStmt.close();
                if (updateBalanceStmt != null) updateBalanceStmt.close();
                if (insertTransactionStmt != null) insertTransactionStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAlert(HttpServletResponse response, String title, String message, String icon, String redirectUrl) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<script src='https://cdn.jsdelivr.net/npm/sweetalert2@11'></script>");
        out.println("</head><body>");
        out.println("<script>");
        out.println("Swal.fire({");
        out.println("  title: '" + title + "',");
        out.println("  html: '" + message + "',");
        out.println("  icon: '" + icon + "',");
        out.println("  allowOutsideClick: false,");
        out.println("  confirmButtonText: 'OK'");
        out.println("}).then((result) => {");
        out.println("  if (result.isConfirmed) {");
        out.println("    window.location.href = '" + redirectUrl + "';");
        out.println("  }");
        out.println("});");
        out.println("</script>");
        out.println("</body></html>");
        out.close();
    }

    private void handleSQLException(HttpServletResponse response, SQLException e) throws IOException {
        e.printStackTrace();
        sendAlert(response, "Error", "Database error: " + e.getMessage(), "error", "C_Dashboard.html");
    }

    private void handleOtherException(HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        sendAlert(response, "Error", "Unexpected error: " + e.getMessage(), "error", "index.html");
    }
}
