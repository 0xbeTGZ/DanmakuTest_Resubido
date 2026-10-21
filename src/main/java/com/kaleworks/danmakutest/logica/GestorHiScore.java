package com.kaleworks.danmakutest.logica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.kaleworks.danmakutest.util.LogJuego;

public class GestorHiScore {

    private static final String NOMBRE_CARPETA = ".danmakutest";

    private static final String NOMBRE_ARCHIVO = "hiscore.txt";

    private Path obtenerRutaArchivo() {
        String home = System.getProperty("user.home");
        return Paths.get(home, NOMBRE_CARPETA, NOMBRE_ARCHIVO);
    }

    public int cargar() {
        Path ruta = obtenerRutaArchivo();

        if (!Files.exists(ruta)) {
            return 0;
        }

        try {

            List<String> lineas = Files.readAllLines(ruta);
            if (lineas.isEmpty()) {
                return 0;
            }

            String contenido = lineas.get(0).trim();
            int valor = Integer.parseInt(contenido);

            if (valor < 0) {
                return 0;
            }

            return valor;

        } catch (IOException e) {
            LogJuego.error("No pude leer el archivo de hi-score: " + ruta, e);
            return 0;
        } catch (NumberFormatException e) {
            LogJuego.error("El archivo de hi-score tiene contenido invalido: " + ruta, e);
            return 0;
        }
    }

    public boolean guardar(int scoreCandidato) {
        if (scoreCandidato < 0) {
            return false;
        }

        int actual = cargar();
        if (scoreCandidato <= actual) {

            return false;
        }

        Path ruta = obtenerRutaArchivo();
        try {

            Files.createDirectories(ruta.getParent());

            Files.writeString(ruta, String.valueOf(scoreCandidato));

            LogJuego.aviso("Nuevo hi-score guardado: " + scoreCandidato + " (anterior: " + actual + ")");
            return true;

        } catch (IOException e) {
            LogJuego.error("No pude escribir el archivo de hi-score: " + ruta, e);
            return false;
        }
    }
}
