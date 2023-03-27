package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FadePane extends GridPane {
    private Controller controller = Controller.getController();
    private ListView<Lager> lstLager = new ListView<>();
    private Label lblNuværendeLager = new Label("Lager Liste:");
    private Label lblLokation = new Label("Lokation");
    private Label lblPladser = new Label("Pladser");
    private Label lblError = new Label();
    private TextField txtFadStr = new TextField();
    private TextField txtFadType = new TextField();


    public FadePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //----------------------------------------------list
        this.add(lstLager, 0, 1);
        this.lstLager.setEditable(false);
        this.lstLager.getItems().setAll(controller.getAlleLagre());
        this.lstLager.setMinHeight(200);



        //------------------------------------------------- Labels

        this.add(lblNuværendeLager, 0, 0);


        //------------------------------------------------button

        //-------------------------------------------------text


        //-------------------------------------------------






    }



    public void updateControls() {
        this.lstLager.getItems().setAll(controller.getAlleLagre());




    }
    private void btnOpretAction() {
        String typeFad = txtFadType.getText().trim();
        double fadStr = Double.parseDouble(txtFadStr.getText().trim());
        if (typeFad.length() == 0 || fadStr == 0 || txtFadStr.getText().trim().length() == 0) {
            lblError.setText("Udfyld venligst alle felter");
        } else {
            //TODO: noget med en listener
            controller.opretFad(typeFad,fadStr,controller.getLagerById(1) );
            this.updateControls();
        }
    }

}
