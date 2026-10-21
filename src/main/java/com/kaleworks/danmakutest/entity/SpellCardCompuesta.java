package com.kaleworks.danmakutest.entity;

import java.util.ArrayList;

public class SpellCardCompuesta implements SpellCard {

    private final SpellCardRegistrada definicion;
    private Jugador jugador;
    private boolean capturada;

    public SpellCardCompuesta(SpellCardRegistrada definicion) {
        this.definicion = definicion;
        this.capturada = false;
    }

    public void definirJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public String obtenerNombre() {
        return definicion.nombre;
    }

    @Override
    public int obtenerVidaInicial() {
        return definicion.vidaBonus;
    }

    @Override
    public int obtenerDuracionMaxima() {
        return definicion.duracion;
    }

    @Override
    public void iniciar(Enemigo origen) {
        capturada = false;
        for (CapaAtaque capa : definicion.capas) {
            capa.resetear();
        }
    }

    @Override
    public void actualizar(Enemigo origen,
                           ArrayList<BalaEnemiga> listaBalasEnemigas,
                           int framesActivos) {
        for (CapaAtaque capa : definicion.capas) {
            capa.actualizar(framesActivos, origen, jugador, listaBalasEnemigas);
        }
    }

    public void marcarCapturada() {
        this.capturada = true;
    }

    public boolean fueCapturada() {
        return capturada;
    }

    public void desactivarCapa(int indice) {
        if (indice >= 0 && indice < definicion.capas.length) {
            definicion.capas[indice].desactivar();
        }
    }

    public void activarCapa(int indice) {
        if (indice >= 0 && indice < definicion.capas.length) {
            definicion.capas[indice].activar();
        }
    }

    public SpellCardRegistrada obtenerDefinicion() {
        return definicion;
    }
}
