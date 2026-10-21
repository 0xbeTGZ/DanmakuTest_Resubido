package com.kaleworks.danmakutest.escenario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.kaleworks.danmakutest.core.GameCore;
import com.kaleworks.danmakutest.dialogo.CatalogoDialogos;
import com.kaleworks.danmakutest.entity.Jugador;
import com.kaleworks.danmakutest.entity.JugadorReimu;
import com.kaleworks.danmakutest.entity.JugadorMarisa;
import com.kaleworks.danmakutest.entity.BalaJugador;
import com.kaleworks.danmakutest.entity.BalaEnemiga;
import com.kaleworks.danmakutest.entity.Enemigo;
import com.kaleworks.danmakutest.entity.Item;
import com.kaleworks.danmakutest.entity.TipoItem;
import com.kaleworks.danmakutest.entity.SeleccionJugador;
import com.kaleworks.danmakutest.graficos.PanelDialogo;

import com.kaleworks.danmakutest.logica.SistemaColisiones;
import com.kaleworks.danmakutest.logica.SistemaLimpieza;
import com.kaleworks.danmakutest.logica.SistemaActualizacion;
import com.kaleworks.danmakutest.logica.SistemaItem;
import com.kaleworks.danmakutest.logica.SistemaInterfaz;
import com.kaleworks.danmakutest.logica.SistemaBomba;
import com.kaleworks.danmakutest.logica.GestorHiScore;

public class StagePlantilla implements Stage {

    protected final int zonaX;
    protected final int zonaY;
    protected final int zonaAncho;
    protected final int zonaAlto;

    protected Jugador player;

    protected ArrayList<BalaJugador>  listaBalasJugador;
    protected ArrayList<Enemigo>      listaEnemigos;
    protected ArrayList<BalaEnemiga>  listaBalasEnemigas;
    protected ArrayList<Item>         listaItems;

    protected int frameActual;
    protected int score;

    protected SistemaColisiones    sistemaColisiones;
    protected SistemaLimpieza      sistemaLimpieza;
    protected SistemaActualizacion sistemaActualizacion;
    protected SistemaItem          sistemaItems;
    protected SistemaInterfaz      sistemaInterfaz;
    protected SistemaBomba         sistemaBomba;
    protected GestorHiScore        gestorHiScore;
    protected StageManager         stageManager;

    protected boolean hiScoreYaGuardado;
    protected SeleccionJugador seleccion;

    // --- sistema de dialogo ---
    protected final PanelDialogo panelDialogo;
    private boolean zYaProcesadoDialogo = false; // anti-repeticion de Z para el dialogo

    public StagePlantilla(int zonaX, int zonaY, int zonaAncho, int zonaAlto, SeleccionJugador seleccion) {
        this.zonaX   = zonaX;
        this.zonaY   = zonaY;
        this.zonaAncho = zonaAncho;
        this.zonaAlto  = zonaAlto;
        this.seleccion = seleccion;

        this.player = crearJugadorSeleccionado();

        this.listaBalasJugador   = new ArrayList<>();
        this.listaBalasEnemigas  = new ArrayList<>();
        this.listaEnemigos       = new ArrayList<>();
        this.listaItems          = new ArrayList<>();

        this.frameActual = 0;
        this.score       = 0;

        this.sistemaColisiones    = new SistemaColisiones();
        this.sistemaLimpieza      = new SistemaLimpieza();
        this.sistemaActualizacion = new SistemaActualizacion();
        this.sistemaItems         = new SistemaItem();
        this.sistemaBomba         = new SistemaBomba();
        this.gestorHiScore        = new GestorHiScore();
        this.hiScoreYaGuardado    = false;
        this.stageManager         = new StageManager();
        this.panelDialogo         = new PanelDialogo();

        programarEventos();

        int panelHudX    = zonaX + zonaAncho + 16;
        int panelHudY    = zonaY;
        int panelHudAncho = GameCore.Ancho - panelHudX - 16;
        int panelHudAlto  = zonaAlto;

        this.sistemaInterfaz = new SistemaInterfaz(
                panelHudX, panelHudY, panelHudAncho, panelHudAlto);

        int hiScoreGuardado = gestorHiScore.cargar();
        this.sistemaInterfaz.definirHighScore(hiScoreGuardado);
    }

    protected Jugador crearJugadorSeleccionado() {
        int inicioX = zonaX + zonaAncho / 2;
        int inicioY = zonaY + zonaAlto - 60;

        switch (seleccion) {
            case REIMU_A:
            case REIMU_B:
                return new JugadorReimu(
                        inicioX, inicioY,
                        zonaX, zonaY, zonaAncho, zonaAlto,
                        seleccion);
            case MARISA_A:
            case MARISA_B:
                return new JugadorMarisa(
                        inicioX, inicioY,
                        zonaX, zonaY, zonaAncho, zonaAlto,
                        seleccion);
            default:
                return new JugadorReimu(
                        inicioX, inicioY,
                        zonaX, zonaY, zonaAncho, zonaAlto,
                        SeleccionJugador.REIMU_A);
        }
    }

    // -----------------------------------------
    // API de dialogo para subclases
    // -----------------------------------------

    /**
     * Abre un dialogo. Llamar desde programarEventos() via stageManager.programar()
     * o desde cualquier evento del stage.
     * Mientras el dialogo este activo, el juego se pausa automaticamente.
     */
    protected void iniciarDialogo(CatalogoDialogos dialogo) {
        panelDialogo.iniciar(dialogo);
    }

    /** true mientras hay un dialogo visible (juego pausado). */
    protected boolean dialogoActivo() {
        return panelDialogo.isActivo();
    }

    @Override
    public void actualizar(boolean arriba, boolean abajo, boolean izquierda, boolean derecha,
                           boolean focus, boolean disparando, boolean bombaPulso) {

        frameActual++;

        // --- si hay dialogo activo, solo actualizar el dialogo ---
        if (panelDialogo.isActivo()) {
            // pulso de Z: true solo el primer frame que esta pulsado
            boolean pulsadoZ = disparando && !zYaProcesadoDialogo;
            zYaProcesadoDialogo = disparando;
            panelDialogo.actualizar(pulsadoZ);
            return; // juego pausado durante el dialogo
        }
        zYaProcesadoDialogo = false;

        // --- logica normal del stage ---
        player.actualizar(arriba, abajo, izquierda, derecha, focus);

        player.intentoDisparo(
                disparando, focus,
                listaBalasJugador,
                zonaY, zonaY + zonaAlto);

        if (bombaPulso) {
            sistemaBomba.detonar(player, listaBalasEnemigas);
        }

        stageManager.tickear(this, frameActual);
        generarOleadas();

        for (Enemigo ene : listaEnemigos) {
            ene.disparo(listaBalasEnemigas);
        }

        sistemaActualizacion.actualizarTodo(
                listaBalasJugador, listaBalasEnemigas,
                listaEnemigos, listaItems);

        sistemaColisiones.procesarTodo(
                player, listaBalasJugador,
                listaBalasEnemigas, listaEnemigos);

        procesarMuertesYDrops();

        score += sistemaItems.procesarItems(player, listaItems);

        sistemaLimpieza.limpiarTodo(
                listaBalasJugador, listaBalasEnemigas,
                listaEnemigos, listaItems);

        sistemaInterfaz.actualizarDatos(
                score,
                player.obtenerVidas(),
                player.obtenerBombas(),
                player.obtenerPowerActual(),
                player.obtenerPowerMaximo(),
                player.obtenerGraze());
    }

    protected void generarOleadas() {}

    protected void programarEventos() {}

    @Override
    public boolean jugadorEstaMuerto() {
        boolean muerto = !player.estaVivo();
        if (muerto && !hiScoreYaGuardado) {
            gestorHiScore.guardar(sistemaInterfaz.obtenerHighScore());
            hiScoreYaGuardado = true;
        }
        return muerto;
    }

    protected void procesarMuertesYDrops() {
        for (Enemigo ene : listaEnemigos) {
            if (ene.acabaDeMorirYNoFueProcesado()) {
                score += ene.obtenerPuntajeAlMorir();

                if (ene.puedeDropearItem()) {
                    Item nuevoItem = new Item(
                            ene.obtenerCentroX(),
                            ene.obtenerCentroY(),
                            ene.obtenerTipoDrop(),
                            zonaX, zonaY,
                            zonaX + zonaAncho, zonaY + zonaAlto);
                    listaItems.add(nuevoItem);
                }

                cancelarBalasCercanasAEnemigo(ene);
                ene.marcarMuerteComoProcesada();
            }
        }

        if (player != null && !player.estaVivo() && !dropsDeMuerteYaSpawneados) {
            spawnearDropsDeMuerteJugador();
            dropsDeMuerteYaSpawneados = true;
        }
    }

    private boolean dropsDeMuerteYaSpawneados = false;

    private void cancelarBalasCercanasAEnemigo(Enemigo ene) {
        final double RADIO_SQ = 80.0 * 80.0;
        int cx = ene.obtenerCentroX();
        int cy = ene.obtenerCentroY();

        for (BalaEnemiga bala : listaBalasEnemigas) {
            if (!bala.estaActiva()) continue;
            double dx = bala.obtenerX() - cx;
            double dy = bala.obtenerY() - cy;
            if (dx * dx + dy * dy <= RADIO_SQ) {
                bala.desactivar();
                listaItems.add(new Item(
                        bala.obtenerX(), bala.obtenerY(),
                        TipoItem.PUNTAJE,
                        zonaX, zonaY,
                        zonaX + zonaAncho, zonaY + zonaAlto));
            }
        }
    }

    private void spawnearDropsDeMuerteJugador() {
        int cx = player.obtenerCentroX();
        int cy = player.obtenerCentroY();

        for (int i = 0; i < 3; i++) {
            listaItems.add(new Item(
                    cx + (-40 + i * 40), cy - 20,
                    TipoItem.POWER_GRANDE,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto));
        }
        for (int i = 0; i < 4; i++) {
            listaItems.add(new Item(
                    cx + (-45 + i * 30), cy + 20,
                    TipoItem.POWER,
                    zonaX, zonaY, zonaX + zonaAncho, zonaY + zonaAlto));
        }
    }

    public int obtenerScore()       { return score;  }
    public Jugador obtenerJugador() { return player; }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameCore.Ancho, GameCore.Alto);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(zonaX, zonaY, zonaAncho, zonaAlto);

        java.awt.Shape clipPrevio = g.getClip();
        g.setClip(zonaX, zonaY, zonaAncho, zonaAlto);

        player.dibujar(g);

        for (BalaJugador  b : new ArrayList<>(listaBalasJugador))  b.dibujar(g);
        for (BalaEnemiga  b : new ArrayList<>(listaBalasEnemigas)) b.dibujar(g);
        for (Enemigo      e : new ArrayList<>(listaEnemigos))      e.dibujar(g);
        for (Item         i : new ArrayList<>(listaItems))         i.dibujar(g);

        // dialogo encima de todo dentro del area de juego
        panelDialogo.dibujar((Graphics2D) g, zonaX, zonaY, zonaAncho, zonaAlto);

        g.setClip(clipPrevio);
        sistemaInterfaz.dibujar(g);
    }
}
