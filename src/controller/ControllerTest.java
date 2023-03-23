package controller;

import application.Fad;
import application.Lager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = Controller.getController();
        controller.opretLager("Aarhus", 50);

    }

    @Test
    void opretFad() {
        int lagerId = 1;
        controller.opretFad("EXBourbon", 100.0, "NM76P", 30, 30, "JJ", lagerId);
        assertEquals(1, controller.getLagerById(lagerId).getFade().size());
    }

    @Test
    void opretTomtFad() {
        int lagerId = 1;
        controller.opretTomtFad("EXBourbon", 100.0, lagerId);
        assertEquals(1, controller.getLagerById(lagerId).getFade().size());
    }
    @Test
    void opretFadMedFlereLiterPåfyldtEndStr() {
        int lagerId = 1;
        Assertions.assertThrows(RuntimeException.class, () -> {
            controller.opretFad("EXBourbon", 100.0, "NM76P", 110, 30, "JJ", lagerId);
        });

    }
    @Test
    void opretFadMedAlkoholprocentHoejereEnd100() {
        int lagerId = 1;
        Assertions.assertThrows(RuntimeException.class, () -> {
            controller.opretFad("EXBourbon", 100.0, "NM76P", 90, 110, "JJ", lagerId);
        });
    }
}