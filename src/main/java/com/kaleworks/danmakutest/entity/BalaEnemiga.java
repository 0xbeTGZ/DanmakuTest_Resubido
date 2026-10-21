package com.kaleworks.danmakutest.entity;

import java.awt.Graphics;
import java.awt.Color;

public class BalaEnemiga {

    private double X;
    private double Y;

    private double speedX;
    private double speedY;

    private int hitboxAncho;
    private int hitboxAlto;

    private TipoDeBalaEnemiga tipo;
    private Color color;

    private boolean activa;

    private int zonaX;
    private int zonaXmax;
    private int zonaY;
    private int zonaYmax;

    public BalaEnemiga(double inicioX, double inicioY,
                       double speedX, double speedY,
                       TipoDeBalaEnemiga tipo,
                       Color color,
                       int zonaX, int zonaXmax,
                       int zonaY, int zonaYmax) {

        this.X = inicioX;
        this.Y = inicioY;

        double mult = Dificultad.actual().multiplicadorVelocidadBala();
        this.speedX = speedX * mult;
        this.speedY = speedY * mult;

        this.tipo = tipo;
        this.color = color;

        this.hitboxAncho = tipo.obtenerAnchoBase();
        this.hitboxAlto = tipo.obtenerAltoBase();

        this.activa = true;

        this.zonaX = zonaX;
        this.zonaXmax = zonaXmax;
        this.zonaY = zonaY;
        this.zonaYmax = zonaYmax;
    }

    public void actualizar() {
        X += speedX;
        Y += speedY;

        if (X <= zonaX) {
            activa = false;
        }

        if (X >= zonaXmax) {
            activa = false;
        }

        if (Y  <= zonaY) {
            activa = false;
        }

        if (Y >= zonaYmax) {
            activa = false;
        }
    }

    public void dibujar(Graphics g) {

        tipo.dibujar(g, (int) X, (int) Y, hitboxAncho, hitboxAlto, color);
    }

    public boolean estaActiva() {
        return activa;
    }

    public void desactivar() {
        this.activa = false;
    }

    public int obtenerX() {
        return (int) X;
    }

    public int obtenerY() {
        return (int) Y;
    }

    public int obtenerAncho() {
        return hitboxAncho;
    }

    public int obtenerAlto() {
        return hitboxAlto;
    }

    public TipoDeBalaEnemiga obtenerTipo() {
        return tipo;
    }

    public Color obtenerColor() {
        return color;
    }
}