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

    // Konstruktør
    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
        væskeHistory = new ArrayList<>();
        omhældningsHistory = new ArrayList<>();
    }

    // Påfyldning af LagretVæske til Fad
    public void påfyldning(LagretVæske valgtLagretVæske, LocalDate påfyldningsDato) {
        this.addLagretVæsker(valgtLagretVæske);
        valgtLagretVæske.addFadTilHistorik(this, påfyldningsDato, null);

        // Tilføj LagretVæske til historikken
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



    // Tilføjer en ny post til historikken
    public void addToHistory(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        FadsLagretVæskeHistorik entry = new FadsLagretVæskeHistorik(lagretVaeske, fillDate, emptyDate);
        væskeHistory.add(entry);
    }

    public void editHistoryWhenBarrelEmpty(LagretVæske lagretVaeske, LocalDate emptyDate) {
        for (FadsLagretVæskeHistorik lV: væskeHistory) {
            if (lV.getLagretVaeske() == lagretVaeske) {
                lV.setEmptyDate(emptyDate);
            }
        }
    }



    // Getters
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

    // Reducerer mængden af LagretVæske i Fad
    public void reducereLagretVaeske(double liter) {
        for (LagretVæske væske : lagretVæsker) {
            væske.setLiter(væske.getLiter() - liter);
            break; // Antager, at der kun er én LagretVæske i fadet
        }
    }

    // Kontrollerer om LagretVæske er færdiglagret (minimum 3 år)
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
    // Getters og setters
    public ArrayList<LagretVæske> getLagretVæsker() {
        return lagretVæsker;
    }

    public void addPlads(int plads) {
        this.plads = plads;
    }

    // Tilføjer LagretVæske til fadet
    public void addLagretVæsker(LagretVæske lagretVæske) {
        this.lagretVæsker.add(0, lagretVæske);
    }

    // Fjerner LagretVæske fra fadet
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

    // toString-metoden for Fad
    public String toString() {
        return this.id + " | " + this.getFadfyldning();
        //return this.id + " | " + fadType + " | " +  + "/" + this.fadStr + " | Fadposition: " + this.plads;
    }}