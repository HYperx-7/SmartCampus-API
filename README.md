# Smart Campus Management API

Welcome to the Smart Campus API. This project was developed as part of the 5COSC022W Client-Server Architectures coursework. It provides a robust backend for managing smart devices (sensors) across a university campus, ensuring data integrity through strict validation and advanced error handling.

---

## API Design Overview
The API follows RESTful architectural principles, utilizing a hierarchical resource structure:

* **Rooms**: The primary containers for all campus locations.
* **Sensors**: Devices linked to specific rooms, capable of filtering by type (e.g., Temperature, CO2).
* **Readings (Sub-Resources)**: Historical data points tied to specific sensors.

**Key Technical Features:**

* **Custom Exception Mapping**: Handles 403 (Maintenance Mode), 409 (Conflict on Delete), and 422 (Validation) errors with custom JSON responses.
* **Global Logging Filter**: Intercepts all traffic to provide real-time console auditing.
* **Business Logic**: Prevents data entry for sensors currently in 'MAINTENANCE' status.

---




## 🛠️ Build & Launch Instructions

Follow these steps to get the server running on your local machine:

### 1. Requirments
* **Java JDK 17** or higher.
* **Apache Maven** (for dependency management).
* An IDE (IntelliJ IDEA is recommended).

### 2. Installation
Clone the repository to your local drive:
```bash
git clone https://github.com/HYperx-7/SmartCampus-API.git
```

### 3. Building the Project
Open the project in your IDE. If using IntelliJ, Maven will automatically prompt you to load the pom.xml. 

### 4.Launching the Server
Navigate to src/main/java/com/smartcampus/Main.java.
Right-click the file and select Run 'Main.main()'.

The server will start at: http://localhost:8081/smartcampus/api/v1/


---




## Sample cURL Commands

Use these commands in your terminal to interact with the live API:

### 1. Register a new Room:
```
curl -X POST -H "Content-Type: application/json" -d '{"id":"R1", "name":"CS Lab 1"}' http://localhost:8081/smartcampus/api/v1/rooms
```
### 2. Register a Sensor:

```
curl -X POST -H "Content-Type: application/json" -d '{"id":"S1", "type":"Temperature", "status":"Active", "roomId":"R1"}' http://localhost:8081/smartcampus/api/v1/sensors
```

### 3. Filter Sensors by Type:
```
curl -X GET "http://localhost:8081/smartcampus/api/v1/sensors?type=Temperature"
```
### 4. Post a Sensor Reading:

```
curl -X POST -H "Content-Type: application/json" -d '{"value": 24.5}' http://localhost:8081/smartcampus/api/v1/sensors/S1/readings
```
### 5. Test Maintenance Mode:
```
curl -i -X POST -H "Content-Type: application/json" -d '{"value": 15.0}' http://localhost:8081/smartcampus/api/v1/sensors/S1/readings
```





