package gui;

import Models.participation;
import Services.ServiceParticipation;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class afficherParticipationController implements Initializable {

    @FXML
    private AnchorPane listParticipationPane;

    @FXML
    private VBox vBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceParticipation src = new ServiceParticipation();
            List<participation> partList = src.recuperer();

            for(int i=0;i<partList.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/itemParticipation.fxml"));
                try {
                    AnchorPane anchorPane = fxmlLoader.load();
                    HBox hBox = (HBox) anchorPane.getChildren().get(0);
                    ItemParticipationController itemController = fxmlLoader.getController();
                    itemController.setData(partList.get(i));
                    vBox.getChildren().add(hBox);
                } catch (IOException ex) {
                    Logger.getLogger(ItemParticipationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
