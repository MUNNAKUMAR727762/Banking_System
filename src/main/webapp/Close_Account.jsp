<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Close Account</title>
    <link rel="stylesheet" href="CSS/dashboard-styles.css">
</head>
<body>
    <div class="dashboard-container">
        <div class="dashboard-title">Close Account</div>

        <div class="dashboard-section">
            <div class="section-title">Your Information</div>
            <div class="details-item">
                <i class="fas fa-user icon"></i><strong>Full Name:</strong> <span id="fullName"><%= session.getAttribute("fullName") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-phone icon"></i><strong>Mobile Number:</strong> <span id="mobileNo"><%= session.getAttribute("mobileNo") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-credit-card icon"></i><strong>Account Number:</strong> <span id="account"><%= session.getAttribute("account") %></span>
            </div>
            <form action="Close_Account" method="post">
                <input type="submit" value="Close Account">
            </form>
        </div>
    </div>
</body>
</html>
