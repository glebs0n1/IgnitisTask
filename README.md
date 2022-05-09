# IgnitisTask
Creating working applications for the backend part of communication. (REST-API)
<h2>Provided 2 types of users: admin and regular user</h2>

Functional requirements:
Support for two types of users: user, admin
<bold>The user can:</bold>
 Receive messages (who, when, what)
 Record a new message
<bold>User admin can:</bold>
 Register a new user
 Remove the user
 <bold>Extract statistics:</bold>
• user
• Number of messages
• The time of the first message
• The time of the last message
• Average message length
• The text of the last message
<h3>Tech stack:</h3>
Basic technical requirements:
 Use GIT version control. Provide a link to a public Git rap or .zip with
attach the .git directory.
 Use Java 11+
 Use Gradle (https://gradle.org/)
 Use a spring boot
 Use springfox Swagger2 or springdoc OpenApi3
 Use the h2 database (https://www.h2database.com)
 Do not use JDBC querium, use ORM
 Clean code (eg https://plugins.jetbrains.com/plugin/7973-sonarlint or similar
equivalent)
 Create at least one automated test
<h3>More complex technical requirements (if possible):</h3>
1. Use Liquibase (https://www.liquibase.org/)
2. Use JOOQ as an ORM (https://www.jooq.org/)
3. Authentication using JWT
4. Implement error handling using and returning HTTP status codes
5. Create automated tests for all methods in the project by integrating and
using the Groovy (https://groovy-lang.org/) programming language
6. Present the result ready for installation in a production environment
7. Select data types and justify them
