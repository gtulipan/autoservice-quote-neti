FROM openjdk:21-jdk

# Set the working directory
WORKDIR /app


# Copy the .env file
COPY .env .env

# Copy the application JAR file
COPY build/libs/quote.jar app.jar

# Declare the build argument and create the directory
ARG LOG_FOLDER
RUN mkdir -p ${LOG_FOLDER}

# Expose the port
ARG SERVER_PORT
EXPOSE ${SERVER_PORT}

# Run the JAR file
ENTRYPOINT ["java", "-cp", "app.jar:BOOT-INF/lib/*", "org.springframework.boot.loader.launch.JarLauncher"]