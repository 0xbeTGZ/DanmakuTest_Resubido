package com.kaleworks.danmakutest.entity;

import java.awt.Graphics;
import java.awt.Color;

public enum TipoDeBalaEnemiga {

    ORB_PEQUENO(8, 8) {
        @Override
        public void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color) {
            g.setColor(color);
            g.fillOval(x, y, ancho, alto);
        }
    },

    ORB_GRANDE(16, 16) {
        @Override
        public void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color) {
            g.setColor(color);
            g.fillOval(x, y, ancho, alto);
        }
    },

    ORB_MEDIANO(12, 12) {
        @Override
        public void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color) {
            g.setColor(color);
            g.fillOval(x, y, ancho, alto);
        }
    },

    AGUJA(6, 14) {
        @Override
        public void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color) {
            g.setColor(color);
            g.fillRect(x, y, ancho, alto);
        }
    },

    PLANTILLA(10, 10) {
        @Override
        public void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color) {
            g.setColor(color);
            g.fillOval(x, y, ancho, alto);
        }
    };

    private final int anchoBase;
    private final int altoBase;

    TipoDeBalaEnemiga(int anchoBase, int altoBase) {
        this.anchoBase = anchoBase;
        this.altoBase = altoBase;
    }

    public int obtenerAnchoBase() {
        return anchoBase;
    }

    public int obtenerAltoBase() {
        return altoBase;
    }

    public abstract void dibujar(Graphics g, int x, int y, int ancho, int alto, Color color);
}