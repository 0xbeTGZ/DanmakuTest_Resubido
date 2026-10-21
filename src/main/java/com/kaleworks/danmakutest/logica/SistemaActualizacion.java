package com.kaleworks.danmakutest.logica;

import java.util.ArrayList;

import com.kaleworks.danmakutest.entity.BalaJugador;
import com.kaleworks.danmakutest.entity.BalaEnemiga;
import com.kaleworks.danmakutest.entity.Enemigo;
import com.kaleworks.danmakutest.entity.Item;
import com.kaleworks.danmakutest.util.LogJuego;

public class SistemaActualizacion {

    public void actualizarTodo(ArrayList<BalaJugador> listaBalasJugador,
                               ArrayList<BalaEnemiga> listaBalasEnemigas,
                               ArrayList<Enemigo> listaEnemigos,
                               ArrayList<Item> listaItems) {

        try {

            actualizarBalasJugador(listaBalasJugador, listaEnemigos);
        } catch (Exception e) {
            LogJuego.error("Fallo en actualizarBalasJugador()", e);
        }

        try {
            actualizarBalasEnemigas(listaBalasEnemigas);
        } catch (Exception e) {
            LogJuego.error("Fallo en actualizarBalasEnemigas()", e);
        }

        try {
            actualizarEnemigos(listaEnemigos);
        } catch (Exception e) {
            LogJuego.error("Fallo en actualizarEnemigos()", e);
        }

        try {
            actualizarItems(listaItems);
        } catch (Exception e) {
            LogJuego.error("Fallo en actualizarItems()", e);
        }
    }

    private void actualizarBalasJugador(ArrayList<BalaJugador> listaBalasJugador,
                                        ArrayList<Enemigo> listaEnemigos) {
        for (int i = 0; i < listaBalasJugador.size(); i++) {
            BalaJugador balaActual = listaBalasJugador.get(i);
            balaActual.actualizar(listaEnemigos);
        }
    }

    private void actualizarBalasEnemigas(ArrayList<BalaEnemiga> listaBalasEnemigas) {
        for (int i = 0; i < listaBalasEnemigas.size(); i++) {
            BalaEnemiga balaActual = listaBalasEnemigas.get(i);
            balaActual.actualizar();
        }
    }

    private void actualizarEnemigos(ArrayList<Enemigo> listaEnemigos) {
        for (int i = 0; i < listaEnemigos.size(); i++) {
            Enemigo enemigoActual = listaEnemigos.get(i);
            enemigoActual.actualizar();
        }
    }

    private void actualizarItems(ArrayList<Item> listaItems) {
        if (listaItems == null) {
            return;
        }

        for (int i = 0; i < listaItems.size(); i++) {
            Item itemActual = listaItems.get(i);
            itemActual.actualizar();
        }
    }
}