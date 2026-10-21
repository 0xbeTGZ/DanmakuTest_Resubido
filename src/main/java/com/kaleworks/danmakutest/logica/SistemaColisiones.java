package com.kaleworks.danmakutest.logica;

import java.util.ArrayList;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import com.kaleworks.danmakutest.entity.Jugador;
import com.kaleworks.danmakutest.entity.BalaJugador;
import com.kaleworks.danmakutest.entity.BalaEnemiga;
import com.kaleworks.danmakutest.entity.Enemigo;
import com.kaleworks.danmakutest.util.CargadorSonidos;
import com.kaleworks.danmakutest.util.LogJuego;

public class SistemaColisiones {

    private static final int RADIO_GRAZE = 15;

    private final Set<BalaEnemiga> balasYaGrazedadas =
            Collections.newSetFromMap(new IdentityHashMap<>());

    public void procesarTodo(Jugador player,
                             ArrayList<BalaJugador> BalasJugadorArr,
                             ArrayList<BalaEnemiga> BalasEnemigasArr,
                             ArrayList<Enemigo> EnemigosArr) {

        try {
            procesarBalasJugadorContraEnemigos(BalasJugadorArr, EnemigosArr);
        } catch (Exception e) {
            LogJuego.error("Fallo en procesarBalasJugadorContraEnemigos()", e);
        }

        try {
            procesarJugadorContraBalasEnemigas(player, BalasEnemigasArr);
        } catch (Exception e) {
            LogJuego.error("Fallo en procesarJugadorContraBalasEnemigas()", e);
        }

        try {
            procesarGraze(player, BalasEnemigasArr);
        } catch (Exception e) {
            LogJuego.error("Fallo en procesarGraze()", e);
        }

    }

    private void procesarBalasJugadorContraEnemigos(ArrayList<BalaJugador> BalasJugadorArr,
                                                    ArrayList<Enemigo> EnemigosArr) {

        for (int i = 0; i < BalasJugadorArr.size(); i++) {

            BalaJugador balaActual = BalasJugadorArr.get(i);

            if (!balaActual.estaActiva()) {
                continue;
            }

            for (int j = 0; j < EnemigosArr.size(); j++) {

                Enemigo enemigoActual = EnemigosArr.get(j);

                if (!enemigoActual.estaActivo()) {
                    continue;
                }

                if (hayColisionRectangular(
                        balaActual.obtenerX(),
                        balaActual.obtenerY(),
                        balaActual.obtenerAncho(),
                        balaActual.obtenerAlto(),
                        enemigoActual.obtenerX(),
                        enemigoActual.obtenerY(),
                        enemigoActual.obtenerAncho(),
                        enemigoActual.obtenerAlto()
                )) {

                    enemigoActual.recibirDmg(1);
                    CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.IMPACTO_ENEMIGO);

                    if (!balaActual.atraviesa()) {
                        balaActual.desactivar();

                        break;
                    }

                }
            }
        }
    }

    private void procesarJugadorContraBalasEnemigas(Jugador player,
                                                    ArrayList<BalaEnemiga> BalasEnemigasArr) {

        if (!player.puedeRecibirImpacto()) {
            return;
        }

        for (int i = 0; i < BalasEnemigasArr.size(); i++) {

            BalaEnemiga balaActual = BalasEnemigasArr.get(i);

            if (!balaActual.estaActiva()) {
                continue;
            }

            if (hayColisionRectangular(
                    balaActual.obtenerX(),
                    balaActual.obtenerY(),
                    balaActual.obtenerAncho(),
                    balaActual.obtenerAlto(),
                    player.obtenerX(),
                    player.obtenerY(),
                    player.obtenerAncho(),
                    player.obtenerAlto()
            )) {

                balaActual.desactivar();

                player.recibirImpacto();

                break;
            }
        }
    }

    private void procesarGraze(Jugador player,
                               ArrayList<BalaEnemiga> BalasEnemigasArr) {

        balasYaGrazedadas.removeIf(b -> !b.estaActiva());

        int gx = player.obtenerX() - RADIO_GRAZE;
        int gy = player.obtenerY() - RADIO_GRAZE;
        int gAncho = player.obtenerAncho() + RADIO_GRAZE * 2;
        int gAlto  = player.obtenerAlto()  + RADIO_GRAZE * 2;

        for (int i = 0; i < BalasEnemigasArr.size(); i++) {
            BalaEnemiga b = BalasEnemigasArr.get(i);
            if (!b.estaActiva()) continue;
            if (balasYaGrazedadas.contains(b)) continue;

            if (hayColisionRectangular(
                    b.obtenerX(), b.obtenerY(), b.obtenerAncho(), b.obtenerAlto(),
                    gx, gy, gAncho, gAlto)) {
                player.sumarGraze(1);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.GRAZE);
                balasYaGrazedadas.add(b);
            }
        }
    }

    private boolean hayColisionRectangular(int x1, int y1, int ancho1, int alto1,
                                           int x2, int y2, int ancho2, int alto2) {

        if (x1 + ancho1 <= x2) {
            return false;
        }

        if (x1 >= x2 + ancho2) {
            return false;
        }

        if (y1 + alto1 <= y2) {
            return false;
        }

        if (y1 >= y2 + alto2) {
            return false;
        }

        return true;
    }
}