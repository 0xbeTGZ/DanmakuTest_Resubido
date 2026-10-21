package com.kaleworks.danmakutest.entity;

import java.util.ArrayList;

public class CapaAtaque {

    public enum Modo {
        BUCLE,
        UNA_VEZ,
        N_VECES
    }

    public enum Condicion {
        SIEMPRE,
        CERCA_JUGADOR,
        ESPERAR_FRAMES
    }

    private final AtaqueEnemigoRegistrado ataque;
    private final Modo modo;
    private final Condicion condicion;
    private final int cooldown;
    private final int vecesMaximas;
    private final int frameInicio;
    private final double distanciaActivacion;

    private int cooldownActual;
    private int vecesEjecutadas;
    private boolean activa;

    public CapaAtaque(AtaqueEnemigoRegistrado ataque,
                      Modo modo,
                      Condicion condicion,
                      int cooldown,
                      int vecesMaximas,
                      int frameInicio,
                      double distanciaActivacion) {
        this.ataque = ataque;
        this.modo = modo;
        this.condicion = condicion;
        this.cooldown = cooldown;
        this.vecesMaximas = vecesMaximas;
        this.frameInicio = frameInicio;
        this.distanciaActivacion = distanciaActivacion;
        this.cooldownActual = 0;
        this.vecesEjecutadas = 0;
        this.activa = true;
    }

    public static CapaAtaque bucle(AtaqueEnemigoRegistrado ataque, int cooldown) {
        return new CapaAtaque(ataque, Modo.BUCLE, Condicion.SIEMPRE,
                cooldown, 0, 0, 0);
    }

    public static CapaAtaque unaVez(AtaqueEnemigoRegistrado ataque) {
        return new CapaAtaque(ataque, Modo.UNA_VEZ, Condicion.SIEMPRE,
                0, 1, 0, 0);
    }

    public static CapaAtaque nVeces(AtaqueEnemigoRegistrado ataque, int cooldown, int n) {
        return new CapaAtaque(ataque, Modo.N_VECES, Condicion.SIEMPRE,
                cooldown, n, 0, 0);
    }

    public static CapaAtaque bucleConEspera(AtaqueEnemigoRegistrado ataque,
                                             int cooldown,
                                             int frameInicio) {
        return new CapaAtaque(ataque, Modo.BUCLE, Condicion.ESPERAR_FRAMES,
                cooldown, 0, frameInicio, 0);
    }

    public static CapaAtaque unaVezConEspera(AtaqueEnemigoRegistrado ataque,
                                              int frameInicio) {
        return new CapaAtaque(ataque, Modo.UNA_VEZ, Condicion.ESPERAR_FRAMES,
                0, 1, frameInicio, 0);
    }

    public static CapaAtaque nVecesConEspera(AtaqueEnemigoRegistrado ataque,
                                              int cooldown,
                                              int n,
                                              int frameInicio) {
        return new CapaAtaque(ataque, Modo.N_VECES, Condicion.ESPERAR_FRAMES,
                cooldown, n, frameInicio, 0);
    }

    public static CapaAtaque cercaJugador(AtaqueEnemigoRegistrado ataque,
                                           int cooldown,
                                           double distancia) {
        return new CapaAtaque(ataque, Modo.BUCLE, Condicion.CERCA_JUGADOR,
                cooldown, 0, 0, distancia);
    }

    public static CapaAtaque cercaJugadorUnaVez(AtaqueEnemigoRegistrado ataque,
                                                 double distancia) {
        return new CapaAtaque(ataque, Modo.UNA_VEZ, Condicion.CERCA_JUGADOR,
                0, 1, 0, distancia);
    }

    public void resetear() {
        cooldownActual = 0;
        vecesEjecutadas = 0;
        activa = true;
    }

    public void desactivar() {
        activa = false;
    }

    public void activar() {
        activa = true;
    }

    public boolean estaActiva() {
        return activa;
    }

    public void actualizar(int framesActivos,
                           Enemigo origen,
                           Jugador jugador,
                           ArrayList<BalaEnemiga> balas) {

        if (!activa) return;

        switch (condicion) {
            case ESPERAR_FRAMES:
                if (framesActivos < frameInicio) return;
                break;
            case CERCA_JUGADOR:
                if (!jugadorEstaCerca(origen, jugador)) return;
                break;
            case SIEMPRE:
            default:
                break;
        }

        if (cooldownActual > 0) {
            cooldownActual--;
            return;
        }

        ataque.disparar(origen, balas);
        vecesEjecutadas++;
        cooldownActual = cooldown;

        if (modo == Modo.UNA_VEZ) {
            activa = false;
        } else if (modo == Modo.N_VECES && vecesEjecutadas >= vecesMaximas) {
            activa = false;
        }
    }

    private boolean jugadorEstaCerca(Enemigo origen, Jugador jugador) {
        if (jugador == null) return false;
        double dx = origen.obtenerCentroX() - jugador.obtenerCentroX();
        double dy = origen.obtenerCentroY() - jugador.obtenerCentroY();
        return Math.sqrt(dx * dx + dy * dy) <= distanciaActivacion;
    }
}
