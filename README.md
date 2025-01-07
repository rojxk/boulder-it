## Boulder-it!
This project was developed as part of "Non-Relational Databases" course at university.

BoulderIt is a comprehensive web application for managing climbing areas, sectors, and boulder problems. It provides an interactive interface for climbers to explore areas, manage parking spots, and track climbing problems with detailed grading and community feedback.

## ✨ Features
- Interactive maps for climbing areas and sectors
- Parking spot management with distance calculations
- Problem grading system with community consensus
- User comments and ratings for problems
- Comprehensive statistical analysis of climbing grades
- Responsive design for desktop

## 🔎 Overview

### 🗺️ Area Management
- View climbing areas on interactive maps
- Add and manage parking spots within 1km radius
- Track hazards and best seasons for climbing

### 🏔️ Sector Details
- Detailed view of each climbing sector
- Grade distribution visualization
- Problem management with community feedback

### 📊 Problem Statistics
- Track sends, projects, and likes
- Grade consensus system
- Comment system with moderation features

## 🌐 API Endpoints
### Areas

- `GET /api/areas` - List all climbing areas
- `GET /api/areas/{id}/full` - Get detailed area information
- `POST /api/areas` - Create new area

### Parking

- `POST /api/parking` - Add new parking spot
- `DELETE /api/parking/{id}` - Remove parking spot
- `GET /api/parking/area/{areaId}` - Get area parking spots

### Problems

- `GET /api/problems` - List all problems
- `POST /api/problems` - Add new problem
- `POST /api/problems/{id}/vote/{type}` - Vote on problem (type: like, project, easier, harder, send)

### Sectors

- `GET /api/sectors/{sectorId}` - Get sector by ID
- `GET /api/sectors/{sectorId}/problems` - Get all problems and their comments for a sector

### Comments

- `POST /api/comment` - Create new comment
- `GET /api/comment/{id}/upvote` - Upvote a comment
- `GET /api/comment/{id}/downvote` - Downvote a comment
- `GET /api/comment/{id}/flag` - Flag a comment


## 📘 Database
### Collections
- `areas`: Climbing area information
- `sectors`: Individual climbing sectors
- `problems`: Boulder problems and routes
- `parking_spots`: Parking location data
- `comments`: User feedback and ratings

## 🚀 Installation
### Prerequisites
- Python 3.7+
- Java JDK 11+
- MongoDB 4.4+
- Required Python libraries: streamlit, folium, requests

### Steps for running the app
1. **Clone repository**
```
git clone https://github.com/rojxk/boulder-it
cd boulderit
```
2. **Configure database**
- Create MongoDB database named `boulderit`
- Run the script ➡️ [here](https://github.com/rojxk/boulder-it/database/init-db.js) to populate the database.
- Update connection settings in `appliction.properties` file:
```
spring.data.mongodb.uri=mongodb://localhost:27017/boulderit
```
3. **Install dependencies**
```
pip install -r requirements.txt -f frontend/
```
4. **Start the backend server**
```
./mvnw spring-boot:run
```
5. **Launch the Streamlit frontend**
```
streamlit run frontend/app.py
```
6. **Access the application**
- Open web browser and navigate to: http://localhost:8501