package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionParticipationController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnList;

    @FXML
    private AnchorPane gestionParticipationPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void goToPages (ActionEvent event) throws IOException {
        if(event.getSource() == btnAdd) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/ajouterParticipation.fxml"));
            gestionParticipationPane.getChildren().removeAll();
            gestionParticipationPane.getChildren().setAll(fxml);
        }else if(event.getSource() == btnList) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/afficherParticipation.fxml"));
            gestionParticipationPane.getChildren().removeAll();
            gestionParticipationPane.getChildren().setAll(fxml);
        }
    }
}
