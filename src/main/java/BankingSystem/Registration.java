package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import BankingSystem.Database_Connector.DatabaseConnectionException;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // SQL queries
    private static final String INSERT_QUERY = "INSERT INTO admin_dashboard(full_name, address, mobile_no, email, account_type, balance, dob, account, id_proff, id_proff_number) VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String PASSWORD_MANAGEMENT_INSERT_QUERY = "INSERT INTO password_management(username, password, email, account, isnewuser) VALUES(?,?,?,?,FALSE)";
    private static final String CHECK_EXISTENCE_QUERY = "SELECT COUNT(*) FROM admin_dashboard WHERE email = ? OR mobile_no = ? OR id_proff_number = ?";

    public Registration() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String email = request.getParameter("email");
        String accountType = request.getParameter("account_type");
        double balance = Double.parseDouble(request.getParameter("balance"));
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("id_proff");
        String idProofNumber = request.getParameter("id_proff_number");

        // Check if date of birth is above 2005
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        if (dateOfBirth.isAfter(Year.of(2005).atDay(1))) {
            forwardWithAlert(request, response, "error", "You are not allowed to create an account if your date of birth is after 2005.", "Admin.html");
            return;
        }

        // Generate account number and a unique password
        String accountNo = generateAccountNumber();
        String uniquePassword = generateUniquePassword();

        try (Connection con = Database_Connector.connect();
             PreparedStatement ps = con.prepareStatement(CHECK_EXISTENCE_QUERY)) {

            con.setAutoCommit(false); // Start transaction

            // Check if email, mobile number, or ID proof number already exists
            ps.setString(1, email);
            ps.setString(2, mobileNo);
            ps.setString(3, idProofNumber);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                int existingCount = rs.getInt(1);
                if (existingCount > 0) {
                    forwardWithAlert(request, response, "error", "Email, mobile number, or ID proof number already exists.", "Admin.html");
                    return;
                }
            }

            // If email, mobile number, and ID proof number are unique, proceed with insertion
            try (PreparedStatement insertPs = con.prepareStatement(INSERT_QUERY)) {
                insertPs.setString(1, fullName);
                insertPs.setString(2, address);
                insertPs.setString(3, mobileNo);
                insertPs.setString(4, email);
                insertPs.setString(5, accountType);
                insertPs.setDouble(6, balance);
                insertPs.setString(7, dob);
                insertPs.setString(8, accountNo); // Insert account number into the account column
                insertPs.setString(9, idProof);
                insertPs.setString(10, idProofNumber);
                int count = insertPs.executeUpdate();

                if (count > 0) {
                    // Insert into password management
                    try (PreparedStatement passwordPs = con.prepareStatement(PASSWORD_MANAGEMENT_INSERT_QUERY)) {
                        passwordPs.setString(1, fullName);
                        passwordPs.setString(2, uniquePassword); // Use the unique password here
                        passwordPs.setString(3, email);
                        passwordPs.setString(4, accountNo);
                        int passwordCount = passwordPs.executeUpdate();
                        
                        if (passwordCount > 0) {
                            con.commit(); // Commit transaction if both inserts are successful

                            // Send registration email
                            Emailing.sendRegistrationEmail(fullName, email, accountNo, uniquePassword);

                            forwardWithAlert(request, response, "success", "Account number: " + accountNo, "Admin.html");
                        } else {
                            forwardWithAlert(request, response, "error", "Failed to insert password information.", "Admin.html");
                        }
                    }
                } else {
                    forwardWithAlert(request, response, "error", "Failed to register customer.", "Admin.html");
                }
            }
        } catch (SQLException | DatabaseConnectionException e) {
            e.printStackTrace();
            forwardWithAlert(request, response, "error", "Database error: " + e.getMessage(), "Admin.html");
        }
    }

    private void forwardWithAlert(HttpServletRequest request, HttpServletResponse response, String status, String message, String forwardUrl) throws ServletException, IOException {
        request.setAttribute("status", status);
        request.setAttribute("message", message);
        request.setAttribute("forward", forwardUrl);
        RequestDispatcher dispatcher = request.getRequestDispatcher("responseHandler.jsp");
        dispatcher.forward(request, response);
    }

    private String generateAccountNumber() {
        long currentTime = System.currentTimeMillis();
        String currentTimeString = Long.toString(currentTime);
        String lastDigits = currentTimeString.substring(currentTimeString.length() - 4); // Adjust the number of digits as needed
        return "AC" + lastDigits + (int) (Math.random() * 100);
    }

    private String generateUniquePassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
