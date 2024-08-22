package BankingSystem;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class Emailing {

    private static final String USERNAME = "ujjawalkumar.engstudent1@gmail.com";
    private static final String PASSWORD = "tyrp deqs vzxk zzxd";
    private static final String LOGO_PATH = "D:\\eclipse_java_devceloper\\Banking_System\\src\\main\\webapp\\IMG\\Samanvay_Logo.png"; // Local path to the logo

    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };

        return Session.getInstance(props, auth);
    }

    public static void sendRegistrationEmail(String fullName, String email, String accountNo, String tempPassword) {
        String subject = "Account Registration Confirmation - Samanvay Bank";
        String body = "<div style='font-family: Arial, sans-serif;'>"
                + "<img src='cid:logo' alt='Samanvay Bank Logo' style='width: 100%;'><br><br>"
                + "<div style='border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #004080; text-shadow: 1px 1px 2px #aaa;'>Dear " + fullName + ",</h2>"
                + "<p>Your account has been successfully registered.</p>"
                + "<p><strong>Account Number:</strong> " + accountNo + "</p>"
                + "<p><strong>Temporary Password:</strong> " + tempPassword + "</p>"
                + "<p>Thank you for choosing Samanvay Bank.</p>"
                + "<p>Best regards,<br><span style='color: #004080; font-weight: bold;'>Samanvay Bank Team</span></p>"
                + "</div></div>";
        sendEmail(email, subject, body);
    }

    public static void sendWithdrawalConfirmationEmail(String fullName, String email, String accountNo, String withdrawAmount, double newBalance) {
        String subject = "Withdrawal Confirmation - Samanvay Bank";
        String body = "<div style='font-family: Arial, sans-serif;'>"
                + "<img src='cid:logo' alt='Samanvay Bank Logo' style='width: 100%;'><br><br>"
                + "<div style='border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #004080; text-shadow: 1px 1px 2px #aaa;'>Dear " + fullName + ",</h2>"
                + "<p>You have successfully withdrawn an amount of $" + withdrawAmount + " from your account.</p>"
                + "<p><strong>Account Number:</strong> " + accountNo + "</p>"
                + "<p><strong>New Balance:</strong> $" + newBalance + "</p>"
                + "<p>Thank you for banking with us.</p>"
                + "<p>Best regards,<br><span style='color: #004080; font-weight: bold;'>Samanvay Bank Team</span></p>"
                + "</div></div>";
        sendEmail(email, subject, body);
    }

    public static void sendDepositConfirmationEmail(String fullName, String email, String accountNo, String depositAmount, double newBalance) {
        String subject = "Deposit Confirmation - Samanvay Bank";
        String body = "<div style='font-family: Arial, sans-serif;'>"
                + "<img src='cid:logo' alt='Samanvay Bank Logo' style='width: 100%;'><br><br>"
                + "<div style='border: 1px solid #ddd; padding: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>"
                + "<h2 style='color: #004080; text-shadow: 1px 1px 2px #aaa;'>Dear " + fullName + ",</h2>"
                + "<p>You have successfully deposited an amount of $" + depositAmount + " to your account.</p>"
                + "<p><strong>Account Number:</strong> " + accountNo + "</p>"
                + "<p><strong>New Balance:</strong> $" + newBalance + "</p>"
                + "<p>Thank you for banking with us.</p>"
                + "<p>Best regards,<br><span style='color: #004080; font-weight: bold;'>Samanvay Bank Team</span></p>"
                + "</div></div>";
        sendEmail(email, subject, body);
    }

    private static void sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is the image attachment
            messageBodyPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(LOGO_PATH);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<logo>");

            // Add the image part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Put the multipart content into the message
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Email sent to: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

     
}
