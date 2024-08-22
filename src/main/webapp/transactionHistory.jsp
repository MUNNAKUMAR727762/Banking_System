<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <link rel="stylesheet" href="CSS/style.css">
    <!-- Add font awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
     <link rel="stylesheet" href="CSS/transactionHistory.css"><link>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.9.2/html2pdf.bundle.min.js"></script>
</head>
<body>
    <div class="container" id="transaction-container">
        <header>
            <img src="IMG/Samanvay_Logo.png" alt="Logo" class="logo" id="pdf-logo">
            <h1>Transaction History</h1>
            <div class="customer-info">
                <p><strong>Name:</strong> <span id="fullName"><%= (String) session.getAttribute("fullName") %></span></p>
                <p><strong>Account Number:</strong> <span id="account"><%= (String) session.getAttribute("account") %></span></p>
                <p><strong>Address:</strong> <span id="address"><%= (String) session.getAttribute("address") %></span></p>
                <p><strong>Mobile No:</strong> <span id="mobileNo"><%= (String) session.getAttribute("mobileNo") %></span></p>
                <p><strong>Email:</strong> <span id="email"><%= (String) session.getAttribute("email") %></span></p>
                <p><strong>Account Type:</strong> <span id="accountType"><%= (String) session.getAttribute("accountType") %></span></p>
            </div>
        </header>

        <div class="options" id="options-section">
            <label for="days">Select number of days:</label>
            <select id="days" name="days">
                <option value="7">Last 7 days</option>
                <option value="30">Last 30 days</option>
                <option value="90">Last 90 days</option>
            </select>
            <button id="fetch-transactions">Fetch Transactions</button>
        </div>

        <section class="transaction-history">
            <table id="transaction-table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Transaction Type</th>
                        <th>Amount</th>
                        <th>Balance After Transaction</th>
                    </tr>
                </thead>
                <tbody id="transaction-data">
                    <!-- Transaction details will be populated here -->
                </tbody>
            </table>
            <div id="loader" class="loader">
                <i class="fas fa-spinner fa-spin"></i> Loading...
            </div>
        </section>

        <div class="buttons">
            <button id="back-to-dashboard"><i class="fas fa-arrow-left"></i> Back to Dashboard</button>
            <button id="download-pdf"><i class="fas fa-file-pdf"></i> Download PDF</button>
        </div>
    </div>
 
  <script>
  
  function fetchTransactionHistory() {
      var days = document.getElementById("days").value;
      fetch('TransactionHistoryServlet?days=' + days)
          .then(response => response.json())
          .then(data => {
              var tableBody = document.getElementById('transaction-data');
              tableBody.innerHTML = ""; // Clear existing data
              data.forEach(function(transaction) {
                  var row = "<tr>" +
                              "<td>" + transaction.date + "</td>" +
                              "<td>" + transaction.transactionType + "</td>" +
                              "<td>" + transaction.amount + "</td>" +
                              "<td>" + transaction.balance + "</td>" +
                            "</tr>";
                  tableBody.innerHTML += row;
              });
          })
          .catch(error => console.error('Error fetching transaction history:', error));
  }

  // Function to download transaction history as PDF
  function downloadTransactionHistory() {
      var element = document.getElementById('transaction-container');
      var buttons = document.querySelector('.buttons');
      var options = document.getElementById('options-section');
      var logo = document.getElementById('pdf-logo');

      // Show the logo
      logo.style.display = 'block';

      // Hide buttons and options
      buttons.style.display = 'none';
      options.style.display = 'none';

      var opt = {
          margin: 1,
          filename: 'transaction_history.pdf',
          image: { type: 'jpeg', quality: 0.98 },
          html2canvas: { scale: 2 },
          jsPDF: { unit: 'in', format: 'letter', orientation: 'portrait' }
      };

      html2pdf().from(element).set(opt).save().then(function() {
          // Show buttons and options again after PDF is generated
          buttons.style.display = 'flex';
          options.style.display = 'block';
          // Hide the logo again
          logo.style.display = 'none';
      });
  }

  document.getElementById("fetch-transactions").addEventListener("click", fetchTransactionHistory);
  document.getElementById("back-to-dashboard").addEventListener("click", function() {
      window.location.href = "C_Dashboard.html"; // Adjust the URL as needed
  });
  document.getElementById("download-pdf").addEventListener("click", downloadTransactionHistory);

  // Initial loading of transaction history
  fetchTransactionHistory();
</script>
</body>
</html>
