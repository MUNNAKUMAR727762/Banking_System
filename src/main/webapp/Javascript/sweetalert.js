// responseHandler.js
function handleResponse(status, message, forward) {
    if (status === "success") {
        Swal.fire({
            title: 'Success',
            text: message,
            icon: 'success'
        }).then(() => {
            window.location.href = forward;
        });
    } else {
        Swal.fire({
            title: 'Error',
            text: message,
            icon: 'error'
        }).then(() => {
            window.location.href = forward;
        });
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const status = document.getElementById("status").value;
    const message = document.getElementById("message").value;
    const forward = document.getElementById("forward").value;
    handleResponse(status, message, forward);
});
