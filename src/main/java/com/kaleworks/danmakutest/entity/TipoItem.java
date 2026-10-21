package com.kaleworks.danmakutest.entity;

import java.awt.Color;

public enum TipoItem {

    PUNTAJE(
            new Color(0, 4, 255),
            new Color(107, 110, 255),
            12,
            12
    ),

    POWER(
            new Color(255, 0, 0),
            new Color(247, 200, 200),
            14,
            14
    ),

    POWER_GRANDE(
            new Color(255, 0, 0),
            new Color(255, 200, 200),
            18,
            18
    ),

    FULL_POWER(
            new Color(255, 221, 0),
            new Color(255, 247, 191),
            16,
            16
    ),

    BOMBA(
            new Color(17, 255, 0),
            new Color(184, 255, 179),
            16,
            16
    ),

    EXTEND(
            new Color(238, 0, 255),
            new Color(249, 158, 255),
            16,
            16
    );

    private final Color colorRelleno;
    private final Color colorBorde;
    private final int ancho;
    private final int alto;

    TipoItem(Color colorRelleno, Color colorBorde, int ancho, int alto) {
        this.colorRelleno = colorRelleno;
        this.colorBorde = colorBorde;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Color obtenerColorRelleno() {
        return colorRelleno;
    }

    public Color obtenerColorBorde() {
        return colorBorde;
    }

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAlto() {
        return alto;
    }
}