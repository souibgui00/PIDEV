package gui;

import Models.Evenement;
import Models.utilisateur;
import Models.participation;
import Services.ServiceEvenement;
import Services.ServiceParticipation;
import Services.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class modifierParticipationController implements Initializable {
    @FXML
    private Button btnClearParticipation;

    @FXML
    private Button btnUpdateParticipation;

    @FXML
    private DatePicker txtDatePart;

    @FXML
    private ComboBox<String> txtEve;

    @FXML
    private TextField txtMotif;

    @FXML
    private ComboBox<String> txtMoyenP;

    @FXML
    private AnchorPane updateParticipationPane;


    participation part;

    ServiceEvenement se = new ServiceEvenement();
    List<Evenement> events = se.recuperer();
    private int idEve=-1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/itemParticipation.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            HBox hBox = (HBox) anchorPane.getChildren().get(0);
            ItemParticipationController item = fxmlLoader.getController();
            ServiceParticipation crs = new ServiceParticipation();

            part = crs.getById(item.getId());
            txtEve.setValue(part.getEvent().getNom());
            /* Debut Conversion Date */
            Date datePart = part.getDate_inscription(); // Get the java.util.Date
            if (datePart != null) {
                LocalDate localDate = ((java.sql.Date) datePart).toLocalDate();
                txtDatePart.setValue(localDate);
            }else{
                txtDatePart.setValue(LocalDate.now());
            }
            /* Fin Conversion Date */
            txtMotif.setText(part.getMotif_annulation());
            txtMoyenP.setValue(part.getMoyen_paiement());
            idEve=part.getEvent().getId();

        } catch (IOException ex) {
            Logger.getLogger(ItemParticipationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        Map<String, Integer> valuesMap = new HashMap<>();
        for(Evenement e : events){
            txtEve.getItems().add(e.getNom());
            valuesMap.put(e.getNom(),e.getId());
        }

        txtEve.setOnAction(event ->{
            String SelectedOption = null;
            SelectedOption = txtEve.getValue();
            int SelectedValue = 0;
            SelectedValue = valuesMap.get(SelectedOption);
            idEve = SelectedValue;
        });

        txtMoyenP.getItems().addAll("Gratuit","Carte Bancaire","Espèces","Virement");
    }

    @FXML
    void UpdateParticipation(ActionEvent event) {
        if (txtEve.getValue().isEmpty() || idEve == -1 || txtMoyenP.getValue().isEmpty() || txtMotif.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez remplir tous les détails concernant votre Participation.");
            Optional<ButtonType> option = alert.showAndWait();

        } else {
            modifParticipation();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modifié avec succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre Participation a été modifié avec succès.");
            Optional<ButtonType> option = alert.showAndWait();
        }
    }

    @FXML
    void clearFieldsParticipation() {
        txtEve.getEditor().clear();
        txtMotif.clear();
        txtDatePart.getEditor().clear();
        txtMoyenP.getEditor().clear();
    }

    private void modifParticipation() {
        ServiceUtilisateur su = new ServiceUtilisateur();
        ServiceEvenement se = new ServiceEvenement();
        // From Formulaire
        utilisateur user = null;
        try {
            user = su.getById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Evenement event = null;
        try {
            event = se.getById(idEve);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Date date_inscription = null;
        try {
            LocalDate localDate = txtDatePart.getValue();
            if (localDate != null) {
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                date_inscription = Date.from(instant);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        String motif_annulation = txtMotif.getText();
        String moyen_p = txtMoyenP.getValue();


        participation p = new participation(
                part.getId(),
                user, event, date_inscription,motif_annulation,moyen_p);
        ServiceParticipation ps = new ServiceParticipation();
        ps.modifier(p);
    }
}
