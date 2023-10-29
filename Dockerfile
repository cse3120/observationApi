# Use an official PostgreSQL image
FROM postgres:latest AS postgres

# Set environment variables for PostgreSQL
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB observations

# Install PostgreSQL client
RUN apt-get update && apt-get install -y postgresql-client

# Create a temporary network to connect containers
RUN apt-get install -y netcat
RUN echo "wait-for-postgres:5432 60" | xargs -n 2 sh -c "until nc -z $0 $1; do sleep $2; done"

# Use an official Maven image as the base image for the multi-stage build
FROM maven:3.8-jdk-11 AS builder

# Set the working directory to /app
WORKDIR /app

# Copy the project source code into the container
COPY . .

# Run 'mvn clean install' to build the project
RUN mvn clean install

# Use an official AdoptOpenJDK image as the final base image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the builder stage into the final image
COPY --from=builder /app/target/observation-api-0.0.1-SNAPSHOT.jar .

# Expose the application port (if needed)
EXPOSE 8080

# Start Java application
CMD ["java", "-jar", "observation-api-0.0.1-SNAPSHOT.jar"]
