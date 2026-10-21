package com.kaleworks.danmakutest.core;

import com.kaleworks.danmakutest.input.TecladoTool;
import com.kaleworks.danmakutest.escenario.Stage;
import com.kaleworks.danmakutest.escenario.StagePlantilla;
import com.kaleworks.danmakutest.escenario.StagePrueba;
import com.kaleworks.danmakutest.entity.SeleccionJugador;
import com.kaleworks.danmakutest.util.CargadorSonidos;
import com.kaleworks.danmakutest.util.LogJuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    private final int FPS = 60;

    private Thread Hilodeljuego;
    private TecladoTool inputsTeclado;

    private final int zonaX = 32;
    private final int zonaY = 16;
    private final int zonaAncho = 384;
    private final int zonaAlto = 448;

    private Stage stageActual;
    private SeleccionJugador seleccion;

    private EstadoJuego estado = EstadoJuego.JUGANDO;

    private boolean escapeYaProcesado = false;

    private boolean bombaYaProcesada = false;

    private boolean reintentarYaProcesado = false;

    private boolean volverTituloYaProcesado = false;

    private int framesEnOverlay = 0;

    public GamePanel(SeleccionJugador seleccion) {
        this.seleccion = seleccion;

        setPreferredSize(new Dimension(GameCore.Ancho, GameCore.Alto));
        setBackground(Color.BLACK);
        setFocusable(true);

        inputsTeclado = new TecladoTool();
        addKeyListener(inputsTeclado);

        stageActual = new StagePrueba(
                zonaX,
                zonaY,
                zonaAncho,
                zonaAlto,
                this.seleccion
        );
    }

    public void iniciarelhiloese() {
        CargadorSonidos.reproducirBGM(CargadorSonidos.BGM.STAGE1);
        Hilodeljuego = new Thread(this);
        Hilodeljuego.start();
    }

    public void actualizarJuego() {

        procesarToggleDePausa();
        procesarVolverAlTitulo();

        if (estado == EstadoJuego.PAUSA || estado == EstadoJuego.GAME_OVER) {
            framesEnOverlay++;
        }

        boolean bombaPulso = calcularPulsoBomba();

        switch (estado) {
            case JUGANDO:
                stageActual.actualizar(
                        inputsTeclado.arriba,
                        inputsTeclado.abajo,
                        inputsTeclado.izquierda,
                        inputsTeclado.derecha,
                        inputsTeclado.focus,
                        inputsTeclado.disparo,
                        bombaPulso
                );

                detectarMuerteYTransicionarAGameOver();
                break;

            case GAME_OVER:

                procesarInputGameOver();
                break;

            case PAUSA:
            case STAGE_CLEAR:
            case TITULO:

                break;
        }
    }

    private void detectarMuerteYTransicionarAGameOver() {
        if (stageActual.jugadorEstaMuerto()) {
            cambiarEstado(EstadoJuego.GAME_OVER);
        }
    }

    private void procesarInputGameOver() {

        if (inputsTeclado.disparo && !reintentarYaProcesado) {
            reintentarYaProcesado = true;
            reiniciarStage();
            cambiarEstado(EstadoJuego.JUGANDO);
        }
        if (!inputsTeclado.disparo) {
            reintentarYaProcesado = false;
        }
    }

    private void reiniciarStage() {
        LogJuego.aviso("Reiniciando stage por peticion del jugador");
        stageActual = new StagePrueba(
                zonaX,
                zonaY,
                zonaAncho,
                zonaAlto,
                this.seleccion
        );
    }

    private boolean calcularPulsoBomba() {
        boolean pulso = false;
        if (inputsTeclado.bomba && !bombaYaProcesada) {
            pulso = true;
            bombaYaProcesada = true;
        }
        if (!inputsTeclado.bomba) {
            bombaYaProcesada = false;
        }
        return pulso;
    }

    private void procesarToggleDePausa() {

        boolean enEstadoPausable = (estado == EstadoJuego.JUGANDO || estado == EstadoJuego.PAUSA);

        if (inputsTeclado.escape && !escapeYaProcesado && enEstadoPausable) {

            if (estado == EstadoJuego.JUGANDO) {
                CargadorSonidos.pausarBGM();
                cambiarEstado(EstadoJuego.PAUSA);
            } else {
                CargadorSonidos.reanudarBGM();
                cambiarEstado(EstadoJuego.JUGANDO);
            }
            escapeYaProcesado = true;
        }

        if (!inputsTeclado.escape) {
            escapeYaProcesado = false;
        }
    }

    public EstadoJuego obtenerEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoJuego nuevoEstado) {
        LogJuego.aviso("Cambio de estado: " + this.estado + " -> " + nuevoEstado);
        this.estado = nuevoEstado;

        this.framesEnOverlay = 0;
    }

    private void volverAlTitulo() {
        LogJuego.aviso("Volviendo al titulo desde " + estado);
        CargadorSonidos.detenerBGM();
        Hilodeljuego = null;
        PantallaMenu menu = new PantallaMenu();
        GameCore ventana = (GameCore) javax.swing.SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(menu);
        ventana.revalidate();
        ventana.repaint();
        menu.requestFocusInWindow();
    }

    private void procesarVolverAlTitulo() {
        boolean enEstadoSalible = (estado == EstadoJuego.PAUSA || estado == EstadoJuego.GAME_OVER);
        if (inputsTeclado.bomba && !volverTituloYaProcesado && enEstadoSalible) {
            volverTituloYaProcesado = true;
            volverAlTitulo();
        }
        if (!inputsTeclado.bomba) {
            volverTituloYaProcesado = false;
        }
    }

    @Override
    public void run() {
        double intervaloDeDibujo = 1000000000.0 / FPS;
        double siguienteDibujo = System.nanoTime() + intervaloDeDibujo;

        while (Hilodeljuego != null) {
            actualizarJuego();
            repaint();

            try {
                double tiempoRestante = siguienteDibujo - System.nanoTime();
                tiempoRestante = tiempoRestante / 1000000.0;

                if (tiempoRestante < 0) {
                    tiempoRestante = 0;
                }

                Thread.sleep((long) tiempoRestante);
                siguienteDibujo += intervaloDeDibujo;

            } catch (InterruptedException e) {
                LogJuego.error("Hilo del juego interrumpido durante Thread.sleep()", e);

                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        stageActual.dibujar(g);

        if (estado == EstadoJuego.PAUSA) {
            dibujarOverlayPausa(g);
        }

        if (estado == EstadoJuego.GAME_OVER) {
            dibujarOverlayGameOver(g);
        }
    }

    private void dibujarOverlayPausa(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        float fade = Math.min(1.0f, framesEnOverlay / 15.0f);

        g2.setColor(new Color(0, 0, 0, (int)(180 * fade)));
        g2.fillRect(0, 0, GameCore.Ancho, GameCore.Alto);

        float pulso = (float)(0.85 + 0.15 * Math.sin(framesEnOverlay * 0.08));
        g2.setColor(new Color(255, 255, 255, (int)(255 * fade * pulso)));

        g2.setFont(new Font("Monospaced", Font.BOLD, 36));
        String titulo = "PAUSA";
        int anchoTitulo = g2.getFontMetrics().stringWidth(titulo);
        int xTitulo = (GameCore.Ancho - anchoTitulo) / 2;
        int yTitulo = GameCore.Alto / 2 - 30;
        g2.drawString(titulo, xTitulo, yTitulo);

        g2.setColor(new Color(220, 220, 220, (int)(255 * fade)));

        g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
        String h1 = "ESC: continuar    X: volver al titulo";
        int aH1 = g2.getFontMetrics().stringWidth(h1);
        g2.drawString(h1, (GameCore.Ancho - aH1) / 2, yTitulo + 40);
    }

    private void dibujarOverlayGameOver(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        float fade = Math.min(1.0f, framesEnOverlay / 30.0f);

        g2.setColor(new Color(80, 0, 0, (int)(200 * fade)));
        g2.fillRect(0, 0, GameCore.Ancho, GameCore.Alto);

        float pulso = (float)(0.85 + 0.15 * Math.sin(framesEnOverlay * 0.05));
        g2.setColor(new Color(255, 255, 255, (int)(255 * fade * pulso)));

        g2.setFont(new Font("Monospaced", Font.BOLD, 42));
        String titulo = "GAME OVER";
        int aT = g2.getFontMetrics().stringWidth(titulo);
        int yT = GameCore.Alto / 2 - 40;
        g2.drawString(titulo, (GameCore.Ancho - aT) / 2, yT);

        g2.setColor(new Color(255, 230, 200, (int)(255 * fade)));

        g2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        String h1 = "Z: reintentar    X: volver al titulo";
        int aH1 = g2.getFontMetrics().stringWidth(h1);
        g2.drawString(h1, (GameCore.Ancho - aH1) / 2, yT + 50);
    }
}