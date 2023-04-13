package gui.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AppController {

    @FXML
    private Button btnLagerePane;
    @FXML
    private Button btnFadePane;
    @FXML
    private Button btnDistillatPane;
    @FXML
    private Button btnPaafyldningPane;
    @FXML
    private Button btnOpretWhiskyPane;
    @FXML
    private AnchorPane contentPane;


    public void initialize() {
        try {
            Pane lagerePane = getLagerePane();
            contentPane.getChildren().setAll(lagerePane);
            lagerePane.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnLagerePaneAction();
        btnFadePaneAction();
        btnDistillatPaneAction();
        btnPaafyldningPaneAction();
        btnOpretWhiskyPaneAction();
    }

    private void btnLagerePaneAction() {
        btnLagerePane.setOnAction(event -> {
            try {
                Pane lagerePane = getLagerePane();
                contentPane.getChildren().setAll(lagerePane);
                lagerePane.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void btnFadePaneAction() {
        btnFadePane.setOnAction(event -> {
            try {
                Pane fadePane = getFadPane();
                contentPane.getChildren().setAll(fadePane);
                fadePane.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void btnDistillatPaneAction() {
        btnDistillatPane.setOnAction(event -> {
            try {
                Pane distillatPane = getDistillatPane();
                contentPane.getChildren().setAll(distillatPane);
                distillatPane.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void btnPaafyldningPaneAction() {
        btnPaafyldningPane.setOnAction(event -> {
            try {
                Pane paafyldningPane = getPaafyldningPane();
                contentPane.getChildren().setAll(paafyldningPane);
                paafyldningPane.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void btnOpretWhiskyPaneAction() {
        btnOpretWhiskyPane.setOnAction(event -> {
            try {
                Pane opretWhiskyPane = getOpretWhiskyPane();
                contentPane.getChildren().setAll(opretWhiskyPane);
                opretWhiskyPane.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Pane getOpretWhiskyPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/opretWhiskyPane/OpretWhiskyPane.fxml"));
        Pane opretWhiskyPane = loader.load();
        return opretWhiskyPane;
    }

    public Pane getLagerePane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/lagerePane/LagerePane.fxml"));
        Pane lagerePane = loader.load();
        return lagerePane;
    }

    public Pane getFadPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/fadePane/FadePane.fxml"));
        Pane fadePane = loader.load();
        return fadePane;
    }

    public Pane getDistillatPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/distillatPane/DistillatPane.fxml"));
        Pane distillatPane = loader.load();
        return distillatPane;
    }

    public Pane getPaafyldningPane() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/paafyldningPane/PaafyldningPane.fxml"));
        Pane paafyldningPane = loader.load();
        return paafyldningPane;
    }
}
