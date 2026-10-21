package com.kaleworks.danmakutest.entity;

import java.awt.Color;
import java.util.ArrayList;

public enum AtaqueEnemigoRegistrado {

    NORMAL {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            crearBala(
                    e,
                    listaBalasEnemigas,
                    0,
                    4,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.WHITE
            );
        }
    },

    NORMAL_AZUL {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            crearBala(
                    e,
                    listaBalasEnemigas,
                    0,
                    4,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.CYAN
            );
        }
    },

    NORMAL_ROJO {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            crearBala(
                    e,
                    listaBalasEnemigas,
                    0,
                    4,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.RED
            );
        }
    },

    DOBLE_RECTO_ROJO {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            crearBalaDesde(
                    e.obtenerX() + 5,
                    e.obtenerY() + e.obtenerAlto(),
                    e,
                    listaBalasEnemigas,
                    0,
                    4,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.RED
            );

            crearBalaDesde(
                    e.obtenerX() + e.obtenerAncho() - 5,
                    e.obtenerY() + e.obtenerAlto(),
                    e,
                    listaBalasEnemigas,
                    0,
                    4,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.RED
            );
        }
    },

ESPIRAL_DOBLE {

    private double anguloRotacion = 0.0;

    @Override
    public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {

        int balasPorBrazo = 14;
        int cantidadBrazos = 4;
        double velocidadBase = 2.1;
        double separacionAngular = 22.0;
        double rotacionPorDisparo = 7.0;

        Color[] coloresBrazos = {Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.GREEN};

        for (int pulso = 0; pulso < 8; pulso++) {

            anguloRotacion = (anguloRotacion + rotacionPorDisparo) % 360.0;

            for (int brazo = 0; brazo < cantidadBrazos; brazo++) {

                double offsetBrazo = (360.0 / cantidadBrazos) * brazo;

                for (int j = 0; j < balasPorBrazo; j++) {

                    double anguloGrados = anguloRotacion + offsetBrazo + (separacionAngular * j);
                    double rad = Math.toRadians(anguloGrados);

                    double velocidad = velocidadBase + (j * 0.08);

                    double speedX = Math.sin(rad) * velocidad;
                    double speedY = Math.cos(rad) * velocidad;

                    Color color = coloresBrazos[brazo % coloresBrazos.length];

                    BalaEnemiga bala = new BalaEnemiga(
                            e.obtenerCentroX(),
                            e.obtenerCentroY(),
                            speedX,
                            speedY,
                            TipoDeBalaEnemiga.ORB_PEQUENO,
                            color,
                            e.obtenerZonaX(),
                            e.obtenerZonaXmax(),
                            e.obtenerZonaY(),
                            e.obtenerZonaYmax()
                    );

                    listaBalasEnemigas.add(bala);
                }
            }
        }
    }
},

MANDALA_CAPAS_SEN {

    private double anguloRotacion = 0.0;

    @Override
    public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {

        int capas = 3;
        int balasPorCapa = 24;
        double velocidadBase = 3.5;
        double amplitudSen = 1.8;
        int frecuencia = 4;
        double rotacionPorDisparo = 5.0;

        Color[] colores = {Color.RED, Color.ORANGE, Color.YELLOW};

        anguloRotacion = (anguloRotacion + rotacionPorDisparo) % 360.0;

        for (int capa = 0; capa < capas; capa++) {

            double offsetCapaGrados = anguloRotacion + (capa * 15.0);

            for (int i = 0; i < balasPorCapa; i++) {

                double anguloGrados = offsetCapaGrados + (360.0 * i / balasPorCapa);
                double rad = Math.toRadians(anguloGrados);

                double velocidad = velocidadBase + amplitudSen * Math.sin(frecuencia * rad);

                double speedX = Math.sin(rad) * velocidad;
                double speedY = Math.cos(rad) * velocidad;

                BalaEnemiga bala = new BalaEnemiga(
                        e.obtenerCentroX(),
                        e.obtenerCentroY(),
                        speedX,
                        speedY,
                        TipoDeBalaEnemiga.ORB_MEDIANO,
                        colores[capa],
                        e.obtenerZonaX(),
                        e.obtenerZonaXmax(),
                        e.obtenerZonaY(),
                        e.obtenerZonaYmax()
                );

                listaBalasEnemigas.add(bala);
            }
        }
    }
},
    ABANICO_AZUL_5 {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            dispararAbanico(
                    e,
                    listaBalasEnemigas,
                    5,
                    -25,
                    25,
                    4.2,
                    TipoDeBalaEnemiga.AGUJA,
                    Color.CYAN
            );
        }
    },

    ABANICO_ROJO_7 {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            dispararAbanico(
                    e,
                    listaBalasEnemigas,
                    7,
                    -40,
                    40,
                    4.0,
                    TipoDeBalaEnemiga.AGUJA,
                    Color.RED
            );
        }
    },

    ANILLO_BLANCO_8 {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            dispararAnillo(
                    e,
                    listaBalasEnemigas,
                    8,
                    0,
                    2.5,
                    TipoDeBalaEnemiga.ORB_GRANDE,
                    Color.WHITE
            );
        }
    },

    ANILLO_AZUL_12 {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            dispararAnillo(
                    e,
                    listaBalasEnemigas,
                    12,
                    0,
                    3.0,
                    TipoDeBalaEnemiga.ORB_MEDIANO,
                    Color.CYAN
            );
        }
    },

    ANILLO_VERDE_64 {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            dispararAnillo(
                    e,
                    listaBalasEnemigas,
                    64,
                    0,
                    4.0,
                    TipoDeBalaEnemiga.ORB_PEQUENO,
                    Color.GREEN
            );
        }
    },

    LLUVIA_VERTICAL_BLANCA {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {
            int baseX = e.obtenerX();
            int ancho = e.obtenerAncho();

            for (int i = 0; i < 5; i++) {
                int spawnX = baseX + (ancho * i / 4);

                crearBalaDesde(
                        spawnX,
                        e.obtenerY() + e.obtenerAlto(),
                        e,
                        listaBalasEnemigas,
                        0,
                        5,
                        TipoDeBalaEnemiga.ORB_PEQUENO,
                        Color.WHITE
                );
            }
        }
    },

    APUNTADO_SIMPLE {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {

            double centroPantalla = (e.obtenerZonaX() + e.obtenerZonaXmax()) / 2.0;
            double diferencia = e.obtenerCentroX() - centroPantalla;
            double speedX = diferencia / 120.0;

            crearBala(
                    e,
                    listaBalasEnemigas,
                    speedX,
                    4.5,
                    TipoDeBalaEnemiga.ORB_MEDIANO,
                    Color.YELLOW
            );
        }
    },

    DANMAKU_VENTILADOR_BY {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {

            int capas = 10;
            int abanicosPorCapa = 1;
            int balasPorAbanico = 10;

            double velocidadBase = 2.8;
            double aperturaAbanico = 50.0;
            double barridoHorizontal = 70.0;
            int separacionY = 12;

            for (int capa = 0; capa < capas; capa++) {

                int origenY = e.obtenerCentroY() - (capa * separacionY);

                boolean invertido = (capa % 2 == 1);

                for (int abanico = 0; abanico < abanicosPorCapa; abanico++) {

                    double tAbanico;
                    if (abanicosPorCapa == 1) {
                        tAbanico = 0.5;
                    } else {
                        tAbanico = (double) abanico / (abanicosPorCapa - 1);
                    }

                    double centroAngular;
                    if (!invertido) {
                        centroAngular = -barridoHorizontal + (barridoHorizontal * 2.0 * tAbanico);
                    } else {
                        centroAngular = barridoHorizontal - (barridoHorizontal * 2.0 * tAbanico);
                    }

                    for (int i = 0; i < balasPorAbanico; i++) {

                        double tBala;
                        if (balasPorAbanico == 1) {
                            tBala = 0.5;
                        } else {
                            tBala = (double) i / (balasPorAbanico - 1);
                        }

                        double angulo = centroAngular - aperturaAbanico + (aperturaAbanico * 2.0 * tBala);
                        double rad = Math.toRadians(angulo);

                        double speedX = Math.sin(rad) * (velocidadBase + capa * 0.25);
                        double speedY = Math.cos(rad) * (velocidadBase + capa * 0.25);

                        Color colorActual;
                        if ((capa + abanico) % 2 == 0) {
                            colorActual = Color.BLUE;
                        } else {
                            colorActual = Color.YELLOW;
                        }

                        BalaEnemiga bala = new BalaEnemiga(
                                e.obtenerCentroX(),
                                origenY,
                                speedX,
                                speedY,
                                TipoDeBalaEnemiga.ORB_MEDIANO,
                                colorActual,
                                e.obtenerZonaX(),
                                e.obtenerZonaXmax(),
                                e.obtenerZonaY(),
                                e.obtenerZonaYmax()
                        );

                        listaBalasEnemigas.add(bala);
                    }
                }
            }
        }
    },

    DANMAKU_VENTILADOR_RG {
        @Override
        public void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas) {

            int capas = 3;
            int abanicosPorCapa = 3;
            int balasPorAbanico = 8;

            double velocidadBase = 2.8;
            double aperturaAbanico = 50.0;
            double barridoHorizontal = 70.0;
            int separacionY = 12;

            for (int capa = 0; capa < capas; capa++) {

                int origenY = e.obtenerCentroY() - (capa * separacionY);

                boolean invertido = (capa % 2 == 1);

                for (int abanico = 0; abanico < abanicosPorCapa; abanico++) {

                    double tAbanico;
                    if (abanicosPorCapa == 1) {
                        tAbanico = 0.5;
                    } else {
                        tAbanico = (double) abanico / (abanicosPorCapa - 1);
                    }

                    double centroAngular;
                    if (!invertido) {
                        centroAngular = -barridoHorizontal + (barridoHorizontal * 2.0 * tAbanico);
                    } else {
                        centroAngular = barridoHorizontal - (barridoHorizontal * 2.0 * tAbanico);
                    }

                    for (int i = 0; i < balasPorAbanico; i++) {

                        double tBala;
                        if (balasPorAbanico == 1) {
                            tBala = 0.5;
                        } else {
                            tBala = (double) i / (balasPorAbanico - 1);
                        }

                        double angulo = centroAngular - aperturaAbanico + (aperturaAbanico * 2.0 * tBala);
                        double rad = Math.toRadians(angulo);

                        double speedX = Math.sin(rad) * (velocidadBase + capa * 0.25);
                        double speedY = Math.cos(rad) * (velocidadBase + capa * 0.25);

                        Color colorActual;
                        if ((capa + abanico) % 2 == 0) {
                            colorActual = Color.RED;
                        } else {
                            colorActual = Color.GREEN;
                        }

                        BalaEnemiga bala = new BalaEnemiga(
                                e.obtenerCentroX(),
                                origenY,
                                speedX,
                                speedY,
                                TipoDeBalaEnemiga.ORB_MEDIANO,
                                colorActual,
                                e.obtenerZonaX(),
                                e.obtenerZonaXmax(),
                                e.obtenerZonaY(),
                                e.obtenerZonaYmax()
                        );

                        listaBalasEnemigas.add(bala);
                    }
                }
            }
        }
    };

    public abstract void disparar(Enemigo e, ArrayList<BalaEnemiga> listaBalasEnemigas);

    protected void crearBala(Enemigo e,
                             ArrayList<BalaEnemiga> listaBalasEnemigas,
                             double speedX,
                             double speedY,
                             TipoDeBalaEnemiga tipoBala,
                             Color color) {

        BalaEnemiga bala = new BalaEnemiga(
                e.obtenerCentroX(),
                e.obtenerY() + e.obtenerAlto(),
                speedX,
                speedY,
                tipoBala,
                color,
                e.obtenerZonaX(),
                e.obtenerZonaXmax(),
                e.obtenerZonaY(),
                e.obtenerZonaYmax()
        );

        listaBalasEnemigas.add(bala);
    }

    protected void crearBalaDesde(int origenX,
                                  int origenY,
                                  Enemigo e,
                                  ArrayList<BalaEnemiga> listaBalasEnemigas,
                                  double speedX,
                                  double speedY,
                                  TipoDeBalaEnemiga tipoBala,
                                  Color color) {

        BalaEnemiga bala = new BalaEnemiga(
                origenX,
                origenY,
                speedX,
                speedY,
                tipoBala,
                color,
                e.obtenerZonaX(),
                e.obtenerZonaXmax(),
                e.obtenerZonaY(),
                e.obtenerZonaYmax()
        );

        listaBalasEnemigas.add(bala);
    }

    protected void dispararAnillo(Enemigo e,
                                  ArrayList<BalaEnemiga> listaBalasEnemigas,
                                  int cantidad,
                                  double anguloInicialGrados,
                                  double velocidad,
                                  TipoDeBalaEnemiga tipoBala,
                                  Color color) {

        for (int i = 0; i < cantidad; i++) {
            double angulo = Math.toRadians(anguloInicialGrados + (360.0 * i / cantidad));
            double speedX = Math.cos(angulo) * velocidad;
            double speedY = Math.sin(angulo) * velocidad;

            BalaEnemiga bala = new BalaEnemiga(
                    e.obtenerCentroX(),
                    e.obtenerCentroY(),
                    speedX,
                    speedY,
                    tipoBala,
                    color,
                    e.obtenerZonaX(),
                    e.obtenerZonaXmax(),
                    e.obtenerZonaY(),
                    e.obtenerZonaYmax()
            );

            listaBalasEnemigas.add(bala);
        }
    }

    protected void dispararAbanico(Enemigo e,
                                   ArrayList<BalaEnemiga> listaBalasEnemigas,
                                   int cantidad,
                                   double anguloMinGrados,
                                   double anguloMaxGrados,
                                   double velocidad,
                                   TipoDeBalaEnemiga tipoBala,
                                   Color color) {

        if (cantidad <= 1) {
            double angulo = Math.toRadians((anguloMinGrados + anguloMaxGrados) / 2.0);
            crearBala(
                    e,
                    listaBalasEnemigas,
                    Math.sin(angulo) * velocidad,
                    Math.cos(angulo) * velocidad,
                    tipoBala,
                    color
            );
            return;
        }

        for (int i = 0; i < cantidad; i++) {
            double t = (double) i / (cantidad - 1);
            double angulo = Math.toRadians(anguloMinGrados + (anguloMaxGrados - anguloMinGrados) * t);

            double speedX = Math.sin(angulo) * velocidad;
            double speedY = Math.cos(angulo) * velocidad;

            crearBala(
                    e,
                    listaBalasEnemigas,
                    speedX,
                    speedY,
                    tipoBala,
                    color
            );
        }
    }

    protected void dispararSubAnilloDireccional(Enemigo e,
                                                ArrayList<BalaEnemiga> listaBalasEnemigas,
                                                int cantidad,
                                                double anguloCentroGrados,
                                                double velocidad,
                                                TipoDeBalaEnemiga tipoBala,
                                                Color color) {

        double apertura = 60.0;

        for (int i = 0; i < cantidad; i++) {
            double t = (double) i / (cantidad - 1);
            double angulo = Math.toRadians(anguloCentroGrados - apertura + (apertura * 2.0 * t));

            double speedX = Math.sin(angulo) * velocidad;
            double speedY = Math.cos(angulo) * velocidad;

            BalaEnemiga bala = new BalaEnemiga(
                    e.obtenerCentroX(),
                    e.obtenerCentroY(),
                    speedX,
                    speedY,
                    tipoBala,
                    color,
                    e.obtenerZonaX(),
                    e.obtenerZonaXmax(),
                    e.obtenerZonaY(),
                    e.obtenerZonaYmax()
            );

            listaBalasEnemigas.add(bala);
        }
    }
}