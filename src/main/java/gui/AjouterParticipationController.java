package gui;

import Models.Evenement;
import Models.participation;
import Models.utilisateur;
import Services.ServiceEvenement;
import Services.ServiceParticipation;
import Services.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AjouterParticipationController implements Initializable {

    @FXML
    private AnchorPane addParticipationPane;

    @FXML
    private Button btnAddParticipation;

    @FXML
    private Button btnClearParticipation;

    @FXML
    private DatePicker txtDatePart;

    @FXML
    private TextField txtMotif;

    @FXML
    private ComboBox<String> txtMoyenP;

    @FXML
    private ComboBox<String> txtEve;


    ServiceEvenement se = new ServiceEvenement();
    List<Evenement> events = se.recuperer();
    private int idEve=-1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    void AjoutParticipation(ActionEvent event) {
        //check if not empty
        if(event.getSource() == btnAddParticipation){
            if (idEve==-1 || txtMotif.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information manquante");
                alert.setHeaderText(null);
                alert.setContentText("Vous devez remplir tous les détails concernant votre Participation.");
                Optional<ButtonType> option = alert.showAndWait();

            } else {
                ajouterParticipation();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ajouté avec succès");
                alert.setHeaderText(null);
                alert.setContentText("Votre Participation a été ajoutée avec succès.");
                Optional<ButtonType> option = alert.showAndWait();

                clearFieldsParticipation();
            }
        }
        if(event.getSource() == btnClearParticipation){
            clearFieldsParticipation();
        }
    }

    @FXML
    void clearFieldsParticipation() {
        txtEve.getEditor().clear();
        txtMotif.clear();
        txtDatePart.getEditor().clear();
        txtMoyenP.getEditor().clear();
    }

    private void ajouterParticipation() {
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
                user, event, date_inscription,motif_annulation,moyen_p);
        ServiceParticipation ps = new ServiceParticipation();
        ps.ajouter(p);
    }
}
