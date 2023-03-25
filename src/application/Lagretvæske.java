package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Lagretvæske {
    private double liter;
    private double alkoholProcent;
    private HashMap<Fad, Integer> fadehistorik = new HashMap<>();
    private ArrayList<Distillat> distillater = new ArrayList<>();

    public Lagretvæske(double liter, double alkoholProcent) {
        this.liter = liter;
        this.alkoholProcent = alkoholProcent;
    }

    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }


}
