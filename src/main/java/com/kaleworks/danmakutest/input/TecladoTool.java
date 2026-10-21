package com.kaleworks.danmakutest.input;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class TecladoTool implements KeyListener {
    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;

    public boolean disparo;
    public boolean bomba;
    public boolean escape;
    public boolean focus;

    public void keyPressed(KeyEvent teclita){
        int tecla = teclita.getKeyCode();

        if (tecla == KeyEvent.VK_UP){
            arriba = true;
        }

        if (tecla == KeyEvent.VK_DOWN) {
            abajo = true;
        }

        if (tecla == KeyEvent.VK_LEFT) {
            izquierda = true;
        }

        if (tecla == KeyEvent.VK_RIGHT) {
            derecha = true;
        }

        if (tecla == KeyEvent.VK_Z) {
            disparo = true;
        }

        if (tecla == KeyEvent.VK_X) {
            bomba = true;
        }

        if (tecla == KeyEvent.VK_ESCAPE) {
            escape = true;
        }

        if (tecla == KeyEvent.VK_SHIFT) {
            focus = true;
        }
    }
    public void keyReleased(KeyEvent teclita){
        int tecla = teclita.getKeyCode();

        if (tecla == KeyEvent.VK_UP){
            arriba = false;
        }

        if (tecla == KeyEvent.VK_DOWN) {
            abajo = false;
        }

        if (tecla == KeyEvent.VK_LEFT) {
            izquierda = false;
        }

        if (tecla == KeyEvent.VK_RIGHT) {
            derecha = false;
        }

        if (tecla == KeyEvent.VK_Z) {
            disparo = false;

        }

        if (tecla == KeyEvent.VK_X) {
            bomba = false;
        }

        if (tecla == KeyEvent.VK_ESCAPE) {
            escape = false;
        }

        if (tecla == KeyEvent.VK_SHIFT) {
            focus = false;
        }
    }
    public void keyTyped(KeyEvent e) {
    }
}
