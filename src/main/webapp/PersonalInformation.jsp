<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Information</title>
    <link rel="stylesheet" href="CSS/dashboard-styles.css">
</head>
<body>
    <div class="dashboard-container">
        <div class="dashboard-title">Welcome to Your Dashboard <span id="account"><%= session.getAttribute("account") %></span></div>

        <div class="dashboard-section">
            <div class="section-title">Personal Information</div>
            <div class="details-item">
                <i class="fas fa-user icon"></i><strong>Full Name:</strong> <span id="fullName"><%=session.getAttribute("fullName")%></span>
            </div>
            <div class="details-item">
                <i class="fas fa-map-marker-alt icon"></i><strong>Address:</strong> <span id="address"><%= session.getAttribute("address") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-phone icon"></i><strong>Mobile Number:</strong> <span id="mobileNo"><%= session.getAttribute("mobileNo") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-envelope icon"></i><strong>Email:</strong> <span id="email"><%= session.getAttribute("email") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-credit-card icon"></i><strong>Account Type:</strong> <span id="accountType"><%= session.getAttribute("accountType") %></span>
            </div>
            <div class="details-item">
                <i class="fas fa-credit-card icon"></i><strong>Balance:</strong> <span id="balance"><%= session.getAttribute("balance") %></span>
            </div>
        </div>
    </div>
    <script src="Javascript/C_Dashboard.js"></script>
</body>
</html>
