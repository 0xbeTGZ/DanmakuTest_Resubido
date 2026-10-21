package com.kaleworks.danmakutest.entity;

import java.util.List;
import com.kaleworks.danmakutest.util.CargadorSonidos;

public class JugadorReimu extends Jugador {

    public JugadorReimu(int inicioX, int inicioY, int zonaX, int zonaY, int zonaAncho, int zonaAlto, SeleccionJugador seleccion) {
        super(inicioX, inicioY, zonaX, zonaY, zonaAncho, zonaAlto, seleccion);

        this.velocidad = 4;
        this.intervaloDisparo = 5;
    }

    @Override
    public void disparar(List<BalaJugador> listaBalas, int limiteSuperior, int limiteInferior, boolean focus) {

        int centroX = X + hitboxAncho / 2;
        int power = obtenerPowerActual();

        if (seleccion == SeleccionJugador.REIMU_A) {

            if (!focus) {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 5, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 6, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 8, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 7, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 60 && power < 80) {

                    agregarBala(listaBalas, centroX - 8, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 7, Y, 0, -8, TipoDeBalaJugador.NORMAL);

                    agregarBala(listaBalas, centroX - 12, Y, -0.5, -7.5, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX + 11, Y, 0.5, -7.5, TipoDeBalaJugador.HOMING);
                }

                if (power >= 80) {

                    agregarBala(listaBalas, centroX - 10, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 5, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0, -9.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 5, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 10, Y, 0, -9, TipoDeBalaJugador.NORMAL);

                    agregarBala(listaBalas, centroX - 14, Y, -0.8, -8.2, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX - 7, Y, -0.4, -8.0, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX + 7, Y, 0.4, -8.0, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX + 14, Y, 0.8, -8.2, TipoDeBalaJugador.HOMING);
                }

            } else {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 3, Y, 0, -8.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0, -8.2, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 4, Y, 0, -8.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -8.2, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 5, Y, 0, -8.3, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 2, Y, 0, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -8.3, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 5, Y, 0, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 2, Y, 0, -8.7, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -8.7, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -8.4, TipoDeBalaJugador.NORMAL);

                    agregarBala(listaBalas, centroX - 8, Y, -0.25, -8.1, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX + 7, Y, 0.25, -8.1, TipoDeBalaJugador.HOMING);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 6, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0, -9.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 3, Y, 0, -9, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 6, Y, 0, -9, TipoDeBalaJugador.NORMAL);

                    agregarBala(listaBalas, centroX - 9, Y, -0.3, -8.5, TipoDeBalaJugador.HOMING);
                    agregarBala(listaBalas, centroX + 9, Y, 0.3, -8.5, TipoDeBalaJugador.HOMING);
                }
            }
        }

        if (seleccion == SeleccionJugador.REIMU_B) {

            if (!focus) {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 5, Y, -2, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 1, Y, 2, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 6, Y, -2, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 4, Y, 2, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 8, Y, -2.5, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 3, Y, -1, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 1, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 7, Y, 2.5, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 10, Y, -3, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 5, Y, -1.5, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 5, Y, 1.5, -8, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 10, Y, 3, -8, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 12, Y, -3.2, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 7, Y, -2, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 2, Y, -0.8, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 3, Y, 0.8, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 8, Y, 2, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 13, Y, 3.2, -8.5, TipoDeBalaJugador.NORMAL);
                }

            } else {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 3, Y, -0.45, -8.1, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0.45, -8.1, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 4, Y, -0.5, -8.1, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 0.5, -8.1, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 5, Y, -0.7, -8.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 2, Y, -0.25, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 1, Y, 0.25, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 4, Y, 0.7, -8.2, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 6, Y, -0.8, -8.2, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 3, Y, -0.35, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX, Y, 0, -8.6, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 3, Y, 0.35, -8.4, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 6, Y, 0.8, -8.2, TipoDeBalaJugador.NORMAL);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 7, Y, -0.9, -8.3, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 4, Y, -0.45, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX - 1, Y, -0.15, -8.7, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 2, Y, 0.15, -8.7, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 5, Y, 0.45, -8.5, TipoDeBalaJugador.NORMAL);
                    agregarBala(listaBalas, centroX + 8, Y, 0.9, -8.3, TipoDeBalaJugador.NORMAL);
                }
            }
        }
    }
}