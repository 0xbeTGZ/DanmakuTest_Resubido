package com.kaleworks.danmakutest.entity;

import java.awt.Color;
import java.util.ArrayList;

public class PatronDisparo {

    public enum Forma {

        SIMPLE,

        ABANICO,

        ANILLO,

        APUNTADO
    }

    private final Forma forma;
    private final TipoDeBalaEnemiga tipoBala;
    private final Color color;
    private final int cantidad;

    private final double velocidad;

    private final double aperturaGrados;

    public PatronDisparo(Forma forma,
                         TipoDeBalaEnemiga tipoBala,
                         Color color,
                         int cantidad,
                         double velocidad,
                         double aperturaGrados) {
        this.forma = forma;
        this.tipoBala = tipoBala;
        this.color = color;
        this.cantidad = cantidad;
        this.velocidad = velocidad;
        this.aperturaGrados = aperturaGrados;
    }

    public void disparar(Enemigo origen, ArrayList<BalaEnemiga> listaBalasEnemigas) {
        if (origen == null || listaBalasEnemigas == null) return;

        int spawnX = origen.obtenerCentroX();
        int spawnY = origen.obtenerY() + origen.obtenerAlto();

        switch (forma) {
            case SIMPLE:
                spawnearBala(spawnX, spawnY, 0, velocidad, listaBalasEnemigas, origen);
                break;

            case ABANICO:
                spawnearAbanico(spawnX, spawnY, listaBalasEnemigas, origen);
                break;

            case ANILLO:
                spawnearAnillo(spawnX, spawnY, listaBalasEnemigas, origen);
                break;

            case APUNTADO:
                spawnearApuntado(spawnX, spawnY, listaBalasEnemigas, origen);
                break;

            default:
                break;
        }
    }

    private void spawnearAbanico(int x, int y,
                                 ArrayList<BalaEnemiga> lista,
                                 Enemigo origen) {
        if (cantidad <= 0) return;
        if (cantidad == 1) {
            spawnearBala(x, y, 0, velocidad, lista, origen);
            return;
        }
        double mitad = aperturaGrados / 2.0;

        double anguloBaseRad = Math.toRadians(90);
        double pasoGrados = aperturaGrados / (cantidad - 1);
        for (int i = 0; i < cantidad; i++) {
            double offsetGrados = -mitad + pasoGrados * i;
            double anguloRad = anguloBaseRad + Math.toRadians(offsetGrados);
            double sx = Math.cos(anguloRad) * velocidad;
            double sy = Math.sin(anguloRad) * velocidad;
            spawnearBala(x, y, sx, sy, lista, origen);
        }
    }

    private void spawnearAnillo(int x, int y,
                                ArrayList<BalaEnemiga> lista,
                                Enemigo origen) {
        if (cantidad <= 0) return;
        double pasoRad = (Math.PI * 2.0) / cantidad;
        for (int i = 0; i < cantidad; i++) {
            double anguloRad = pasoRad * i;
            double sx = Math.cos(anguloRad) * velocidad;
            double sy = Math.sin(anguloRad) * velocidad;
            spawnearBala(x, y, sx, sy, lista, origen);
        }
    }

    private void spawnearApuntado(int x, int y,
                                  ArrayList<BalaEnemiga> lista,
                                  Enemigo origen) {

        int destinoX = (origen.obtenerZonaX() + origen.obtenerZonaXmax()) / 2;
        int destinoY = origen.obtenerZonaYmax() - 60;
        double dx = destinoX - x;
        double dy = destinoY - y;
        double mag = Math.sqrt(dx * dx + dy * dy);
        if (mag < 0.0001) {
            spawnearBala(x, y, 0, velocidad, lista, origen);
            return;
        }
        double sx = (dx / mag) * velocidad;
        double sy = (dy / mag) * velocidad;
        spawnearBala(x, y, sx, sy, lista, origen);
    }

    private void spawnearBala(int x, int y,
                              double speedX, double speedY,
                              ArrayList<BalaEnemiga> lista,
                              Enemigo origen) {
        BalaEnemiga b = new BalaEnemiga(
                x, y,
                speedX, speedY,
                tipoBala,
                color,
                origen.obtenerZonaX(), origen.obtenerZonaXmax(),
                origen.obtenerZonaY(), origen.obtenerZonaYmax()
        );
        lista.add(b);
    }

    public Forma obtenerForma() { return forma; }
    public TipoDeBalaEnemiga obtenerTipoBala() { return tipoBala; }
    public Color obtenerColor() { return color; }
    public int obtenerCantidad() { return cantidad; }
    public double obtenerVelocidad() { return velocidad; }
    public double obtenerAperturaGrados() { return aperturaGrados; }
}
