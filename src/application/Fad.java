package application;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private static int count;
    private int id;
    private String fadType;
    private double fadStr;

    private double currentCapacity;
    private int plads;
    private ArrayList<LagretVæske> lagretVæsker;


    public Fad(String fadType, double fadStr, int plads) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
        this.plads = plads;
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

    public double getCurrentCapacity() {
        return this.fadStr - getFadfyldning();
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

    public int getId() {
        return id;
    }

    public String toString() {
        return this.id + " | " + fadType + " | " + getFadfyldning() + "/" + fadStr;
    }
}