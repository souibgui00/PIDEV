package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) {
        try {
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomeParticipant.fxml"));

            Parent root = loader.load(); // Chargement correct du fichier FXML

            stage.setTitle("Ajouter");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Lancement de l'application JavaFX
    }
}
