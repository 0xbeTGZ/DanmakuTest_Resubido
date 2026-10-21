package com.kaleworks.danmakutest.escenario;

import com.kaleworks.danmakutest.entity.AtaqueEnemigoRegistrado;
import com.kaleworks.danmakutest.entity.Boss;
import com.kaleworks.danmakutest.entity.CapaAtaque;
import com.kaleworks.danmakutest.entity.Enemigo;
import com.kaleworks.danmakutest.entity.SeleccionJugador;
import com.kaleworks.danmakutest.entity.SpellCardCompuesta;
import com.kaleworks.danmakutest.entity.SpellCardRegistrada;
import com.kaleworks.danmakutest.entity.SpellCardSimple;
import com.kaleworks.danmakutest.entity.TipoItem;

import java.util.ArrayList;
import java.util.List;

public class StageEjemplo extends StagePlantilla {

    public StageEjemplo(int zonaX, int zonaY, int zonaAncho, int zonaAlto, SeleccionJugador seleccion) {
        super(zonaX, zonaY, zonaAncho, zonaAlto, seleccion);
    }

    @Override
    protected void programarEventos() {

        stageManager.programar(90, s -> {
            for (int i = 0; i < 3; i++) {
                listaEnemigos.add(new Enemigo(
                        zonaX + 60 + i * 130, zonaY,
                        0, 1.0, 20, 20, 1,
                        zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                        new SpellCardSimple(
                                CapaAtaque.bucle(AtaqueEnemigoRegistrado.NORMAL_AZUL, 90)),
                        50, true, TipoItem.PUNTAJE));
            }
        });

        stageManager.programar(240, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + 50, zonaY,
                    0.1, 0.8, 28, 28, 6,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.ABANICO_AZUL_5, 70)),
                    150, true, TipoItem.POWER));

            listaEnemigos.add(new Enemigo(
                    zonaX + zonaAncho - 78, zonaY,
                    -0.1, 0.8, 28, 28, 6,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.ABANICO_ROJO_7, 70)),
                    150, true, TipoItem.POWER));
        });

        stageManager.programar(480, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + zonaAncho / 2 - 28, zonaY + 30,
                    0, 0.3, 56, 56, 60,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.nVeces(AtaqueEnemigoRegistrado.ANILLO_AZUL_12, 60, 1),
                            CapaAtaque.nVeces(AtaqueEnemigoRegistrado.ABANICO_ROJO_7, 90, 1),
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.LLUVIA_VERTICAL_BLANCA, 50)),
                    800, true, TipoItem.BOMBA));
        });

        stageManager.programar(900, s -> {
            List<com.kaleworks.danmakutest.entity.SpellCard> spells = new ArrayList<>();
            spells.add(new SpellCardCompuesta(SpellCardRegistrada.PRUEBA_BASICA));
            spells.add(new SpellCardCompuesta(SpellCardRegistrada.TORMENTA_NOCTURNA));

            listaEnemigos.add(new Boss(
                    zonaX + zonaAncho / 2 - 32, zonaY + 60,
                    64, 64,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    spells));
        });

    }
}
