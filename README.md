Distributed Sensor Network

This project implements a distributed system designed for environmental parameter sensing, using a client-server architecture to manage and calibrate data from geographically dispersed sensors.
Features

    Server Implementation:
        Developed using Spring Boot, the server operates as a central repository and management point for all registered sensors.
        It provides RESTful services for sensor registration, nearest neighbor detection, and data storage.
        JSON format is used for communication, ensuring compatibility and ease of use with various clients.

    Sensor (Client) Implementation:
        Each sensor operates as a standalone client, emulating data collection through pre-generated readings (e.g., temperature, humidity, CO, and SO2 levels).
        Sensors register their location and data via REST calls to the server.
        Each sensor communicates with the server to identify its closest neighbor and uses gRPC to exchange data with this neighbor.
        Sensor readings are calibrated by comparing a sensor’s data with its nearest neighbor’s, generating adjusted values to enhance accuracy.

    Data Management:
        Calibrated data is saved on the server through REST API endpoints, allowing for centralized data storage and easy retrieval.
        The server exposes endpoints for querying registered sensors and accessing individual sensor readings.

Technologies Used

    Spring Boot for server-side RESTful API development.
    gRPC for inter-sensor communication, enabling efficient peer-to-peer data exchanges.
    JSON for data formatting and transmission between server and clients.
    Java as the primary programming language, with support for Gradle for dependency management.
