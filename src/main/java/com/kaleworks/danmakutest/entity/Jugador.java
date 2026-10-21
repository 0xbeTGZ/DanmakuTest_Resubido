package com.kaleworks.danmakutest.entity;

import java.util.List;
import java.awt.Color;
import java.awt.Graphics;

import com.kaleworks.danmakutest.util.CargadorSonidos;

public abstract class Jugador {

    protected int X;
    protected int Y;
    protected int velocidad;
    protected final int hitboxAncho;
    protected final int hitboxLargo;
    protected String estado;

    protected SeleccionJugador seleccion;

    protected int powerActual;
    protected int powerMaximo;

    protected int vidasActuales;
    protected int vidasMaximas;

    protected int bombasActuales;
    protected int bombasMaximas;

    protected final int zonaX;
    protected final int zonaY;
    protected final int zonaAncho;
    protected final int zonaAlto;

    protected int cooldownDisparo;
    protected int intervaloDisparo;

    protected int cooldownImpacto;
    protected int intervaloInvulnerabilidad;

    public static final int FRAMES_DEATHBOMB = 8;
    protected int framesDeathbombRestantes;

    protected boolean tambaleandose;

    public Jugador(int inicioX, int inicioY,
                   int zonaX, int zonaY, int zonaAncho, int zonaAlto,
                   SeleccionJugador seleccion) {

        this.X = inicioX;
        this.Y = inicioY;
        this.velocidad = 5;
        this.hitboxAncho = 5;
        this.hitboxLargo = 5;
        this.estado = "vivo";
        this.seleccion = seleccion;

        this.powerActual = 0;
        this.powerMaximo = 80;

        this.vidasActuales = 3;
        this.vidasMaximas = 8;

        this.bombasActuales = 3;
        this.bombasMaximas = 8;

        this.zonaX = zonaX;
        this.zonaY = zonaY;
        this.zonaAncho = zonaAncho;
        this.zonaAlto = zonaAlto;

        this.cooldownDisparo = 0;
        this.intervaloDisparo = 5;

        this.cooldownImpacto = 0;
        this.intervaloInvulnerabilidad = 60;
        this.framesDeathbombRestantes = 0;
        this.tambaleandose = false;
    }

    public void actualizar(boolean arriba, boolean abajo, boolean izquierda, boolean derecha, boolean focus) {

        if (focus) {
            velocidad = 2;
        } else {
            velocidad = 4;
        }

        if (arriba) {
            Y -= velocidad;
        }

        if (abajo) {
            Y += velocidad;
        }

        if (izquierda) {
            X -= velocidad;
        }

        if (derecha) {
            X += velocidad;
        }

        if (X < zonaX) {
            X = zonaX;
        }

        if (Y < zonaY) {
            Y = zonaY;
        }

        if (X > zonaX + zonaAncho - hitboxAncho) {
            X = zonaX + zonaAncho - hitboxAncho;
        }

        if (Y > zonaY + zonaAlto - hitboxLargo) {
            Y = zonaY + zonaAlto - hitboxLargo;
        }

        if (cooldownDisparo > 0) {
            cooldownDisparo--;
        }

        if (cooldownImpacto > 0) {
            cooldownImpacto--;
        }

        if (tambaleandose) {
            framesDeathbombRestantes--;
            if (framesDeathbombRestantes <= 0) {
                aplicarDanioReal();
            }
        }
    }

    public void intentoDisparo(boolean disparando, boolean focus,
            List<BalaJugador> listaBalas,
            int limiteSuperior, int limiteInferior) {
        if (!disparando) {

            alSoltarDisparo();
            return;
        }

        if (cooldownDisparo > 0) {
            return;
        }

        disparar(listaBalas, limiteSuperior, limiteInferior, focus);
        CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.DISPARO);
        cooldownDisparo = intervaloDisparo;
    }

    public abstract void disparar(List<BalaJugador> listaBalas,
                                  int limiteSuperior, int limiteInferior,
                                  boolean focus);

    protected void alSoltarDisparo() {

    }

    protected void agregarBala(List<BalaJugador> lista,
                               double x, double y,
                               double speedX, double speedY,
                               TipoDeBalaJugador tipo) {
        lista.add(new BalaJugador(
                x, y,
                speedX, speedY,
                tipo.obtenerAncho(), tipo.obtenerAlto(),
                tipo,
                zonaX, zonaX + zonaAncho,
                zonaY, zonaY + zonaAlto
        ));
    }

    private static final int PENALTY_POWER_AL_MORIR = 16;

    public void recibirImpacto() {
        if (!puedeRecibirImpacto()) {
            return;
        }
        if (tambaleandose) {

            return;
        }

        tambaleandose = true;
        framesDeathbombRestantes = FRAMES_DEATHBOMB;
    }

    public boolean intentarDeathbomb() {
        if (!tambaleandose) return false;
        tambaleandose = false;
        framesDeathbombRestantes = 0;
        return true;
    }

    public boolean estaTambaleandose() {
        return tambaleandose;
    }

    private void aplicarDanioReal() {
        tambaleandose = false;
        framesDeathbombRestantes = 0;
        bombasActuales = 3;
        restarVida(1);
        definirPowerActual(this.powerActual - PENALTY_POWER_AL_MORIR);

        CargadorSonidos.reproducirSFX(CargadorSonidos.SFX.JUGADOR_MUERE);
        if (vidasActuales <= 0) {
            estado = "muerto";
            cooldownImpacto = 0;
        } else {
            estado = "vivo";
            cooldownImpacto = intervaloInvulnerabilidad;
        }
    }

    public boolean puedeRecibirImpacto() {
        return cooldownImpacto <= 0 && !estado.equals("muerto") && !tambaleandose;
    }

    public void activarInvulnerabilidad(int frames) {
        if (frames < 0) {
            return;
        }
        if (frames > cooldownImpacto) {
            cooldownImpacto = frames;
        }

    }

    public void dibujar(Graphics g) {

        if (estado.equals("muerto")) {
            return;
        }

        if (cooldownImpacto > 0 && cooldownImpacto % 4 < 2) {
            g.setColor(Color.PINK);
        } else {
            g.setColor(Color.RED);
        }

        g.fillRect(X, Y, hitboxAncho, hitboxLargo);
    }

    public int obtenerPowerActual() {
        return powerActual;
    }

    public int obtenerPowerMaximo() {
        return powerMaximo;
    }

    public void definirPowerActual(int nuevoPower) {
        this.powerActual = nuevoPower;

        if (this.powerActual < 0) {
            this.powerActual = 0;
        }

        if (this.powerActual > powerMaximo) {
            this.powerActual = powerMaximo;
        }
    }

    public void agregarPower(int cantidad) {
        definirPowerActual(this.powerActual + cantidad);
    }

    public boolean estaEnPowerMaximo() {
        return powerActual >= powerMaximo;
    }

    public int obtenerVidas() {
        return vidasActuales;
    }

    public int obtenerVidasMaximas() {
        return vidasMaximas;
    }

    public void definirVidas(int nuevasVidas) {
        this.vidasActuales = nuevasVidas;

        if (this.vidasActuales < 0) {
            this.vidasActuales = 0;
        }

        if (this.vidasActuales > vidasMaximas) {
            this.vidasActuales = vidasMaximas;
        }
    }

    public void agregarVida(int cantidad) {
        definirVidas(this.vidasActuales + cantidad);
    }

    public void restarVida(int cantidad) {
        definirVidas(this.vidasActuales - cantidad);
    }

    public boolean estaSinVidas() {
        return vidasActuales <= 0;
    }

    public int obtenerBombas() {
        return bombasActuales;
    }

    public int obtenerBombasMaximas() {
        return bombasMaximas;
    }

    public void definirBombas(int nuevasBombas) {
        this.bombasActuales = nuevasBombas;

        if (this.bombasActuales < 0) {
            this.bombasActuales = 0;
        }

        if (this.bombasActuales > bombasMaximas) {
            this.bombasActuales = bombasMaximas;
        }
    }

    public void agregarBombas(int cantidad) {
        definirBombas(this.bombasActuales + cantidad);
    }

    public void restarBombas(int cantidad) {
        definirBombas(this.bombasActuales - cantidad);
    }

    public boolean tieneBombas() {
        return bombasActuales > 0;
    }

    private int grazeAcumulado = 0;

    public int obtenerGraze() {
        return grazeAcumulado;
    }

    public void sumarGraze(int cantidad) {
        if (cantidad > 0) this.grazeAcumulado += cantidad;
    }

    public int obtenerX() {
        return X;
    }

    public int obtenerY() {
        return Y;
    }

    public int obtenerAncho() {
        return hitboxAncho;
    }

    public int obtenerAlto() {
        return hitboxLargo;
    }

    public int obtenerCentroX() {
        return X + hitboxAncho / 2;
    }

    public int obtenerCentroY() {
        return Y + hitboxLargo / 2;
    }

    public String obtenerEstado() {
        return estado;
    }

    public SeleccionJugador obtenerSeleccion() {
        return seleccion;
    }

    public boolean estaVivo() {
        return !estado.equals("muerto");
    }

    public boolean estaEnMaximoDeBombas() {
        return bombasActuales >= bombasMaximas;
    }

    public boolean estaEnMaximoDeVidas() {
        return vidasActuales >= vidasMaximas;
    }
}