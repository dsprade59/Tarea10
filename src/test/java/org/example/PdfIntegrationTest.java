package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfIntegrationTest {

    @TempDir
    File tempDir;

    @Test
    void testGeneracionPDFCompleta() throws Exception {


        File csv = new File(tempDir, "clientes_integracion.csv");

        try (FileWriter fw = new FileWriter(csv)) {
            fw.write("id,nombre,email,ciudad\n");
            fw.write("1,Ana,ana@test.com,Madrid\n");
            fw.write("2,Carlos,carlos@test.com,Valencia\n");
            fw.write("3,Lucia,lucia@test.com,Madrid\n");
        }


        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.cargarDesdeCSV(csv);

        assertEquals(3, clientes.size());


        File pdf = new File(tempDir, "informe.pdf");
        PdfService.exportar(pdf.getAbsolutePath(), clientes);


        assertTrue(pdf.exists());
        assertTrue(pdf.length() > 0);
    }
}
