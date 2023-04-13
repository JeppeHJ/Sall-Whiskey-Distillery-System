package gui.views.lagerePane;

import application.Lager;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LagerePaneController {

    @FXML
    private ListView<Lager> lstLager;
    @FXML
    private Label lblAntalLagre;
    @FXML
    private Label lblTotalAntalFad;
    @FXML
    private TextField txtLagerLokation;
    @FXML
    private TextField txtLagerPladser;

    private final Controller controller = Controller.getController();

    public void initialize() {

        lstLager.setItems(getLagerList());
        lstLager.setEditable(false);
        lstLager.setMinHeight(200);

        lblTotalAntalFad.setText("Total antal fad: " + controller.totalAntalFad());
        lblAntalLagre.setText("Antal lagre: " + getLagerList().size());

    }

    private ObservableList<Lager> getLagerList() {
        return FXCollections.observableList(controller.getAlleLagre());
    }

    public void updateControls() {
        lstLager.setItems(getLagerList());
        lblAntalLagre.setText("Antal lagre: " + getLagerList().size());
        lblTotalAntalFad.setText("Total antal fad: " + controller.totalAntalFad());
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void btnOpretAction() {
        String lokation = txtLagerLokation.getText().trim();
        String pladserStr = txtLagerPladser.getText().trim();
        int pladser;

        if (lokation.isEmpty() || pladserStr.isEmpty()) {
            showErrorAlert("Udfyld venligst alle felter");
            return;
        }

        try {
            pladser = Integer.parseInt(pladserStr);
        } catch (NumberFormatException e) {
            showErrorAlert("Indtast venligst et gyldigt tal for pladser");
            return;
        }

        if (pladser <= 0) {
            showErrorAlert("Antallet af pladser skal være større end 0");
            return;
        }

        controller.opretLager(lokation, pladser);
        updateControls();
        clearFields();
    }

    @FXML
    private void btnOpdaterAction() {
        // TODO: Add update logic here
    }

    @FXML
    private void btnSletAction() {
        // TODO: Add delete logic here
    }

    private void clearFields() {
        txtLagerLokation.clear();
        txtLagerPladser.clear();
    }
}
