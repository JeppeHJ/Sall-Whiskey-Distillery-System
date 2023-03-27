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
    private ArrayList<Lagretvæske> lagretvæsker;


    public Fad(String fadType, double fadStr, Lager lager) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretvæsker = new ArrayList<>();
    }

    public void påfyldning(double mængde, Lagretvæske lagretvæske) {
        if ((this.fadStr - getFadfyldning()) >= mængde) {
            double newVolume = this.getFadfyldning() + mængde;
            this.addLagretVæsker(lagretvæske);
            for (Fad fad : lagretvæske.getFadehistorik().keySet()) {
                fad.removeLagretVæsker(lagretvæske);
            }
            lagretvæske.getFadehistorik().put(this, 0);
        } else {
            throw new IllegalArgumentException("Not enough space in the barrel.");
        }
    }

    public double getFadfyldning() {
        double fyldning = 0.0;
        for (Lagretvæske lagretvæske : lagretvæsker) {
            fyldning += lagretvæske.getLiter();
        }
        return fyldning;
    }

    public void addLagretVæsker(Lagretvæske lagretvæske) {
        if (!(this.lagretvæsker.contains(lagretvæske))) {
            this.lagretvæsker.add(lagretvæske);
        }
    }

    public void removeLagretVæsker(Lagretvæske lagretvæske) {
        if (this.lagretvæsker.contains(lagretvæske)) {
            this.lagretvæsker.remove(lagretvæske);
        }
    }

}