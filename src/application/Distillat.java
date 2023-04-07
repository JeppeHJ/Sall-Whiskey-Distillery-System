package application;

import java.time.LocalDate;

public class Distillat {
    private static int count;
    private final int id;
    private double liter;
    private double literTilbage;
    private final String maltBatch;
    private final String kornsort;
    private double alkoholprocent;
    private final String rygemateriale;
    private final LocalDate datoForDone;

    public Distillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale, LocalDate dato) {
        this.liter = liter;
        this.id = count++;
        this.maltBatch = maltBatch;
        this.kornsort = kornsort;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
        this.datoForDone = dato;

    }

    public LocalDate getDatoForDone() {
        return datoForDone;
    }

    public double getLiterTilbage() {
        return literTilbage + liter;
    }

    public void subtractFilledLiters(double filledLiters) {
        this.literTilbage -= filledLiters;
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

    public void setLiter(double liter) {
        this.liter = liter;
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

    public String toString() {
        return id + " | " + maltBatch + " | liter: " + (liter + literTilbage);
    }
}
