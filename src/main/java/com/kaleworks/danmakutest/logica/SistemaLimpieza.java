package com.kaleworks.danmakutest.logica;

import java.util.ArrayList;

import com.kaleworks.danmakutest.entity.BalaJugador;
import com.kaleworks.danmakutest.entity.BalaEnemiga;
import com.kaleworks.danmakutest.entity.Enemigo;
import com.kaleworks.danmakutest.entity.Item;
import com.kaleworks.danmakutest.util.LogJuego;

public class SistemaLimpieza {

    public void limpiarTodo(ArrayList<BalaJugador> listaBalasJugador,
                            ArrayList<BalaEnemiga> listaBalasEnemigas,
                            ArrayList<Enemigo> listaEnemigos,
                            ArrayList<Item> listaItems) {

        try {
            limpiarBalasJugador(listaBalasJugador);
        } catch (Exception e) {
            LogJuego.error("Fallo en limpiarBalasJugador()", e);
        }

        try {
            limpiarBalasEnemigas(listaBalasEnemigas);
        } catch (Exception e) {
            LogJuego.error("Fallo en limpiarBalasEnemigas()", e);
        }

        try {
            limpiarEnemigos(listaEnemigos);
        } catch (Exception e) {
            LogJuego.error("Fallo en limpiarEnemigos()", e);
        }

        try {
            limpiarItems(listaItems);
        } catch (Exception e) {
            LogJuego.error("Fallo en limpiarItems()", e);
        }
    }

    private void limpiarBalasJugador(ArrayList<BalaJugador> listaBalasJugador) {
        if (listaBalasJugador == null) {
            return;
        }

        for (int i = 0; i < listaBalasJugador.size(); i++) {
            BalaJugador balaActual = listaBalasJugador.get(i);

            if (balaActual == null || !balaActual.estaActiva()) {
                listaBalasJugador.remove(i);
                i--;
            }
        }
    }

    private void limpiarBalasEnemigas(ArrayList<BalaEnemiga> listaBalasEnemigas) {
        if (listaBalasEnemigas == null) {
            return;
        }

        for (int i = 0; i < listaBalasEnemigas.size(); i++) {
            BalaEnemiga balaActual = listaBalasEnemigas.get(i);

            if (balaActual == null || !balaActual.estaActiva()) {
                listaBalasEnemigas.remove(i);
                i--;
            }
        }
    }

    private void limpiarEnemigos(ArrayList<Enemigo> listaEnemigos) {
        if (listaEnemigos == null) {
            return;
        }

        for (int i = 0; i < listaEnemigos.size(); i++) {
            Enemigo enemigoActual = listaEnemigos.get(i);

            if (enemigoActual == null || !enemigoActual.estaActivo()) {
                listaEnemigos.remove(i);
                i--;
            }
        }
    }

    private void limpiarItems(ArrayList<Item> listaItems) {
        if (listaItems == null) {
            return;
        }

        for (int i = 0; i < listaItems.size(); i++) {
            Item itemActual = listaItems.get(i);

            if (itemActual == null || !itemActual.estaActivo()) {
                listaItems.remove(i);
                i--;
            }
        }
    }
}