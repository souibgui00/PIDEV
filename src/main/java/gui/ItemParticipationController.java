package gui;

import Models.Evenement;
import Models.participation;
import Services.ServiceParticipation;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemParticipationController implements Initializable {

    @FXML
    private Button btnModifierParticipation;

    @FXML
    private Button btnSupprimerParticipation;

    @FXML
    private AnchorPane itemParticipationPane;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelEvent;

    @FXML
    private Label labelMotif;

    @FXML
    private Label labelMoyP;

    @FXML
    private Label labelUser;


    private static int id;

    public int getId(){
        return this.id;
    }

    participation part;
    public void setData (participation part){
        this.part = part;

        labelUser.setText(part.getUser().getNom());
        labelEvent.setText(String.valueOf(part.getEvent().getNom()));
        labelDate.setText(String.valueOf(part.getDate_inscription()));
        labelMotif.setText(String.valueOf(part.getMotif_annulation()));
        labelMoyP.setText(String.valueOf(part.getMoyen_paiement()));
        this.id=part.getId();
    }

    public participation getData (participation part){
        this.part = part;
        return this.part;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateParticipation(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/updateParticipation.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerParticipation(ActionEvent event) throws SQLException {
        ServiceParticipation crs = new ServiceParticipation();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette Participation ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la participation sélectionnée
            int id = this.part.getId();

            // Supprimer la participation de la base de données
            crs.supprimer(id);
        }

    }


}
