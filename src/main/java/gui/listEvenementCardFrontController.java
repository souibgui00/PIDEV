package gui;

import Models.utilisateur;
import Services.ServiceEvenement;
import Services.ServiceParticipation;
import Services.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import Models.Evenement;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import Models.participation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class listEvenementCardFrontController implements Initializable {
    @FXML
    private Label labelDateEvent;

    @FXML
    private Label labelDescriptionEvent;

    @FXML
    private Label labelLieuEvent;

    @FXML
    private Label labelNomEvent;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ImageView imageEvent;

    @FXML
    private Button btnParticiper;

    @FXML
    private TextField txtMotif;

    @FXML
    private ComboBox<String> txtMoyenP;  // Assuming it's a ComboBox with payment methods

    private Evenement eve;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize if needed
    }

    public void setData(Evenement eve) {
        this.eve = eve;

        labelNomEvent.setText(eve.getNom());
        labelDateEvent.setText("Date : " + eve.getDate().toString());
        labelLieuEvent.setText("Lieu : " + eve.getLieu());
        labelDescriptionEvent.setText(eve.getDescription());

        // Appliquer l'image en arrière-plan
        URL imageUrl = getClass().getResource("/uploads/" + eve.getImage());
        if (imageUrl != null) {
            Image image = new Image(imageUrl.toString());
            imageEvent.setImage(image);
        } else {
            System.out.println("⚠️ Image file not found: " + eve.getImage());
        }




    }


    @FXML
    void open_ajouterParticipation(ActionEvent event) throws IOException {
        // Charger le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouterParticipationFront.fxml"));
        Parent fxml = loader.load();

        // Obtenez le contrôleur de la vue ajouterParticipationFrontController
        ajouterParticipationFrontController controller = loader.getController();

        // Créer un utilisateur avec ID = 1
        utilisateur user = new utilisateur();
        user.setId(1);  // Utilisateur fixe avec ID = 1

        // Passez l'événement sélectionné et l'utilisateur avec ID = 1 au contrôleur de la vue
        controller.setData(eve, user);

        // Créer la nouvelle scène pour afficher la vue
        Stage stage = new Stage();
        stage.setTitle("Add Participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }


}
