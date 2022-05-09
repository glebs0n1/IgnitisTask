# IgnitisTask
Creating working applications for the backend part of communication. (REST-API)
Functional requirements:
Support for two types of users: user, admin
The user can:
1. Receive messages (who, when, what)
2. Record a new message
User admin can:
1. Register a new user
2. Remove the user
3. Extract statistics:
• user
• Number of messages
• The time of the first message
• The time of the last message
• Average message length
• The text of the last message
Basic technical requirements:
1. Use GIT version control. Provide a link to a public Git rap or .zip with
attach the .git directory.
2. Use Java 11+
3. Use Gradle (https://gradle.org/)
4. Use a spring boot
5. Use springfox Swagger2 or springdoc OpenApi3
6. Use the h2 database (https://www.h2database.com)
7. Do not use JDBC querium, use ORM
8. Clean code (eg https://plugins.jetbrains.com/plugin/7973-sonarlint or similar
equivalent)
9. Create at least one automated test
More complex technical requirements (if possible):
1. Use Liquibase (https://www.liquibase.org/)
2. Use JOOQ as an ORM (https://www.jooq.org/)
3. Authentication using JWT
4. Implement error handling using and returning HTTP status codes
5. Create automated tests for all methods in the project by integrating and
using the Groovy (https://groovy-lang.org/) programming language
6. Present the result ready for installation in a production environment
7. Select data types and justify them
