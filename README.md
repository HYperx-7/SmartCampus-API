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




///Report 


Coursework Conceptual Report 
 
Part 1: Service Architecture & Setup 
Q1.1: Life Cycle of JAX-RS Resources By default, JAX-RS resources are "per-request." This means every time someone hits an endpoint, the server creates a new instance, uses it, and then tosses it. You can't just store data in a normal class variable because it’ll vanish instantly. To keep our campus data alive, I used a thread-safe singleton for the DataStore. It ensures that while the resource classes come and go, our rooms and sensors stay consistent in memory without hitting any race conditions. 

Q1.2: The Role of Hypermedia (HATEOAS) Think of HATEOAS as a "live map" for the API. Instead of the client dev having to memorize URLs from a PDF, the API provides the next steps (links) right in the JSON response. It makes the system way more flexible because if we ever change a URL path, the client doesn't break they’re just following the links we gave them. 


Part 2: Room Management 
Q2.1: Returning IDs vs. Full Objects It’s all about speed vs. detail. Returning just IDs is lightweight and saves bandwidth, which is great for mobile. But then the client has to make a million extra calls just to see a room's name. Returning full objects is a bit heavier on the first trip, but it lets the client show the whole dashboard immediately. For this campus app, full objects make the most sense for a smooth UI. 

Q2.2: Idempotency in DELETE Operations Yeah, my DELETE is idempotent. If you delete Room R1, it’s gone. If you accidentally click "Delete" again, nothing changes on the server the room is still gone. You might get a different status code (like a 404 instead of a 204), but the actual state of the campus doesn't change after that first successful hit. 
 
 
 
Part 3: Sensor Operations & Linking 
Q3.1: Media Type Mismatches Because I locked the POST methods to application/json, the server is picky. If a client tries to send plain text or XML, the server won't even try to read it; it’ll just kick back a 415 Unsupported Media Type. This keeps our models safe from trying to parse garbage data they weren't built for. 

Q3.2: Query Parameters vs. Path Parameters I use Path Parameters (like /sensors/S1) to point to a specific "thing." I use Query Parameters (like ?type=CO2) for "searching." It’s much cleaner to use query params for filtering because it keeps the URL structure simple even when you start adding multiple filters like status or room. 
Part 4: Deep Nesting with Sub-Resources 
Q4.1: Benefits of Sub-Resource Locators Locators keep the code from turning into a "God Class." If I put every single reading and sensor logic into one file, it would be a nightmare to maintain. By delegating the readings to a separate SensorReadingResource, the code stays modular and way easier to debug as the project grows. 


Part 5: Advanced Error Handling 
Q5.1: 422 vs. 404 A 404 usually means "I can't find that URL." But if the URL is right and the JSON is perfect, but the roomId inside it doesn't exist, a 422 Unprocessable Entity is much more honest. It tells the dev: "I see what you're trying to do, but your data is logically broken". 

Q5.2: The Danger of Stack Traces Showing a stack trace is a massive security NO. It basically hands an attacker a blueprint of your code, showing line numbers and library versions they can exploit. Custom Exception Mappers let us hide that mess and just show a clean, safe error message. 

Q5.3: Why Use Filters for Logging? Filters are a "write once, use everywhere" solution. Instead of manually pasting log lines into 50 different methods, the filter sits at the door and watches every request and response automatically. It keeps the business logic clean and ensures we never miss a log entry. 
 



