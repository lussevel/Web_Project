# Web-based Online Suggestion and Feedback System for the College of Science

The **Web-based Online Suggestion and Feedback System** for the College of Science is an academic web application developed for the subject **Web Systems and Technologies**.

The system provides a structured platform for collecting, managing, and analyzing suggestions and feedback within the College of Science.

Users can submit feedback, optionally remain anonymous, vote on existing concerns, and view submitted feedback. Administrators are able to manage feedback entries, monitor their status, and analyze trends through a dashboard to support informed decision-making.

---

## Features
- User login authentication  
- Feedback submission with category selection  
- Optional anonymous feedback submission  
- Voting on existing feedback  
- Viewing submitted feedback  
- Admin dashboard  
- Feedback management and analytics  
- Admin account management  

---

## Tech Stack
- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, JavaScript  
- **Database:** MySQL
- **Server:**   Spring Boot Embedded Tomcat

---

## 🗄️ Database Setup

The system uses a MySQL database to store application data.  
A SQL file is provided in the repository to initialize the required tables.

### Setup Instructions
1. Create a database in your local MySQL server.
2. Import the provided `db.sql` file into the created database.
3. Open the Spring Boot configuration file located at:  
   `/src/main/resources/application.properties`
4. Update the database credentials to match your local setup:

spring.datasource.username= sia_system.db  
spring.datasource.password= CCS-L1  

Once the database is imported and the configuration is updated, the backend application can successfully connect to the database.

---

## How to Run
1. Install XAMPP  
2. Start Apache and MySQL  
3. Place the project folder inside the htdocs directory  
4. Import the SQL file into phpMyAdmin  
5. Configure the database connection  
6.After running the application, the system can be accessed through the following URLs:

### Administrator Access
- Admin Login Page:  
  http://localhost:8080/admin/login  

This page allows administrators to log in and manage system-related data.

### Student Access
- Student Interface:  
  http://localhost:8080/user  

This page allows students to access the system and interact with the feedback features.

---

## Scope and Limitations
- Academic use only
- Authorized admins only
- Event attendance management only
- Requires local database and server

---

## Team Members
Backend Developers:  
- Elcy D. Moyar  
- Eloisa A. Valen  

Frontend Developers:  
- Chares T. Brences  
- April Lyssa Dugan  
- Alejandra Shan Recare  

---

- *Subject:* Web Systems and Technologies  
- *Project Type:* Web-based Information System  
- *Purpose:* Academic Requirement


## 📌 Purpose of Meaningful Commits of Each Member

Providing five meaningful commits demonstrates the development process of the system. Each commit represents a clear and intentional change, allowing progress, responsibility, and proper use of version control to be evaluated throughout the project.


/* Created by: Elcy Moyar D. */ (admin-scripts.js)
/* This Admin Feedback script manages the modal functionality for viewing and updating feedback. 
It allows admins to open a modal by clicking a feedback row, review details, update status, 
and send replies to the backend via fetch. The script also updates the table and analytics 
counters dynamically without refreshing the page. */

/* Created by: Elcy Moyar D. */ (AdminLogin.css)
/* This stylesheet defines the layout and design
   for the Admin Login and Create Account pages.
   It includes reset rules, responsive layout,
   container styling, headers, form inputs,
   buttons, and navigation links. */

/* Created by: Elcy Moyar D. */ (UserLogin.html) 
/* This Confidential Feedback Portal page introduces the College of Science's secure feedback system. 
  It displays the college and department logos, a title section, and a trust statement block 
  assuring students of privacy and anonymity. The page encourages participation with a call-to-action 
  button that links to the feedback submission form. Styling ensures responsive layout and 
  professional presentation across devices. */

/* Created by: Elcy Moyar D. */ (feedback.css)
/* This stylesheet defines the layout and design
   for the Student Feedback Form page. It includes
   body layout, container styling, headings,
   form inputs, labels, checkboxes, and submit button. */

/* Created by: Elcy Moyar D. (Voting.html)
/* This Voting page allows users to prioritize feedback items by casting a single vote. 
   It displays a list of submitted feedback with category, title, description, and current vote count. 
   Users can select one top priority issue using radio buttons and submit their choice. 
   Thymeleaf expressions dynamically bind feedback data from the backend. 
   The layout includes instructions, conditional rendering for empty lists, and a styled submit button. */

<!-- RECARE, ALEJANDRA SHAN T. --> (FeedbackList.html)
<!-- This page displays submitted feedback entries in an organized list format.
It allows users or administrators to view feedback details clearly,
supporting efficient review and monitoring of feedback records. -->

<!-- RECARE, ALEJANDRA SHAN T. --> (ResetPassword.html)
<!-- This page allows authorized users to update their account password securely.
It provides a simple form interface to ensure credentials are changed safely
and helps maintain account security within the system. -->

<!-- RECARE, ALEJANDRA SHAN T. --> (UserFeedback.html)
<!-- This page allows users to submit feedback to the system.
It includes input fields for category selection, title, and description,
enabling users to clearly communicate their concerns or suggestions. -->

<!-- RECARE, ALEJANDRA SHAN T. --> (accounts.css)
<!-- This stylesheet styles the Accounts page, focusing on layout consistency,
table readability, and overall visual clarity.-->

!-- RECARE, ALEJANDRA SHAN T. --> (Accounts.html)
<!--This page manages administrative account information within the system.
It displays a list of admin accounts and provides options for viewing and maintaining account records.
The layout supports organized presentation of account details. -->

<!--BRENCES CHARES T. -->  (AdminLogin.html)
<!--This page is the main entrance for administrators to get into the system. It asks for a username and password to make sure only the right people can see private data. 
If the info is wrong, it shows an error message to help the user try again.-->

<!--BRENCES CHARES T. -->  (AdminCreateAccount.html)
 <!--This page is a signup form for new administrators. It collects their details and makes sure they pick a strong password to keep the system safe.
 It also shows a message to let them know if their account was created successfully or if there was an error.-->

 <!-- BRENCES CHARES T. -->  (AdminDashboard.html)
<!--This page is the main dashboard where admins can see an overview of all feedback. It shows quick stats like the total number of reports and how many are resolved,
 while the table allows you to filter, view details, and update the status of each message.-->

<!--BRENCES CHARES T. -->  (ThankU.html)
 <!--This page is a confirmation screen that appears after a user submits their feedback. It thanks the user for their input,
 confirms that their submission was anonymous, and provides a simple link to return to the home page.-->

<!--BRENCES CHARES T. -->  (Logout.html)
<!--This page is a secure logout script that ends the current session. 
It clears all stored admin data and redirects the user back to the login page to ensure no one else can access the account.-->

<!-- DUGAN, APRIL LYSSA P. (AdminEditAccount.html) -->
<!-- This page allows administrators to edit existing admin account details.
It displays a form pre-filled with selected admin information such as name, username, and email.
The page supports validation messages and confirms successful updates. -->

<!-- DUGAN, APRIL LYSSA P. (Analytics.html) -->
<!-- This page displays analytics and statistics related to system feedback.
It shows feedback distribution by category, overall counts, and voting summaries.
The layout helps helps administrators analyze system usage and feedback trends.-->

/* DUGAN, APRIL LYSSA P. (admindashboard.css) */
/*This stylesheet controls the layout and visual design of the admin dashboard.
It defines the sidebar, dashboard cards, tables, filters, and responsive behavior.
The file focuses purely on styling and does not affect backend functionality. */

/* DUGAN, APRIL LYSSA P. (voting.css) */
/* This stylesheet styles the feedback voting interface for users.
It formats feedback cards, radio button selection, and submit actions.
The design improves clarity, accessibility, and user interaction during voting. */

## 📘 Purpose of the README

The `README.md` file was created to provide a clear overview of the system, including its purpose, setup instructions, and access points. It serves as a guide for users, developers, and evaluators to understand how the system works and how it can be properly run and tested.

This documentation helps ensure consistency, ease of maintenance, and proper evaluation of the project.



