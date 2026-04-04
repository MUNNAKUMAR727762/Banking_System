# 🏦 Banking System Project

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![Tomcat](https://img.shields.io/badge/Tomcat-064F8C?style=for-the-badge&logo=apache-tomcat&logoColor=white)

A secure and efficient **Banking System** designed for seamless account management, transaction processing, and administrative control.

## 📑 Table of Contents
- [📌 Introduction](#-introduction)
- [🚀 Features](#-features)
- [🛠 Technologies Used](#-technologies-used)
- [⚙️ Architecture](#-architecture)
- [📥 Setup and Installation](#-setup-and-installation)
- [📌 Usage](#-usage)
- [🧪 Testing](#-testing)
- [🤝 Contributing](#-contributing)

---

## 📌 Introduction
This project enhances the **banking experience** by providing a convenient, secure, and efficient platform for managing customer accounts and transactions. Designed for both administrators and users, it offers a **responsive and intuitive interface**.

---

## 🚀 Features
✅ **User Management**: Registration, login, and account management.
✅ **Transaction Management**: Deposits, withdrawals, and transaction history tracking.
✅ **Admin Control**: Admins can **add, delete, and view customer details**.
✅ **Security**: Robust security measures to **protect sensitive data** and transactions.
✅ **Responsive UI**: A clean, modern interface built with the latest **web technologies**.

---

## 🛠 Technologies Used

| **Category**  | **Technologies** |
|--------------|----------------|
| **Frontend** | HTML, CSS, JavaScript, SweetAlert JS |
| **Backend**  | Java Servlets, JDBC |
| **Database** | MySQL |
| **Web Server** | Apache Tomcat |
| **IDE** | Eclipse |
| **Code Quality** | SonarQube |

---

## ⚙️ Architecture
The **Banking System** follows a **client-server architecture** with a structured database schema for **data integrity** and **security**.

📌 **Architecture Diagram** *(To be added)*
📌 **Class Diagram** *(To be added)*
📌 **Sequence Diagram** *(To be added)*

---

## 📥 Setup and Installation

### **🛠 Prerequisites**
🔹 **Java Development Kit (JDK)**
🔹 **Apache Tomcat Server**
🔹 **MySQL Database**
🔹 **Eclipse IDE**
🔹 **SonarQube**

### **📌 Installation Steps**
1️⃣ **Clone the repository:**
```sh
git clone  https://github.com/MUNNAKUMAR727762/Banking_System.git
```
2️⃣ **Setup the database:**
   - Install MySQL and **create a database**.
   - Import the **provided SQL script** to set up tables.

3️⃣ **Configure the backend:**
   - Open the project in **Eclipse**.
   - Configure the **JDBC connection** in the configuration file.

4️⃣ **Deploy on Apache Tomcat:**
   - Build the project and **deploy the WAR file**.

5️⃣ **Run SonarQube:**
   - Install and configure **SonarQube** for **code quality analysis**.
   - Run analysis to **ensure coding standards**.

---

## 📌 Usage

### **Admin Panel**
👨‍💼 Admins can **register users, manage accounts, and oversee transactions**.

### **User Dashboard**
👤 Users can **log in, check account details, perform transactions, and view transaction history**.

---

## 🧪 Testing
### **🔍 Test Cases**
✔ **Mobile Number Validation**: Ensures input is a valid **10-digit** number.
✔ **Aadhar Number Validation**: Checks for a valid **12-digit** Aadhar number.
✔ **Initial Balance Validation**: Ensures a minimum **₹1000** initial deposit.
✔ **Email Validation**: Verifies that the email **contains '@' and a valid domain**.
✔ **Account Deletion Validation**: Ensures **non-zero balance accounts cannot be deleted**.
✔ **Insufficient Balance Validation**: Prevents transactions if the **balance is insufficient**.

---

## 🤝 Contributing
🔹 Contributions are **welcome**! Feel free to **fork, create issues, and submit PRs**.

---

## 👨‍💻 Author
**Munna Kumarr**   

© 2025 Banking System Project. All rights reserved.
