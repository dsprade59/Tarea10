package org.example;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class PdfService {

    public static void exportar(String ruta, List<Cliente> clientes) {

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
            cs.newLineAtOffset(50, 800);
            cs.showText("Informe de Clientes");
            cs.endText();

            cs.setFont(PDType1Font.HELVETICA, 10);
            int y = 760;

            for (Cliente c : clientes) {
                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText(
                        c.getNombre() + " - " + c.getEmail() +
                                " (" + c.getCiudad() + ")"
                );
                cs.endText();
                y -= 14;
            }

            BufferedImage imgChart =
                    ChartService.crearImagenGrafico(clientes);

            PDImageXObject img =
                    LosslessFactory.createFromImage(doc, imgChart);

            cs.drawImage(img, 50, 300, 500, 250);

            cs.close();
            doc.save(ruta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
