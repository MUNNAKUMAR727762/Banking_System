<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Close Account Response</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
     <script src="Javascript/sweetalert.js"></script>
</head>
<body>
    <input type="hidden" id="status" value="<%= request.getAttribute("status") %>">
    <input type="hidden" id="message" value="<%= request.getAttribute("message") %>">
    <input type="hidden" id="forward" value="<%= request.getAttribute("forward") %>">
    
</body>
</html>
