package com.kaleworks.danmakutest.entity;

import java.util.List;
import com.kaleworks.danmakutest.util.CargadorSonidos;
public class JugadorMarisa extends Jugador {

    private BalaJugador laserEjeIzq;
    private BalaJugador laserEjeDer;

    public JugadorMarisa(int inicioX, int inicioY, int zonaX, int zonaY, int zonaAncho, int zonaAlto, SeleccionJugador seleccion) {
        super(inicioX, inicioY, zonaX, zonaY, zonaAncho, zonaAlto, seleccion);

        this.velocidad = 5;
        this.intervaloDisparo = 4;
    }

    @Override
    public void actualizar(boolean arriba, boolean abajo, boolean izquierda, boolean derecha, boolean focus) {
        super.actualizar(arriba, abajo, izquierda, derecha, focus);

        if (seleccion != SeleccionJugador.MARISA_B) {
            return;
        }

        if (laserEjeIzq == null && laserEjeDer == null) {
            return;
        }

        int offsetBeam = focus ? 10 : 18;
        int wLaserEje = TipoDeBalaJugador.LASER_EJE_Y.obtenerAncho();
        int centroHitboxX = X + hitboxAncho / 2;
        int centroHitboxY = Y + hitboxLargo / 2;
        int xBeamIzq = centroHitboxX - offsetBeam - (wLaserEje / 2);
        int xBeamDer = centroHitboxX + offsetBeam - (wLaserEje / 2);
        double alturaVisible = Math.max(0, centroHitboxY - zonaY);

        if (laserEjeIzq != null && laserEjeIzq.estaActiva()) {
            laserEjeIzq.fijarX(xBeamIzq);
            laserEjeIzq.fijarY(zonaY);
            laserEjeIzq.fijarAlturaVisible(alturaVisible);
        }

        if (laserEjeDer != null && laserEjeDer.estaActiva()) {
            laserEjeDer.fijarX(xBeamDer);
            laserEjeDer.fijarY(zonaY);
            laserEjeDer.fijarAlturaVisible(alturaVisible);
        }
    }

    @Override
    public void disparar(List<BalaJugador> listaBalas, int limiteSuperior, int limiteInferior, boolean focus) {

        int centroX = X + hitboxAncho / 2;
        int power = obtenerPowerActual();

        int wLaserEje = TipoDeBalaJugador.LASER_EJE_Y.obtenerAncho();
        int hLaserEje = TipoDeBalaJugador.LASER_EJE_Y.obtenerAlto();

        if (seleccion == SeleccionJugador.MARISA_A) {

            if (!focus) {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 4, Y, 0, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 6, Y, 0, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 8, Y, 0, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 7, Y, 0, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 8, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 7, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 12, Y, -1.2, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 11, Y, 1.2, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 10, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 5, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -11.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 5, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 10, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 15, Y, -1.5, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 15, Y, 1.5, -10.5, TipoDeBalaJugador.LASER);
                }

            } else {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 2, Y, 0, -10.2, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -10.2, TipoDeBalaJugador.LASER);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 4, Y, 0, -10.2, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 1, Y, 0, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 0, -10.2, TipoDeBalaJugador.LASER);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 5, Y, 0, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, 0, -10.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -10.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -10.3, TipoDeBalaJugador.LASER);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 5, Y, 0, -10.6, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 4, Y, 0, -10.6, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 8, Y, -0.35, -10.2, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 7, Y, 0.35, -10.2, TipoDeBalaJugador.LASER);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 6, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 3, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -11.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 3, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 6, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 9, Y, -0.4, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 9, Y, 0.4, -10.7, TipoDeBalaJugador.LASER);
                }
            }
        }

        if (seleccion == SeleccionJugador.MARISA_B) {

            int offsetBeam = focus ? 10 : 18;

            int centroHitboxX = X + hitboxAncho / 2;
            int centroHitboxY = Y + hitboxLargo / 2;

            int xBeamIzq = centroHitboxX - offsetBeam - (wLaserEje / 2);
            int xBeamDer = centroHitboxX + offsetBeam - (wLaserEje / 2);

            double alturaVisible = Math.max(0, centroHitboxY - zonaY);

            if (power > 20) {

                if (laserEjeIzq == null || !laserEjeIzq.estaActiva()) {
                    laserEjeIzq = new BalaJugador(xBeamIzq, zonaY, 0, 0,
                            wLaserEje, hLaserEje, TipoDeBalaJugador.LASER_EJE_Y,
                            zonaX, zonaX + zonaAncho, zonaY, zonaY + zonaAlto);
                    laserEjeIzq.fijarAlturaVisible(alturaVisible);
                    listaBalas.add(laserEjeIzq);
                } else {
                    laserEjeIzq.fijarX(xBeamIzq);
                    laserEjeIzq.fijarY(zonaY);
                    laserEjeIzq.fijarAlturaVisible(alturaVisible);
                }

                if (laserEjeDer == null || !laserEjeDer.estaActiva()) {
                    laserEjeDer = new BalaJugador(xBeamDer, zonaY, 0, 0,
                            wLaserEje, hLaserEje, TipoDeBalaJugador.LASER_EJE_Y,
                            zonaX, zonaX + zonaAncho, zonaY, zonaY + zonaAlto);
                    laserEjeDer.fijarAlturaVisible(alturaVisible);
                    listaBalas.add(laserEjeDer);
                } else {
                    laserEjeDer.fijarX(xBeamDer);
                    laserEjeDer.fijarY(zonaY);
                    laserEjeDer.fijarAlturaVisible(alturaVisible);
                }
            }

            if (!focus) {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 6, Y, -3, -9, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, 0, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 3, -9, TipoDeBalaJugador.LASER);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 8, Y, -3, -9.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 3, Y, -1, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 1, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 7, Y, 3, -9.5, TipoDeBalaJugador.LASER);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 10, Y, -3.2, -9.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 5, Y, -1.5, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 5, Y, 1.5, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 10, Y, 3.2, -9.5, TipoDeBalaJugador.LASER);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 12, Y, -3.5, -9.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 7, Y, -2, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, -0.8, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 3, Y, 0.8, -10.5, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 8, Y, 2, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 13, Y, 3.5, -9.5, TipoDeBalaJugador.LASER);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 14, Y, -3.8, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 9, Y, -2.5, -10.2, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 4, Y, -1.2, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -11, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 6, Y, 1.2, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 11, Y, 2.5, -10.2, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 16, Y, 3.8, -10, TipoDeBalaJugador.LASER);
                }

            } else {

                if (power >= 0 && power < 20) {
                    agregarBala(listaBalas, centroX - 3, Y, -0.6, -9.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 3, Y, 0.6, -9.8, TipoDeBalaJugador.LASER);
                }

                if (power >= 20 && power < 40) {
                    agregarBala(listaBalas, centroX - 5, Y, -0.9, -9.9, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, -0.3, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0.3, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 4, Y, 0.9, -9.9, TipoDeBalaJugador.LASER);
                }

                if (power >= 40 && power < 60) {
                    agregarBala(listaBalas, centroX - 6, Y, -1.0, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 3, Y, -0.45, -10.4, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX, Y, 0, -10.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 3, Y, 0.45, -10.4, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 6, Y, 1.0, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 60 && power < 80) {
                    agregarBala(listaBalas, centroX - 7, Y, -1.1, -10, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 4, Y, -0.6, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 1, Y, -0.2, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 2, Y, 0.2, -10.7, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 5, Y, 0.6, -10.3, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 8, Y, 1.1, -10, TipoDeBalaJugador.LASER);
                }

                if (power >= 80) {
                    agregarBala(listaBalas, centroX - 8, Y, -1.2, -10.1, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 5, Y, -0.7, -10.4, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX - 2, Y, -0.25, -10.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 1, Y, 0, -11.1, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 4, Y, 0.25, -10.8, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 7, Y, 0.7, -10.4, TipoDeBalaJugador.LASER);
                    agregarBala(listaBalas, centroX + 10, Y, 1.2, -10.1, TipoDeBalaJugador.LASER);
                }
            }
        }
    }

    @Override
    protected void alSoltarDisparo() {
        if (laserEjeIzq != null) {
            laserEjeIzq.desactivar();
            laserEjeIzq = null;
        }

        if (laserEjeDer != null) {
            laserEjeDer.desactivar();
            laserEjeDer = null;
        }
    }
}