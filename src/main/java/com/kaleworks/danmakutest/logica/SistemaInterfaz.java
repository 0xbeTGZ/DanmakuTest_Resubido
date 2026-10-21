package com.kaleworks.danmakutest.logica;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class SistemaInterfaz {

    private int score;
    private int highScore;
    private int vidas;
    private int bombas;
    private int powerActual;
    private int powerMaximo;

    private int graze;

    private final int panelX;
    private final int panelY;
    private final int panelAncho;
    private final int panelAlto;

    public SistemaInterfaz(int panelX, int panelY, int panelAncho, int panelAlto) {
        this.panelX = panelX;
        this.panelY = panelY;
        this.panelAncho = panelAncho;
        this.panelAlto = panelAlto;

        this.score = 0;
        this.highScore = 0;
        this.vidas = 3;
        this.bombas = 3;
        this.powerActual = 0;
        this.powerMaximo = 4;
    }

    public void actualizarDatos(int score, int vidas, int bombas, int powerActual, int powerMaximo) {
        actualizarDatos(score, vidas, bombas, powerActual, powerMaximo, this.graze);
    }

    public void actualizarDatos(int score, int vidas, int bombas,
                                int powerActual, int powerMaximo, int graze) {
        this.score = score;
        this.vidas = vidas;
        this.bombas = bombas;
        this.powerActual = powerActual;
        this.powerMaximo = powerMaximo;
        this.graze = graze;

        if (score > highScore) {
            highScore = score;
        }
    }

    public void dibujar(Graphics g) {
        g.setColor(new Color(30, 30, 30));
        g.fillRect(panelX, panelY, panelAncho, panelAlto);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("DANMAKU TEST", panelX + 20, panelY + 35);

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString("Score", panelX + 20, panelY + 80);
        g.drawString(String.valueOf(score), panelX + 20, panelY + 100);

        g.drawString("Hi-Score", panelX + 20, panelY + 140);
        g.drawString(String.valueOf(highScore), panelX + 20, panelY + 160);

        g.drawString("Vidas", panelX + 20, panelY + 210);
        g.drawString(String.valueOf(vidas), panelX + 20, panelY + 230);

        g.drawString("Bombas", panelX + 20, panelY + 270);
        g.drawString(String.valueOf(bombas), panelX + 20, panelY + 290);

        g.drawString("Power", panelX + 20, panelY + 330);
        g.drawString(powerActual + " / " + powerMaximo, panelX + 20, panelY + 350);

        g.drawString("Graze", panelX + 20, panelY + 395);
        g.drawString(String.valueOf(graze), panelX + 20, panelY + 415);

        dibujarBarraPower(g, panelX + 20, panelY + 370, 140, 14);
    }

    private void dibujarBarraPower(Graphics g, int x, int y, int ancho, int alto) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, ancho, alto);

        int anchoLleno = 0;
        if (powerMaximo > 0) {
            anchoLleno = (int) ((double) powerActual / powerMaximo * ancho);
        }

        g.setColor(Color.CYAN);
        g.fillRect(x, y, anchoLleno, alto);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, ancho, alto);
    }

    public int obtenerHighScore() {
        return highScore;
    }

    public void definirHighScore(int highScore) {
        this.highScore = highScore;
    }
}