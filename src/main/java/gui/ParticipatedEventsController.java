package gui;
import Models.Evenement;
import Models.utilisateur;
import Models.participation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ParticipatedEventsController implements Initializable {

    @FXML
    private TableView<Evenement> eventTable;

    @FXML
    private TableColumn<Evenement, String> nameColumn;
    @FXML
    private TableColumn<Evenement, String> dateColumn;
    @FXML
    private TableColumn<Evenement, String> statusColumn;

    private ObservableList<Evenement> eventList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (nameColumn != null) {
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        }
        if (dateColumn != null) {
            dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
        }
        if (statusColumn != null) {
            statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatut()));
        }

        // Load participated events for user with id = 1
        loadParticipatedEvents();
    }



    private void loadParticipatedEvents() {
        // Fetch the user with id = 1
        utilisateur user = getUserById(1);  // Get user with id = 1

        if (user != null) {
            // Get the list of participations for the user
            List<participation> participations = user.getParticipations();

            // Extract events from participations
            List<Evenement> participatedEvents = extractEventsFromParticipations(participations);

            // Convert the list to an ObservableList for TableView
            eventList = FXCollections.observableArrayList(participatedEvents);

            // Set the items of the table
            eventTable.setItems(eventList);
        }
    }

    private List<Evenement> extractEventsFromParticipations(List<participation> participations) {
        // Extract events from the participation list
        List<Evenement> events = new ArrayList<>();
        for (participation p : participations) {
            events.add(p.getEvent()); // Add the event associated with each participation
        }
        return events;
    }

    private utilisateur getUserById(int id) {
        // Replace this with the actual logic to get a user by their ID from the database or a mock function
        // Example:
        // For now, we will create a mock user with a set of participations

        utilisateur mockUser = new utilisateur(); // Replace with actual user fetching logic
        mockUser.setId(id);
        mockUser.setNom("John Doe");

        // Create mock participations for testing
        Evenement event1 = new Evenement(1, "Event 1", "Description for Event 1", new java.util.Date(), "Location 1", "Active", 100, "image1.jpg", mockUser, "Type1");
        Evenement event2 = new Evenement(2, "Event 2", "Description for Event 2", new java.util.Date(), "Location 2", "Active", 100, "image2.jpg", mockUser, "Type2");

        participation participation1 = new participation(mockUser, event1, new java.util.Date(), null, "Credit Card");
        participation participation2 = new participation(mockUser, event2, new java.util.Date(), null, "Paypal");

        // Add participations to the mock user
        mockUser.setParticipations(List.of(participation1, participation2));

        return mockUser;
    }

    @FXML
    private void onSupportButtonClick(MouseEvent event) {
        // Logic to handle support for an event
    }

    @FXML
    private void onFeedbackButtonClick(MouseEvent event) {
        // Logic to handle feedback for an event
    }

    @FXML
    private void onBackButtonClick(MouseEvent event) {
        // Logic to go back to the previous page
    }

    @FXML
    private void goToHomePage(ActionEvent event) {
        try {
            // Load the new FXML file for the "Participated Events" page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeParticipant.fxml"));
            Parent root = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(new Scene(root));

            // Optionally, you can set the stage's title or make other adjustments
            stage.setTitle("Events");

            // Show the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


}
