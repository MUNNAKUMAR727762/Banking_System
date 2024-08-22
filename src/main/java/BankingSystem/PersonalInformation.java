package BankingSystem;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PersonalInformation")
public class PersonalInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve user details from session
        String fullName = (String) session.getAttribute("fullName");
        String address = (String) session.getAttribute("address");
        String mobileNo = (String) session.getAttribute("mobileNo");
        String email = (String) session.getAttribute("email");
        String accountType = (String) session.getAttribute("accountType");
        String account = (String) session.getAttribute("account");
        Double balance = (Double) session.getAttribute("balance");
        String balanceStr = balance != null ? String.valueOf(balance) : null;

        // Set user details as request attributes
        request.setAttribute("fullName", fullName);
        request.setAttribute("address", address);
        request.setAttribute("mobileNo", mobileNo);
        request.setAttribute("email", email);
        request.setAttribute("accountType", accountType);
        request.setAttribute("account", account);
        request.setAttribute("balance", balanceStr);

        // Forward the request to the JSP page
        request.getRequestDispatcher("/C_Dashboard.html").forward(request, response);
    }
}
