# 🚀 Coding Shuttle: Week 5 (PART 1) - Spring Security

Welcome to Week 5 of the Coding Shuttle course! This week, we dive into security features of Spring Boot. Below are the topics we will cover:

---

# 📚 Topics Covered in Week 5
1. Types of Security Attacks
2. Internal Working of Spring Security
3. Core Spring Security Components
4. Configuring default SecurityFilterChain
5. Understanding JWT
6. Login/SignUp using JWT
7. Authenticating requests using JWT
8. Spring Security Exception Handling

---

# 1. Web Security Vulnerabilities: CSRF, XSS, and SQL Injection

This document provides detailed information about three common web security vulnerabilities: Cross-Site Request Forgery (CSRF), Cross-Site Scripting (XSS), and SQL Injection. Each vulnerability is explained with its definition, impacts, and mitigation strategies, along with examples.

---

## 1. Cross-Site Request Forgery (CSRF)

### Definition
CSRF is a type of attack that tricks a user into executing unintended actions on a web application in which they are authenticated. This attack leverages the trust that the web application has in the user's browser.

### How It Works
1. A user logs into a web application.
2. The attacker tricks the user into clicking on a malicious link or visiting a malicious site.
3. The malicious request is sent to the web application using the user’s session cookies.
4. The web application processes the request as if it were legitimate.

### Example
```html
<!-- Malicious HTML -->
<img src="https://example.com/deleteAccount" style="display:none;" />
```
- If a logged-in user visits the attacker’s page, their browser will send a request to `example.com` to delete their account.

### Mitigation
- Use anti-CSRF tokens: Include unique tokens in forms and validate them on the server side.
- Implement SameSite cookies: Ensure cookies are only sent with requests originating from the same site.
- Require re-authentication for sensitive actions.
- Validate the `Referer` and `Origin` headers.

---

## 2. Cross-Site Scripting (XSS)

### Definition
XSS is an injection attack where malicious scripts are injected into trusted web applications. The malicious script is executed in the browser of a victim, potentially compromising their data or accounts.

### Types of XSS
1. **Stored XSS**: The malicious script is permanently stored on the server (e.g., in a database).
2. **Reflected XSS**: The malicious script is embedded in a URL and reflected back in the response.
3. **DOM-Based XSS**: The attack occurs in the client-side JavaScript without involving the server.

### Example
**Vulnerable Code:**
```html
<p>Welcome, <script>document.write(location.search.split('=')[1]);</script>!</p>
```
**Exploit:**
```
https://example.com?name=<script>alert('Hacked');</script>
```
- When the link is visited, an alert box will be displayed, indicating the script was executed.

### Mitigation
- Sanitize inputs: Escape or remove dangerous characters before processing.
- Use content security policy (CSP): Restrict the sources from which scripts can be loaded.
- Encode outputs: Encode data before rendering it in the browser (e.g., HTML entity encoding).
- Avoid using `eval()` and similar functions.

---

## 3. SQL Injection

### Definition
SQL Injection is a vulnerability where an attacker manipulates SQL queries by injecting malicious SQL code into input fields. This can lead to unauthorized data access or database manipulation.

### How It Works
1. An application takes user input and inserts it directly into an SQL query.
2. The attacker crafts malicious input that modifies the SQL query’s logic.

### Example
**Vulnerable Code:**
```sql
SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';
```
**Malicious Input:**
```
username: ' OR 1=1 --
password: anything
```
**Resulting Query:**
```sql
SELECT * FROM users WHERE username = '' OR 1=1 --' AND password = 'anything';
```
- This query always returns true, potentially granting unauthorized access.

### Mitigation
- Use parameterized queries or prepared statements.
- Avoid dynamic SQL: Do not concatenate user input directly into SQL queries.
- Validate inputs: Restrict input to expected formats and lengths.
- Use stored procedures: Encapsulate SQL logic within database procedures.
- Limit database permissions: Ensure the application’s database user has only the necessary privileges.

---

## Conclusion
CSRF, XSS, and SQL Injection are critical vulnerabilities that can compromise the security of web applications. Developers should employ robust validation, encoding, and security mechanisms to mitigate these risks and protect user data.

---

# 2. Internal Working of Spring Security


