package controller;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller-klassen er ansvarlig for at administrere Lager og Fad objekter.
 * Den bruger Singleton designmønsteret for at sikre, at kun én instans
 * af Controller oprettes i løbet af programmets levetid.
 */
public class Controller {
    private static Controller controller;
    private Storage storage;


    public Controller() {
        storage = Storage.getStorage();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**-------------- LAGER METODER --------------**/
    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - antalPladser ikke 0 eller -
    // - add tilsvarende pre-condition i selve klassen
    public Lager opretLager(String lokation, int antalPladser) {
        Lager lager = new Lager(lokation, antalPladser);
        storage.addLager(lager);
        return lager;
    }

    public ArrayList<Lager> getAlleLagre() {
        return storage.getLagre();
    }

    public ArrayList<Fad> getFadeILager(int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            return lager.getFade();
        }
        return new ArrayList<>();
    }

    public Lager getLagerById(int id) {
        return storage.getLagerById(id);
    }

    public int totalAntalLager() {
        return storage.getLagre().size();
    }

    /**-------------- FAD METODER --------------**/
    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - fadStr ikke - eller 0
    // - add tilsvarende pre-condition i selve klassen
    public Fad opretFad(String fadType, double fadStr, Lager lager, int plads) {
        Fad fad = new Fad(fadType, fadStr, plads);
        storage.getLagerById(lager.getId()).addFad(fad);
        storage.addFad(fad);
        return fad;
    }

    public int totalAntalFad() {
        int total = 0;
        for (Lager lager : storage.getLagre()) {
            total += lager.getFade().size();
        }
        return total;
    }

    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - liter ikke 0 eller -
    // - fad skal kunne findes i storage
    // - distillat skal kunne findes i storage
    // - add tilsvarende pre-condition i selve klassen
    public LagretVæske fyldPåSpecifiktFad(double liter, LocalDate påfyldningsDato, Fad fad, Distillat distillat) {
        LagretVæske valgtLagretVæske = controller.opretLagretVæske(liter,påfyldningsDato, distillat);
        storage.addLagretVæske(valgtLagretVæske);
        Fad valgtFad = storage.getFadById(fad.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        valgtFad.påfyldning(valgtLagretVæske, påfyldningsDato);
        valgtDistillat.setLiter(valgtDistillat.getLiter() - liter);
        return valgtLagretVæske;
    }

    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - liter må ikke være 0 eller -
    // - fade må ikke være empty
    // - distillat skal findes i storage
    public void fyldPåFlereFade(double liter, LocalDate påfyldningsDato, ArrayList<Fad> fade, Distillat distillat) {
        ArrayList<Fad> påfyldteFade = new ArrayList<>();
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());
        double literTilbage = liter;
        for (Fad fad : fade) {
            if (literTilbage > 0) {
                double fadCapacity = fad.getFadStr() - fad.getFadfyldning();
                double amountToFill = Math.min(fadCapacity, literTilbage);

                if (amountToFill > 0) {
                    LagretVæske lV = controller.opretLagretVæske(amountToFill, påfyldningsDato, valgtDistillat);
                    fad.addLagretVæsker(lV);
                    literTilbage -= amountToFill;
                    storage.addLagretVæske(lV);
                    påfyldteFade.add(fad);
                }
            }
        }
    }

    /**-------------- LagretVæske METODER --------------**/
    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - liter ikke være 0 eller minus
    // - liter ikke være over distillats liter
    // - distillat skal findes i storage
    // - add tilsvarende pre-condition i selve klassen
    public LagretVæske opretLagretVæske(double liter, LocalDate påfyldningsDato, Distillat distillat) {
        LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
        lagretVæske.addDistillat(storage.getDistillatById(distillat.getId()));
        distillat.subtractFilledLiters(liter); // Use the subtractFilledLiters method to update literTilbage
        storage.addLagretVæske(lagretVæske);
        return lagretVæske;
    }

    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - lagretVæske skal findes i storage
    public HashMap<Fad, LocalDate> getFadehistorik(LagretVæske lagretVæske) {
        return storage.getLagretVæskeById(lagretVæske.getId()).getFadehistorik();
    }

    /**-------------- Distillat METODER --------------**/
    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - liter ikke 0 eller -
    // - alkoholprocent ikke over 100 eller -
    // - add tilsvarende pre-condition(s) i selve klassen
    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale, LocalDate dato) {
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale, dato);
        storage.addDistillat(distillat);
        return distillat;
    }

    public ArrayList<Distillat> getDistillaterMedActualVaeske() {
        ArrayList<Distillat> distillaterMedVæske = new ArrayList<>();
        for (Distillat distillat: storage.getDistillater()) {
            if (distillat.getLiter() > 0) {
                distillaterMedVæske.add(distillat);
            }
        }
        return distillaterMedVæske;
    }




    public void createSomeObjects() {
        Lager l1 = controller.opretLager("Aarhus",100);
        Fad f1 = controller.opretFad("Grim",250.0, l1, 1);
        Fad f2 = controller.opretFad("Grim",30.0, l1, 2);
        Fad f3 = controller.opretFad("Grim",30.0, l1, 3);
        Fad f4 = controller.opretFad("Grim",30.0, l1,4 );
        Fad f5 = controller.opretFad("Grim",50.0, l1, 5);
        Fad f6 = controller.opretFad("Grim",50.0, l1, 6);
        ArrayList<Fad> fade = new ArrayList<>();
        fade.add(f1);
        fade.add(f2);
        fade.add(f3);
        fade.add(f4);
        fade.add(f5);
        fade.add(f6);
        Distillat d1 = controller.opretDistillat(300,"SortBatch73","Black",40.0, "Hmm", LocalDate.now());
        controller.fyldPåFlereFade(250,LocalDate.now(), fade, d1);

    }
}

