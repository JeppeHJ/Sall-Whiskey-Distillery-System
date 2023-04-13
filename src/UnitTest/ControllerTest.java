package UnitTest;

import application.Distillat;
import application.Fad;
import application.Lager;
import application.LagretVæske;
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


}





