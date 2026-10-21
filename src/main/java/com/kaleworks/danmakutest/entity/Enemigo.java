package com.kaleworks.danmakutest.entity;

import com.kaleworks.danmakutest.util.CargadorSonidos;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Enemigo {

    private double X;
    private double Y;
    private double speedX;
    private double speedY;

    private int hitboxAncho;
    private int hitboxAlto;

    private int vida;
    private boolean activo;

    private int zonaX;
    private int zonaY;
    private int zonaXmax;
    private int zonaYmax;

    private SpellCardSimple sistemaDisparo;
    private int framesActivos;

    private int puntajeAlMorir;
    private boolean dropeaItem;
    private TipoItem tipoDrop;
    private boolean muerteYaProcesada;

    public Enemigo(double inicioX, double inicioY,
                   double speedX, double speedY,
                   int hitboxAncho, int hitboxAlto,
                   int vida,
                   int zonaX, int zonaY,
                   int zonaXmax, int zonaYmax,
                   SpellCardSimple sistemaDisparo,
                   int puntajeAlMorir,
                   boolean dropeaItem,
                   TipoItem tipoDrop) {

        this.X = inicioX;
        this.Y = inicioY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hitboxAncho = hitboxAncho;
        this.hitboxAlto = hitboxAlto;

        this.vida = Math.max(1, (int) Math.round(vida * Dificultad.actual().multiplicadorVidaEnemigos()));
        this.activo = true;

        this.zonaX = zonaX;
        this.zonaY = zonaY;
        this.zonaXmax = zonaXmax;
        this.zonaYmax = zonaYmax;

        this.sistemaDisparo = sistemaDisparo;
        this.framesActivos = 0;

        if (sistemaDisparo != null) {
            sistemaDisparo.iniciar(this);
        }

        this.puntajeAlMorir = puntajeAlMorir;
        this.dropeaItem = dropeaItem;
        this.tipoDrop = tipoDrop;
        this.muerteYaProcesada = false;
    }

    public Enemigo(double inicioX, double inicioY,
                   double speedX, double speedY,
                   int hitboxAncho, int hitboxAlto,
                   int vida,
                   int zonaX, int zonaY,
                   int zonaXmax, int zonaYmax,
                   SpellCardSimple sistemaDisparo) {
        this(inicioX, inicioY,
             speedX, speedY,
             hitboxAncho, hitboxAlto,
             vida,
             zonaX, zonaY,
             zonaXmax, zonaYmax,
             sistemaDisparo,
             100, false, null);
    }

    public void definirJugador(Jugador jugador) {
        if (sistemaDisparo != null) {
            sistemaDisparo.definirJugador(jugador);
        }
    }

    public void actualizar() {
        X += speedX;
        Y += speedY;

        if (Y > zonaYmax)           activo = false;
        if (X + hitboxAncho < zonaX) activo = false;
        if (X > zonaXmax)           activo = false;
    }

    public void disparo(ArrayList<BalaEnemiga> listaBalasEnemigas) {
        if (!activo) return;
        if (sistemaDisparo == null) return;

        sistemaDisparo.actualizar(this, listaBalasEnemigas, framesActivos);
        framesActivos++;
    }

    public void dibujar(Graphics g) {

        g.setColor(Color.MAGENTA);
        g.fillRect((int) X, (int) Y, hitboxAncho, hitboxAlto);
    }

    public void recibirDmg(int dmg) {
        if (!activo) return;
        vida -= dmg;
        if (vida <= 0) {
            vida = 0;
            activo = false;
            CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.ENEMIGO_MUERTO);
        }
    }

    public boolean estaActivo()                  { return activo; }
    public int obtenerVida()                     { return vida; }
    public int obtenerX()                        { return (int) X; }
    public int obtenerY()                        { return (int) Y; }
    public int obtenerAncho()                    { return hitboxAncho; }
    public int obtenerAlto()                     { return hitboxAlto; }
    public int obtenerCentroX()                  { return (int) X + hitboxAncho / 2; }
    public int obtenerCentroY()                  { return (int) Y + hitboxAlto / 2; }
    public int obtenerZonaX()                    { return zonaX; }
    public int obtenerZonaY()                    { return zonaY; }
    public int obtenerZonaXmax()                 { return zonaXmax; }
    public int obtenerZonaYmax()                 { return zonaYmax; }
    public int obtenerPuntajeAlMorir()           { return puntajeAlMorir; }
    public boolean puedeDropearItem()            { return dropeaItem && tipoDrop != null; }
    public TipoItem obtenerTipoDrop()            { return tipoDrop; }
    public SpellCardSimple obtenerSistemaDisparo() { return sistemaDisparo; }

    public boolean acabaDeMorirYNoFueProcesado() {
        return !activo && !muerteYaProcesada;
    }

    public void marcarMuerteComoProcesada() {
        muerteYaProcesada = true;
    }
}
