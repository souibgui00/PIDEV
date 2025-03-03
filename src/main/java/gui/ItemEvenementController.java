package gui;

import Models.Evenement;
import Services.ServiceEvenement;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemEvenementController implements Initializable {


    @FXML
    private Button btnModifierEvenement;

    @FXML
    private Button btnSupprimerEvenement;

    @FXML
    private AnchorPane itemEvenementPane;

    @FXML
    private Label labelCapaciteMax;

    @FXML
    private Label labelDate;

    @FXML
    private ImageView labelImage;

    @FXML
    private Label labelLieu;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelStatut;

    @FXML
    private Label labelType;



    private static int id;

    public int getId(){
        return this.id;
    }

    Evenement eve;
    public void setData (Evenement eve){
        this.eve = eve;

        labelNom.setText(eve.getNom());
        labelDate.setText(String.valueOf(eve.getDate()));
        labelLieu.setText(String.valueOf(eve.getLieu()));
        labelStatut.setText(String.valueOf(eve.getStatut()));
        labelType.setText(String.valueOf(eve.getType()));
        labelCapaciteMax.setText(String.valueOf(eve.getCapacite_max()));
        String filePath = "C:\\Users\\amine\\IdeaProjects\\evenement\\src\\main\\java\\uploads\\" + eve.getImage();
        File file = new File(filePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            labelImage.setImage(image);
        } else {
            System.err.println("File does not exist: " + filePath);
        }
        this.id=eve.getId();
    }

    public Evenement getData (Evenement eve){
        this.eve = eve;
        return this.eve;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void open_UpdateEvenement(ActionEvent event) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/updateEvenement.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Update Evenement");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }

    @FXML
    void supprimerEvenement(ActionEvent event) throws SQLException {
        ServiceEvenement cs = new ServiceEvenement();

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cet Evenement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de l'evenement sélectionnée
            int id = this.eve.getId();

            // Supprimer l'evenement de la base de données
            cs.supprimer(id);
        }

    }
}
