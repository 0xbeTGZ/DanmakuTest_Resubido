package com.kaleworks.danmakutest.entity;

import java.util.ArrayList;

public class SpellCardSimple implements SpellCard {

    private final CapaAtaque[] capas;
    private Jugador jugador;
    private int framesActivos;

    public SpellCardSimple(CapaAtaque... capas) {
        this.capas = capas;
        this.framesActivos = 0;
    }

    public void definirJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public String obtenerNombre() {

        return "";
    }

    @Override
    public int obtenerVidaInicial() {

        return 0;
    }

    @Override
    public int obtenerDuracionMaxima() {

        return Integer.MAX_VALUE;
    }

    @Override
    public void iniciar(Enemigo origen) {
        framesActivos = 0;
        for (CapaAtaque capa : capas) {
            capa.resetear();
        }
    }

    @Override
    public void actualizar(Enemigo origen,
                           ArrayList<BalaEnemiga> listaBalasEnemigas,
                           int framesActivos) {
        this.framesActivos = framesActivos;
        for (CapaAtaque capa : capas) {
            capa.actualizar(framesActivos, origen, jugador, listaBalasEnemigas);
        }
    }

    public void desactivarCapa(int indice) {
        if (indice >= 0 && indice < capas.length) {
            capas[indice].desactivar();
        }
    }

    public void activarCapa(int indice) {
        if (indice >= 0 && indice < capas.length) {
            capas[indice].activar();
        }
    }
}
