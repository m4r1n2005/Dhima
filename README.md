# Media Ratings Platform (MRP)

## 1. Project Description

Media Ratings Platform (MRP) is a standalone Java REST API developed for the Software Engineering 1 course at FH Technikum Wien.

The intermediate submission implements user registration, login, profile management, and create/read/update/delete (CRUD) operations for movies, series, and games.

**GitHub repository:** https://github.com/m4r1n2005/Dhima

## 2. Technologies

- Java 27 and Maven
- Java built-in `HttpServer` (no full-stack web framework)
- PostgreSQL running in Docker
- JDBC with the PostgreSQL driver
- Jackson for JSON requests and responses
- Postman for manual HTTP API testing

## 3. Architecture

The application uses a layered architecture:

- **Domain (`domain`):** entities such as `User`, `Media`, `Movie`, `Serie`, `Game`, and `Rating`.
- **Data (`data`):** repository interfaces and implementations, JDBC persistence, `DatabaseManager`, and `UnitOfWork` for database transactions.
- **Business (`business`):** services for application logic, registration, password verification, token management, and media operations.
- **Presentation (`presentation`):** controllers and request/response DTOs.
- **REST server (`restserver`):** HTTP server, handlers, request parsing, and HTTP responses.

The project uses the Repository pattern, interfaces, and Singleton instances in its application layers.

## 4. Database Setup

PostgreSQL must run inside Docker. The Java application's database connection settings are:

| Setting | Value |
|---|---|
| Host | `localhost` |
| PostgreSQL port | `5432` |
| Database | `mrp` |
| User | `postgres` |
| Password | Environment variable `MRP_DB_PASSWORD` |

The table-creation SQL is in the separate **`database.sql`** file in the project root.

### Create a new PostgreSQL container (first-time setup only)

In a terminal in the project folder, run:

```powershell
docker run --name mrp-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=CHANGE_ME -e POSTGRES_DB=mrp -p 5432:5432 -d postgres:16
```

Replace `CHANGE_ME` with a password of your choice. **Use the same password for `MRP_DB_PASSWORD` in the Java run configuration.** This command is an example for a *new* setup; if you already have a working PostgreSQL container, keep using it instead of creating another one.

### Create the database tables

After PostgreSQL is running, run these commands from the folder containing `database.sql`:

```powershell
docker cp .\database.sql mrp-postgres:/database.sql
docker exec mrp-postgres psql -U postgres -d mrp -f /database.sql
```

These commands assume the container was named `mrp-postgres`. If you have an existing container with a different name, replace `mrp-postgres` with that name. The SQL script creates the `users` and `media` tables if they do not already exist; it does not delete existing records.

For subsequent runs of a stopped container created with the command above:

```powershell
docker start mrp-postgres
```

## 5. Running the Java Application

1. Install **JDK 27**, Maven, and Docker.
2. Start the PostgreSQL container and make sure the `mrp` database and tables exist.
3. Open the Maven project in IntelliJ IDEA and load the dependencies from `pom.xml`.
4. Open **Run > Edit Configurations**, select the configuration for `org.example.app.Main`, and add the environment variable `MRP_DB_PASSWORD` with the PostgreSQL password.
5. Run `org.example.app.Main`.

The server listens at **`http://localhost:8080`**.

## 6. Implemented API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register a user |
| POST | `/api/users/login` | Log in and receive a token |
| GET | `/api/users/{userId}/profile` | Get the authenticated user's profile |
| PUT | `/api/users/{userId}/profile` | Update the authenticated user's profile |
| GET | `/api/media` | List all media entries |
| GET | `/api/media/{mediaId}` | Get media by ID |
| POST | `/api/media` | Create a media entry |
| PUT | `/api/media/{mediaId}` | Update a media entry |
| DELETE | `/api/media/{mediaId}` | Delete a media entry |

## 7. Authentication and Authorization

A successful login returns a UUID token. Protected requests must include:

```http
Authorization: Bearer YOUR_LOGIN_TOKEN
```

Users may retrieve and update only their own profiles. Authenticated users may create media, but only the creator may update or delete a media entry.

Passwords are stored as salted **PBKDF2-HMAC-SHA256** hashes. Login tokens are held in server memory and become invalid after a server restart; log in again to obtain a new token.

## 8. Postman Testing

The original assignment collection is included as **`MRP_Postman_Collection.json`**. Import this file into Postman and use the server address `http://localhost:8080`.

The intermediate endpoints were manually tested for registration, login, profile management, media CRUD, and token-based authorization. Obtain a current Bearer token by logging in before sending protected requests. Use an existing media ID for requests targeting `/api/media/{mediaId}`.

The professor's collection also includes endpoints and query parameters planned for the final submission. Those features are not all implemented in the intermediate version. In particular, `GET /api/media` currently returns all media and does not apply the provided search/filter parameters.

These Postman checks are **manual API tests**, not automated unit tests.

## 9. Intermediate Submission Scope

The intermediate submission includes the standalone HTTP server, user registration/login, token handling, user profiles, media CRUD, and PostgreSQL persistence for users and media.

The complete rating system, favorites API, filtering/sorting, recommendations, statistics, and leaderboard are planned for the final submission. The development protocol is provided separately from this README.
