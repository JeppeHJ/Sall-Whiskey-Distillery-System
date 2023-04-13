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

    /**
     * Constructor for Distillat klassen, der opretter et nyt distillat med de givne parametre.
     *
     * @param liter          en double, der repræsenterer mængden af destillat i liter.
     * @param maltBatch      en String, der repræsenterer maltsorten til det pågældende destillat.
     * @param kornsort       en String, der repræsenterer kornsorten til det pågældende destillat.
     * @param alkoholprocent en double, der repræsenterer alkoholprocenten for det pågældende destillat.
     * @param rygemateriale  en String, der repræsenterer rygematerialet til det pågældende destillat.
     * @param dato           en LocalDate, der repræsenterer datoen, hvor destillatet blev færdiggjort.
     * @param medarbejder    en String, der repræsenterer navnet på den medarbejder, der var ansvarlig for at færdiggøre destillatet.
     *
     *                       Præbetingelse:
     *                       liter skal være en ikke-negativ double værdi.
     *                       maltBatch, kornsort, rygemateriale og medarbejder skal være ikke-null strenge.
     *                       alkoholprocent skal være en ikke-negativ double værdi.
     *                       dato skal være en gyldig LocalDate værdi.
     */
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

    /**
     * @return en LocalDate, der repræsenterer datoen, hvor destillatet blev færdiggjort.
     */
    public LocalDate getDatoForDone() {
        return datoForDone;
    }

    /**
     * @return en streng, der repræsenterer navnet på den medarbejder, der var ansvarlig for at færdiggøre destillatet.
     */
    public String getMedarbejder() {
        return medarbejder;
    }

    /**
     * @return en double, der repræsenterer mængden af destillat, der er tilbage, i liter.
     */
    public double getLiterTilbage() {
        return literTilbage + liter;
    }

    /**
     * @return distillatets unikke ID som en hel værdi.
     */
    public int getId() {
        return id;
    }

    /**
     * @return en double, der repræsenterer mængden af destillat i liter.
     */
    public double getLiter() {
        return liter;
    }

    /**
     * @return en streng, der repræsenterer maltsorten til det pågældende destillat.
     */
    public String getMaltBatch() {
        return maltBatch;
    }

    /**
     * @return en streng, der repræsenterer kornsorten til det pågældende destillat.
     */
    public String getKornsort() {
        return kornsort;
    }

    /**
     * @return en double, der repræsenterer alkoholprocenten for det pågældende destillat.
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * @return en streng, der repræsenterer rygematerialet til det pågældende destillat.
     */
    public String getRygemateriale() {
        return rygemateriale;
    }

    /**
     * Sætter mængden af destillat i liter til den angivne værdi.
     *
     * @param liter En double, der repræsenterer den nye mængde af destillat i liter.
     *
     *              Præbetingelse: liter skal være større end eller lig med 0.
     */
    public void setLiter(double liter) {
        this.liter = liter;
    }

    /**
     * Trækker mængden af destillat, der er blevet fyldt i flasker, fra mængden af destillat, der er tilbage.
     *
     * @param filledLiters: En double, der repræsenterer mængden af destillat, der er blevet fyldt i flasker.
     *
     *                      Præbetingelse: filledLiters skal være større end eller lig med 0.
     */
    public void subtractFilledLiters(double filledLiters) {
        this.literTilbage -= filledLiters;
    }

    /**
     * @return en strengrepræsentation af distillatet, der indeholder distillatets ID og maltsort.
     */
    public String toString() {
        return id + " | " + maltBatch;
    }
}