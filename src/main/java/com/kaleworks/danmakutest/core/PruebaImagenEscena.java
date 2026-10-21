package com.kaleworks.danmakutest.core;

import com.kaleworks.danmakutest.graficos.ImagenEscena;
import com.kaleworks.danmakutest.util.HojaSpritesRegistrada;

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

public class PruebaImagenEscena extends JPanel {

    private static final HojaSpritesRegistrada[] SPRITES = HojaSpritesRegistrada.values();

    private int indiceActual = 0;
    private ImagenEscena imagenActual;
    private boolean opacidadAlternada = false;

    private final Timer timer;

    public PruebaImagenEscena() {
        setPreferredSize(new Dimension(GameCore.Ancho, GameCore.Alto));
        setBackground(new Color(15, 15, 35));
        setFocusable(true);

        cargarSpriteActual();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    ciclarSprite();
                } else if (e.getKeyCode() == KeyEvent.VK_X) {
                    volverAlMenu();
                }
            }
        });

        timer = new Timer(1000 / 60, e -> repaint());
        timer.start();
    }

    private void cargarSpriteActual() {
        HojaSpritesRegistrada sprite = SPRITES[indiceActual];
        imagenActual = new ImagenEscena(sprite);

        int anchoImg = imagenActual.getAnchoPantalla();
        int altoImg  = imagenActual.getAltoPantalla();
        int cx = (GameCore.Ancho - anchoImg) / 2;
        int cy = (GameCore.Alto  - altoImg)  / 2 - 30;

        imagenActual
                .setPosicion(cx, cy)
                .setOpacidad(opacidadAlternada ? 0.4f : 1.0f)
                .setVisible(true);
    }

    private void ciclarSprite() {
        indiceActual = (indiceActual + 1) % SPRITES.length;
        opacidadAlternada = !opacidadAlternada;
        cargarSpriteActual();
    }

    private void volverAlMenu() {
        timer.stop();
        PantallaMenu menu = new PantallaMenu();
        GameCore ventana = (GameCore) javax.swing.SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(menu);
        ventana.revalidate();
        ventana.repaint();
        menu.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        HojaSpritesRegistrada spriteActual = SPRITES[indiceActual];

        if (imagenActual.getAnchoPantalla() == 0) {

            int px = GameCore.Ancho / 2 - 64;
            int py = GameCore.Alto  / 2 - 64 - 30;
            g2.setColor(new Color(200, 0, 200, 120));
            g2.fillRect(px, py, 128, 128);
            g2.setColor(Color.MAGENTA);
            g2.drawRect(px, py, 128, 128);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.setColor(Color.WHITE);
            g2.drawString("IMAGEN NO ENCONTRADA", px + 4, py + 70);
        } else {
            imagenActual.dibujar(g2);
        }

        int panelY = GameCore.Alto - 130;
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(20, panelY, GameCore.Ancho - 40, 110);
        g2.setColor(new Color(80, 80, 120));
        g2.drawRect(20, panelY, GameCore.Ancho - 40, 110);

        g2.setFont(new Font("Monospaced", Font.BOLD, 14));
        g2.setColor(new Color(255, 220, 80));
        g2.drawString("[" + indiceActual + "/" + (SPRITES.length - 1) + "]  "
                + spriteActual.name(), 34, panelY + 22);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.setColor(new Color(180, 200, 255));
        g2.drawString("Ruta  : " + spriteActual.getRuta(),           34, panelY + 42);
        g2.drawString("Recorte: x=" + spriteActual.getX()
                + "  y=" + spriteActual.getY()
                + "  w=" + spriteActual.getAncho()
                + "  h=" + spriteActual.getAlto(),                    34, panelY + 58);

        String estadoImg = (imagenActual.getAnchoPantalla() > 0)
                ? "OK  (" + imagenActual.getAnchoPantalla() + " x " + imagenActual.getAltoPantalla() + " px)"
                : "NO ENCONTRADA";
        g2.setColor(imagenActual.getAnchoPantalla() > 0
                ? new Color(100, 255, 130)
                : new Color(255, 80, 80));
        g2.drawString("Imagen : " + estadoImg,                        34, panelY + 74);

        g2.setColor(new Color(180, 180, 180));
        g2.drawString("Opacidad: " + String.format("%.1f", imagenActual.getOpacidad()),
                34, panelY + 90);

        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(new Color(140, 140, 180));
        String hint = "[ V ] Siguiente sprite     [ X ] Volver al menu";
        int anchoHint = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, (GameCore.Ancho - anchoHint) / 2, GameCore.Alto - 8);
    }
}
