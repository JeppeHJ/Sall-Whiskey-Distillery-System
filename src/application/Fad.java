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
    private ArrayList<LagretVæske> lagretVæsker;


    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
    }

    /** Præ-condition:
     * Mængde < (fadStr - getFadfyldning)
     * @param
     * @param lagretVæske
     */

    public void påfyldning(LagretVæske lagretVæske, LocalDate påfyldningsDato) {
            this.addLagretVæsker(lagretVæske);
            lagretVæske.getFadehistorik().put(this, påfyldningsDato);


    }

    public double getFadfyldning() {
        double fyldning = 0.0;
        for (LagretVæske lagretVæske : lagretVæsker) {
            fyldning += lagretVæske.getLiter();
        }
        this.currentCapacity = fyldning;
        return this.currentCapacity;
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
}