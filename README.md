# Full-stack-cinema Application
The Full-Stack Cinema is an application that allows users to reserve,purchase,cancel movies at Awesome-0 cinema, with an admin interface for managing movies, onlineReservations,projections, soon and users. The project uses Java Spring Boot for the backend, React+Vite for the frontend, and Docker for managing all components within containers.

## Features
-**Movies – Users can browse movies and make purchase,cancellation or reservation.
-**Projections - Users can browse through projections and see which movie can watch.
-**Soon - Users can browse which movie will come soon and read in-depth details about specific one.
-**Info page - Users can follow us via social network like TikTok, Instagram, x ... also can send us email and be in contact with us.
-**Find-my-Ticket - After user/users choose movie and get confirmation code, he/she can gave up by clicking cancellation button.
-**Admin Panel – Administrators can add new rooms, view, and manage reservations.

-**User Interface – A simple and intuitive design for users to reserve, purchase or cancel projection.

-**Reservation API – Enables communication between the frontend and backend via a REST API.

## Technologies
-**Backend: Java, Spring Boot, JPA, MySQL

-**Frontend: React, React Bootstrap, Axios

-**Docker: For containerizing the application

-**Database: MySQL


## Setting up the Project
# Prerequisites

Before running the project, ensure that you have the following installed:
-**Docker
-**Docker Compose

## Running the Application

Clone the repository: 
https://github.com/jovangolic/full-stack-cinema.git
cd full-stack-cinema

## Run the Docker containers:
docker-compose up --build  this is for starting
#This will automatically:

    Build Docker images for both the backend and frontend.

    Start containers for MySQL database, Spring Boot backend, and React frontend.

    Access the application:

    Frontend: The application will be available at http://localhost:5173.

    Backend (API): The API will be available at http://localhost:8080.
# Project structure:
full-stack-hotel/
├── backend/
│   ├── src/
│   ├── Dockerfile
│   ├── pom.xml
│   └── application.properties
├── frontend/
│   ├── src/
│   ├── Dockerfile
│   ├── package.json
│   └── .env
├── docker-compose.yml
└── README.md

docker-compose dowm  -this is for shuting down

#Docker Compose

The project uses a docker-compose.yml file to define and manage Docker containers. It contains configurations for:

    MySQL: The database used to store information about users, tickets, movie, movies_coming-soon,halls,seats, roles and projection.

    Backend: The Spring Boot application running in a Docker container.

    Frontend: The React application running in a Docker container.


#cinema-front-image is the name of the container.

## Development Instructions

    Frontend Development: If you want to work on the frontend, you can run the application with npm start instead of Docker, and access it at http://localhost:5173.

    Backend Development: For backend development, use mvn spring-boot:run or ./mvnw spring-boot:run to run the application in Spring Boot environment.

## Full-Stack-Cinema App

## Running the application with Docker (Steps):

 1. Clone the Project
 git clone https://github.com/jovangolic/full-stack-cinema
 Make sure to clone the project in a directory where you want your full-stack application to live.
 2. Log in to Docker Hub
 Run the following command in your terminal:
 docker login
 Enter your Docker Hub username and password when prompted.
 3. Pull Docker Images
 Download the pre-built Docker images from Docker Hub:
 # Backend image
 docker pull jovangolic/full-stack-cinema:backend-picture
 # Frontend image
 docker pull jovangolic/full-stack-cinema:cinema-front-part-c2
4. Build the Backend JAR File
 Full-Stack Cinema App
 Navigate to the backend folder and build the .jar file:
 cd full-stack-cinema/backend-c2
 type  mvn clean package -DskipTests
 This step ensures that your JAR is built and ready to be included in the Docker image.
 5. Start the Application
 Navigate to the root folder of the project (where docker-compose.yml is located), and run:
 docker-compose up
 If you want to rebuild images before running:
 docker-compose up --build
 6. Access the Application
 Once everything is up and running:
 Frontend: http://localhost:5173
 Backend (for API testing with Postman): http://localhost:8080
 7. Shut Down the Application
To stop all running containers, use:
 Full-Stack Cinema App
 docker-compose down



##Author

# Jovan Golić - Author of this project.

## License



















