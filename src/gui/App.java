package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import controller.Controller;
import javafx.application.Application;

import java.time.LocalDate;

public class App {
    private static Controller controller = Controller.getController();

    public static void main(String[] args) {
        //Application.launch(StartWindow.class);
        Lager l1 = controller.opretLager("Aarhus",100);
        Fad f1 = controller.opretFad("Grim",50.0, l1);
        LagretVæske lV1 = controller.opretLagretVæske(30, LocalDate.now());
        Distillat d1 = controller.opretDistillat(300,"SortBatch73","Black",40.0, "Hmm");

        controller.fyldPåSpecifiktFad(30,LocalDate.now(),f1,d1, lV1);
        System.out.println(lV1.getFadehistorik());




    }
}
