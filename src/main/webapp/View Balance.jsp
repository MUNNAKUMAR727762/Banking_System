<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Balance</title>
    <link rel="stylesheet" href="CSS/dashboard-styles.css">
</head>
<body>
    <div class="dashboard-container">
        <div class="card">
            <h2>View Balance</h2>
            <div class="user-info">
                <div class="details-item">
                    <strong>Current Balance:</strong> <%= session.getAttribute("balance") %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
