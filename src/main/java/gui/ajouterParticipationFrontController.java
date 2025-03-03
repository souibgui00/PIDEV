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
        // Vérifier si l'événement sélectionné est nul
        if (selectedEvent == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("L'événement sélectionné est invalide.");
            alert.showAndWait();
            return;  // Arrêter l'exécution si l'événement est invalide
        }

        // Vérifier si tous les champs sont remplis
        if (txtDatePart.getValue() == null || txtMoyenP.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.showAndWait();
            return;
        }

        // Vérifier la capacité maximale de l'événement avant d'ajouter la participation
        try {
            int nbParticipants = sp.getNbParticipationsByIdEvenement(selectedEvent.getId());
            int capaciteMax = selectedEvent.getCapacite_max(); // Supposons que cette méthode existe dans votre modèle Evenement

            if (nbParticipants >= capaciteMax) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Capacité maximale atteinte");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a atteint sa capacité maximale de participants.");
                alert.showAndWait();

                // Mettre à jour le statut de l'événement pour le marquer comme "complet"
                selectedEvent.setStatut("Complet");  // Vous devez avoir une méthode pour mettre à jour le statut
                sp.updateEventStatus(selectedEvent);

                return;  // Ne pas ajouter la participation si la capacité est atteinte
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de vérification");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la vérification de la capacité.");
            alert.showAndWait();
            return;
        }

        // Ajouter la participation si la capacité n'est pas atteinte
        Date date_inscription = Date.from(Instant.from(txtDatePart.getValue().atStartOfDay(ZoneId.systemDefault())));
        String motif = txtMotif.getText();
        String moyen_p = txtMoyenP.getValue();

        // Créer l'utilisateur avec l'ID 1 (à remplacer par l'utilisateur connecté une fois intégré)
        utilisateur user = new utilisateur();
        user.setId(1);

        participation p = new participation(user, selectedEvent, date_inscription, motif, moyen_p);

        // Ajouter la participation
        sp.ajouter(p);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Participation enregistrée");
        alert.setHeaderText(null);
        alert.setContentText("Votre participation a été enregistrée avec succès !");
        alert.showAndWait();

        // Réinitialiser les champs après l'ajout
        clearFieldsParticipation();
    }


    @FXML
    void clearFieldsParticipation() {
        txtDatePart.setValue(null);
        txtMotif.clear();
        txtMoyenP.getSelectionModel().clearSelection();
    }
}
