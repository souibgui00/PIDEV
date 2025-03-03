package gui;


import Services.ServiceEvenement;
import Services.ServiceUtilisateur;
import Models.utilisateur;
import Models.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;


import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;




public class AjouterEvenementController implements Initializable {

    @FXML
    private AnchorPane addEvenementPane;

    @FXML
    private Button btnAddEvenement;

    @FXML
    private Button btnClearEvenement;

    @FXML
    private ImageView imageInput;

    @FXML
    private TextField txtCapacite;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtLieu;

    @FXML
    private TextField txtNom;

    @FXML
    private ComboBox<String> txtStatus;

    @FXML
    private ComboBox<String> txtType;

    @FXML
    private Button uploadBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtType.getItems().addAll("Conferences","Seminaires","Ateliers Pratiques","Competitions");
    }

    @FXML
    void AjoutEvenement(ActionEvent event) {
        if(event.getSource() == btnAddEvenement){
            if (txtLieu.getText().isEmpty() || txtDescription.getText().isEmpty() || txtNom.getText().isEmpty() ||
                    txtCapacite.getText().isEmpty() || imageName == null)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Evenement.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajouterEvenement();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Evenement a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();
                //send_sms();
                clearFieldsEvenement();
            }
        }
        if(event.getSource() == btnClearEvenement){
            clearFieldsEvenement();
        }
    }

    private File selectedImageFile;
    private String imageName = null;

    @FXML
    void ajouterImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf("."));
            imageName = uniqueID + extension;

            Path destination = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "uploads", imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @FXML
    void clearFieldsEvenement() {
        txtDescription.clear();
        txtCapacite.clear();
        txtDate.getEditor().clear();
        txtNom.clear();
        txtLieu.clear();
        txtType.getEditor().clear();
        imageInput.setImage(null);
        imageInput=null;
    }

    void ajouterEvenement(){
        ServiceUtilisateur us = new ServiceUtilisateur();

        String nom = txtNom.getText();
        String description = txtDescription.getText();
        String lieu = txtLieu.getText();
        Date dateEve=null;
        try {
            LocalDate localDate = txtDate.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                dateEve = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        String statut = "Actif";
        String type = txtType.getValue();
        int capacite_max = Integer.parseInt(txtCapacite.getText());
        utilisateur user = null;
        try {
            user = us.getById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Evenement c = new Evenement(nom,description,dateEve,lieu,statut,capacite_max,imageName,user,type);
        ServiceEvenement cs = new ServiceEvenement();
        cs.ajouter(c);
    }


    void send_sms(){
        String ACCOUNT_SID = "AC9dc02485e477293272425babdce9951d";
        String AUTH_TOKEN = "4084588404cd4134aa39f325e604b4b8";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recepientNumber = "+21627185473";
        String message = "Bonjour Mr, \n"
                +"Nous sommes ravis de vous informer qu'un evenement a été ajouté.\n"
                +"Veuillez contactez l'administration pour plus de details. \n"
                +"Merci de votre fidélité et à bientôt.\n"
                +"Cordialement | SahtekEvent, \n";

        Message twilioMessage = Message.creator(
                new PhoneNumber(recepientNumber),
                new PhoneNumber("+16083510757"),message).create();
        System.out.println("SMS envoyé : "+twilioMessage.getSid());
    }
}