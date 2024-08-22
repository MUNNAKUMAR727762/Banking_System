// Function to validate form data
function validateForm() {
    var fullName = document.getElementById("fullName").value;
    var address = document.getElementById("address").value;
    var mobileNo = document.getElementById("mobileNo").value;
    var email = document.getElementById("email").value;
    var accountType = document.getElementById("accountType").value;
    var balance = document.getElementById("balance").value;
    var dob = document.getElementById("dob").value;
    var idProof = document.getElementById("idProof").value;
    var idProofNumber = document.getElementById("id_proff_number").value; // Corrected ID access

    var error = "";

    if (!fullName.trim()) {
        error += "Full Name is required.\n";
    }
    if (!address.trim()) {
        error += "Address is required.\n";
    }
    if (!mobileNo.trim() || mobileNo.length !== 10 || isNaN(mobileNo)) {
        error += "Mobile Number must be 10 digits.\n";
    }
    if (!email.trim() || !validateEmail(email)) {
        error += "Valid Email is required.\n";
    }
    if (!accountType.trim()) {
        error += "Account Type is required.\n";
    }
    if (balance.trim() === "" || balance < 1000) {
        error += "Balance should be at least 1000.\n";
    }
    if (!dob.trim()) {
        error += "Date of Birth is required.\n";
    }
    // Additional validation for ID Proof
    if (idProof === "Aadhar") {
        if (idProofNumber.trim().length !== 12 || isNaN(idProofNumber)) {
            error += "Aadhar Number must be 12 digits.\n";
        }
    } else if (idProof === "PAN") {
        var panRegex = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
        if (!panRegex.test(idProofNumber)) {
            error += "Invalid PAN Number format.\n";
        }
    }

    if (error !== "") {
        alert(error);
        return false;
    }

    return true;
}

// Function to validate email format
function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}
