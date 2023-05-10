package akad.multithreadeddbms;

import akad.multithreadeddbms.controller.applicationlayer.InputHandler;
import akad.multithreadeddbms.controller.applicationlayer.OutputHandler;
import akad.multithreadeddbms.controller.applicationlayer.QueryExecutor;
import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnection;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnectionPool;
import akad.multithreadeddbms.model.persistencelayer.ThreadPool;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MainView extends Application {

    private TextField teacherField;
    private TextField courseField;
    private TextField searchField;
    private Label teacherResultLabel;
    private Label courseResultLabel;

    public static ThreadPool newThreadPool;
    static DatabaseConnection newDbConnection;
    static DatabaseConnectionPool newDbPool;

    @Override
    public void start(Stage primaryStage) {
        // UI header
        Label headerLabel = new Label("EduTrack Database");
        headerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Input section
        Label inputLabel = new Label("Add New Entry");

        teacherField = new TextField();
        teacherField.setPromptText("Teacher");

        courseField = new TextField();
        courseField.setPromptText("Course");

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String name = teacherField.getText();
            String course = courseField.getText();
            if (InputHandler.validateInput(name, course)) {
                try {
                    boolean success = QueryExecutor.insertTeacherIntoDatabase(InputHandler.convertInputToTeacherObject(name, course),
                              newThreadPool.getThreadFromPool(),
                              newDbPool.getConnectionFromPool());
                    System.out.println("Success: " + success);
                        if (success) {
                            teacherField.clear();
                            courseField.clear();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText("Teacher added to database");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Teacher not added to database");
                            alert.showAndWait();
                        }
                } catch (InterruptedException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.addRow(0, new Label("Name: "), teacherField);
        inputGrid.addRow(1, new Label("Course: "), courseField);
        inputGrid.addRow(2, addButton);

        VBox inputSection = new VBox(10, inputLabel, inputGrid);
        inputSection.setPadding(new Insets(20));
        inputSection.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px;");

// Search section
        Label teacherResultLabel = new Label();
        Label courseResultLabel = new Label();
        Label searchLabel = new Label("Search Database");
        searchField = new TextField();
        searchField.setPromptText("Search by id");
        Button searchButton = new Button("Search");


        searchButton.setOnAction(event -> {
            TeacherEntryObject teacher;
            int id = OutputHandler.parseAndValidateQuery(searchField.getText());
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid input");
                alert.showAndWait();
                return;
            }
            try {
                teacher = QueryExecutor.retrieveTeacherById(id, newThreadPool.getThreadFromPool(), newDbPool.getConnectionFromPool());
            } catch (InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }
            String name = teacher.getName();
            String course = teacher.getCourse();

            teacherResultLabel.setText("Teacher: " + name);
            courseResultLabel.setText("Course: " + course);
        });


        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setPadding(new

                Insets(10, 0, 10, 0));


        VBox searchSection = new VBox(10, searchLabel, new Separator(), searchBox, teacherResultLabel, courseResultLabel);
        searchSection.setPadding(new

                Insets(20));
        searchSection.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px;");


        Button executeBothButton = new Button("Execute Both");
        executeBothButton.setOnAction(event -> {
            String name = teacherField.getText();
            String course = courseField.getText();
            if (InputHandler.validateInput(name, course)) {
                try {
                    boolean success = QueryExecutor.insertTeacherIntoDatabase(InputHandler.convertInputToTeacherObject(name, course),
                            newThreadPool.getThreadFromPool(),
                            newDbPool.getConnectionFromPool());
                    System.out.println("Success: " + success);
                    if (success) {
                        teacherField.clear();
                        courseField.clear();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Teacher added to database");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Teacher not added to database");
                        alert.showAndWait();
                    }
                } catch (InterruptedException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            TeacherEntryObject teacher;
            int id = OutputHandler.parseAndValidateQuery(searchField.getText());
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid input");
                alert.showAndWait();
                return;
            }
            try {
                teacher = QueryExecutor.retrieveTeacherById(id, newThreadPool.getThreadFromPool(), newDbPool.getConnectionFromPool());
            } catch (InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }
            String nameBoth = teacher.getName();
            String courseBoth = teacher.getCourse();

            teacherResultLabel.setText("Teacher: " + nameBoth);
            courseResultLabel.setText("Course: " + courseBoth);
        });

        HBox executeBothBox = new HBox(executeBothButton);
        executeBothBox.setAlignment(Pos.CENTER);

        VBox middleSection = new VBox(20, new Separator(), executeBothBox);
        middleSection.setAlignment(Pos.CENTER);

// Main layout
        VBox mainContainer = new VBox(20, headerLabel);

        HBox inputSearchLayout = new HBox(20, inputSection, searchSection);
        inputSearchLayout.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, inputSearchLayout, middleSection);
        mainLayout.setAlignment(Pos.CENTER);
        mainContainer.getChildren().

                add(mainLayout);
        mainContainer.setStyle("-fx-alignment: center;");


        Scene scene = new Scene(mainContainer, 700, 400);

        primaryStage.setTitle("EduTrack Database");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            try {
                newThreadPool.stopThreadPool();
                newDbPool.closeConnectionPool();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        primaryStage.show();
    }

    public static ThreadPool getNewThreadPool() {
        return newThreadPool;
    }

    public static void main(String[] args) throws SQLException {
        newThreadPool = new ThreadPool();
        newDbConnection = new DatabaseConnection();
        newDbPool = new DatabaseConnectionPool(newDbConnection);
        launch();
    }
}