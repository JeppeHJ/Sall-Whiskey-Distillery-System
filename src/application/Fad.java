package application;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private static int count = 1;
    private final int id;
    private final String fadType;
    private final double fadStr;
    private int plads;
    private final ArrayList<FadsLagretVæskeHistorik> væskeHistory;
    private final ArrayList<FadsOmhældningsHistorik> omhældningsHistory;
    private final ArrayList<LagretVæske> lagretVæsker;

    /**
     * Constructor for Distillat klassen, der opretter et nyt distillat med de givne parametre.
     *
     * @param fadType en String, der repræsenterer typen af fad.
     * @param fadStr  en double, der repræsenterer størrelsen af fadet i liter.
     *
     *                Præbetingelse: fadType skal være en gyldig streng, og fadStr skal være en positiv double-værdi.
     */
    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
        væskeHistory = new ArrayList<>();
        omhældningsHistory = new ArrayList<>();
    }

    /**
     * Tilføjer en LagretVæske til fadet og opdaterer historikken for både fadet og den valgte LagretVæske.
     *
     * @param valgtLagretVæske et LagretVæske-objekt, der repræsenterer den væske, der skal fyldes på fadet.
     * @param påfyldningsDato  en LocalDate-instans, der repræsenterer datoen for påfyldningen af væsken på fadet.
     *
     *                         Præbetingelse: valgtLagretVæske skal være et gyldigt LagretVæske-objekt, og påfyldningsDato skal være en gyldig LocalDate-instans.
     */
    public void påfyldning(LagretVæske valgtLagretVæske, LocalDate påfyldningsDato) {
        this.addLagretVæsker(valgtLagretVæske);
        valgtLagretVæske.addFadTilHistorik(this, påfyldningsDato, null);


        addToHistory(valgtLagretVæske, påfyldningsDato, null);
    }

    public ArrayList<FadsOmhældningsHistorik> getOmhældningsHistory() {
        return omhældningsHistory;
    }


    public void addToOmhældningsHistorik(FadsOmhældningsHistorik object) {
        this.omhældningsHistory.add(object);
    }

    public void omhældning(Fad kilde, Fad destination, double mængde, LagretVæske nyeVæske) {
        FadsOmhældningsHistorik omhældningKilde = new FadsOmhældningsHistorik(kilde, destination, mængde, LocalDate.now());
        FadsOmhældningsHistorik omhældningDestination = new FadsOmhældningsHistorik(destination, kilde, mængde, LocalDate.now());

        kilde.addToOmhældningsHistorik(omhældningKilde);
        destination.addToOmhældningsHistorik(omhældningDestination);

        destination.addToHistory(nyeVæske, LocalDate.now(), null);
        if (destination.lagretVæsker.size() != 0) {
            destination.editHistoryWhenBarrelEmpty(destination.lagretVæsker.get(0), LocalDate.now());
        }

        if ((kilde.fadStr - kilde.getFadfyldning()) - mængde == 0) {
            kilde.editHistoryWhenBarrelEmpty(kilde.lagretVæsker.get(0), LocalDate.now());
        }

        // Handle kilde fads nye mængde
        kilde.reducereLagretVaeske(mængde);

        // Sub handle hvis reducereLagretVæske == 0

        // Handle destination fads nye mængde
        if (!(destination.getLagretVæsker().isEmpty())) {
            destination.removeLagretVæsker(destination.getLagretVæsker().get(0));

        }

        destination.addLagretVæsker(nyeVæske);
    }


    /**
     * Tilføjer en ny post til fadets væskehistorik med angivne oplysninger om den lagrede væske, påfyldnings- og tømningsdatoer.
     *
     * @param lagretVaeske LagretVæske-objektet der repræsenterer den væske, der er blevet lagret i fadet.
     * @param fillDate     LocalDate-objektet der repræsenterer den dato, hvor væsken er blevet fyldt på fadet.
     * @param emptyDate    LocalDate-objektet der repræsenterer den dato, hvor væsken er blevet tømt fra fadet, eller null hvis fadet ikke er tømt endnu.
     *
     *                     Præbetingelse: lagretVæske skal være et gyldigt LagretVæske-objekt, og fillDate og emptyDate skal være gyldige LocalDate-instanser eller null.
     */
    public void addToHistory(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        FadsLagretVæskeHistorik entry = new FadsLagretVæskeHistorik(lagretVaeske, fillDate, emptyDate);
        væskeHistory.add(entry);
    }

    /**
     * Opdaterer fadets LagretVæske-historik, når fadet tømmes.
     *
     * @param lagretVaeske LagretVæske-objektet der repræsenterer den væske, hvis historik skal opdateres med tømningsdatoen.
     * @param emptyDate    LocalDate-objektet der repræsenterer den dato, hvor fadet blev tømt.
     *
     *                     Præbetingelse: lagretVaeske skal være et gyldigt LagretVæske-objekt, og emptyDate skal være en gyldig LocalDate-instans.
     */
    public void editHistoryWhenBarrelEmpty(LagretVæske lagretVaeske, LocalDate emptyDate) {
        for (FadsLagretVæskeHistorik lV : væskeHistory) {
            if (lV.getLagretVaeske() == lagretVaeske) {
                lV.setEmptyDate(emptyDate);
            }
        }
    }


    /**
     * @return ArrayList af FadsLagretVæskeHistorik objekter.
     */
    public ArrayList<FadsLagretVæskeHistorik> getFadsLagretVæskeHistorik() {
        return væskeHistory;
    }

    public double getFadStr() {
        return fadStr;
    }

    public double getFadfyldning() {
        double fyldning = 0.0;
        for (LagretVæske lagretVæske : lagretVæsker) {
            fyldning += lagretVæske.getLiter();
        }
        return fyldning;
    }

    /**
     * Reducerer mængden af LagretVæske i fadet med den angivne mængde.
     *
     * @param liter double-værdien, der repræsenterer mængden af væske, der skal reduceres fra fadet.
     *              Præbetingelse: liter skal være en positiv double-værdi.
     */
    public void reducereLagretVaeske(double liter) {
        for (LagretVæske væske : lagretVæsker) {
            væske.setLiter(væske.getLiter() - liter);
            break; // Antager, at der kun er én LagretVæske i fadet
        }
    }

    /**
     * Kontrollerer om LagretVæske er færdiglagret (minimum 3 år).
     *
     * @return true, hvis væsken er færdiglagret, ellers false.
     */
    public boolean erFaerdigLagret() {
        LocalDate currentDate = LocalDate.now();
        for (LagretVæske lV : lagretVæsker) {
            LocalDate påfyldningsDato = lV.getPåfyldningsDato();
            if (!(påfyldningsDato.plusYears(3).isBefore(currentDate) || påfyldningsDato.plusYears(3).isEqual(currentDate))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return ArrayList af LagretVæske objekter.
     */
    public ArrayList<LagretVæske> getLagretVæsker() {
        return lagretVæsker;
    }

    public void addPlads(int plads) {
        this.plads = plads;
    }


    /**
     * Tilføjer en LagretVæske til fadet.
     *
     * @param lagretVæske LagretVæske-objektet, der skal tilføjes til fadet.
     *                    Præbetingelse: lagretVæske skal være et gyldigt LagretVæske-objekt.
     */
    public void addLagretVæsker(LagretVæske lagretVæske) {
        this.lagretVæsker.add(0, lagretVæske);
    }

    /**
     * Fjerner en LagretVæske fra fadet.
     *
     * @param lagretVæske LagretVæske-objektet, der skal fjernes
     *                    Præbetingelse: lagretVæske skal være et gyldigt LagretVæske-objekt.
     */
    public void removeLagretVæsker(LagretVæske lagretVæske) {
        if (this.lagretVæsker.contains(lagretVæske)) {
            this.lagretVæsker.remove(lagretVæske);
        }
    }

    public int getPlads() {
        return plads;
    }

    public int getId() {
        return id;
    }

    /**
     * @return en streng, der repræsenterer fadet.
     */
    public String toString() {
        return this.id + " | " + this.getFadfyldning();
        //return this.id + " | " + fadType + " | " +  + "/" + this.fadStr + " | Fadposition: " + this.plads;
    }
}