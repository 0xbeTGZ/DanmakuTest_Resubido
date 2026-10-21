package com.kaleworks.danmakutest.core;

import com.kaleworks.danmakutest.entity.Dificultad;
import com.kaleworks.danmakutest.entity.SeleccionJugador;
import com.kaleworks.danmakutest.util.CargadorSonidos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PantallaMenu extends JPanel {

    private enum Paso { TITULO, DIFICULTAD, PERSONAJE, DISPARO }

    private Paso pasoActual = Paso.TITULO;
    private int indiceSeleccion = 1;

    private Dificultad dificultadElegida = Dificultad.NORMAL;
    private boolean esReimu = true;
    private boolean esVarianteA = true;

    private double cursorY = 0;
    private double cursorYObjetivo = 0;
    private double alphaPaso = 0.0;
    private int frame = 0;

    private final Timer timer;

    public PantallaMenu() {
        CargadorSonidos.reproducirBGM(CargadorSonidos.BGM.MENU);
        setPreferredSize(new Dimension(GameCore.Ancho, GameCore.Alto));
        setBackground(new Color(8, 8, 24));
        setFocusable(true);

        recalcularCursorObjetivo();
        cursorY = cursorYObjetivo;

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) { manejarTecla(e.getKeyCode()); }
        });

        timer = new Timer(1000 / 60, e -> {
            tick();
            repaint();
        });
        timer.start();
    }

    private void manejarTecla(int code) {
        if (code == KeyEvent.VK_UP) {
            indiceSeleccion = Math.max(0, indiceSeleccion - 1);
            recalcularCursorObjetivo();
        } else if (code == KeyEvent.VK_DOWN) {
            int max = opcionesDelPaso().length - 1;
            indiceSeleccion = Math.min(max, indiceSeleccion + 1);
            recalcularCursorObjetivo();
        } else if (code == KeyEvent.VK_Z) {
            avanzar();
        } else if (code == KeyEvent.VK_X) {
            retroceder();
        } else if (code == KeyEvent.VK_V) {

            abrirPruebaSprites();
        }
    }

    private void avanzar() {
        switch (pasoActual) {
            case TITULO:
                cambiarPaso(Paso.DIFICULTAD, 1);
                break;
            case DIFICULTAD:
                dificultadElegida = Dificultad.values()[indiceSeleccion];
                Dificultad.setActual(dificultadElegida);
                cambiarPaso(Paso.PERSONAJE, 0);
                break;
            case PERSONAJE:
                esReimu = (indiceSeleccion == 0);
                cambiarPaso(Paso.DISPARO, 0);
                break;
            case DISPARO:
                esVarianteA = (indiceSeleccion == 0);
                lanzarJuego();
                break;
        }
    }

    private void retroceder() {
        switch (pasoActual) {
            case TITULO:                        break;
            case DIFICULTAD:  cambiarPaso(Paso.TITULO, 0);            break;
            case PERSONAJE:   cambiarPaso(Paso.DIFICULTAD,
                                  dificultadElegida.ordinal());      break;
            case DISPARO:     cambiarPaso(Paso.PERSONAJE,
                                  esReimu ? 0 : 1);                  break;
        }
    }

    private void cambiarPaso(Paso nuevo, int indiceInicial) {
        pasoActual = nuevo;
        indiceSeleccion = indiceInicial;
        alphaPaso = 0.0;
        recalcularCursorObjetivo();
        cursorY = cursorYObjetivo;
    }

    private void abrirPruebaSprites() {
        timer.stop();
        PruebaImagenEscena prueba = new PruebaImagenEscena();
        GameCore ventana = (GameCore) javax.swing.SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(prueba);
        ventana.revalidate();
        ventana.repaint();
        prueba.requestFocusInWindow();
    }

    private void tick() {
        frame++;

        cursorY += (cursorYObjetivo - cursorY) * 0.25;

        if (alphaPaso < 1.0) alphaPaso = Math.min(1.0, alphaPaso + 0.06);
    }

    private void recalcularCursorObjetivo() {
        cursorYObjetivo = yBaseOpciones() + indiceSeleccion * altoLinea();
    }

    private int yBaseOpciones() {

        return (pasoActual == Paso.TITULO) ? 280 : 210;
    }

    private int altoLinea() {

        return (pasoActual == Paso.DIFICULTAD) ? 38 : 46;
    }

    private String[] opcionesDelPaso() {
        switch (pasoActual) {
            case TITULO:     return new String[] { "Jugar" };
            case DIFICULTAD: return new String[] { "Facil", "Normal", "Dificil", "Lunatic" };
            case PERSONAJE:  return new String[] { "cosiaco", "cosianpirulo" };
            case DISPARO:    return new String[] { "Tipo A", "Tipo B" };
            default:         return new String[] {};
        }
    }

    private String tituloDelPaso() {
        switch (pasoActual) {
            case TITULO:     return "DANMAKU TEST";
            case DIFICULTAD: return "Dificultad";
            case PERSONAJE:  return "Personaje";
            case DISPARO:    return "Tipo de Disparo";
            default:         return "";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        dibujarFondo(g2);

        String titulo = tituloDelPaso();
        g2.setFont(new Font("Arial", Font.BOLD, pasoActual == Paso.TITULO ? 44 : 28));

        if (pasoActual == Paso.TITULO) {
            float pulso = (float) (0.6 + 0.4 * Math.abs(Math.sin(frame * 0.04)));
            g2.setColor(new Color(255, 220, 100, (int)(pulso * 255)));
        } else {
            g2.setColor(new Color(255, 230, 200, (int)(alphaPaso * 255)));
        }
        int anchoTitulo = g2.getFontMetrics().stringWidth(titulo);

        g2.drawString(titulo, (GameCore.Ancho - anchoTitulo) / 2, 130);

        if (pasoActual == Paso.TITULO) {

            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.setColor(new Color(180, 180, 220));
            String hint = "Z = Avanzar    X = Retroceder    Flechas = Mover";
            int anchoHint = g2.getFontMetrics().stringWidth(hint);
            g2.drawString(hint, (GameCore.Ancho - anchoHint) / 2, 175);
        }

        String[] ops = opcionesDelPaso();

        g2.setFont(new Font("Arial", Font.BOLD, pasoActual == Paso.TITULO ? 26 : 20));
        for (int i = 0; i < ops.length; i++) {
            boolean sel = (i == indiceSeleccion);
            int y = yBaseOpciones() + i * altoLinea();
            int alpha = (int) (alphaPaso * 255);
            if (sel) {

                float p = (float) (0.7 + 0.3 * Math.abs(Math.sin(frame * 0.12)));
                g2.setColor(new Color(255, (int)(220*p), 80, alpha));
            } else {
                g2.setColor(new Color(200, 200, 230, alpha));
            }
            int anchoOp = g2.getFontMetrics().stringWidth(ops[i]);
            g2.drawString(ops[i], (GameCore.Ancho - anchoOp) / 2, y);
        }

        if (pasoActual != Paso.TITULO || ops.length > 1) {
            g2.setColor(new Color(255, 255, 120, (int)(alphaPaso * 255)));
            int cy = (int) cursorY;

            int cx = GameCore.Ancho / 2 - 100;
            int[] xs = { cx, cx + 12, cx };
            int[] ys = { cy - 14, cy - 7, cy };
            g2.fillPolygon(xs, ys, 3);
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(new Color(140, 140, 180));
        String pie;
        switch (pasoActual) {
            case TITULO:     pie = "myowndanmakutest_v0.1  |  [V] Prueba sprites"; break;
            case DIFICULTAD: pie = "Z confirma | X regresa al titulo"; break;
            case PERSONAJE:  pie = "Dificultad: " + dificultadElegida.name()
                                  + "    | Z confirma | X regresa"; break;
            case DISPARO:    pie = "Dificultad: " + dificultadElegida.name()
                                  + " | " + (esReimu ? "Reimu" : "Marisa")
                                  + " | Z empieza | X regresa"; break;
            default: pie = "";
        }
        int anchoPie = g2.getFontMetrics().stringWidth(pie);
        g2.drawString(pie, (GameCore.Ancho - anchoPie) / 2, GameCore.Alto - 20);
    }

    private void dibujarFondo(Graphics2D g2) {

        g2.setColor(new Color(20, 20, 50));
        g2.fillRect(0, 0, GameCore.Ancho, 90);

        for (int i = 0; i < 60; i++) {
            int sx = (i * 137) % GameCore.Ancho;
            int sy = (int) ((i * 59 + frame * (1 + (i % 3))) % GameCore.Alto);
            int b = 80 + (i * 17) % 120;
            g2.setColor(new Color(b, b, Math.min(255, b + 40)));
            g2.fillRect(sx, sy, 2, 2);
        }
    }

    private void lanzarJuego() {
        timer.stop();
        SeleccionJugador sel;
        if (esReimu && esVarianteA)       sel = SeleccionJugador.REIMU_A;
        else if (esReimu)                 sel = SeleccionJugador.REIMU_B;
        else if (esVarianteA)             sel = SeleccionJugador.MARISA_A;
        else                              sel = SeleccionJugador.MARISA_B;

        GamePanel panelJuego = new GamePanel(sel);
        GameCore ventana = (GameCore) javax.swing.SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(panelJuego);
        ventana.revalidate();
        ventana.repaint();
        panelJuego.requestFocusInWindow();
        panelJuego.iniciarelhiloese();
    }
}
