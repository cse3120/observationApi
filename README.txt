# observationApi
Backend API service for observation model
********************************************************
                  OBSERVATION MODEL APIs
********************************************************

**************************************************
Tools and Technologies Used
**************************************************
Java        - 11
Spring Boot - v2.7.17
PostgreSQL  - 42.3.8
Swagger     - 1.7.0
Docker
**************************************************

The application is developed to perform CRUD operations for the 'Observation' model.
POST - http://localhost:8080/api/observations/
PUT - http://localhost:8080/api/observations/{id}
DELETE-  http://localhost:8080/api/observations/{id}
GET -   http://localhost:8080/api/observations/
Sample JSON for creating the observation model
{
    "observationValue": 34.5,
    "observationType":"skin-temperature",
    "patient": {
        "patientId": 1,
        "patientName": "Tom"
    }
}
The 'Observation' model is based on the data structure of the following table.
-------------------------------------------------------------------------
Type              Date                  Patient  Value    Unit          |
-------------------------------------------------------------------------
heart-rate        2023-09-06T11:02:44Z  101      65     beats/minute    |
-------------------------------------------------------------------------
skin-temperature  2023-09-07T11:23:24Z  101      37.2   degrees Celsius |
-------------------------------------------------------------------------
respiratory-rate  2023-09-06T11:02:44Z  101      15     breaths/minute  |
-------------------------------------------------------------------------
heart-rate        2023-09-04T08:54:33Z  102      76     beats/minute    |
-------------------------------------------------------------------------
respiratory-rate  2023-09-04T08:54:33Z  102      18     breaths/minute  |
-------------------------------------------------------------------------
skin-temperature  2023-09-05T15:12:23Z  103      37.8   degrees Celsius |
-------------------------------------------------------------------------

Docker Configuration:

The application is containerized using Docker. To run the application in a Docker container, make sure you have Docker installed.



Database Initialization:

The initial database setup and schema are defined in schema.sql.
You can execute this script to create the required database structure.

Explanation of a Normalized Schema:
ObservationType: This is a static table containing predefined types used to measure human parameters.
Patient: In this table, data will be created using the Create API to store patient information.
Observation: This is the primary model table designed to store patient information along with observation types.

Implementation:

1. Adherence to RESTful architecture principles to design robust and scalable APIs.
2. Leveraging JpaRepository to seamlessly connect the persistence layer with the data layer, simplifying database operations.
3. Implementation of a dedicated service class to encapsulate business logic, complemented by the use of Data Transfer Objects (DTOs) to structure data exchange between clients and the API.
4. Integration of mappers to enable smooth conversion between DTOs and JPA entities, ensuring data consistency.
5. Creation of an enumeration to define the static table, with special attention to case-sensitive deserialization to handle observation type and value.
6. Introduction of a global exception handler capable of effectively managing custom ObservationException instances, enhancing error handling for application exceptions within the Observation API.
7. Robust input request validation, ensuring data integrity and API reliability.
8. OpenAPI documentation is implemented and accessible at http://localhost:8080/swagger-ui/index.html.

To run the application:

1. Navigate to the project path in your terminal.
2. Build the Docker image using the following command,
    docker build -t observation-api .
3. Ensure the image is created by listing Docker images,
    docker images
4. Run the Docker container with port mapping to make it accessible on your host machine,
    docker run -p 8080:8080 observation-api
