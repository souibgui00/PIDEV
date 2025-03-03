package gui;

import Models.Evenement;
import Services.ServiceEvenement;
import Services.ServiceParticipation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class statistiquesController implements Initializable {

    @FXML
    private LineChart<String, Integer> lineChartEvenements;

    @FXML
    private AnchorPane statPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            statistique();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void statistique() throws SQLException {
        ServiceEvenement se = new ServiceEvenement();
        ServiceParticipation sp = new ServiceParticipation();

        List<Evenement> events = se.recuperer();

        // Créer les axes pour le graphique
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Titre Evenement");
        yAxis.setLabel("Nombre des participations");

        // Créer la série de données à afficher
        XYChart.Series series = new XYChart.Series();
        series.setName("Statistiques des evenements selon leurs nombre de participation");
        for (Evenement eve : events) {
            series.getData().add(new XYChart.Data<>(eve.getNom(), sp.getNbParticipationsByIdEvenement(eve.getId())));
        }

        // Créer le graphique et ajouter la série de données
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Statistiques des Evenements");
        lineChart.getData().add(series);

        // Afficher le graphique dans votre scène
        lineChartEvenements.setCreateSymbols(false);
        lineChartEvenements.getData().add(series);
    }

}
