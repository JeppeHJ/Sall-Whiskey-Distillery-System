    package application;

    import jdk.swing.interop.SwingInterOpUtils;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;

    public class LagretVæske {
        private static int count = 0;
        private int id;
        private double liter;
        private double alkoholProcent;
        private LocalDate påfyldningsDato;
        private HashMap<Fad, LocalDate> fadehistorik;
        private ArrayList<Distillat> distillater;

        public LagretVæske(double liter, LocalDate påfyldningsDato) {
            this.påfyldningsDato = påfyldningsDato;
            this.liter = liter;
            this.id = count++;
            this.distillater = new ArrayList<>();
            this.fadehistorik =  new HashMap<>();
        }

        public int getId() {
            return id;
        }

        public double getLiter() {
            return liter;
        }

        public void addDistillat(Distillat distillat) {
            this.distillater.add(distillat);
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

        public HashMap<Fad, LocalDate> getFadehistorik() {
            return this.fadehistorik;
        }

        public void addFadTilHistorik(Fad fad, LocalDate påfyldningsDato) {
            System.out.println("2. Fad: Fad added to historik: " + fad + " Date: " + påfyldningsDato);
            this.fadehistorik.put(fad, påfyldningsDato);
        }


    }
