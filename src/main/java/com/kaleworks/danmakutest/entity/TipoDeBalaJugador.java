package com.kaleworks.danmakutest.entity;

import java.awt.Color;

public enum TipoDeBalaJugador {

    NORMAL(
            4, 8,
            Color.CYAN,
            false,
            false,
            false,
            false,
            false,
            0.0,
            0.0
    ),

    HOMING(
            4, 8,
            Color.GREEN,
            false,
            true,
            false,
            false,
            false,
            160.0,
            0.10
    ),

    LASER(
            4, 16,
            Color.YELLOW,
            true,
            false,
            false,
            false,
            false,
            0.0,
            0.0
    ),

    LASER_EJE_Y(
            12, 0,
            Color.ORANGE,
            true,
            false,
            true,
            true,
            true,
            0.0,
            0.0
    );

    private final int ancho;
    private final int alto;
    private final Color color;
    private final boolean atraviesa;
    private final boolean homing;
    private final boolean ejeYCompleto;
    private final boolean persistente;
    private final boolean ancladoADisparo;
    private final double radioHoming;
    private final double giroHoming;

    TipoDeBalaJugador(int ancho, int alto, Color color,
                      boolean atraviesa, boolean homing,
                      boolean ejeYCompleto, boolean persistente,
                      boolean ancladoADisparo,
                      double radioHoming, double giroHoming) {
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
        this.atraviesa = atraviesa;
        this.homing = homing;
        this.ejeYCompleto = ejeYCompleto;
        this.persistente = persistente;
        this.ancladoADisparo = ancladoADisparo;
        this.radioHoming = radioHoming;
        this.giroHoming = giroHoming;
    }

    public int obtenerAncho() { return ancho; }
    public int obtenerAlto() { return alto; }
    public Color obtenerColor() { return color; }
    public boolean atraviesa() { return atraviesa; }
    public boolean esHoming() { return homing; }
    public boolean ocupaTodoEjeY() { return ejeYCompleto; }
    public boolean esPersistente() { return persistente; }
    public boolean esAncladoADisparo() { return ancladoADisparo; }
    public double obtenerRadioHoming() { return radioHoming; }
    public double obtenerGiroHoming() { return giroHoming; }
}