# Plant Inventory Management System (PIMS)

The Plant Inventory Management System (PIMS) is a full-stack web application developed for Verdant Roots Nursery to manage inventory, monitor stock levels, and generate reports.

The application provides a centralized and user-friendly solution for tracking plants, supplies, and locally sourced goods.

---

## Live Application

The application is deployed and accessible at:

https://vrn-pims.up.railway.app

Users can access the system directly through a web browser without any local setup.

---

## Demo Accounts

### Employee
- Username: employee
- Password: employee123

### Manager
- Username: manager
- Password: manager123

### Admin
- Username: admin
- Password: admin123

---

## Features

- Inventory Management (Create, Read, Update, Delete)
- Search and Filtering Across Inventory
- Reporting System:
    - Low Stock Report
    - Inventory by Location
    - Expiring Local Goods
- Low Stock Indicators
- Role-Based Access Control
- Real-Time Inventory Visibility
- Print-Friendly Reports

---

## Technology Stack

- Backend: Spring Boot (Java)
- Frontend: Thymeleaf, HTML, CSS
- Database: MySQL (Railway Hosted)
- Deployment: Railway
- Version Control: Git (GitHub & GitLab)

---

## Validation and Error Handling

The application enforces validation rules across all inventory forms:

- Required fields must be completed before submission
- Invalid inputs (such as negative values) are rejected
- Inline error messages are displayed directly on the form
- Invalid data is not saved to the database

---

## Running Locally (Optional)

The application is fully deployed and can be accessed online.  
However, developers may run the application locally for testing or development.

### Requirements
- Java 17
- Maven
- IntelliJ IDEA
- MySQL (optional if using hosted database)

### Steps

1. Clone the repository:
   ```bash
   git clone <https://gitlab.com/wgu-gitlab-environment/student-repos/aal1206/d424-software-engineering-capstone.git>