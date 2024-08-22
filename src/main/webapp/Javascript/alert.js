// alerts.js

// Function to fetch data from Registration servlet and display using SweetAlert
function fetchDataFromServlet(event) {
    event.preventDefault(); // Prevent form from submitting the default way

    fetch('Registration', {
        method: 'POST', // Assuming your servlet accepts POST method
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded' // Adjust content type as per your servlet
        },
        body: new URLSearchParams(new FormData(document.getElementById('registrationForm')))
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json(); // assuming the servlet returns JSON data
    })
    .then(data => {
        // Format the data to display in SweetAlert
        let message = `
            <b>Status:</b> ${data.status}<br>
            <b>Message:</b> ${data.message}
        `;

        // Display data using SweetAlert
        Swal.fire({
            icon: data.status === 'success' ? 'success' : 'error',
            title: 'Registration Status',
            html: message
        });
    })
    .catch(error => {
        console.error('Error fetching data:', error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Failed to fetch data from servlet.'
        });
    });
}

// Attach event listener to the form for submission
document.getElementById('registrationForm').addEventListener('submit', fetchDataFromServlet);
