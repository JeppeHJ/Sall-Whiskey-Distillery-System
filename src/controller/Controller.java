package controller;

import application.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.*;

/**
 * Controller-klassen
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

    /**
     * -------------- LAGER METODER --------------
     **/

    // Opretter et nyt lager og tilføjer det til storage
    public Lager opretLager(String lokation, int antalPladser) {
        if (antalPladser < 0) {
            throw new IllegalArgumentException("Der kan ikke være negative pladser på et lager");
        }
        if (lokation == null) {
            throw new IllegalArgumentException("Lokation være udfyldt");
        }

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

    /**
     * -------------- FAD METODER --------------
     **/

    // Opretter et nyt Fad objekt og tilføjer det til storage og lager
    public Fad opretFad(String fadType, double fadStr, Lager lager, int plads) {
        if (fadType == null || fadType.isEmpty()) {
            throw new IllegalArgumentException("Fad type må ikke være null eller tom.");
        }
        if (fadStr <= 0) {
            throw new IllegalArgumentException("Fad størrelse skal være større end 0.");
        }
        if (lager == null) {
            throw new IllegalArgumentException("Lager må ikke være null.");
        }
        if (plads < 0) {
            throw new IllegalArgumentException("Plads skal være større eller lig med 0.");
        }
        Fad fad = new Fad(fadType, fadStr);
        // Tilføj fad til lager og storage
        storage.getLagerById(lager.getId()).addFad(fad, plads);
        storage.addFad(fad, plads);
        return fad;
    }

    public ArrayList<Fad> getAlleFad() {
        return storage.getFade();
    }

    public ArrayList<Fad> getAlleFadDerKanOmhældesTil(Fad inputFad) {
        ArrayList<Fad> fade = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad != inputFad) {
                if (fad.getFadStr() - fad.getFadfyldning() != 0) {
                    fade.add(fad);
                }
            }

        }
        return fade;
    }

    public ArrayList<Fad> getAlleFadDerKanOmhældesFra() {
        ArrayList<Fad> fade = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getFadStr() - fad.getFadfyldning() != fad.getFadStr()) {
                fade.add(fad);
            }
        }
        return fade;
    }

    // Returnerer en liste af fade med færdiglagret væske
    public ArrayList<Fad> getFadeMedFærdigVæske() {
        ArrayList<Fad> fade = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.erFaerdigLagret()) {
                fade.add(fad);
            }
        }
        return fade;
    }

    public LagretVæske opretNyLagretVæskeOmhældning(Fad kilde, Fad destination, double mængde, LocalDate dato) {
        if (kilde == null) {
            throw new IllegalArgumentException("Kildefad må ikke være null.");
        }

        if (destination == null) {
            throw new IllegalArgumentException("Destinationsfad må ikke være null.");
        }

        if (mængde <= 0) {
            throw new IllegalArgumentException("Mængde skal være større end 0.");
        }

        if (dato == null) {
            throw new IllegalArgumentException("Dato må ikke være null.");
        }

        ArrayList<Distillat> kildeDestillatListe = kilde.getLagretVæsker().get(0).getDistillater();
        ArrayList<Distillat> destinationDestillatListe = null;
        LagretVæske destLv = null;
        if (!(destination.getLagretVæsker().isEmpty())) {
            destinationDestillatListe = destination.getLagretVæsker().get(0).getDistillater();
            destLv = destination.getLagretVæsker().get(0);
        }
        // Cojoin the lists into a new
        LagretVæske kildeLv = kilde.getLagretVæsker().get(0);
        LagretVæske nyeVæske = controller.opretLagretVæske(mængde + destination.getFadfyldning(), dato, null, kildeDestillatListe, destinationDestillatListe, kildeLv, destLv);

        kilde.omhældning(kilde, destination, mængde, nyeVæske);

        // Handle væsken i kilde-fad
        if (kilde.getFadfyldning() == 0) {
            controller.tømtFad(kilde, nyeVæske, dato);
        }
        return nyeVæske;
    }

    private void tømtFad(Fad fad, LagretVæske nyeVæske, LocalDate dato) {
        // Fad skal have et FadsLagretVæskeHistorik objekt med lagretvæske, påfyldningsdato, final omhældningsdato
        fad.editHistorikNårFadErTømt(fad.getLagretVæsker().get(0), dato);

        // Kilde Fad er nu tomt i systemet
        fad.removeLagretVæsker(fad.getLagretVæsker().get(0));


    }

    // Returnerer det samlede antal fade i alle lagre
    public int totalAntalFad() {
        int total = 0;
        for (Lager lager : storage.getLagre()) {
            total += lager.amountAfFade();
        }
        return total;
    }

    // Henter en liste af fade, der indeholder en bestemt LagretVæske
    public List<Fad> getFadDerIndholder(LagretVæske lagretVaeske) {
        List<Fad> barrels = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getLagretVæsker().contains(lagretVaeske))
                barrels.add(fad);
        }
        return barrels;
    }

    // Metode til at fylde et specifikt Fad op med en given mængde af lagret væske fra et distillat
    public LagretVæske fyldPåSpecifiktFad(double liter, LocalDate påfyldningsDato, Fad fad, Distillat distillat) {
        // Validerer input for liter, fad og distillat
        validateFyldPåSpecifiktFadInput(liter, fad, distillat);

        // Opretter et nyt LagretVæske objekt med den ønskede mængde og dato for påfyldning
        LagretVæske valgtLagretVæske = opretLagretVæske(liter, påfyldningsDato, distillat, null, null, null, null);

        // Finder det ønskede Fad og Distillat i storage
        Fad valgtFad = storage.getFadById(fad.getId());
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        // Påføres det valgte LagretVæske objekt til det valgte Fad
        valgtFad.påfyldning(valgtLagretVæske, påfyldningsDato);

        // Opdaterer mængden af distillat efter påfyldning
        valgtDistillat.setLiter(valgtDistillat.getLiter() - liter);

        // Returnerer det opdaterede LagretVæske objekt
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

    // Fylder flere fade op med en given mængde lagret væske fra et distillat
    public void fyldPåFlereFade(double liter, LocalDate påfyldningsDato, ArrayList<Fad> fade, Distillat distillat) {
        // Validerer input
        validateFyldPåFlereFadeInput(liter, distillat);

        // Finder det ønskede Distillat
        Distillat valgtDistillat = storage.getDistillatById(distillat.getId());

        // Initialiserer variabelen for mængden af væske, der skal fyldes på
        double literTilbage = liter;

        // Loop igennem listen af fade
        for (Fad fad : fade) {
            // Tjekker om der er væske tilbage at fylde på
            if (literTilbage > 0) {
                // Beregner ledige kapacitet og mængden af væske, der skal fyldes på dette fad
                double fadCapacity = fad.getFadStr() - fad.getFadfyldning();
                double amountToFill = Math.min(fadCapacity, literTilbage);

                // Fylder væske på dette fad, hvis nødvendigt
                if (amountToFill > 0) {
                    // Opretter og tilføjer LagretVæske til fad og historik
                    LagretVæske lV = opretLagretVæske(amountToFill, påfyldningsDato, valgtDistillat, null, null, null, null);
                    fad.addLagretVæsker(lV);
                    lV.addFadTilHistorik(fad, påfyldningsDato, null);

                    // Opdaterer mængden af væske, der er tilbage at fylde på
                    literTilbage -= amountToFill;
                }
            }
        }
    }

    // Validerer input for fyldPåFlereFade metoden
    private void validateFyldPåFlereFadeInput(double liter, Distillat distillat) {
        // Tjekker om litermængden er positiv
        if (liter <= 0) {
            throw new IllegalArgumentException("Litermængden kan ikke være 0 eller under");
        }
        // Tjekker om distillatet findes i storage
        if (storage.getDistillatById(distillat.getId()) == null) {
            throw new IllegalArgumentException("Distillat kunne ikke findes i storage");
        }
    }

    // Opretter og tilføjer en ny LagretVæske til storage
    public LagretVæske opretLagretVæske(double liter, LocalDate påfyldningsDato, Distillat distillat, ArrayList<Distillat> kildeDestillatListe, ArrayList<Distillat> destinationDestillatListe, LagretVæske kildeLagretVæske, LagretVæske destinationLagretVæske) {
        if (liter < 0) {
            throw new IllegalArgumentException("Mængde skal være større eller lig 0");
        }
        if (påfyldningsDato == null) {
            throw new IllegalArgumentException("Påfyldningsdato kan ikke være null");
        }
        if (distillat == null&& kildeDestillatListe ==null) {
            throw new IllegalArgumentException("Distillat kan ikke være null");
        }
        // hvis den lagrede væske oprettes fra et destillat basically
        if (kildeDestillatListe == null && destinationDestillatListe == null) {
            // Opretter ny LagretVæske
            //TODO paafyldningsDato has to be AFTER distillat.DatoForDone.
            LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
            // Tilføjer distillat til LagretVæske
            lagretVæske.addDistillat(storage.getDistillatById(distillat.getId()));
            // Opdaterer distillatets liter
            distillat.subtractFilledLiters(liter);
            // Tilføjer LagretVæske til storage
            storage.addLagretVæske(lagretVæske);
            return lagretVæske;
        } else {
            if (kildeDestillatListe == null) {
                throw new IllegalArgumentException("KildeDestillatListe kan ikke være null");
            }
            if (destinationDestillatListe == null) {
                throw new IllegalArgumentException("DestinationDestillatListe kan ikke være null");
            }
            if (kildeLagretVæske == null) {
                throw new IllegalArgumentException("KildeLagretVæske kan ikke være null");
            }
            if (destinationLagretVæske == null) {
                throw new IllegalArgumentException("DestinationLagretVæske kan ikke være null");
            }
            // Opretter det nye LagretVæske objekt
            // tilføjer de kombinerede distillater til den nye LagretVæskes distillatliste
            LagretVæske lagretVæske = new LagretVæske(liter, påfyldningsDato);
            ArrayList<Distillat> nyListeAfDistillater = joinDistillatLists(kildeDestillatListe, destinationDestillatListe);
            ArrayList<LagretVæskesFadHistorik> nyListeAfLagretVæskesFadHistorik = kildeLagretVæske.getFadehistorik();
            if (destinationLagretVæske != null) {
                nyListeAfLagretVæskesFadHistorik = joinFadHistory(kildeLagretVæske.getFadehistorik(), destinationLagretVæske.getFadehistorik());
                // Co-join the lists of LagretVæskesFadHistorik object stored in kilde and dest
            }
            lagretVæske.addFadHistorikker(nyListeAfLagretVæskesFadHistorik);
            lagretVæske.editHistoryNårDerOmhældes(kildeLagretVæske, påfyldningsDato);
            lagretVæske.addDestillater(nyListeAfDistillater);
            storage.addLagretVæske(lagretVæske);
            return lagretVæske;

        }


    }

    private ArrayList<Distillat> joinDistillatLists(ArrayList<Distillat> kilde, ArrayList<Distillat> destination) {
        ArrayList<Distillat> combinedList = new ArrayList<>(kilde);

        if (destination != null) {
            // Add all elements from both lists
            combinedList.addAll(kilde);
            combinedList.addAll(destination);

            // Sort the combined list by the getDatoForDone method
            Collections.sort(combinedList, (d1, d2) -> {
                LocalDate date1 = d1.getDatoForDone();
                LocalDate date2 = d2.getDatoForDone();
                return date1.compareTo(date2);
            });
            return combinedList;

        }
        return combinedList;
    }

    private ArrayList<LagretVæskesFadHistorik> joinFadHistory(ArrayList<LagretVæskesFadHistorik> kilde, ArrayList<LagretVæskesFadHistorik> destination) {
        ArrayList<LagretVæskesFadHistorik> combinedList = new ArrayList<>(kilde);

        if (destination != null) {
            // Add all elements from both lists
            combinedList.addAll(kilde);
            combinedList.addAll(destination);

            // Sort the combined list by the getDatoForDone method
            Collections.sort(combinedList, (d1, d2) -> {
                LocalDate date1 = d1.getFraDato();
                LocalDate date2 = d2.getFraDato();
                return date1.compareTo(date2);
            });

            return combinedList;

        }
        return combinedList;
    }


    // Validerer input for opretLagretVæske metoden
    private void validereOpretLagretVæskeInput(double liter, Distillat distillat) {
        if (liter <= 0) {
            throw new IllegalArgumentException("Litermængden kan ikke være 0 eller under");
        }
        if (storage.getDistillatById(distillat.getId()) == null) {
            throw new IllegalArgumentException("Distillat kunne ikke findes i storage");
        }
    }

    // Henter færdige LagretVæsker baseret på deres alder
    public ArrayList<LagretVæske> getFaerdigLagretVaeske() {
        ArrayList<LagretVæske> færdigeVæsker = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        // Gennemgår alle lagrede væsker
        for (LagretVæske væske : getAktiveLagredeVæsker()) {
            LocalDate påfyldningsDato = væske.getPåfyldningsDato();

            // Tjekker om væsken har lagret i mindst 3 år
            if (påfyldningsDato.plusYears(3).isBefore(currentDate) || påfyldningsDato.plusYears(3).isEqual(currentDate)) {
                færdigeVæsker.add(væske);
            }
        }
        return færdigeVæsker;
    }


    public List<Fad> getFaerdigLagretVæskePåFad(LagretVæske lagretVaeske) {
        List<Fad> barrels = new ArrayList<>();
        for (Fad fad : storage.getFade()) {
            if (fad.getLagretVæsker().contains(lagretVaeske))
                barrels.add(fad);
        }
        return barrels;
    }

    public boolean checkOmFærdigLagretVæskeHarFad(LagretVæske lagretVaeske) {
        boolean harFad = false;
        for (Fad fad : storage.getFade()) {
            if (fad.getLagretVæsker().contains(lagretVaeske)) {
                harFad = true;
            }

        }
        return harFad;
    }

    public ArrayList<LagretVæske> getAktiveLagredeVæsker() {
        ArrayList<LagretVæske> aktiveVæsker = new ArrayList<>();
        for (LagretVæske væske : storage.getLagretVæsker()) {
            if (checkOmFærdigLagretVæskeHarFad(væske) && væske.getLiter() != 0) {
                aktiveVæsker.add(væske);
            }
        }

        return aktiveVæsker;
    }

    // Henter fadehistorik for en LagretVæske
    public ArrayList<LagretVæskesFadHistorik> getFadehistorik(LagretVæske lagretVæske) {
        // Validerer input
        validateGetFadehistorikInput(lagretVæske);
        // Henter fadehistorik for LagretVæske
        return storage.getLagretVæskeById(lagretVæske.getId()).getFadehistorik();
    }

    // Validerer input for Fadehistorik
    private void validateGetFadehistorikInput(LagretVæske lagretVæske) {
        if (storage.getLagretVæskeById(lagretVæske.getId()) == null) {
            throw new IllegalArgumentException("LagretVæske kunne ikke findes i storage");
        }
    }

    /**
     * -------------- Distillat METODER --------------
     **/

// Opretter et nyt Distillat og tilføjer det til storage
    public Distillat opretDistillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale, LocalDate dato, String medarbejder) {
        if (liter <= 0) {
            throw new IllegalArgumentException("Liter skal være større end 0.");
        }

        if (maltBatch == null || maltBatch.isEmpty()) {
            throw new IllegalArgumentException("Malt batch må ikke være null eller tom.");
        }

        if (kornsort == null || kornsort.isEmpty()) {
            throw new IllegalArgumentException("Kornsort må ikke være null eller tom.");
        }

        if (alkoholprocent <= 0 || alkoholprocent > 100) {
            throw new IllegalArgumentException("Alkoholprocent skal være mellem 0 og 100.");
        }

        if (rygemateriale == null || rygemateriale.isEmpty()) {
            throw new IllegalArgumentException("Rygemateriale må ikke være null eller tom.");
        }

        if (dato == null) {
            throw new IllegalArgumentException("Dato må ikke være null.");
        }

        if (medarbejder == null || medarbejder.isEmpty()) {
            throw new IllegalArgumentException("Medarbejder må ikke være null eller tom.");
        }
        // Opretter nyt Distillat
        Distillat distillat = new Distillat(liter, maltBatch, kornsort, alkoholprocent, rygemateriale, dato, medarbejder);
        // Tilføjer Distillat til storage
        storage.addDistillat(distillat);
        return distillat;
    }

    // Henter en liste over Distillater med væske tilbage
    public ArrayList<Distillat> getDistillaterMedFaktiskVæske() {
        ArrayList<Distillat> distillaterMedVæske = new ArrayList<>();
        // Gennemgår alle distillater i storage
        for (Distillat distillat : storage.getDistillater()) {
            // Tjekker om der er væske tilbage i distillatet
            if (distillat.getLiter() > 0) {
                distillaterMedVæske.add(distillat);
            }
        }
        return distillaterMedVæske;
    }

    /**
     * ------------- WHISKY METODER -------------
     **/

    // Opretter en ny Whisky og tilføjer den til storage
    public Whisky opretWhisky(String navn, LocalDate påfyldningsDato, double liter, Flasketype flasketype, LagretVæske væske, Fad fad, String vandKilde, double fortyndelsesProcent) {
        // Beregner kapaciteten i fadet
        // Input validering
        if (navn == null || navn.isEmpty()) {
            throw new IllegalArgumentException("Navn må ikke være null eller tom.");
        }

        if (påfyldningsDato == null) {
            throw new IllegalArgumentException("Påfyldningsdato må ikke være null.");
        }

        if (liter <= 0) {
            throw new IllegalArgumentException("Liter skal være større end 0.");
        }

        if (flasketype == null) {
            throw new IllegalArgumentException("Flasketype må ikke være null.");
        }

        if (væske == null) {
            throw new IllegalArgumentException("Lagret væske må ikke være null.");
        }

        if (fad == null) {
            throw new IllegalArgumentException("Fad må ikke være null.");
        }

        if (vandKilde == null || vandKilde.isEmpty()) {
            throw new IllegalArgumentException("Vandkilde må ikke være null eller tom.");
        }

        if (fortyndelsesProcent < 0 || fortyndelsesProcent > 100) {
            throw new IllegalArgumentException("Fortyndelsesprocent skal være mellem 0 og 100.");
        }
        double fadStr = fad.getFadStr();
        double fadFyldning = fad.getFadfyldning();
        double capacity = fadStr - fadFyldning;

        // Validerer litermængden
        if (liter > fadFyldning) {
            throw new IllegalArgumentException("Så meget indeholder fadet ikke.");
        }

        // Opretter ny Whisky
        Whisky whisky = new Whisky(navn, påfyldningsDato, liter, flasketype, væske, vandKilde, fortyndelsesProcent);
        // Tilføjer Whisky til storage
        storage.addWhisky(whisky);

        // Opdaterer LagretVæske i Fad
        fad.reducereLagretVaeske(liter);

        return whisky;
    }

    // Henter fadhistorikken for en given Whisky
    public List<LagretVæskesFadHistorik> getFadHistoryForWhisky(Whisky whisky) {
        // Henter LagretVæske fra Whisky
        LagretVæske lagretVæske = whisky.getVæske();
        // Henter fadhistorikken for LagretVæske
        ArrayList<LagretVæskesFadHistorik> fadHistory = lagretVæske.getFadehistorik();

        return fadHistory;
    }

    // Henter en liste over alle Whisky i storage
    public ArrayList<Whisky> getWhisky() {
        // Henter listen over whiskyer
        ArrayList<Whisky> whiskyer = storage.getWhiskyer();

        // Hvis der ikke er nogen whiskyer, returner en tom liste
        if (whiskyer == null) {
            return new ArrayList<>();
        }

        return whiskyer;
    }

    public void createSomeObjects() {
        Lager l1 = controller.opretLager("Aarhus", 100);
        Lager l2 = controller.opretLager("Copenhagen", 80);

        Fad f1 = controller.opretFad("Oak", 250.0, l1, 1);
        Fad f2 = controller.opretFad("Oak", 500.0, l1, 2);
        Fad f3 = controller.opretFad("Oak", 500.0, l1, 3);
        Fad f4 = controller.opretFad("Oak", 500.0, l1, 4);
        Fad f5 = controller.opretFad("Oak", 500.0, l1, 5);
        Fad f6 = controller.opretFad("Oak", 500.0, l1, 6);

        ArrayList<Fad> fade = new ArrayList<>();
        fade.add(f1);
        fade.add(f2);
        fade.add(f3);
        fade.add(f4);
        fade.add(f5);
        fade.add(f6);

        Distillat d1 = controller.opretDistillat(3000, "Batch73", "Rye", 40.0, "Peat", LocalDate.of(2022, 6, 30), "Niels");
        Distillat d2 = controller.opretDistillat(3000, "Batch39", "Wheat", 40.0, "Peat", LocalDate.of(1992, 6, 30), "Anders");
        Distillat d3 = controller.opretDistillat(3000, "Batch41", "Barley", 40.0, "Peat", LocalDate.of(2000, 6, 30), "Kent");

        LagretVæske lV1 = controller.fyldPåSpecifiktFad(100, LocalDate.of(1992, 6, 30), f1, d1);
        LagretVæske lV2 = controller.fyldPåSpecifiktFad(100, LocalDate.of(1982, 6, 30), f2, d2);
        LagretVæske lV3 = controller.fyldPåSpecifiktFad(100, LocalDate.of(2020, 6, 30), f3, d3);
        LagretVæske lV4 = controller.fyldPåSpecifiktFad(100, LocalDate.of(2015, 6, 30), f4, d1);
        LagretVæske lV5 = controller.fyldPåSpecifiktFad(100, LocalDate.of(2011, 6, 30), f5, d2);
        LagretVæske lV6 = controller.fyldPåSpecifiktFad(100, LocalDate.of(2009, 6, 30), f6, d3);

        // Add more test objects
        Fad f7 = controller.opretFad("Oak", 100.0, l2, 7);
        Fad f8 = controller.opretFad("Oak", 100.0, l2, 8);

        Fad f9 = controller.opretFad("Oak", 100.0, l1, 9);
        Fad f10 = controller.opretFad("Oak", 100.0, l1, 10);
        Fad f11 = controller.opretFad("Oak", 100.0, l1, 11);
        Fad f12 = controller.opretFad("Oak", 100.0, l1, 12);

        Distillat d4 = controller.opretDistillat(3000, "Batch55", "Corn", 40.0, "Peat", LocalDate.of(2005, 6, 30), "Mads");
        Distillat d5 = controller.opretDistillat(3000, "Batch60", "Rye", 45.0, "Peat", LocalDate.of(2010, 6, 30), "Rasmus");

        LagretVæske lV7 = controller.fyldPåSpecifiktFad(50, LocalDate.of(2005, 6, 30), f7, d4);
        LagretVæske lV8 = controller.fyldPåSpecifiktFad(50, LocalDate.of(2010, 6, 30), f8, d5);
        LagretVæske lV9 = controller.fyldPåSpecifiktFad(50, LocalDate.of(1998, 6, 30), f9, d1);
        LagretVæske lV10 = controller.fyldPåSpecifiktFad(50, LocalDate.of(2002, 6, 30), f10, d2);
        LagretVæske lV11 = controller.fyldPåSpecifiktFad(50, LocalDate.of(2018, 6, 30), f11, d3);
        LagretVæske lV12 = controller.fyldPåSpecifiktFad(50, LocalDate.of(2019, 6, 30), f12, d1);

        Distillat d6 = controller.opretDistillat(3000, "Batch78", "Malted Barley", 43.0, "Peat", LocalDate.of(2015, 6, 30), "Søren");
        Distillat d7 = controller.opretDistillat(3000, "Batch90", "Rye", 48.0, "Peat", LocalDate.of(2018, 6, 30), "Frederik");

        LagretVæske lvOmhældning1 = controller.opretNyLagretVæskeOmhældning(f1, f7, 10, LocalDate.of(2023, 1, 1));
        LagretVæske lvOmhældning2 = controller.opretNyLagretVæskeOmhældning(f2, f8, 20, LocalDate.of(2023, 1, 15));
        LagretVæske lvOmhældning3 = controller.opretNyLagretVæskeOmhældning(f3, f9, 15, LocalDate.of(2023, 2, 1));
        LagretVæske lvOmhældning4 = controller.opretNyLagretVæskeOmhældning(f4, f10, 25, LocalDate.of(2023, 2, 15));
        LagretVæske lvOmhældning5 = controller.opretNyLagretVæskeOmhældning(f5, f11, 30, LocalDate.of(2023, 3, 1));
        LagretVæske lvOmhældning6 = controller.opretNyLagretVæskeOmhældning(f6, f12, 40, LocalDate.of(2023, 3, 15));

        Whisky whisky1 = controller.opretWhisky("Aarhus Single Malt", LocalDate.of(2023, 4, 1), Flasketype.LILLE.getCapacity(), Flasketype.LILLE, lV1, f1, "Aarhus Spring", 46.0);
        Whisky whisky2 = controller.opretWhisky("Copenhagen Rye", LocalDate.of(2023, 4, 1), Flasketype.MEDIUM.getCapacity(), Flasketype.MEDIUM, lV2, f2, "Copenhagen Spring", 48.0);
        Whisky whisky3 = controller.opretWhisky("Odense Malted Barley", LocalDate.of(2023, 4, 1), Flasketype.STOR.getCapacity(), Flasketype.STOR, lV3, f3, "Odense Spring", 50.0);
        Whisky whisky4 = controller.opretWhisky("Aalborg Rye", LocalDate.of(2023, 4, 1), Flasketype.JUBILÆUMSFLASKE.getCapacity(), Flasketype.JUBILÆUMSFLASKE, lV4, f4, "Aalborg Spring", 52.0);
    }
}

