package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainController {

    @FXML private TextField txtFiltroNombre;
    @FXML private ComboBox<String> cmbCiudad;

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colCiudad;

    @FXML private Pane panelGrafico;
    @FXML private Label lblTotal;

    private final ClienteDAO dao = new ClienteDAO();
    private List<Cliente> datosActuales;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        txtFiltroNombre.setOnAction(e -> filtrar());

        cmbCiudad.valueProperty().addListener((obs, oldV, newV) -> filtrar());
    }


    @FXML
    private void cargarCSV() {

        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar CSV");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

        File file = fc.showOpenDialog(null);
        if (file == null) return;

        datosActuales = dao.cargarDesdeCSV(file);
        cargarCiudades();
        filtrar();
    }

    private void cargarCiudades() {

        cmbCiudad.getItems().clear();

        Set<String> ciudades = dao.obtenerTodos()
                .stream()
                .map(Cliente::getCiudad)
                .collect(Collectors.toSet());

        cmbCiudad.getItems().add("Todas");
        cmbCiudad.getItems().addAll(ciudades);
        cmbCiudad.setValue("Todas");
    }

    @FXML
    private void filtrar() {

        String texto = txtFiltroNombre.getText();
        String ciudad = cmbCiudad.getValue();

        datosActuales = dao.obtenerTodos().stream()
                .filter(c -> texto == null || texto.isBlank()
                        || c.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .filter(c -> ciudad == null || ciudad.equals("Todas")
                        || c.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());

        tablaClientes.getItems().setAll(datosActuales);
        lblTotal.setText("Total clientes: " + datosActuales.size());

        ChartService.mostrarGrafico(datosActuales, panelGrafico);
    }

    @FXML
    private void exportarPDF() {

        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar PDF");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
        fc.setInitialFileName("clientes.pdf");

        File file = fc.showSaveDialog(null);
        if (file == null) return;

        PdfService.exportar(file.getAbsolutePath(), datosActuales);
    }

    @FXML
    private void mostrarAyuda() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText("Información");
        alert.setContentText(
                "Gestión de Clientes\n" +
                        "Autor: Sergio Íñiguez García\n" +
                        "CSV + PDF + Gráficas"
        );
        alert.showAndWait();
    }
}
