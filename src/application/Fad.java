package application;

import java.util.ArrayList;

/**
 * Fad klassen repræsenterer et fad, der kan opbevares i et lager.
 */
public class Fad {
    private static int count;
    private int id;
    private String fadType;
    private double fadStr;
    private ArrayList<LagretVæske> lagretVæsker;


    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretVæsker = new ArrayList<>();
    }

    public void påfyldning(double mængde, LagretVæske lagretVæske) {
        if ((this.fadStr - getFadfyldning()) >= mængde) {
            double newVolume = this.getFadfyldning() + mængde;
            this.addLagretVæsker(lagretVæske);
            for (Fad fad : lagretVæske.getFadehistorik().keySet()) {
                fad.removeLagretVæsker(lagretVæske);
            }
            lagretVæske.getFadehistorik().put(this, 0);
        } else {
            throw new IllegalArgumentException("Not enough space in the barrel.");
        }
    }

    public double getFadfyldning() {
        double fyldning = 0.0;
        for (LagretVæske lagretVæske : lagretVæsker) {
            fyldning += lagretVæske.getLiter();
        }
        return fyldning;
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