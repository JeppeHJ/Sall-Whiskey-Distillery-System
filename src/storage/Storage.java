package storage;

import application.Lager;
import java.util.ArrayList;

public class Storage {
    //todo add nye lister
    private static Storage storage;
    private ArrayList<Lager> lagre;

    public Storage() {
        lagre = new ArrayList<>();
    }

    /**
     * Returnerer den eneste Storage instans. Opretter en ny, hvis den ikke eksisterer.
     *
     * @return den eneste Storage instans.
     */
    public static Storage getStorage() {
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }


    public void addLager(Lager lager) {
        lagre.add(lager);
    }

    /**
     * Finder og returnerer et Lager objekt baseret på dets ID.
     *
     * @return Lager objektet med det angivne ID, eller null hvis det ikke findes.
     */
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
}