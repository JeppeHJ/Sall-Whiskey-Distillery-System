package application;

public class Distillat {
    private int id;
    private double liter;
    private String maltBatch;
    private String kornsort;
    private double alkoholprocent;
    private  String rygemateriale;

    public Distillat(int id, double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale) {
        this.id = id;
        this.liter = liter;
        this.maltBatch = maltBatch;
        this.kornsort = kornsort;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
    }

    public int getId() {
        return id;
    }

    public double getLiter() {
        return liter;
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public String getKornsort() {
        return kornsort;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public String getRygemateriale() {
        return rygemateriale;
    }
}
