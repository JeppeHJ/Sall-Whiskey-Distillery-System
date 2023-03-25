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

    //todo
    public void påfyldning() {
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