Self-notes: If I ever need to redeploy
1. add file to ./backend/src/main/resources called application-secret.properties
```
spring.datasource.username=root
spring.datasource.password=Applemon236!
recaptcha.secret=6Lfi-tApAAAAAD36ZclDmYDBAC1T0_ATQzXmD3CN
```
2. change file at ./docker/.env
```
MYSQL_ROOT_PASSWORD=Applemon236!
```
3. add to file at ./frontend/.env.production
```
VITE_BACKEND_URL=http://localhost:8080/api
```
4. go in moviedb container and run this command:
```
UPDATE customers SET password = '$2a$10$wAvhJKN8WeZzSc.LFAeZ4uubVsO1wCX5U2YoiY/l/OD5A8VuvySjS' WHERE email = 'b@email.com';
INSERT INTO employees (email, password, full_name) VALUES ('admin@filmflicks.com', '$2b$12$bFt.bdrNleh1PvmcPJu3bOuDNc3rphash0AyHAZGdrtfcYNL7wRwi', 'Admin User');
```
the encrypted password is admin123
5. You might need to look at the files  ./backend/src/main/java/resources/application-properties and ./backend/src/main/java/com/filmflicks/security/SecurityConfig