package org.example;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChartServiceTest {

    @Test
    void testCrearImagenGrafico() {

        List<Cliente> clientes = List.of(
                new Cliente(1, "Ana", "a@a.com", "Madrid"),
                new Cliente(2, "Luis", "l@l.com", "Madrid"),
                new Cliente(3, "Eva", "e@e.com", "Sevilla")
        );

        BufferedImage img =
                ChartService.crearImagenGrafico(clientes);

        assertNotNull(img);
        assertTrue(img.getWidth() > 0);
        assertTrue(img.getHeight() > 0);
    }
}
