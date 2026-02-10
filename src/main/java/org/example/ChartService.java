package org.example;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class ChartService {

    public static JFreeChart crearGraficoCircular(List<Cliente> clientes) {

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        clientes.stream()
                .collect(Collectors.groupingBy(
                        Cliente::getCiudad,
                        Collectors.counting()
                ))
                .forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart(
                "Clientes por ciudad",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 10));

        return chart;
    }

    public static void mostrarGrafico(List<Cliente> clientes, Pane panel) {

        panel.getChildren().clear();

        if (clientes.isEmpty()) {
            return;
        }

        SwingNode swingNode = new SwingNode();

        // Escuchar cambios de tamaño del panel JavaFX
        panel.widthProperty().addListener((obs, oldV, newV) ->
                swingNode.resize(newV.doubleValue(), panel.getHeight())
        );

        panel.heightProperty().addListener((obs, oldV, newV) ->
                swingNode.resize(panel.getWidth(), newV.doubleValue())
        );

        SwingUtilities.invokeLater(() -> {
            JFreeChart chart = crearGraficoCircular(clientes);
            ChartPanel chartPanel = new ChartPanel(chart);

            chartPanel.setPreferredSize(new Dimension(
                    (int) panel.getWidth(),
                    (int) panel.getHeight()
            ));

            chartPanel.revalidate();
            chartPanel.repaint();

            swingNode.setContent(chartPanel);
        });

        panel.getChildren().add(swingNode);

        // Fuerza el layout inicial
        panel.applyCss();
        panel.layout();
    }



    public static BufferedImage crearImagenGrafico(List<Cliente> clientes) {
        return crearGraficoCircular(clientes)
                .createBufferedImage(500, 300);
    }
}
