package application;

import java.time.LocalDate;

/**
 * Denne klasse repræsenterer et distillat i destilleringsprocessen.
 */
public class Distillat {
    private static int count = 1; // For unikke ID'er
    private final int id;
    private double liter;
    private double literTilbage;
    private final String maltBatch;
    private final String kornsort;
    private double alkoholprocent;
    private final String rygemateriale;
    private final LocalDate datoForDone;
    private final String medarbejder;

    // Konstruktør
    public Distillat(double liter, String maltBatch, String kornsort, double alkoholprocent, String rygemateriale, LocalDate dato, String medarbejder) {
        this.liter = liter;
        this.id = count++;
        this.maltBatch = maltBatch;
        this.kornsort = kornsort;
        this.alkoholprocent = alkoholprocent;
        this.rygemateriale = rygemateriale;
        this.datoForDone = dato;
        this.medarbejder = medarbejder;
    }

    // Getters
    public LocalDate getDatoForDone() {
        return datoForDone;
    }

    public String getMedarbejder() {
        return medarbejder;
    }

    public double getLiterTilbage() {
        return literTilbage + liter;
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

    // Setter
    public void setLiter(double liter) {
        this.liter = liter;
    }

    // Tildeler subtractFilledLiters
    public void subtractFilledLiters(double filledLiters) {
        this.literTilbage -= filledLiters;
    }

    // String repræsentation
    public String toString() {
        return id + " | " + maltBatch;
    }
}