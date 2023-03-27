package application;

import java.util.ArrayList;
import java.util.HashMap;

public class LagretVæske {
    private static int count;
    private int id;
    private double liter;
    private double alkoholProcent;
    private HashMap<Fad, Integer> fadehistorik = new HashMap<>();
    private ArrayList<Distillat> distillater;

    public LagretVæske(double liter, ArrayList<Distillat> distillater) {
        this.distillater=distillater;
        this.liter = liter;
        this.id = count++;
        this.alkoholProcent = alkoholProcent;
    }

    //todo hvad fanden er det nu du bruger hashmap til?


    public int getId() {
        return id;
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
    public ArrayList<Distillat> getDistillater() {
        return new ArrayList<>(distillater);
    }

    //todo find en måde at regne ud hvad alkohol procenten er på baggrund af alle distilaters alkohol procent og hvormeget der er blevet hældt i dem
    public static double calculateNewAlkoholProcent(ArrayList<Distillat> distilats, double[] volumes) {
        double totalAlcoholContent = 0;
        double totalVolume = 0;

        for (int i = 0; i < distilats.size(); i++) {
            totalAlcoholContent += distilats.get(i).getAlkoholprocent() * volumes[i];
            totalVolume += volumes[i];
        }

        if (totalVolume == 0) {
            throw new IllegalArgumentException("Total volume cannot be zero.");
        }

        double newAlkoholProcent = totalAlcoholContent / totalVolume;
        return newAlkoholProcent;
    }


}
