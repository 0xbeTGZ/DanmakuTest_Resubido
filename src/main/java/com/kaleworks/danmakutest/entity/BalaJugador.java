package com.kaleworks.danmakutest.entity;

import java.awt.Graphics;
import java.util.List;

public class BalaJugador {
    private double X;
    private double Y;
    private double speedX;
    private double speedY;
    private int hitboxAncho;
    private int hitboxAlto;
    private int altoVisible;
    private TipoDeBalaJugador tipo;
    private boolean activa;
    private int zonaY;
    private int zonaYmax;
    private int zonaX;
    private int zonaXmax;

    public BalaJugador(double inicioX, double inicioY,
            double speedX, double speedY,
            int hitboxAncho, int hitboxAlto,
            TipoDeBalaJugador tipo,
            int zonaX, int zonaXmax,
            int zonaY, int zonaYmax) {

        this.X = inicioX;
        this.Y = inicioY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hitboxAncho = hitboxAncho;
        this.hitboxAlto = hitboxAlto;
        this.altoVisible = hitboxAlto;
        this.tipo = tipo;
        this.activa = true;
        this.zonaX = zonaX;
        this.zonaXmax = zonaXmax;
        this.zonaY = zonaY;
        this.zonaYmax = zonaYmax;

        if (tipo == TipoDeBalaJugador.LASER_EJE_Y) {
            this.Y = zonaY;
            this.altoVisible = 0;
        }
    }

    public void actualizar() {
        actualizar(null);
    }

    public void actualizar(List<Enemigo> enemigos) {
        switch (tipo) {
            case NORMAL:
                X += speedX;
                Y += speedY;
                break;

            case HOMING:
                if (enemigos != null) {
                    aplicarHoming(enemigos);
                }
                X += speedX;
                Y += speedY;
                break;

            case LASER:
                X += speedX;
                Y += speedY;
                break;

            case LASER_EJE_Y:
                break;
        }

        if (tipo == TipoDeBalaJugador.LASER_EJE_Y) {
            if (altoVisible <= 0) {
                activa = false;
            }

            if (Y < zonaY) {
                Y = zonaY;
            }

            if (Y + altoVisible > zonaYmax) {
                altoVisible = Math.max(0, zonaYmax - (int) Y);
            }
        } else {
            if (Y < zonaY) {
                activa = false;
            }

            if (Y + hitboxAlto > zonaYmax) {
                activa = false;
            }
        }

        if (X < zonaX) {
            activa = false;
        }

        if (X + hitboxAncho > zonaXmax) {
            activa = false;
        }
    }

    private void aplicarHoming(List<Enemigo> enemigos) {
        double radio = tipo.obtenerRadioHoming();
        double giro = tipo.obtenerGiroHoming();
        if (radio <= 0 || giro <= 0) {
            return;
        }

        Enemigo objetivo = null;
        double distanciaMin = radio;
        for (Enemigo e : enemigos) {
            if (!e.estaActivo()) {
                continue;
            }
            double dx = e.obtenerCentroX() - X;
            double dy = e.obtenerCentroY() - Y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < distanciaMin) {
                distanciaMin = dist;
                objetivo = e;
            }
        }

        if (objetivo == null) {
            return;
        }

        double dx = objetivo.obtenerCentroX() - X;
        double dy = objetivo.obtenerCentroY() - Y;
        double anguloDeseado = Math.atan2(dy, dx);

        double anguloActual = Math.atan2(speedY, speedX);

        double diferencia = anguloDeseado - anguloActual;
        while (diferencia > Math.PI)  { diferencia -= 2 * Math.PI; }
        while (diferencia < -Math.PI) { diferencia += 2 * Math.PI; }

        if (diferencia > giro)  { diferencia = giro; }
        if (diferencia < -giro) { diferencia = -giro; }

        double anguloNuevo = anguloActual + diferencia;
        double rapidez = Math.sqrt(speedX * speedX + speedY * speedY);

        speedX = Math.cos(anguloNuevo) * rapidez;
        speedY = Math.sin(anguloNuevo) * rapidez;
    }

    public void dibujar(Graphics g) {

        g.setColor(tipo.obtenerColor());

        if (tipo == TipoDeBalaJugador.LASER_EJE_Y) {
            g.fillRect((int) X, (int) Y, hitboxAncho, altoVisible);
        } else if (tipo == TipoDeBalaJugador.LASER) {
            g.fillRect((int) X, (int) Y, hitboxAncho, hitboxAlto);
        } else {
            g.fillOval((int) X, (int) Y, hitboxAncho, hitboxAlto);
        }
    }

    public boolean estaActiva() {
        return activa;
    }

    public void desactivar() {
        activa = false;
    }

    public void fijarX(double nuevoX) {
        this.X = nuevoX;
    }

    public void fijarY(double nuevoY) {
        this.Y = nuevoY;
    }

    public void fijarAlturaVisible(double nuevaAltura) {
        this.altoVisible = Math.max(0, (int) Math.round(nuevaAltura));
    }

    public int obtenerAlturaVisible() {
        return altoVisible;
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
        if (tipo == TipoDeBalaJugador.LASER_EJE_Y) {
            return altoVisible;
        }
        return hitboxAlto;
    }

    public TipoDeBalaJugador obtenerTipo() {
        return tipo;
    }

    public boolean atraviesa() {
        return tipo.atraviesa();
    }

    public boolean esHoming() {
        return tipo.esHoming();
    }

    public boolean ocupaTodoEjeY() {
        return tipo.ocupaTodoEjeY();
    }
}