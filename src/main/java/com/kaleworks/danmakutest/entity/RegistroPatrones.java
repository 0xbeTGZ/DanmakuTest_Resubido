package com.kaleworks.danmakutest.entity;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public final class RegistroPatrones {

    private static final Map<String, PatronDisparo> tabla = new HashMap<>();

    static {
        registrar("simple_blanco", new PatronDisparo(
                PatronDisparo.Forma.SIMPLE, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.WHITE, 1, 4.0, 0));

        registrar("simple_azul", new PatronDisparo(
                PatronDisparo.Forma.SIMPLE, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.CYAN, 1, 4.0, 0));

        registrar("simple_rojo", new PatronDisparo(
                PatronDisparo.Forma.SIMPLE, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.RED, 1, 4.0, 0));

        registrar("abanico_azul_5", new PatronDisparo(
                PatronDisparo.Forma.ABANICO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.CYAN, 5, 3.5, 60));

        registrar("abanico_rojo_7", new PatronDisparo(
                PatronDisparo.Forma.ABANICO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.RED, 7, 3.5, 90));

        registrar("anillo_blanco_8", new PatronDisparo(
                PatronDisparo.Forma.ANILLO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.WHITE, 8, 3.0, 0));

        registrar("anillo_azul_12", new PatronDisparo(
                PatronDisparo.Forma.ANILLO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.CYAN, 12, 3.0, 0));

        registrar("anillo_verde_24", new PatronDisparo(
                PatronDisparo.Forma.ANILLO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.GREEN, 24, 2.5, 0));

        registrar("apuntado_blanco", new PatronDisparo(
                PatronDisparo.Forma.APUNTADO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.WHITE, 1, 4.5, 0));

        registrar("ventilador_amarillo", new PatronDisparo(
                PatronDisparo.Forma.ABANICO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.YELLOW, 9, 3.2, 120));

        registrar("ventilador_magenta", new PatronDisparo(
                PatronDisparo.Forma.ABANICO, TipoDeBalaEnemiga.ORB_PEQUENO,
                Color.MAGENTA, 11, 3.2, 140));
    }

    private RegistroPatrones() {

    }

    public static void registrar(String clave, PatronDisparo patron) {
        tabla.put(clave, patron);
    }

    public static PatronDisparo obtener(String clave) {
        return tabla.get(clave);
    }

    public static boolean existe(String clave) {
        return tabla.containsKey(clave);
    }
}
