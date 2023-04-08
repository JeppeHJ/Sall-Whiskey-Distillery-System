package controller;

import application.*;
import storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller-klassen er ansvarlig for at administrere Lager og Fad objekter.
 * Den bruger Singleton designmønsteret for at sikre, at kun én instans
 * af Controller oprettes i løbet af programmets levetid.
 */
public class Controller {
    private static Controller controller;
    private Storage storage;

    private Controller() {
        storage = Storage.getStorage();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**-------------- LAGER METODER --------------**/

    // Opretter et nyt lager og tilføjer det til storage
    public Lager opretLager(String lokation, int antalPladser) {
        Lager lager = new Lager(lokation, antalPladser);
        storage.addLager(lager);
        return lager;
    }

    // Henter alle lagre fra storage
    public ArrayList<Lager> getAlleLagre() {
        return storage.getLagre();
    }

    // Henter en HashMap med fade for et givet lagerID
    public HashMap<Integer, Fad> getFadeIHashMap(int lagerId) {
        Lager lager = storage.getLagerById(lagerId);
        if (lager != null) {
            return lager.getFadeHashMap();
        }
        return new HashMap<>();
    }

    // Henter et lager med et bestemt ID fra storage
    public Lager getLagerById(int id) {
        return storage.getLagerById(id);
    }

    // Returnerer det samlede antal lagre
    public int totalAntalLager() {
        return storage.getLagre().size();
    }

    /**-------------- FAD METODER --------------**/

    // Opretter et nyt Fad objekt og tilføjer det til storage og lager
    public Fad opretFad(String fadType, double fadStr, Lager lager, int plads) {
        Fad fad = new Fad(fadType, fadStr);
        storage.getLagerById(lager.getId()).addFad(fad, plads);
        storage.addFad(fad, plads);
        return fad;
    }

    // Returnerer en liste af fade med færdiglagret væske
    public ArrayList<Fad> getFadeMedFærdigVæske() {
        ArrayList<Fad> fade = new ArrayList<>();
        for (Fad fad: storage.getFade()) {
            if (fad.erFaerdigLagret()) {
                fade.add(fad);
            }
        }
        return fade;
    }

    // Returnerer det samlede antal fade i alle lagre
    public int totalAntalFad() {
        int total = 0;
        for (Lager lager : storage.getLagre()) {
            total += lager.amountOfFade();
        }
        return total;
    }

    // Henter en liste af fade, der indeholder en bestemt LagretVæske
    public List<Fad> getBarrelsContainingLagretVaeske(LagretVæske lagretVaeske) {
        List<Fad> barrels = new ArrayList<>();
        for (Fad fad: storage.getFade()) {
            if (fad.getLagretVæsker().contains(lagretVaeske))
                barrels.add(fad);
            }
        return barrels;
    }

    public LagretVæske fyldPåSpecifiktFad(double liter, LocalDate påfyldningsDato, Fad fad, Distillat distillat) {
        validateFyldPåSpecifiktFadInput(liter, fad, distillat);
        LagretVæske valgtLagretVæske = opretLagretVæske(liter, påfyldningsDato, distillat);

        Fad valgtFad = storage.getFadById(fad.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        valgtFad.påfyldning(valgtLagretVæske, påfyldningsDato);
        valgtDistillat.setLiter(valgtDistillat.getLiter() - liter);
        return valgtLagretVæske;
    }

    private void validateFyldPåSpecifiktFadInput(double liter, Fad fad, Distillat distillat) {
        if (liter <= 0) {
            throw new IllegalArgumentException("Litermængden kan ikke være 0 eller under");
        }
        if (storage.getFadById(fad.getId()) == null) {
            throw new IllegalArgumentException("Fad kunne ikke findes i storage");
        }
        if (storage.getDistillatById(distillat.getId()) == null) {
            throw new IllegalArgumentException("Distillat kunne ikke findes i storage");
        }
        if (liter > (fad.getFadStr() - fad.getFadfyldning())) {
            throw new IllegalArgumentException("Du kan ikke fylde så meget på fadet.");
        }
    }

    public void fyldPåFlereFade(double liter, LocalDate påfyldningsDato, ArrayList<Fad> fade, Distillat distillat) {
        validateFyldPåFlereFadeInput(liter, distillat);
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());
        double literTilbage = liter;
        for (Fad fad : fade) {
            if (literTilbage > 0) {
                double fadCapacity = fad.getFadStr() - fad.getFadfyldning();
                double amountToFill = Math.min(fadCapacity, literTilbage);

                if (amountToFill > 0) {
                    LagretVæske lV = opretLagretVæske(amountToFill, påfyldningsDato, valgtDistillat);
                    fad.addLagretVæsker(lV);
                    lV.addFadTilHistorik(fad, påfyldningsDato);
                    literTilbage -= amountToFill;
                }
            }
        }
    }

    private void validateFyldPåFlereFadeInput(double liter, Distillat distillat) {
        if (liter <= 0) {
            throw new IllegalArgumentException("Litermængden kan ikke være 0 eller under");
        }
        if (storage.getDistillatById(distillat.getId()) == null) {
            throw new IllegalArgumentException("Distillat kunne ikke findes i storage");
        }
    }
    public LagretVæske opretLagretVæske(double liter, LocalDate påfyldningsDato, Distillat distillat) {
        validateOpretLagretVæskeInput(liter, distillat);
        LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
        lagretVæske.addDistillat(storage.getDistillatById(distillat.getId()));
        distillat.subtractFilledLiters(liter);
        storage.addLagretVæske(lagretVæske);
        return lagretVæske;
    }

    private void validateOpretLagretVæskeInput(double liter, Distillat distillat) {
        if (liter <= 0) {
            throw new IllegalArgumentException("Litermængden kan ikke være 0 eller under");
        }
        if (storage.getDistillatById(distillat.getId()) == null) {
            throw new IllegalArgumentException("Distillat kunne ikke findes i storage");
        }
    }

    public ArrayList<LagretVæske> getFaerdigLagretVaeske() {
        ArrayList<LagretVæske> færdigeVæsker = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for (LagretVæske væske : storage.getLagretVæsker()) {
            LocalDate påfyldningsDato = væske.getPåfyldningsDato();
            if (påfyldningsDato.plusYears(3).isBefore(currentDate) || påfyldningsDato.plusYears(3).isEqual(currentDate)) {
                færdigeVæsker.add(væske);
            }
        }
        return færdigeVæsker;
    }

    public HashMap<Fad, LocalDate> getFadehistorik(LagretVæske lagretVæske) {
        validateGetFadehistorikInput(lagretVæske);
        return storage.getLagretVæskeById(lagretVæske.getId()).getFadehistorik();
    }

    private void validateGetFadehistorikInput(LagretVæske lagretVæske) {
        if (storage.getLagretVæskeById(lagretVæske.getId()) == null) {
            throw new IllegalArgumentException("LagretVæske kunne ikke findes i storage");
        }
    }
    /**-------------- Distillat METODER --------------**/
    //TODO (30/03/2023 13:22)
    // Input-validering:
    // - liter ikke 0 eller -
    // - alkoholprocent ikke over 100 eller -
    // - add tilsvarende pre-condition(s) i selve klassen
    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale, LocalDate dato, String medarbejder) {
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale, dato, medarbejder);
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

    /** ------------- WHISKY METODER ------------- **/
    public Whisky opretWhisky(String navn, LocalDate påfyldningsDato, double alkoholprocent, double liter, Flasketype flasketype, LagretVæske væske, Fad fad, String vandKilde, double fortyndelsesProcent) {
        double fadStr = fad.getFadStr();
        double fadFyldning = fad.getFadfyldning();
        double capacity = fadStr - fadFyldning;
        System.out.println("Capacity: " + capacity);
        System.out.println("Liter: " + liter);
        if (liter > fadFyldning) {
            throw new IllegalArgumentException("Så meget indeholder fadet ikke.");
        }
        Whisky whisky = new Whisky(navn, påfyldningsDato, alkoholprocent, liter, flasketype, væske, vandKilde, fortyndelsesProcent);
        storage.addWhisky(whisky);
        System.out.println(fad.getFadfyldning());
        fad.reducereLagretVaeske(liter); // Reducerer mængden af LagretVæske i Fad
        return whisky;
    }

    public List<FadHistoryEntry> getFadHistoryForWhisky(Whisky whisky) {
        LagretVæske lagretVæske = whisky.getVæske();
        HashMap<Fad, LocalDate> fadHistory = lagretVæske.getFadehistorik();
        List<FadHistoryEntry> fadHistoryEntries = new ArrayList<>();

        for (Map.Entry<Fad, LocalDate> entry : fadHistory.entrySet()) {
            fadHistoryEntries.add(new FadHistoryEntry(entry.getKey(), entry.getValue()));
        }

        return fadHistoryEntries;
    }
    public ArrayList<Whisky> getWhisky() {
        ArrayList<Whisky> whiskyer = storage.getWhiskyer();

        if (whiskyer == null) {
            return new ArrayList<>();
        }
        return whiskyer;
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
        Distillat d1 = controller.opretDistillat(3000,"SortBatch73","Black",40.0, "Hmm", LocalDate.now(), "Niels");
        controller.fyldPåFlereFade(250,LocalDate.of(1990,3,3), fade, d1);
        LagretVæske lV = controller.fyldPåSpecifiktFad(30,LocalDate.of(1992,6,30),f3,d1);

    }
}

