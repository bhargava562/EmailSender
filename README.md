<h1 align="center">
  <img src="https://img.icons8.com/bubbles/100/000000/new-post.png" alt="Email Icon" width="80" />
  <br>
  Spring Boot Email Sender
</h1>

<p align="center">
  A Java Spring Boot application to send emails with attachments to multiple, individually-selected recipients managed via a simple text file.
</p>

<p align="center">
  <a href="https://github.com/_YOUR_USERNAME_/_YOUR_REPOSITORY_/stargazers">
    <img src="https://img.shields.io/github/stars/_YOUR_USERNAME_/_YOUR_REPOSITORY_?style=for-the-badge&color=ffd000" alt="GitHub stars">
  </a>
  <a href="https://github.com/_YOUR_USERNAME_/_YOUR_REPOSITORY_/network/members">
    <img src="https://img.shields.io/github/forks/_YOUR_USERNAME_/_YOUR_REPOSITORY_?style=for-the-badge&color=8000ff" alt="GitHub forks">
  </a>
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">
</p>

---

## 🎯 Overview

Sharing information within a community or group should be seamless, but managing recipient lists can be a hassle. **Email Sender** is a mini-project built to solve this. It's an efficient Java Spring Boot application that allows a user to send customized emails—with file attachments—to a list of recipients that is dynamically loaded from a simple text file.

This project demonstrates a full-stack, self-contained application using Spring Boot for the backend logic and Thymeleaf for rendering a simple, user-friendly HTML interface.

## ✨ Key Features

* ✅ **Dynamic Recipient Management:** Reads email addresses from a `.txt` file and displays them as a checklist.
* ✅ **Selective & Bulk Messaging:** Gives the user fine-grained control to message individuals or the entire list with a "Select All" option.
* ✅ **File Attachments:** Effortlessly attach files (up to **10MB** by default) to your emails.
* ✅ **User-Friendly Interface:** A simple HTML form for composing the subject and message body.
* ✅ **Secure & Configurable:** Email credentials are kept out of the code and managed in the `application.properties` file for security and ease of setup.

## 📸 Screenshots

<p align="center">
  <strong>Main Interface - Recipient Selection & Composition</strong><br>
  <em><-- Add a screenshot of your main UI here, showing the checkboxes, form fields, and file attachment button. --></em>
  <br><br>
  <strong>Email Sent - Success Message</strong><br>
  <em><-- Add a screenshot of the success confirmation page here. --></em>
</p>

## 🛠️ Technology Stack

| Technology       | Icon                                                                                           | Description                                                    |
| :--------------- | :---------------------------------------------------------------------------------------------: | :------------------------------------------------------------- |
| **Java** | <img src="https://img.icons8.com/color/48/000000/java-coffee-cup-logo.png" alt="Java"/>         | Core programming language and platform.                        |
| **Spring Boot** | <img src="https://img.icons8.com/color/48/000000/spring-logo.png" alt="Spring Boot"/>          | Framework for building the robust backend and web application. |
| **JavaMail API** | <img src="https://img.icons8.com/ios-filled/50/000000/send-mass-email.png" alt="JavaMail"/>    | The underlying API used for sending emails via SMTP protocol.  |
| **Thymeleaf** | <img src="https://img.icons8.com/color/48/000000/thymeleaf.png" alt="Thymeleaf"/>              | A modern server-side Java template engine for rendering the HTML user interface. |
| **Maven** | <img src="https://img.icons8.com/color/48/000000/maven-project.png" alt="Maven"/>              | Dependency management and project build tool.                  |
| **HTML5/CSS** | <img src="https://img.icons8.com/color/48/000000/html-5.png" alt="HTML5"/>                     | For structuring and styling the frontend pages.                |

## 🚀 Getting Started

This project is not publicly deployed and is intended to be run on a local machine. Follow these instructions to get it set up.

### Prerequisites

* Java Development Kit (JDK) 11 or newer
* Apache Maven
* An IDE (like IntelliJ IDEA, Eclipse, or VS Code)
* An active email account (e.g., Gmail) to use as the sender.

### Installation & Configuration

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/_YOUR_USERNAME_/_YOUR_REPOSITORY_.git](https://github.com/_YOUR_USERNAME_/_YOUR_REPOSITORY_.git)
    cd _YOUR_REPOSITORY_
    ```

2.  **Configure Email & Attachment Properties:**
    Open `src/main/resources/application.properties`. This file holds all the necessary configurations. Update it with your sender email credentials and attachment settings.

    ```properties
    # Spring Mail Properties
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=your-email@gmail.com
    spring.mail.password=your-app-password # CRITICAL: Use an App Password, not your regular password!

    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

    # File Upload Properties
    spring.servlet.multipart.enabled=true
    spring.servlet.multipart.max-file-size=10MB
    spring.servlet.multipart.max-request-size=10MB
    ```

    > **🔒 Gmail Security Note:** If you are using a Gmail account, you **must** enable 2-Factor Authentication and generate an **"App Password"**. Using your regular Google account password will fail.
    > [Learn how to create App Passwords here.](https://support.google.com/accounts/answer/185833)

3.  **Create the Recipient List:**
    In the `src/main/resources/` directory, create a file named `emails.txt`. Add one email address per line. The application will read from this file.
    ```txt
    example.user1@domain.com
    test.user2@email.org
    another.recipient@provider.net
    ```

### Running the Application

1.  **Build the project using Maven:**
    ```sh
    mvn clean install
    ```
2.  **Run the Spring Boot application:**
    ```sh
    mvn spring-boot:run
    ```
    Alternatively, you can run the main application class `YourApplicationNameApplication.java` directly from your IDE.

3.  **Access the application:**
    Open your web browser and navigate to `http://localhost:8080`.

## 📂 Project Structure

Here are the key files and directories in the project:
/src<br>
├──/main<br>
│  ├──/java/...         # Main application Java source code (Controllers, Services)<br/>
│  └──/resources<br/>
│     ├──/templates     # HTML files for the UI (e.g., index.html)<br/>
│     ├── emails.txt    # Your list of recipient emails<br/>
│     └── application.properties # Application and email configuration<br/>
└──/test<br/>
pom.xml                 # Maven project configuration<br/>

## 📜 License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

---

<p align="center">
  Developed with ❤️ and Java by Bhargava A
</p>
