package application;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private static int count;
    private final int id;
    private final String fadType;
    private final double fadStr;
    private int plads;
    private ArrayList<LagretVæskeHistoryEntry> history;
    private ArrayList<LagretVæske> lagretVæsker;


    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
        history = new ArrayList<>();
    }

    /**
     * Præ-condition:
     * Mængde < (fadStr - getFadfyldning)
     *
     * @param
     * @param
     * @param valgtLagretVæske
     */

    public void påfyldning(LagretVæske valgtLagretVæske, LocalDate påfyldningsDato) {
        this.addLagretVæsker(valgtLagretVæske);
        valgtLagretVæske.addFadTilHistorik(this, påfyldningsDato);

        // Add the LagretVæske to the history with the fill date and null as the empty date
        addToHistory(valgtLagretVæske, påfyldningsDato, null);
    }

    public void addToHistory(LagretVæske lagretVaeske, LocalDate fillDate, LocalDate emptyDate) {
        LagretVæskeHistoryEntry entry = new LagretVæskeHistoryEntry(lagretVaeske, fillDate, emptyDate);
        history.add(entry);
    }

    public ArrayList<LagretVæskeHistoryEntry> getHistory() {
        return history;
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

    public void reducereLagretVaeske(double liter) {
        for (LagretVæske væske : lagretVæsker) {
            væske.setLiter(væske.getLiter() - liter);
            System.out.println(væske.getLiter());
            break; // Antager, at der kun er én LagretVæske i fadet
        }
    }

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

    public ArrayList<LagretVæske> getLagretVæsker() {
        return lagretVæsker;
    }

    public void addPlads(int plads) {
        this.plads = plads;
    }


    public void addLagretVæsker(LagretVæske lagretVæske) {
        if (!(this.lagretVæsker.contains(lagretVæske))) {
            this.lagretVæsker.add(lagretVæske);
        }
    }

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

    public String toString() {
        return this.id + " | " + fadType + " | " + this.getFadfyldning() + "/" + this.fadStr + " | Fadposition: " + this.plads;
    }
}