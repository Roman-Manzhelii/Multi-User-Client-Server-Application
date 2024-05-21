# Multi-User Client-Server Application (OOP CA5)

**Multi-User Client-Server Application** is a project developed for the Object-Oriented Programming module. This application implements a database-driven client-server system with various features including data access, JSON communication, and multithreading.

## Objectives

- Design, implement and test a database-driven client-server application.
- Design and implement a Data Access Layer for database access.
- Implement a Multithreaded Server.
- Implement data exchange between client and server using JSON format with GSON Parser.
- Implement binary data stream exchange.

## Features

1. **Get all Entities**: Retrieve and display a list of all entities.
2. **Find and Display Entity by Key**: Retrieve and display a single entity by its key.
3. **Delete an Entity by Key**: Remove a specified entity from the database.
4. **Insert an Entity**: Gather data, create an entity, and insert it into the database.
5. **Update an Existing Entity by ID**: Update an entity by its ID.
6. **Get List of Entities Matching a Filter**: Retrieve and display entities that match a specified filter.
7. **Convert List of Entities to JSON String**: Serialize a list of entities to JSON format.
8. **Convert Single Entity to JSON String**: Serialize a single entity to JSON format.
9. **Display Entity by ID**: Implement client-server communication to display an entity by ID.
10. **Display All Entities**: Implement client-server communication to display all entities.
11. **Add an Entity**: Implement client-server communication to add a new entity.
12. **Delete Entity by ID**: Implement client-server communication to delete an entity by ID.
13. **Get Images List**: Implement client-server communication to download an image file.
14. **Exit**: Notify the server that the client is quitting and terminate the client.

## Technologies Used

- **Programming Language**: Java
- **Database**: MySQL
- **Data Access**: DAO, DTO, and corresponding interfaces
- **Data Format**: JSON (GSON Parser)
- **Multithreading**: Java multithreading
- **Testing**: JUnit

## Installation Requirements

- Java Development Kit (JDK)
- MySQL Database
- GSON Library
- Git

## Installation and Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Roman-Manzhelii/Multi-User-Client-Server-Application.git
   cd Multi-User-Client-Server-Application
   ```

2. **Set up MySQL Database:**
    - Create the database and table using the provided `CreatePlayers.sql` file.
    - Populate the table with initial data.

3. **Install dependencies:**
    - Ensure you have the GSON library included in your project.
    - Set up the database connection in your configuration files.

4. **Compile the application:**
   Use your preferred IDE to build the project, ensuring all dependencies are correctly set up.

## Usage Instructions

1. **Initialize Database:**
    - Run the `CreatePlayers.sql` script to set up your MySQL database.

2. **Start the Server:**
    - Run the server application to start listening for client connections.

3. **Start the Client:**
    - Run the client application and use the menu to interact with the server.

   
## Contributors

- [Roman Manzhelii](https://github.com/Roman-Manzhelii)
- [Mila](https://github.com/milamurphy)
- [Elga J Henry](https://github.com/henryelga)

## Contact

For more information or questions, please contact: romanmanzheliy1@gmail.com

