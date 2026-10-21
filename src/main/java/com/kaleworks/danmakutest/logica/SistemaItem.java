package com.kaleworks.danmakutest.logica;

import java.util.ArrayList;

import com.kaleworks.danmakutest.entity.Item;
import com.kaleworks.danmakutest.entity.Jugador;
import com.kaleworks.danmakutest.entity.TipoItem;
import static com.kaleworks.danmakutest.entity.TipoItem.POWER_GRANDE;

import com.kaleworks.danmakutest.util.CargadorSonidos;

public class SistemaItem {

    private int calcularLineaAutocollect(Jugador player) {

        return 200;
    }

    public int procesarItems(Jugador player, ArrayList<Item> listaItems) {
        int scoreGanado = 0;

        int lineaAuto = calcularLineaAutocollect(player);
        boolean autocollectActivo = player.obtenerY() < lineaAuto;

        for (Item it : listaItems) {
            if (!it.estaActivo()) {
                continue;
            }

            if (autocollectActivo) {
                aplicarAutocollect(player, it);
            }

            if (colisiona(player, it)) {
                scoreGanado += aplicarEfecto(player, it.obtenerTipo());
                it.recoger();

            }
        }

        return scoreGanado;
    }

    private void aplicarAutocollect(Jugador player, Item it) {
        double dx = player.obtenerCentroX() - it.obtenerCentroX();
        double dy = player.obtenerCentroY() - it.obtenerCentroY();
        double mag = Math.sqrt(dx * dx + dy * dy);
        if (mag < 0.0001) {
            return;
        }
        double velAuto = 8.0;
        double sx = (dx / mag) * velAuto;
        double sy = (dy / mag) * velAuto;
        it.definirVelocidad(sx, sy);
    }

    private boolean colisiona(Jugador player, Item it) {
        int playerX = player.obtenerX();
        int playerY = player.obtenerY();
        int playerW = player.obtenerAncho();
        int playerH = player.obtenerAlto();

        int itemX = it.obtenerX();
        int itemY = it.obtenerY();
        int itemW = it.obtenerAncho();
        int itemH = it.obtenerAlto();

        return playerX < itemX + itemW &&
               playerX + playerW > itemX &&
               playerY < itemY + itemH &&
               playerY + playerH > itemY;
    }

    private int aplicarEfecto(Jugador player, TipoItem tipo) {
        switch (tipo) {
            case PUNTAJE:
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ITEM);
                return 100;

            case POWER:
                player.agregarPower(1);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ITEM);
                return 50;

            case POWER_GRANDE:
                player.agregarPower(20);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ITEM);
                return 100;

            case FULL_POWER:
                player.agregarPower(80);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ITEM);
                return 100;

            case BOMBA:
                player.agregarBombas(1);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ITEM);
                return 150;

            case EXTEND:
                player.agregarVida(1);
                CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.EXTEND);
                return 300;

            default:
                return 0;
        }
    }
}