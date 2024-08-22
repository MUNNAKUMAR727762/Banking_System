<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit Money</title>
    <link rel="stylesheet" href="CSS/dashboard-styles.css">
  
</head>
<body>
    <div class="dashboard-container">
        <div class="card">
            <h2>Deposit Amount</h2>
            <div class="user-info">
                <div class="details-item">
                    <i class="fas fa-user"></i><strong>Name:</strong> <%= session.getAttribute("fullName") %>
                </div>
                <div class="details-item">
                    <i class="fas fa-phone"></i><strong>Mobile Number:</strong> <%= session.getAttribute("mobileNo") %>
                </div>
                <div class="details-item">
                    <i class="fas fa-credit-card"></i><strong>Account Number:</strong> <%= session.getAttribute("account") %>
                </div>
            </div>
            <form id="deposit-form" action="DepositServlet" method="post">
                <input type="text" id="depositAmount" name="depositAmount" placeholder="Enter Amount" required>
                <button type="submit">Deposit</button>
            </form>
        </div>
    </div>

   <script src="Javascript/C_Dashboard.js"></script>
</body>
</html>
