package controller;

import application.Fad;
import application.Lager;
import storage.Storage;

import java.util.ArrayList;

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

    /**
     * Returnerer singleton-instansen af Controller-klassen.
     *
     * @return singleton-instansen af Controller-klassen.
     */
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }


    public void opretLager(String lokation, int antalPladser) {
        Lager lager = new Lager(lokation, antalPladser);
        storage.addLager(lager);
    }


    public void opretFad(String fadType, double fadStr, String newSpiritBatchNr, double antalLiterPåFyldt, double alkoholProcent, String medarbejderinitialer, int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            if (antalLiterPåFyldt > fadStr) {
                throw new RuntimeException("Du kan ikke fylde flere liter på, end der er plads til");
            }

            if (alkoholProcent > 100) {
                throw new RuntimeException("Alkohol kan ikke udgøre mere end 100%");
            }

            if ()

            Fad fad = new Fad(fadType, fadStr, newSpiritBatchNr, antalLiterPåFyldt, alkoholProcent, medarbejderinitialer, lager);
            storage.getLagerById(lagerId).addFad(fad);
        }
    }

    public void opretTomtFad(String fadType, double fadStr, int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            Fad fad = new Fad(fadType, fadStr, lager);
            storage.getLagerById(lagerId).addFad(fad);
        }
    }

    public ArrayList<Lager> getAlleLagre() {
        return storage.getLagre();
    }

    /**
     * Returnerer en liste over alle Fad objekter i det angivne Lager.
     *
     * @param lagerId ID'et for det Lager, hvis fade skal hentes.
     * @return en ArrayList af Fad objekter.
     */
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

}

