     +---------------------+    +---------------------+
    |   Database Model    |------|   JavaFX View Model  |
    +---------------------+    +---------------------+
               |                          |
               |                          |
          +---------+              +---------+
          |         |              |         |
    +-----------+  |          +-----------+  |
    |DB Controller|  |          |GUI Controller|  |
    +-----------+  |          +-----------+  |
               |                          |
               |                          |
          +---------+              +---------+
          |         |              |         |
    +-------------+          +-------------+
    |    Database     |          |   JavaFX View   |
    +-------------+          +-------------+


**Legend**:
- Arrows indicate the flow of information and commands
- Models contain the business logic and are responsible for data access and manipulation
- Controllers handle user input and facilitate communication between views and models
- Views display information to the user and receive input from them

The above diagram shows four components of your application:

- **Database Model**: This component represents the database and the data access layer. It provides an interface for executing SQL queries and retrieving results.

- **JavaFX View Model**: This component represents the JavaFX view and the state of the user interface. It provides an interface for updating the user interface based on changes in the application state.

- **DB Controller**: This component acts as an intermediary between the database model and the GUI controller. It receives requests from the GUI controller to retrieve or modify data in the database, and interacts with the database model to execute the necessary SQL queries.

- **GUI Controller**: This component acts as an intermediary between the JavaFX view model and the DB controller. It receives user input events from the view, and interacts with the JavaFX view model to update the state of the user interface.

When the user inputs a search query in the JavaFX view, the event is passed to the GUI controller. The GUI controller interacts with the JavaFX view model to retrieve the input and constructs a search request to send to the DB controller.

The DB controller receives the search request and interacts with the database model to execute the SQL query. The database model retrieves the data from the database and returns it to the DB controller.

The DB controller then sends the result back to the GUI controller, which updates the JavaFX view model with the new data. The JavaFX view model updates the view, causing the updated search results to be displayed to the user.

