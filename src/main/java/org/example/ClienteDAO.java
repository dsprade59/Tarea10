package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private List<Cliente> cache = new ArrayList<>();

    public List<Cliente> cargarDesdeCSV(File file) {
        cache = leerCSV(file);
        return cache;
    }

    public List<Cliente> obtenerTodos() {
        return cache;
    }

    private List<Cliente> leerCSV(File file) {

        List<Cliente> clientes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {

                if (primera) {
                    primera = false;
                    continue;
                }

                String[] c = linea.split(",");

                clientes.add(new Cliente(
                        Integer.parseInt(c[0]),
                        c[1],
                        c[2],
                        c[3]
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientes;
    }
}
