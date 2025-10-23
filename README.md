# Employee Lifecycle Management System

A web-based workflow orchestration platform for automating employee onboarding and offboarding processes.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** - [Download](https://adoptium.net/)
- **Node.js 18+** - [Download](https://nodejs.org/)
- **Docker** - [Download](https://www.docker.com/products/docker-desktop)
- **Docker Compose** - Included with Docker Desktop
- **Maven** (optional) - Included in project via Maven Wrapper

## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Hackathon-Main-Project
```

### 2. Start Database (Docker)

```bash
cd docker
docker-compose up -d
```

Verify PostgreSQL is running:
```bash
docker ps
```

You should see `employee-lifecycle-postgres` container running.

### 3. Start Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

Or on Windows:
```bash
cd backend
mvnw.cmd spring-boot:run
```

The backend will start on `http://localhost:8080`

Verify backend is running:
```bash
curl http://localhost:8080/api/health
```

Expected response: `{"status":"UP"}`

### 4. Start Frontend (React + Vite)

Open a new terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`

Open your browser and navigate to `http://localhost:5173` to see the application.

## Verification

### Check Backend Health Endpoint

```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{"status":"UP"}
```

### Check Frontend

1. Open browser to `http://localhost:5173`
2. You should see "Employee Lifecycle Management System" heading
3. Backend status should show "UP" in a green alert

### Check Database Connection

The backend logs should show:
```
Flyway migration executed successfully
Database connection established
```

## Project Structure

```
.
├── backend/          # Spring Boot backend application
├── frontend/         # React + TypeScript frontend application
├── docker/           # Docker Compose configurations
├── docs/             # Project documentation
├── scripts/          # Utility scripts
└── README.md         # This file
```

## Stopping the Application

### Stop Frontend
Press `Ctrl+C` in the terminal running the frontend

### Stop Backend
Press `Ctrl+C` in the terminal running the backend

### Stop Database
```bash
cd docker
docker-compose down
```

To also remove the database volume (⚠️ deletes all data):
```bash
docker-compose down -v
```

## Troubleshooting

### Backend won't start
- Ensure PostgreSQL is running: `docker ps`
- Check database connection in `backend/src/main/resources/application.properties`
- Verify Java version: `java -version` (should be 17+)

### Frontend won't start
- Ensure Node.js is installed: `node --version` (should be 18+)
- Delete `node_modules` and run `npm install` again
- Check for port conflicts on 5173

### Database connection errors
- Ensure Docker is running
- Check PostgreSQL container logs: `docker logs employee-lifecycle-postgres`
- Verify database credentials match in docker-compose.yml and application.properties

## Development

See [backend/README.md](backend/README.md) for backend development details.

See [frontend/README.md](frontend/README.md) for frontend development details.

## Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## License

Proprietary - All rights reserved
