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
     * Konstruktør for Whisky klassen
     * @param navn Navnet på whiskyen
     * @param tappetDato Datoen whiskyen er tappet
     * @param liter Antallet af liter whiskyen indeholder
     * @param flasketype Typen af flaske whiskyen er tappet i
     * @param væske Lagret væskeobjekt, der indeholder information om whiskyen
     * @param vandKilde Kilden til vandet anvendt til at fortynde whiskyen
     * @param fortyndelsesProcent Procentdelen af vand anvendt til at fortynde whiskyen
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


    public int getId() {
        return id;
    }

    public double getFortyndelsesProcent() {
        return fortyndelsesProcent;
    }

    public String getVandKilde() {
        return vandKilde;
    }

    public LagretVæske getVæske() {
        return væske;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getTappetDato() {
        return tappetDato;
    }

    public ArrayList<LagretVæske> getHistorik() {
        return historik;
    }

    public double getAlkoholProcent() {
        return alkoholProcent;
    }

    public double getLiter() {
        return liter;
    }

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

    public Flasketype getFlasketype() {
        return flasketype;
    }

    @Override
    public String toString() {
        return this.navn;
    }
}