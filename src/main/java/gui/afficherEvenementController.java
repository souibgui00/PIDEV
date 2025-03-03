package gui;

import Models.Evenement;
import Services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class afficherEvenementController implements Initializable {

    @FXML
    private AnchorPane listEvenementPane;

    @FXML
    private VBox vBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceEvenement sc = new ServiceEvenement();
            List<Evenement> eves = sc.recuperer();

            for(int i=0;i<eves.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemEvenement.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    HBox hBox = (HBox) anchorPane.getChildren().get(0);
                    ItemEvenementController itemController = fxmlLoader.getController();
                    itemController.setData(eves.get(i));
                    vBox.getChildren().add(hBox);
                } catch (IOException ex) {
                    Logger.getLogger(ItemEvenementController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void open_stat(ActionEvent event) throws IOException{
        Parent fxml= FXMLLoader.load(getClass().getResource("/Statistiques.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Statistiques des evenements selon leurs nombre de participation");
        stage.setScene(new Scene(fxml));
        stage.showAndWait();
    }
}
