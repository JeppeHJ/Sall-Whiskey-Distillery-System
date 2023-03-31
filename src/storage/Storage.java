package storage;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static Storage storage;
    private ArrayList<Lager> lagre;
    private HashMap<Integer, Fad> fade; // Change to HashMap
    private ArrayList<Distillat> distillater;
    private ArrayList<LagretVæske> lagretVæsker;

    public Storage() {
        lagre = new ArrayList<>();
        fade = new HashMap<>(); // Initialize HashMap
        distillater = new ArrayList<>();
        lagretVæsker = new ArrayList<>();
    }

    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    //------------------- Lager storage -----------------//

    public void addLager(Lager lager) {
        lagre.add(lager);
    }

    public ArrayList<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    public Lager getLagerById(int id) {
        for (Lager lager : lagre) {
            if (lager.getId() == id) {
                return lager;
            }
        }
        return null;
    }

    //------------------- Fad storage -----------------//

    //------------------- Fad storage -----------------//

    public void addFad(Fad fad) {
        fade.put(fad.getId(), fad); // Update to use HashMap
    }

    public ArrayList<Fad> getFade() {
        return new ArrayList<>(fade.values()); // Update to use HashMap
    }

    public Fad getFadById(int id) {
        return fade.get(id); // Update to use HashMap
    }

    //------------------- Distillat storage -----------------//

    public void addDistillat(Distillat distillat) {
        distillater.add(distillat);
    }

    public ArrayList<Distillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    public Distillat getDistillatById(int id) {
        for (Distillat distillat : distillater) {
            if (distillat.getId() == id) {
                return distillat;
            }
        }
        return null;
    }

    //------------------- LagretVæske storage -----------------//

    public void addLagretVæske(LagretVæske lagretVæske) {
        lagretVæsker.add(lagretVæske);
    }

    public ArrayList<LagretVæske> getLagretVæsker() {
        return new ArrayList<>(lagretVæsker);
    }

    public LagretVæske getLagretVæskeById(int id) {
        for (LagretVæske lagretVæske : lagretVæsker) {
            if (lagretVæske.getId() == id) {
                return lagretVæske;
            }
        }
        return null;
    }
}
