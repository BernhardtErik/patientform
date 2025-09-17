# Patient Form API

A simple Spring Boot REST API for managing patients.

Server default port: 8081 (see src/main/resources/application.properties).

## Quick start

- Java 17+
- Maven 3.9+
- PostgreSQL running locally with a database `patients` and credentials configured in application.properties

Commands:
- Build: `./mvnw clean package`
- Run: `./mvnw spring-boot:run`

Health check: GET http://localhost:8081/patients/ping -> `ok`

## Endpoints

Base URL: `http://localhost:8081`
All responses are JSON. CORS allows http://localhost:3000 by default.

- GET /patients
  - Description: List all patients
  - Response: 200 OK, array of PatientResponseDto

- GET /patients/search?query={q}
  - Description: Search by firstName, lastName, email, phoneNumber, idNumber, or address. If `query` is missing or blank, returns all patients.
  - Response: 200 OK, array of PatientResponseDto

- GET /patients/{id}
  - Description: Get a single patient by id
  - Responses: 200 OK with PatientResponseDto, or 404 Not Found

- POST /patients
  - Description: Create a new patient
  - Request body (PatientRequestDto):
    {
      "firstName": "Jane",
      "lastName": "Doe",
      "email": "jane.doe@example.com",
      "phoneNumber": "+1-555-1234",
      "dateOfBirth": "1990-05-20", // optional; defaults to today if null
      "idNumber": "9005201234088",
      "address": "123 Main Street",
      "medicalAid": "HealthPlus",
      "medicalHistory": "...",
      "allergies": "...",
      "currentMedication": "..."
    }
  - Responses: 201 Created with body PatientResponseDto and `Location: /patients/{id}`

- PUT /patients/{id}
  - Description: Update an existing patient (fields set to null will be ignored; behaves like partial update)
  - Request body: PatientRequestDto
  - Responses: 200 OK with updated PatientResponseDto, or 404 Not Found

- DELETE /patients/{id}
  - Description: Delete a patient
  - Responses: 204 No Content, or 404 Not Found

## Data models

PatientRequestDto:
- firstName, lastName, email, phoneNumber, dateOfBirth (LocalDate), idNumber, address, medicalAid, medicalHistory, allergies, currentMedication

PatientResponseDto: all of the above plus
- id (Long), createdAt (OffsetDateTime), updatedAt (OffsetDateTime)

Notes:
- Entity auto-populates createdAt/updatedAt. If dateOfBirth is not provided on create, it defaults to current date.

## Example requests

Use the provided IntelliJ HTTP Client file: `generated-requests.http` at project root. Make sure host is http://localhost:8081.

Examples include:
- Ping
- List all
- Search
- Create and capture {id}
- Get by id
- Update
- Delete and verify 404

## Frontend integration

If calling from a web app, set your API base to `http://localhost:8081`.
CORS is already configured to allow `http://localhost:3000` GET/POST/PUT/PATCH/DELETE.

## Install Docker

To run the local PostgreSQL database with Docker, first install Docker on your machine.

- Windows 10/11 (recommended):
  - Install Docker Desktop: https://www.docker.com/products/docker-desktop/
  - Ensure WSL 2 is enabled (Docker Desktop installer can set this up). Restart when prompted.
  - Start Docker Desktop and wait until it shows “Docker engine is running”.
  - Verify in a terminal (PowerShell):
    - docker --version
    - docker compose version

- macOS (Intel or Apple Silicon):
  - Install Docker Desktop for Mac: https://www.docker.com/products/docker-desktop/
  - After installation, launch Docker Desktop and wait for it to start.
  - Verify in Terminal:
    - docker --version
    - docker compose version

- Linux (example: Ubuntu):
  - Install Docker Engine and Compose Plugin (one option):
    - sudo apt-get update && sudo apt-get install -y docker.io docker-compose-plugin
  - Add your user to the docker group (optional to run without sudo):
    - sudo usermod -aG docker "$USER" && newgrp docker
  - Verify:
    - docker --version
    - docker compose version

After Docker is installed and running, you can start the project database with Docker Compose from the project root:
- docker compose up -d

If you see permission or connectivity issues, ensure Docker is running and your user has permission to run docker commands.

## Database

- Schema/table is created/updated automatically by Hibernate (ddl-auto=update). A schema.sql is also included and executed at startup (spring.sql.init.mode=always).

### Run PostgreSQL locally with Docker

This project includes a Docker Compose file to spin up PostgreSQL quickly.

Commands (run from the project root):
- docker compose up -d
- docker compose ps  # wait until db is healthy
- docker compose logs -f db  # optional, to watch logs

Connection details (match application.properties):
- Host: localhost
- Port: 5432
- Database: patients
- Username: postgres
- Password: password

Stop and remove the container when done:
- docker compose down

Note: If port 5432 is already in use on your machine, edit compose.yaml to map a different host port, e.g. "5433:5432", and update spring.datasource.url accordingly.

## Using Podman instead of Docker

Podman can run this compose.yml without changes. Key notes:
- The compose file maps container port 5432 to host 5432, so the app connects to jdbc:postgresql://localhost:5432/patients as-is.
- We disabled Spring Boot’s automatic Docker Compose integration to avoid requiring Docker Desktop (spring.docker.compose.enabled=false).

Windows/macOS with Podman Desktop or podman-machine:
1) Install Podman: https://podman.io/ or Podman Desktop.
2) Ensure the Podman VM is running:
   - podman machine init  # first time only
   - podman machine start
3) From the project root, start the database:
   - podman compose up -d
4) Check status:
   - podman compose ps
   - podman logs -f patientform-postgres
5) Stop when done:
   - podman compose down

Linux (rootless):
- Same commands as above (no machine needed):
  - podman compose up -d
  - podman compose down

If your Podman only provides the docker-compose-compatible wrapper, these also work:
- docker compose up -d  # when DOCKER_HOST points to Podman’s socket

Troubleshooting Podman specifics:
- If 5432 is busy, change ports in compose.yaml and in spring.datasource.url.
- If Spring tries to start Compose and fails, ensure spring.docker.compose.enabled=false in application.properties (already set).
- On Windows, ensure the Podman machine has port forwarding enabled. Podman Desktop handles this automatically.

## Troubleshooting

- 400 on /patients/search: ensure you call port 8081, not 8080.
- Database connection errors: verify PostgreSQL is running and credentials in application.properties.
- CORS errors in browser: ensure origin matches allowedOrigins in CorsConfig or update it accordingly.


## Database setup (normal configuration)

Overview
- Spring Boot app listens on port 8081
- Default DB connection (matches application.properties):
  - Host: localhost
  - Port: 5432
  - Database: patients
  - Username: postgres
  - Password: password

Option A: Run PostgreSQL with Docker or Podman (recommended)
- Prerequisites: Docker Desktop/Engine + Compose plugin OR Podman/Podman Desktop
- Start DB from project root:
  - docker compose up -d
  - OR podman compose up -d
- Check status:
  - docker compose ps  (or podman compose ps)
  - Optional logs: docker compose logs -f db  (or podman logs -f patientform-postgres)
- Stop when done:
  - docker compose down (or podman compose down)
- If port 5432 is busy, edit compose.yaml to map another host port (e.g., "5433:5432") and update spring.datasource.url accordingly.

Option B: Use a locally installed PostgreSQL (no Docker)
1) Install PostgreSQL: https://www.postgresql.org/download/
2) Create a database and user (examples use default superuser "postgres"):
   - Create database: patients
   - Ensure you know the password for the postgres user (examples assume password)
3) Ensure the server listens on localhost:5432 (default). If you change the port, adjust the URL below.

application.properties configuration (src/main/resources/application.properties)
Use these defaults and adjust if your local setup differs:

server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/patients
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate/JPA
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# Ensure schema.sql runs at startup (optional)
spring.sql.init.mode=always

# Disable Spring Boot's Docker Compose integration (supports both Docker and Podman)
spring.docker.compose.enabled=false

Verification
- Start the DB (Docker/Podman or local), then run the app:
  - Windows (PowerShell): .\mvnw spring-boot:run
  - macOS/Linux: ./mvnw spring-boot:run
- Health check: GET http://localhost:8081/patients/ping  -> ok

Troubleshooting
- Connection refused: ensure PostgreSQL is running and listening on the expected port.
- Authentication failed: verify username/password; update application.properties accordingly.
- Port in use (5432): change host port in compose.yaml (e.g., 5433:5432) and update spring.datasource.url to jdbc:postgresql://localhost:5433/patients
- SSL errors on some local setups: append ?sslmode=disable to the JDBC URL if necessary (e.g., jdbc:postgresql://localhost:5432/patients?sslmode=disable)
- Time zone issues: application sets JDBC time zone to UTC; adjust if needed.
