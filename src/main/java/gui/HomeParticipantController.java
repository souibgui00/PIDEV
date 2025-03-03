package gui;

import Models.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Services.ServiceEvenement;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeParticipantController implements Initializable {
    @FXML
    private ImageView background;

    @FXML
    private Pagination pag;

    @FXML
    private TextField searchBar;

    private ObservableList<Evenement> evenementList;
    private FilteredList<Evenement> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ServiceEvenement es = new ServiceEvenement();
            evenementList = FXCollections.observableArrayList(es.recuperer());
            filteredData = new FilteredList<>(evenementList, p -> true);

            // Listen for changes in the search bar and update the filter
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(event -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return event.getNom().toLowerCase().contains(lowerCaseFilter) ||
                            event.getLieu().toLowerCase().contains(lowerCaseFilter) ||
                            event.getDate().toString().contains(lowerCaseFilter);
                });
                updatePagination();
            });
            updatePagination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePagination() {
        SortedList<Evenement> sortedData = new SortedList<>(filteredData);
        int itemsPerPage = 3;
        pag.setPageCount((int) Math.ceil(sortedData.size() / (double) itemsPerPage));
        pag.setPageFactory(pageIndex -> {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            int page = pageIndex * itemsPerPage;
            for (int i = page; i < Math.min(page + itemsPerPage, sortedData.size()); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/listEvenementCardFront.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    listEvenementCardFrontController itemController = fxmlLoader.getController();
                    itemController.setData(sortedData.get(i));
                    hbox.getChildren().add(anchorPane);
                    HBox.setMargin(anchorPane, new Insets(10));
                } catch (IOException ex) {
                    Logger.getLogger(HomeParticipantController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return hbox;
        });
    }

    @FXML
    private void goToParticipatedEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParticipatedEvents.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Participated Events");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
