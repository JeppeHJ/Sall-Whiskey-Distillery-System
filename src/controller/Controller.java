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

    public Fad opretFad(String fadType, double fadStr, Lager lager) {
        Fad fad = new Fad(fadType, fadStr);
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

    //many testcases - remove return (for testing purposes atm)
    public LagretVæske fyldPåSpecifiktFad(double liter, LocalDate påfyldningsDato, Fad fad, Distillat distillat) {
        LagretVæske valgtLagretVæske = controller.opretLagretVæske(liter,påfyldningsDato, distillat);
        storage.addLagretVæske(valgtLagretVæske);
        Fad valgtFad = storage.getFadById(fad.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        valgtFad.påfyldning(valgtLagretVæske, påfyldningsDato);
        valgtDistillat.setLiter(valgtDistillat.getLiter() - liter);
        return valgtLagretVæske;
    }

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

    //TODO Håndter oprettelse af lagretVæske
    public LagretVæske opretLagretVæske(double liter, LocalDate påfyldningsDato, Distillat distillat) {
        LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
        lagretVæske.addDistillat(distillat);
        distillat.subtractFilledLiters(liter); // Use the subtractFilledLiters method to update literTilbage
        storage.addLagretVæske(lagretVæske);
        return lagretVæske;
    }

    public HashMap<Fad, LocalDate> getFadehistorik(LagretVæske lagretVæske) {
        return storage.getLagretVæskeById(lagretVæske.getId()).getFadehistorik();
    }

    /**-------------- Distillat METODER --------------**/

    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale) {
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale);
        storage.addDistillat(distillat);
        return distillat;
    }




    public void createSomeObjects() {
    }
}

