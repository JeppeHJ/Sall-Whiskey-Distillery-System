package UnitTest;

import application.Fad;
import application.Lager;
import controller.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;


        @BeforeEach
        public void setUp() {
            controller = Controller.getController();
        }

        @Test
        public void testOpretLager() {
            String lokation = "Test Location";
            int antalPladser = 10;

            Lager lager = controller.opretLager(lokation, antalPladser);

            assertNotNull(lager);
            assertEquals(lokation, lager.getLokation());
            assertEquals(antalPladser, lager.getAntalPladser());
        }

        @Test
        public void testGetAlleLagre() {
            String lokation1 = "Test Location 1";
            int antalPladser1 = 10;
            Lager lager1 = controller.opretLager(lokation1, antalPladser1);

            String lokation2 = "Test Location 2";
            int antalPladser2 = 15;
            Lager lager2 = controller.opretLager(lokation2, antalPladser2);

            ArrayList<Lager> lagre = controller.getAlleLagre();

            assertEquals(2, lagre.size());
            assertEquals(lager1, lagre.get(0));
            assertEquals(lager2, lagre.get(1));
        }

        @Test
        public void testGetFadeIHashMap() {
            String lokation = "Test Location";
            int antalPladser = 10;
            Lager lager = controller.opretLager(lokation, antalPladser);

            HashMap<Integer, Fad> fadeHashMap = controller.getFadeIHashMap(lager.getId());

            assertNotNull(fadeHashMap);
        }

        @Test
        public void testGetLagerById() {
            String lokation = "Test Location";
            int antalPladser = 10;
            Lager lager = controller.opretLager(lokation, antalPladser);

            Lager retrievedLager = controller.getLagerById(lager.getId());

            assertEquals(lager, retrievedLager);
        }

        @Test
        public void testTotalAntalLager() {
            String lokation1 = "Test Location 1";
            int antalPladser1 = 10;
            controller.opretLager(lokation1, antalPladser1);

            String lokation2 = "Test Location 2";
            int antalPladser2 = 15;
            controller.opretLager(lokation2, antalPladser2);

            int totalLager = controller.totalAntalLager();

            assertEquals(2, totalLager);
        }

    }



