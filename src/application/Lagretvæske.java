package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Lagretvæske {
    private double liter;
    private double alkoholProcent;
    private HashMap<Fad, Integer> fadehistorik = new HashMap<>();
    private ArrayList<Distillat> distillater;

    public Lagretvæske(double liter, ArrayList<Distillat> distillater) {
        this.distillater=distillater;
        this.liter = liter;
        this.alkoholProcent = alkoholProcent;
    }

    //todo hvad fanden er det nu du bruger hashmap til?


    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }


    public double getAlkoholProcent() {
        return alkoholProcent;
    }
    public ArrayList<Distillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    // find en måde at regne ud hvad alkohol procenten er på baggrund af alle distilaters alkohol procent og hvormeget der er blevet hældt i dem
    public static double calculateNewAlkoholProcent(ArrayList<Distillat> distillater, double[] volumes) {
        double totalAlkohol = 0;
        double totalVolumen = 0;
        for (int i = 0; i < distillater.size(); i++) {
            totalAlkohol += distillater.get(i).getAlkoholprocent() * volumes[i];
            totalVolumen += volumes[i];
        }
        return totalAlkohol / totalVolumen;
    }

    public HashMap<Fad, Integer> getFadehistorik() {
        return new HashMap<>(fadehistorik);
    }
}
