# Media Ratings Platform (MRP)
# Development Protocol – Intermediate Submission

**Course:** Software Engineering 1  
**University:** FH Technikum Wien  
**Project:** Media Ratings Platform (MRP)  
**Submission:** Intermediate Submission (Class 13)  
**Date:** 13 November 2026  
**GitHub Repository:** https://github.com/m4r1n2005/Dhima

---

## 1. Project Overview

The Media Ratings Platform (MRP) is a standalone Java REST API for managing media entries, including movies, series, and games. Users can register, log in, manage their profiles, and perform CRUD operations on media entries.

The intermediate submission focuses on the HTTP server, user management, token-based authentication, media CRUD operations, and PostgreSQL persistence.

Additional functionality, including ratings, favorites, advanced filtering, recommendations, and leaderboards, forms part of the final submission.

The application was developed using Java 27, Maven, PostgreSQL, Docker, JDBC, Jackson, and Java's built-in HTTP server. No full-stack web framework is used.

## 2. Development Process and Technical Steps

### 2.1 Project Initialization

Development began by creating a Maven-based Java project and organizing its source code into separate packages according to their responsibilities.

The first implementation steps involved creating the domain classes, repository and service interfaces, controllers, and the basic HTTP server structure.

The source code is maintained in GitHub, with commits documenting the implementation and subsequent changes.

### 2.2 Layered Architecture

The application follows a layered architecture consisting of five main components.

| Layer | Responsibility |
|---|---|
| Domain | Represents application entities and domain exceptions |
| Data | Handles database access, repository operations, and transactions |
| Business | Implements authentication, authorization-related logic, and application operations |
| Presentation | Provides controllers and data transfer objects |
| REST Server | Receives HTTP requests, dispatches operations, and sends responses |

The main domain classes are `User`, `Media`, `Movie`, `Serie`, `Game`, and `Rating`.

The `Media` class is abstract. `Movie`, `Serie`, and `Game` inherit from it and provide their respective media types.

Repository interfaces separate data access from business logic. Controllers act as an intermediate layer between business services and HTTP handlers.

The `ApplicationConfig` class initializes and connects repositories, services, controllers, and handlers through dependency injection.

Many of these components use the Singleton pattern, with private constructors and static `getInstance()` methods, to reuse their instances throughout the application.

**Architecture decision:** Layer separation was selected to improve maintainability, reduce dependencies between application components, and allow individual parts to be tested independently.

### 2.3 HTTP Server Implementation

The REST server was implemented using Java's built-in `com.sun.net.httpserver.HttpServer`.

The application starts in `org.example.app.Main`, creates the application configuration, initializes the server, and starts listening on port `8080`.

Two main HTTP contexts are registered:

- `/api/users` – User registration, login, and profile management.
- `/api/media` – Media retrieval and CRUD operations.

The `UserHandler` and `MediaHandler` process HTTP requests and forward operations to the appropriate controllers.

Custom `Request` and `Response` classes provide an abstraction for working with HTTP requests and responses.

Jackson's `ObjectMapper` is used to deserialize incoming JSON requests and serialize response objects.

The application uses standard HTTP methods and appropriate response codes, including `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request`, `401 Unauthorized`, `403 Forbidden`, `404 Not Found`, and `409 Conflict`, depending on the operation.

**Architecture decision:** The built-in HTTP server was selected to satisfy the requirement for a standalone server without introducing Spring or another full-stack framework.

### 2.4 PostgreSQL Database Integration

PostgreSQL runs inside a Docker container. The application connects to the `mrp` database using JDBC and the PostgreSQL driver.

The connection settings are:

| Setting | Value |
|---|---|
| Host | localhost |
| Port | 5432 |
| Database | mrp |
| Username | postgres |
| Password | Environment variable `MRP_DB_PASSWORD` |

The database contains the following eight tables:

| Table | Purpose |
|---|---|
| `users` | User accounts, password hashes, and profile information |
| `media` | Media information and creator references |
| `genres` | Individual genre records |
| `media_genres` | Relationships between media and genres |
| `ratings` | User ratings and comments |
| `favorites` | Users' favorite media entries |
| `rating_likes` | Relationships between users and liked ratings |
| `auth_tokens` | Schema for persistent authentication tokens |

For the intermediate version, the Java `UserRepositoryImpl` and `MediaRepositoryImpl` classes persist user and media information to PostgreSQL.

The remaining tables are included in the database schema for the broader application but are not yet fully integrated into the intermediate REST implementation.

The `media` table includes a PostgreSQL `TEXT[]` column for storing genres. The media repository maps this field to a Java `List<String>` using JDBC SQL array functionality.

Primary keys, foreign keys, uniqueness constraints, and validation constraints are defined in the database schema.

**Architecture decision:** PostgreSQL was used for persistent storage, while JDBC was chosen to provide direct control over SQL operations.

### 2.5 Database Transactions

Database connections are obtained through `DatabaseManager`.

The `UnitOfWork` class manages database transactions and implements `AutoCloseable`.

When a database operation begins, auto-commit is disabled. Successful write operations explicitly commit their changes.

If a transaction is not committed or explicitly rolled back, `UnitOfWork.close()` attempts to roll it back before closing the connection.

The repository implementations use Java's try-with-resources statement to ensure that resources are released when their scope ends.

Prepared statements are used for SQL operations, separating SQL commands from their parameter values.

**Architecture decision:** A dedicated transaction-management component was introduced to centralize transaction handling and reduce the risk of uncommitted database operations and unclosed connections.

### 2.6 User Registration and Authentication

The intermediate implementation supports user registration, login, profile retrieval, and profile updating.

During registration, the application validates the provided credentials, checks whether the username is already registered, and stores the new user.

Passwords are not stored in plaintext. The `PasswordHasher` class uses PBKDF2-HMAC-SHA256 with a randomly generated 16-byte salt, 600,000 iterations, and a 256-bit derived key.

During login, the supplied password is verified against the stored password hash.

When authentication succeeds, the `AuthServiceImpl` generates a UUID token and associates it with the user's ID.

Protected requests must include the following HTTP header:

`Authorization: Bearer YOUR_LOGIN_TOKEN`

The HTTP handlers validate the token and determine whether the authenticated user is authorized to perform the requested action.

Users can retrieve and update only their own profiles. Media modification and deletion are restricted to the user who created the media entry.

Authentication tokens are currently maintained in an in-memory `HashMap`. They become invalid when the server restarts.

**Architecture decision:** UUID-based Bearer tokens provide a straightforward authentication mechanism for the intermediate implementation. Password hashing protects stored credentials, while authorization checks restrict access to protected resources.

### 2.7 Media CRUD Implementation

The media module supports creating, retrieving, updating, and deleting media entries.

The implemented endpoints are:

| Method | Endpoint | Function |
|---|---|---|
| POST | `/api/users/register` | Register a user |
| POST | `/api/users/login` | Authenticate and return a token |
| GET | `/api/users/{userId}/profile` | Retrieve a user profile |
| PUT | `/api/users/{userId}/profile` | Update a user profile |
| GET | `/api/media` | Retrieve media entries |
| GET | `/api/media/{mediaId}` | Retrieve an individual media entry |
| POST | `/api/media` | Create a media entry |
| PUT | `/api/media/{mediaId}` | Update a media entry |
| DELETE | `/api/media/{mediaId}` | Delete a media entry |

For media creation, the HTTP handler converts incoming JSON data into a `Movie`, `Serie`, or `Game` object.

The media repository stores the resulting object in PostgreSQL and retrieves the generated media ID.

When media is read from PostgreSQL, the repository reconstructs the appropriate Java subclass based on the stored `media_type`.

Media updates modify the permitted fields while preserving the original creator ID.

Media deletion removes the corresponding record from PostgreSQL.

**Architecture decision:** Media inheritance was used to share common attributes and behavior while representing different media categories through separate subclasses.

## 3. Testing and Verification

### 3.1 Manual API Testing

The implemented REST endpoints were manually tested using Postman.

The original assignment Postman collection was used without requiring modifications to the professor-provided request definitions.

Testing included both successful requests and authorization-related error cases.

The following results were observed during intermediate testing:

| Test case | Observed HTTP status | Result |
|---|---|---|
| Register user | 201 Created | Successful |
| Log in | 200 OK | Successful |
| Retrieve own profile | 200 OK | Successful |
| Update own profile | 200 OK | Successful |
| Create media | 201 Created | Successful |
| Retrieve media | 200 OK | Successful |
| Update media | 200 OK | Successful |
| Delete media | 204 No Content | Successful |
| Access protected endpoint without token | 401 Unauthorized | Successful rejection |
| Modify another user's media | 403 Forbidden | Successful rejection |

The manual tests confirmed that the core user and media operations could be executed through the HTTP API and that the tested authorization restrictions were enforced.

A database reset was also performed during testing to obtain a consistent state for registration and media operations.

### 3.2 Automated Unit Test Coverage

The assignment specification requires at least 20 automated unit tests, including tests of authorization, filtering, recommendations, and business rules.

**Current verified status:** No automated unit test classes were present in the GitHub repository snapshot reviewed for this intermediate protocol. The manual Postman tests described above do not count as automated unit tests.

The following automated-test coverage is identified for implementation:

| Test area | Planned tests | Purpose |
|---|---:|---|
| Registration and login | 5 | Validate successful registration, duplicate usernames, invalid credentials, password verification, and successful authentication |
| Authentication and authorization | 4 | Check missing tokens, invalid tokens, access to another user's profile, and media ownership restrictions |
| Media business operations | 6 | Verify creation, retrieval, updating, deletion, invalid IDs, and missing media |
| Repository and persistence behavior | 3 | Verify stored and retrieved values, SQL array mapping, and transaction behavior |
| Filtering and recommendations | 2 | Verify filtering conditions and recommendation logic when the final features are implemented |
| **Total planned** | **20** | **Not yet implemented as automated tests** |

The selected test areas focus on logic that can affect correctness, data integrity, and access control.

Authorization tests are particularly important because a valid token must not automatically grant access to another user's protected resources.

Persistence tests are important because the database representation must remain consistent with the Java domain objects.

Filtering and recommendation tests will become important as the remaining business functionality is integrated.

### 3.3 Testing Limitations

The intermediate implementation does not yet expose the full ratings, favorites, recommendations, statistics, or leaderboard API.

Requests for rating history and favorites returned `404 Not Found` during testing because the corresponding endpoints were not implemented.

The media-list endpoint currently returns media entries without applying the search and filtering query parameters from the complete Postman collection.

Therefore, an HTTP `200 OK` response from a filtering request does not demonstrate that filtering has been implemented correctly.

These limitations are documented rather than counted as successful functionality.

## 4. Problems Encountered and Solutions

### 4.1 HTTP Port Incompatibility

**Problem:** During development, the HTTP server used port `5000`, while the Postman collection expected port `8080`.

**Solution:** The server initialization in `Main.java` was changed to use port `8080`.

**Result:** The Java server address now matches the address used by the assignment's API requests.

### 4.2 Media Type Compatibility

**Problem:** The provided Postman requests used lowercase media types such as `"movie"`, while the Java domain model uses `"Movie"`, `"Serie"`, and `"Game"`.

**Solution:** The media HTTP handler was updated to accept both lowercase and capitalized representations and create the appropriate Java subclass.

**Result:** The API can accept the provided request values while the database retains the media type representation expected by the Java repository.

### 4.3 PostgreSQL Integration and Data Mapping

**Problem:** Domain objects and PostgreSQL records require different representations, particularly for media genres.

**Solution:** The media repository uses JDBC's `createArrayOf("text", ...)` for storing genres and reconstructs Java lists from SQL arrays when reading records.

**Result:** Genre lists can be persisted and retrieved through PostgreSQL.

### 4.4 Database Schema Consistency

**Problem:** The SQL documentation needed to reproduce the existing database structure accurately, including the correct nullability, identity columns, primary keys, foreign keys, and constraints.

**Solution:** The database definitions were inspected directly using PostgreSQL's `\d` command for all eight tables. The separate `database.sql` file was corrected to reflect the inspected structure, including the nullable `genres` array in the `media` table.

**Result:** The database schema can be documented using the actual PostgreSQL table definitions instead of relying only on assumptions derived from the Java classes.

### 4.5 Authentication and Ownership Restrictions

**Problem:** Authentication alone is not sufficient to determine whether a user may update or delete a particular resource.

**Solution:** The HTTP handlers validate Bearer tokens and compare the authenticated user's ID with the relevant profile or media creator ID.

**Result:** Manual testing confirmed that requests without valid authentication were rejected with `401 Unauthorized`, and unauthorized modification of another user's media was rejected with `403 Forbidden`.

### 4.6 Separation Between Intermediate and Final Features

**Problem:** The complete assignment and Postman collection include functionality beyond the intermediate submission scope.

**Solution:** Development was prioritized around the HTTP server, registration/login, token handling, media CRUD, and PostgreSQL persistence.

**Result:** The implemented intermediate functionality is separated from features that remain incomplete. The latter are explicitly identified rather than represented as finished.

## 5. Estimated Development Time

The table below contains **provisional retrospective estimates**, not a measured time log. These values must be checked against the time actually invested before final submission.

| Development area | Estimated hours |
|---|---:|
| Requirements analysis and project planning | 3 |
| Domain classes and layered architecture | 6 |
| HTTP server, request/response handling, and routing | 7 |
| Docker, PostgreSQL schema, JDBC, and transactions | 6 |
| User management, password hashing, and authentication | 7 |
| Media CRUD and repository integration | 7 |
| Manual Postman testing and debugging | 5 |
| README, database script, and development documentation | 3 |
| **Total estimated effort** | **44 hours** |

The estimates cover the main areas of the intermediate implementation. Git commit timestamps provide additional evidence of development progression but do not independently establish the number of hours worked.

## 6. Version Control and Development History

Git and GitHub were used to manage the project source code.

The repository history includes the following major milestones:

| Date | Development milestone |
|---|---|
| 22 September 2026 | Initial repository commit |
| 28 September 2026 | Initial architecture and classes |
| 6 October 2026 | Layered architecture and basic API |
| 20 October 2026 | HTTP server development |
| 11 November 2026 | Database connection and user repository development |
| 12 November 2026 | User repository and password hashing |
| 13 November 2026 | User module, media repository, and intermediate functionality |
| 13 November 2026 | HTTP port adjustment and endpoint compatibility corrections |

The complete Git history is available at:

https://github.com/m4r1n2005/Dhima

The repository history is part of the project documentation and is not reproduced commit by commit in this protocol.

## 7. Current Project Status and Remaining Work

The intermediate implementation provides a standalone Java HTTP server with PostgreSQL-backed user and media operations, password hashing, Bearer-token authentication, and ownership restrictions.

The main features remaining for the final submission are:

- Full rating and commenting functionality
- Favorite media management through the REST API
- Search, filtering, and sorting
- Recommendation logic and API endpoints
- Statistics and leaderboard functionality
- Integration of remaining persistent functionality with PostgreSQL
- Automated unit tests meeting the assignment's coverage requirements

The current authentication implementation stores login tokens in memory rather than using the existing `auth_tokens` database table.

The rating and favorites repositories also currently use in-memory collections, despite the corresponding database tables being present.

These differences between the database schema and the active Java persistence implementation are known limitations of the intermediate version.

## 8. Conclusion

The intermediate MRP project demonstrates a layered Java REST API using Java's built-in HTTP server, PostgreSQL, JDBC, and token-based authentication.

The principal intermediate functions—registration, login, profile management, and media CRUD—have been implemented and manually verified through Postman.

The project separates domain logic, persistence, business services, controllers, and HTTP handling into distinct components.

The remaining work consists primarily of completing the advanced business functionality, integrating the remaining persistence components, and implementing the required automated test suite.

The source code, README, Postman collection, database schema, and Git history provide supporting documentation for the current implementation.