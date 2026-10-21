package com.kaleworks.danmakutest.entity;

import static com.kaleworks.danmakutest.entity.AtaqueEnemigoRegistrado.*;

public enum SpellCardRegistrada {

    PRUEBA_BASICA(
        "Test Sign: First Pattern",
        1800,
        500,
        new CapaAtaque[] {
            CapaAtaque.bucle(NORMAL, 60)
        }
    ),

    VIENTO_ESCARLATA(
        "Scarlet Sign: Chaotic Wind",
        3600,
        800,
        new CapaAtaque[] {

            CapaAtaque.bucle(ANILLO_AZUL_12, 90),

            CapaAtaque.bucleConEspera(NORMAL, 45, 120),
            CapaAtaque.bucleConEspera(ESPIRAL_DOBLE, 5, 120),

            CapaAtaque.cercaJugador(NORMAL_AZUL, 20, 150)
        }
    ),

    TORMENTA_NOCTURNA(
        "Night Sign: Endless Storm",
        3600,
        1000,
        new CapaAtaque[] {

            CapaAtaque.unaVez(ANILLO_VERDE_64),

            CapaAtaque.bucle(DANMAKU_VENTILADOR_BY, 8),

            CapaAtaque.nVecesConEspera(ANILLO_AZUL_12, 30, 3, 300)
        }
    );

    public final String nombre;

    public final int duracion;

    public final int vidaBonus;

    public final CapaAtaque[] capas;

    SpellCardRegistrada(String nombre,
                        int duracion,
                        int vidaBonus,
                        CapaAtaque[] capas) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.vidaBonus = vidaBonus;
        this.capas = capas;
    }
}
