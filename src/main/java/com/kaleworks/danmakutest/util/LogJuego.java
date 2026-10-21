package com.kaleworks.danmakutest.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogJuego {

    private static final Logger LOG = Logger.getLogger("DanmakuTest");

    private LogJuego() {

    }

    public static void error(String mensaje, Throwable causa) {

        LOG.log(Level.SEVERE, mensaje, causa);
    }

    public static void error(String mensaje) {
        LOG.log(Level.SEVERE, mensaje);
    }

    public static void aviso(String mensaje) {
        LOG.log(Level.INFO, mensaje);
    }

    public static void depurar(String mensaje) {
        LOG.log(Level.FINE, mensaje);
    }
}
