package com.kaleworks.danmakutest.entity;

import java.awt.Graphics;

public class Item {

    private double x;
    private double y;
    private double speedX;
    private double speedY;

    private int ancho;
    private int alto;

    private boolean activo;

    private TipoItem tipo;

    private int zonaX;
    private int zonaY;
    private int zonaXmax;
    private int zonaYmax;

    public Item(double x, double y,
                TipoItem tipo,
                int zonaX, int zonaY, int zonaXmax, int zonaYmax) {

        this.x = x;
        this.y = y;
        this.tipo = tipo;

        this.speedX = 0;
        this.speedY = 2.0;

        this.ancho = tipo.obtenerAncho();
        this.alto = tipo.obtenerAlto();

        this.activo = true;

        this.zonaX = zonaX;
        this.zonaY = zonaY;
        this.zonaXmax = zonaXmax;
        this.zonaYmax = zonaYmax;
    }

    public void actualizar() {
        if (!activo) {
            return;
        }

        x += speedX;
        y += speedY;

        if (y > zonaYmax + alto) {
            activo = false;
        }

        if (x + ancho < zonaX) {
            activo = false;
        }

        if (x > zonaXmax) {
            activo = false;
        }
    }

    public void dibujar(Graphics g) {

        if (!activo) {
            return;
        }

        g.setColor(tipo.obtenerColorRelleno());
        g.fillOval((int) x, (int) y, ancho, alto);

        g.setColor(tipo.obtenerColorBorde());
        g.drawOval((int) x, (int) y, ancho, alto);
    }

    public boolean estaActivo() {
        return activo;
    }

    public void recoger() {
        activo = false;
    }

    public void desactivar() {
        activo = false;
    }

    public int obtenerX() {
        return (int) x;
    }

    public int obtenerY() {
        return (int) y;
    }

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAlto() {
        return alto;
    }

    public int obtenerCentroX() {
        return (int) x + ancho / 2;
    }

    public int obtenerCentroY() {
        return (int) y + alto / 2;
    }

    public TipoItem obtenerTipo() {
        return tipo;
    }

    public void definirVelocidad(double nuevaSpeedX, double nuevaSpeedY) {
        this.speedX = nuevaSpeedX;
        this.speedY = nuevaSpeedY;
    }
}