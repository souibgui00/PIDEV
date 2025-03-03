package gui;

import Models.Evenement;
import Models.participation;
import Models.utilisateur;
import Services.ServiceParticipation;
import Services.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class ajouterParticipationFrontController implements Initializable {
    @FXML
    private DatePicker txtDatePart;
    @FXML
    private TextField txtMotif;
    @FXML
    private ComboBox<String> txtMoyenP;
    @FXML
    private Button btnAddParticipation;
    @FXML
    private Button btnCancel;

    private int idEve;
    private Evenement selectedEvent;
    private utilisateur currentUser;

    ServiceParticipation sp = new ServiceParticipation();
    ServiceUtilisateur su = new ServiceUtilisateur();

    // Method to set the selected event and user
    public void setData(Evenement event, utilisateur user) {
        this.selectedEvent = event;
        this.idEve = event.getId();
        this.currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtMoyenP.getItems().addAll("Carte Bancaire", "Espèces", "Virement");
    }

    @FXML
    void ajoutParticipation(ActionEvent event) {
        // Check if the selected event is null
        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'événement sélectionné est invalide.");
            alert.showAndWait();
            return;  // Stop further execution if no valid event is selected
        }

        if (txtDatePart.getValue() == null || txtMoyenP.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return;
        }

        Date date_inscription = Date.from(Instant.from(txtDatePart.getValue().atStartOfDay(ZoneId.systemDefault())));
        String motif = txtMotif.getText();
        String moyen_p = txtMoyenP.getValue();

        // Forcer l'utilisateur avec l'ID 1 (placeholder)
        utilisateur user = new utilisateur();
        user.setId(1); // Fixe l'ID à 1 (should be the actual logged-in user)

        participation p = new participation(user, selectedEvent, date_inscription, motif, moyen_p);

        // Add participation using the service
        sp.ajouter(p);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participation enregistrée");
        alert.setHeaderText(null);
        alert.setContentText("Votre participation a été enregistrée avec succès !");
        alert.showAndWait();

        clearFieldsParticipation();
    }

    @FXML
    void clearFieldsParticipation() {
        txtDatePart.setValue(null);
        txtMotif.clear();
        txtMoyenP.getSelectionModel().clearSelection();
    }
}
