package com.kaleworks.danmakutest.entity;

import java.util.ArrayList;

public interface SpellCard {

    String obtenerNombre();

    int obtenerVidaInicial();

    int obtenerDuracionMaxima();

    void actualizar(Enemigo boss, ArrayList<BalaEnemiga> listaBalasEnemigas, int framesActivos);

    default void iniciar(Enemigo boss) {
    }

    default void terminar(Enemigo boss, ArrayList<BalaEnemiga> listaBalasEnemigas) {

        listaBalasEnemigas.clear();
    }
}
