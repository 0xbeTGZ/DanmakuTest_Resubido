package com.kaleworks.danmakutest.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public final class CargadorImagenes {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private CargadorImagenes() { }

    public static BufferedImage cargar(String ruta) {

        BufferedImage cacheada = CACHE.get(ruta);
        if (cacheada != null) {
            return cacheada;
        }

        try (InputStream in = CargadorImagenes.class.getResourceAsStream(ruta)) {
            if (in == null) {
                LogJuego.error("Imagen no encontrada en classpath: " + ruta, null);
                return null;
            }
            BufferedImage img = ImageIO.read(in);
            if (img == null) {
                LogJuego.error("ImageIO.read devolvio null para: " + ruta, null);
                return null;
            }
            CACHE.put(ruta, img);
            return img;
        } catch (IOException e) {
            LogJuego.error("Fallo al leer imagen: " + ruta, e);
            return null;
        }
    }

    public static BufferedImage frameDeGrid(String ruta,
                                            int anchoFrame, int altoFrame,
                                            int columna, int fila) {
        BufferedImage hoja = cargar(ruta);
        if (hoja == null) return null;
        int x = columna * anchoFrame;
        int y = fila * altoFrame;

        if (x < 0 || y < 0 || x + anchoFrame > hoja.getWidth() ||
                y + altoFrame > hoja.getHeight()) {
            LogJuego.error("frameDeGrid fuera de rango: " + ruta +
                    " col=" + columna + " fila=" + fila, null);
            return null;
        }

        return hoja.getSubimage(x, y, anchoFrame, altoFrame);
    }

    public static BufferedImage[] framesDeFila(String ruta,
                                               int anchoFrame, int altoFrame,
                                               int fila, int cantidad) {
        BufferedImage[] frames = new BufferedImage[cantidad];
        for (int i = 0; i < cantidad; i++) {
            frames[i] = frameDeGrid(ruta, anchoFrame, altoFrame, i, fila);
        }
        return frames;
    }

    public static BufferedImage[] framesDeColumna(String ruta,
                                                  int anchoFrame, int altoFrame,
                                                  int columna, int cantidad) {
        BufferedImage[] frames = new BufferedImage[cantidad];
        for (int i = 0; i < cantidad; i++) {
            frames[i] = frameDeGrid(ruta, anchoFrame, altoFrame, columna, i);
        }
        return frames;
    }

    public static BufferedImage subimagen(String ruta, int x, int y, int ancho, int alto) {
        BufferedImage hoja = cargar(ruta);
        if (hoja == null) return null;
        if (x < 0 || y < 0 || x + ancho > hoja.getWidth() ||
                y + alto > hoja.getHeight()) {
            LogJuego.error("subimagen fuera de rango: " + ruta +
                    " (" + x + "," + y + " " + ancho + "x" + alto + ")", null);
            return null;
        }
        return hoja.getSubimage(x, y, ancho, alto);
    }

    public static void limpiarCache() {
        CACHE.clear();
    }
}
