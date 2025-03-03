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

public class HomeController implements Initializable {

    @FXML
    private Button btnEve;

    @FXML
    private Button btnPart;

    @FXML
    private AnchorPane view_pages;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void switchForm(ActionEvent event) throws IOException {
        if(event.getSource() == btnEve) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/gestionEvenement.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }else if(event.getSource() == btnPart) {
            Parent fxml = FXMLLoader.load(getClass().getResource("/gestionParticipation.fxml"));
            view_pages.getChildren().removeAll();
            view_pages.getChildren().setAll(fxml);
        }
    }
}
