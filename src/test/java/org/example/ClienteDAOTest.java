package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteDAOTest {

    @TempDir
    File tempDir;

    @Test
    void testLeerCSV() throws Exception {

        File csv = new File(tempDir, "clientes_test.csv");

        try (FileWriter fw = new FileWriter(csv)) {
            fw.write("id,nombre,email,ciudad\n");
            fw.write("1,Ana,ana@test.com,Madrid\n");
            fw.write("2,Carlos,carlos@test.com,Valencia\n");
        }

        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.cargarDesdeCSV(csv);

        assertEquals(2, clientes.size());
        assertEquals("Ana", clientes.get(0).getNombre());
        assertEquals("Valencia", clientes.get(1).getCiudad());
    }
}
