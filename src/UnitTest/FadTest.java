package UnitTest;

import application.Fad;
import application.FadsLagretVæskeHistorik;
import application.FadsOmhældningsHistorik;
import application.LagretVæske;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FadTest {
    private Fad fad;
    private LagretVæske lagretVæske;

    @BeforeEach
    public void setUp() {
        fad = new Fad("Egetræ", 225);
        lagretVæske = new LagretVæske(100,  LocalDate.now().minusYears(4));
    }

    @Test
    public void testPåfyldning() {
        fad.påfyldning(lagretVæske, LocalDate.now());
        ArrayList<LagretVæske> lagretVæsker = fad.getLagretVæsker();
        assertEquals(1, lagretVæsker.size());
        assertEquals(lagretVæske, lagretVæsker.get(0));
    }

    @Test
    public void testGetFadStr() {
        assertEquals(225, fad.getFadStr());
    }

    @Test
    public void testGetFadfyldning() {
        fad.påfyldning(lagretVæske, LocalDate.now());
        assertEquals(100, fad.getFadfyldning());
    }

    @Test
    public void testReducereLagretVaeske() {
        fad.påfyldning(lagretVæske, LocalDate.now());
        fad.reducereLagretVaeske(50);
        assertEquals(50, fad.getFadfyldning());
    }

    @Test
    public void testErFaerdigLagret() {
        fad.påfyldning(lagretVæske, LocalDate.now());
        assertTrue(fad.erFaerdigLagret());
    }

    @Test
    public void testErFaerdigLagretFalse() {
        LagretVæske nyLagretVæske = new LagretVæske( 100, LocalDate.now().minusYears(2));
        fad.påfyldning(nyLagretVæske, LocalDate.now());
        assertFalse(fad.erFaerdigLagret());
    }
    @Test
    public void testGetPlads() {
        fad.addPlads(5);
        assertEquals(5, fad.getPlads());
    }

    @Test
    public void testAddLagretVæsker() {
        fad.addLagretVæsker(lagretVæske);
        assertEquals(1, fad.getLagretVæsker().size());
        assertEquals(lagretVæske, fad.getLagretVæsker().get(0));
    }

    @Test
    public void testRemoveLagretVæsker() {
        fad.addLagretVæsker(lagretVæske);
        fad.removeLagretVæsker(lagretVæske);
        assertEquals(0, fad.getLagretVæsker().size());
    }

    @Test
    public void testGetId() {
        int fadId = fad.getId();
        Fad anotherFad = new Fad("Egetræ", 225);
        int anotherFadId = anotherFad.getId();
        assertEquals(fadId + 1, anotherFadId);
    }

    @Test
    public void testToString() {
        String expectedString = fad.getId() + " | " + fad.getFadfyldning();
        assertEquals(expectedString, fad.toString());
    }

    @Test
    public void testOmhældning() {
        Fad destinationFad = new Fad("Egetræ", 225);
        LagretVæske nyLagretVæske = new LagretVæske( 50, LocalDate.now().minusYears(2));
        fad.påfyldning(lagretVæske, LocalDate.now());
        fad.omhældning(fad, destinationFad, 50, nyLagretVæske);

        assertEquals(50, fad.getFadfyldning());
        assertEquals(50, destinationFad.getFadfyldning());
        assertEquals(nyLagretVæske, destinationFad.getLagretVæsker().get(0));
    }
    @Test
    public void testGetOmhældningsHistory() {
        assertNotNull(fad.getOmhældningsHistory());
    }

    @Test
    public void testAddToOmhældningsHistorik() {
        FadsOmhældningsHistorik historik = new FadsOmhældningsHistorik(fad, null, 50, LocalDate.now());
        fad.addToOmhældningsHistorik(historik);
        assertEquals(1, fad.getOmhældningsHistory().size());
    }

    @Test
    public void testAddToHistory() {
        fad.addToHistory(lagretVæske, LocalDate.now(), null);
        ArrayList<FadsLagretVæskeHistorik> history = fad.getFadsLagretVæskeHistorik();
        assertEquals(1, history.size());
        assertEquals(lagretVæske, history.get(0).getLagretVaeske());
    }

    @Test
    public void testEditHistoryWhenBarrelEmpty() {
        fad.addToHistory(lagretVæske, LocalDate.now(), null);
        fad.editHistoryWhenBarrelEmpty(lagretVæske, LocalDate.now().plusDays(1));
        ArrayList<FadsLagretVæskeHistorik> history = fad.getFadsLagretVæskeHistorik();
        assertEquals(LocalDate.now().plusDays(1), history.get(0).getEmptyDate());
    }
}

