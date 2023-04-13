package application;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Whisky klassen repræsenterer en whisky med forskellige egenskaber og historik af lagret væske.
 */
public class Whisky {
    private String navn;
    private static int count = 0;
    private int id;
    private LocalDate tappetDato;
    private double alkoholProcent;
    private double liter;
    private Flasketype flasketype;

    private ArrayList<LagretVæske> historik = new ArrayList<>();
    private LagretVæske væske;
    private double fortyndelsesProcent;
    private String vandKilde;

    /**
     * Constructor for Whisky klassen
     *
     * @param navn                Navnet på whiskyen
     * @param tappetDato          Datoen whiskyen er tappet
     * @param liter               Antallet af liter whiskyen indeholder
     * @param flasketype          Typen af flaske whiskyen er tappet i
     * @param væske               Lagret væskeobjekt, der indeholder information om whiskyen
     * @param vandKilde           Kilden til vandet anvendt til at fortynde whiskyen
     * @param fortyndelsesProcent Procentdelen af vand anvendt til at fortynde whiskyen
     *                            <p>
     *                            Præbetingelser:
     *                            navn må ikke være null eller tom.
     *                            tappetDato må ikke være null.
     *                            liter skal være større end 0.
     *                            Præbetingelse: flasketype må ikke være null.
     *                            væske må ikke være null.
     *                            vandKilde må ikke være null eller tom.
     *                            fortyndelsesProcent skal være mellem 0 og 100 (inklusiv).
     */
    public Whisky(String navn, LocalDate tappetDato, double liter, Flasketype flasketype, LagretVæske væske, String vandKilde, double fortyndelsesProcent) {
        this.navn = navn;
        this.tappetDato = tappetDato;

        this.liter = liter;
        this.flasketype = flasketype;
        this.historik.add(væske);
        this.væske = væske;
        this.id = count++;
        this.vandKilde = vandKilde;
        this.fortyndelsesProcent = fortyndelsesProcent;
        this.alkoholProcent = udregnAlkoholprocent();
    }

    // Getters

    /**
     * Returnerer whiskyen's ID.
     *
     * @return en int værdi, der repræsenterer whiskyen's unikke ID.
     */

    public int getId() {
        return id;
    }

    /**
     * Returnerer whiskyen's fortyndelsesprocent.
     *
     * @return en double værdi, der repræsenterer procenten af vand tilsat til whiskyen.
     */
    public double getFortyndelsesProcent() {
        return fortyndelsesProcent;
    }

    /**
     * Returnerer vandkilden, der er brugt til at fortynde whiskyen.
     *
     * @return en String, der repræsenterer vandkilden brugt til fortynding.
     */
    public String getVandKilde() {
        return vandKilde;
    }
    /**
     * Returnerer den lagrede væske, whiskyen er tappet fra.
     *
     * @return et LagretVæske objekt, der repræsenterer den lagrede væske.
     */


    public LagretVæske getVæske() {
        return væske;
    }

    /**
     * Returnerer navnet på whiskyen.
     *
     * @return en String, der repræsenterer whiskyen's navn.
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Returnerer datoen, hvor whiskyen er blevet tappet på flaske.
     *
     * @return et LocalDate objekt, der repræsenterer tappetdatoen.
     */
    public LocalDate getTappetDato() {
        return tappetDato;
    }

    /**
     * Returnerer en liste af historik over lagrede væsker, whiskyen er tappet fra.
     *
     * @return en ArrayList af LagretVæske objekter, der repræsenterer historikken over lagrede væsker.
     */
    public ArrayList<LagretVæske> getHistorik() {
        return historik;
    }

    /**
     * Returnerer whiskyen's alkoholprocent.
     *
     * @return en double værdi, der repræsenterer whiskyen's alkoholprocent.
     */
    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    /**
     * Returnerer antallet af liter whisky i flasken.
     *
     * @return en double værdi, der repræsenterer antallet af liter whisky.
     */
    public double getLiter() {
        return liter;
    }
/**  * Beregner og returnerer whiskyen's alkoholprocent efter fortynding.
 *
 * @return en double værdi, der repræsenterer whiskyen's alkoholprocent efter fortynding.
 */
    public double udregnAlkoholprocent() {
        double sum = 0;
        for (Distillat distillat : væske.getDistillater()) {
            sum += distillat.getAlkoholprocent();
        }
        double gennemsnitligAlkoholprocent = sum / væske.getDistillater().size();

        // Tager hensyn til fortyndelsesprocenten
        double alkoholprocentEfterFortynding = gennemsnitligAlkoholprocent * (1 - fortyndelsesProcent);

        return alkoholprocentEfterFortynding;
    }

    /**
     * Returnerer flasketypen, whiskyen er tappet på.
     *
     * @return et Flasketype objekt, der repræsenterer whiskyen flasketypen.
     */
    public Flasketype getFlasketype() {
        return flasketype;
    }

    /**
     * Returnerer en String repræsentation af whiskyen.
     *
     * @return en String, der repræsenterer whiskyen, primært baseret på navnet.
     */
    @Override
    public String toString() {
        return this.navn;
    }
}