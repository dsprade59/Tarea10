package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testGettersCliente() {
        Cliente c = new Cliente(
                1,
                "Ana",
                "ana@email.com",
                "Madrid"
        );

        assertEquals(1, c.getId());
        assertEquals("Ana", c.getNombre());
        assertEquals("ana@email.com", c.getEmail());
        assertEquals("Madrid", c.getCiudad());
    }
}
