package gui;

import Models.Evenement;
import Models.utilisateur;
import Services.ServiceEvenement;
import Services.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class modifierEvenementController implements Initializable {
    @FXML
    private Button btnClearEvenement;

    @FXML
    private Button btnUpdateEvenement;

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
    private AnchorPane updateEvenementPane;

    @FXML
    private Button uploadBtn;


    Evenement event;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtStatus.getItems().addAll("Actif","Annulé","Terminé");
        txtType.getItems().addAll("Conferences","Seminaires","Ateliers Pratiques","Competitions");


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/itemEvenement.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            HBox hBox = (HBox) anchorPane.getChildren().get(0);
            ItemEvenementController item = fxmlLoader.getController();
            ServiceEvenement cs = new ServiceEvenement();

            event = cs.getById(item.getId());
            txtNom.setText(event.getNom());
            txtCapacite.setText(String.valueOf(event.getCapacite_max()));
            txtDescription.setText(event.getDescription());
            /* Debut Conversion Date */
            Date dateEve = event.getDate(); // Get the java.util.Date
            if (dateEve != null) {
                LocalDate localDate = ((java.sql.Date) dateEve).toLocalDate();
                txtDate.setValue(localDate);
            }else{
                txtDate.setValue(LocalDate.now());
            }
            /* Fin Conversion Date */
            txtLieu.setText(event.getLieu());
            txtStatus.setValue(event.getStatut());
            txtType.setValue(event.getType());
            txtCapacite.setText(String.valueOf(event.getCapacite_max()));
            imageName = event.getImage();
            String filePath = "C:\\Users\\amine\\IdeaProjects\\evenement\\src\\main\\java\\uploads\\" + event.getImage();
            File file = new File(filePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageInput.setImage(image);
            } else {
                System.err.println("File does not exist: " + filePath);
            }

        } catch (IOException ex) {
            Logger.getLogger(ItemEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void UpdateEvenement(ActionEvent event) {
        if (txtNom.getText().isEmpty() || txtDescription.getText().isEmpty() ||
                txtLieu.getText().isEmpty() || txtStatus.getValue().isEmpty() ||txtType.getValue().isEmpty() ||
                txtCapacite.getText().isEmpty() || imageName == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Evenement.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifEvenement();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Evenement a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
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
        txtStatus.getEditor().clear();
        txtType.getEditor().clear();
        imageInput.setImage(null);
        imageInput=null;
    }


    void modifEvenement(){
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
        String statut = txtStatus.getValue();
        String type = txtType.getValue();
        int capacite_max = Integer.parseInt(txtCapacite.getText());
        utilisateur user = null;
        try {
            user = us.getById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Evenement c = new Evenement(
                event.getId(),
                nom,description,dateEve,lieu,statut,capacite_max,imageName,user,type);
        ServiceEvenement cs = new ServiceEvenement();
        cs.modifier(c);
    }

}
