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
    private ArrayList<LagretVæske> lagretvæsker;


    public Fad(String fadType, double fadStr) {
        count++;
        this.id = count;
        this.fadType = fadType;
        this.fadStr = fadStr;
        this.lagretvæsker = new ArrayList<>();
    }

    //todo
    public void påfyldning() {
    }

    public void addLagretVæsker(LagretVæske lagretvæske) {
        if (!(this.lagretvæsker.contains(lagretvæske))) {
            this.lagretvæsker.add(lagretvæske);
        }
    }
    public void removeLagretVæsker(LagretVæske lagretvæske) {
        if (this.lagretvæsker.contains(lagretvæske)) {
            this.lagretvæsker.remove(lagretvæske);
        }
    }

    public int getId() {
        return id;
    }
}