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

# Build Java application
FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY target/observation-api-0.0.1-SNAPSHOT.jar .

# Expose the application port (if needed)
EXPOSE 8080

# Start Java application
CMD ["java", "-jar", "observation-api-0.0.1-SNAPSHOT.jar"]
