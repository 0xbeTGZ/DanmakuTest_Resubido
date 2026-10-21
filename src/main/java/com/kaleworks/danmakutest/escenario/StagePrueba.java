package com.kaleworks.danmakutest.escenario;

import com.kaleworks.danmakutest.dialogo.CatalogoDialogos;
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

public class StagePrueba extends StagePlantilla {

    public StagePrueba(int zonaX, int zonaY, int zonaAncho, int zonaAlto, SeleccionJugador seleccion) {
        super(zonaX, zonaY, zonaAncho, zonaAlto, seleccion);
    }

    @Override
    protected void programarEventos() {

        // dialogo de apertura: se activa en el primer frame
        stageManager.programar(1, s -> {
            iniciarDialogo(CatalogoDialogos.PRUEBA_CUATRO_PERSONAJES);
        });

        stageManager.programar(60, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + 80, zonaY,
                    0, 1.2, 24, 24, 12,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.NORMAL_AZUL, 50)),
                    100, true, TipoItem.POWER));
        });

        stageManager.programar(100, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + zonaAncho - 104, zonaY,
                    0, 1.2, 24, 24, 12,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.NORMAL_ROJO, 50)),
                    150, true, TipoItem.FULL_POWER));
        });

        stageManager.programar(220, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + 70, zonaY,
                    0.2, 1.0, 36, 36, 24,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.ABANICO_AZUL_5, 70)),
                    300, true, TipoItem.POWER_GRANDE));

            listaEnemigos.add(new Enemigo(
                    zonaX + zonaAncho - 110, zonaY,
                    -0.2, 1.0, 26, 26, 14,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.ABANICO_ROJO_7, 70)),
                    120, false, null));
        });

        stageManager.programar(420, s -> {
            listaEnemigos.add(new Enemigo(
                    zonaX + zonaAncho / 2 - 28, zonaY + 30,
                    0, 0.35, 56, 56, 70,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    new SpellCardSimple(
                            CapaAtaque.nVeces(AtaqueEnemigoRegistrado.ANILLO_AZUL_12, 60, 1),
                            CapaAtaque.nVeces(AtaqueEnemigoRegistrado.ABANICO_ROJO_7, 90, 1),
                            CapaAtaque.bucle(AtaqueEnemigoRegistrado.LLUVIA_VERTICAL_BLANCA, 50)),
                    1000, true, TipoItem.EXTEND));
        });

        stageManager.programar(900, s -> {
            List<com.kaleworks.danmakutest.entity.SpellCard> spells = new ArrayList<>();
            spells.add(new SpellCardCompuesta(SpellCardRegistrada.PRUEBA_BASICA));
            spells.add(new SpellCardCompuesta(SpellCardRegistrada.VIENTO_ESCARLATA));

            listaEnemigos.add(new Boss(
                    zonaX + zonaAncho / 2 - 32, zonaY + 60,
                    64, 64,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto,
                    spells));
        });
    }
}
