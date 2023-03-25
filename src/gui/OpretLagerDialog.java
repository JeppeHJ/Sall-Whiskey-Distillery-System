package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class OpretLagerDialog extends Stage {

    private Button btnOpret = new Button("Opret");
    private Button btnFortryd = new Button("Fortryd");
    private Controller controller;
    private Label lblError = new Label();
    private TextField txtLagerNavn = new TextField();
    private TextField txtLagerAdresse = new TextField();

    public OpretLagerDialog() {
        this.controller = Controller.getController();
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.setTitle("Opret lager");

        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        this.initContent(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(20);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
//        -------------------------------------
pane.add(new Label("Lager adresse:"), 0, 0);
        pane.add(txtLagerNavn, 1, 0);
        pane.add(new Label("Lager pladser:"), 0, 1);
        pane.add(txtLagerAdresse, 1, 1);
        pane.add(btnOpret, 0, 2);
        pane.add(btnFortryd, 1, 2);
        pane.add(lblError, 0, 3, 2, 1);
        btnOpret.setOnAction(event -> btnOpretAction());
        btnFortryd.setOnAction(event -> this.hide());
    }
//todo fik if statement og update a listview
    private void btnOpretAction() {
        String lokation = txtLagerNavn.getText().trim();
        int pladser = Integer.parseInt(txtLagerAdresse.getText().trim());
        if (lokation.length() == 0 || pladser ==0) {
            lblError.setText("Udfyld venligst alle felter");
            return;
        }
        controller.opretLager(lokation,pladser);
        this.hide();
    }}

