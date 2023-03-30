package gui;

import application.Lager;
import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FadePane extends GridPane {
    private Controller controller = Controller.getController();
    private TextField txtxFadtype = new TextField();
    private TextField txtfadStr= new TextField();
    private Label lblfadType= new Label("Fad Type");
    private Label lblfadStr = new Label("Fad størrelse");
    private Label lblvealglager = new Label("Vælg lager:");
    private Label lblError = new Label();
    private Label lbltotalfad=new Label("Total fade: "+controller.totalAntalFad());
    private ComboBox comboBoxLager = new ComboBox<>();
    private Button btnopretfad = new Button("Opret fad");






    public FadePane() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //----------------------------------------------list
        this.comboBoxLager.getItems().addAll(controller.getAlleLagre());
        this.add( comboBoxLager, 3, 0);



        //------------------------------------------------- Labels
        this.add(lblfadStr,0,0);
        this.add(lblfadType,0,1);
        this.add(lblvealglager,2,0);
        this.add(lblError,3,2);
        lblError.setStyle("-fx-text-fill: red;");
        this.add(lbltotalfad, 0, 7);

        //------------------------------------------------button
        this.add(btnopretfad,3,1);
        btnopretfad.setOnAction(event -> btnOpretAction());

        //-------------------------------------------------text
        this.add(txtfadStr,1,0);
        this.add(txtxFadtype, 1, 1);


        //-------------------------------------------------






    }
    //TODO lav input validering
    private void btnOpretAction() {
        lblError.setText("");
        Lager lager = (Lager)comboBoxLager.getValue();
        String fadtype = txtxFadtype.getText().trim();
        int fadStr = Integer.parseInt(txtfadStr.getText().trim());
        if (fadtype.length() == 0 || fadStr == 0 || txtfadStr.getText().trim().length() == 0) {
            lblError.setText("Udfyld venligst alle felter");
        } else {
            controller.opretFad(fadtype, fadStr, lager);
            txtfadStr.clear();
            txtxFadtype.clear();
            comboBoxLager.getSelectionModel().clearSelection();
            this.updateControls();
        }
    }

    public void updateControls() {
        lbltotalfad.setText("Total fade: "+controller.totalAntalFad());
        comboBoxLager.getItems().addAll(controller.getAlleLagre());


    }

}
