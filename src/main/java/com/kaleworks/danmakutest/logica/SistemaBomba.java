package com.kaleworks.danmakutest.logica;

import java.util.ArrayList;

import com.kaleworks.danmakutest.entity.Jugador;
import com.kaleworks.danmakutest.entity.BalaEnemiga;
import com.kaleworks.danmakutest.util.CargadorSonidos;
import com.kaleworks.danmakutest.util.LogJuego;

public class SistemaBomba {

    public static final int FRAMES_INVULNERABILIDAD_BOMBA = 180;

    public boolean detonar(Jugador player, ArrayList<BalaEnemiga> listaBalasEnemigas) {

        if (!player.tieneBombas()) {
            return false;
        }

        boolean deathbombLogrado = player.intentarDeathbomb();

        if (!player.estaVivo() && !deathbombLogrado) {
            return false;
        }

        CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.BOMBA);
        player.restarBombas(1);

        int balasCanceladas = listaBalasEnemigas.size();
        listaBalasEnemigas.clear();

        player.activarInvulnerabilidad(FRAMES_INVULNERABILIDAD_BOMBA);

        LogJuego.aviso("Bomba detonada" + (deathbombLogrado ? " [DEATHBOMB]" : "")
                     + ". Balas canceladas: " + balasCanceladas
                     + ". Bombas restantes: " + player.obtenerBombas());

        return true;
    }
}
