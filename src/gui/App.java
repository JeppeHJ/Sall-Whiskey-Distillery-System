package gui;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import controller.Controller;
import javafx.application.Application;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    private static Controller controller = Controller.getController();

    public static void main(String[] args) {
        //Application.launch(StartWindow.class);
        Lager l1 = controller.opretLager("Aarhus",100);
        Fad f1 = controller.opretFad("Grim",250.0, l1);
        Fad f2 = controller.opretFad("Grim",30.0, l1);
        Fad f3 = controller.opretFad("Grim",30.0, l1);
        Fad f4 = controller.opretFad("Grim",30.0, l1);
        Fad f5 = controller.opretFad("Grim",50.0, l1);
        Fad f6 = controller.opretFad("Grim",50.0, l1);
        ArrayList<Fad> fade = new ArrayList<>();
        fade.add(f1);
        fade.add(f2);
        fade.add(f3);
        fade.add(f4);
        fade.add(f5);
        fade.add(f6);
        Distillat d1 = controller.opretDistillat(300,"SortBatch73","Black",40.0, "Hmm");
        controller.fyldPåFlereFade(250,LocalDate.now(), fade, d1);
        System.out.println("f1 " + f1.getCurrentCapacity());
        System.out.println("f2 " + f2.getCurrentCapacity());
        System.out.println("f3 " + f3.getCurrentCapacity());
        System.out.println("f4 " + f4.getCurrentCapacity());
        System.out.println("f5 " + f5.getCurrentCapacity());
        System.out.println("f6 " + f6.getCurrentCapacity());
        System.out.println("Liter tilbage: " + d1.getLiterTilbage());






    }
}
