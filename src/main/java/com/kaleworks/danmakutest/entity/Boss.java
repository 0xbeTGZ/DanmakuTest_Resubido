package com.kaleworks.danmakutest.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Boss extends Enemigo {

    private final List<SpellCard> spellCards;
    private int indiceSpellActual;
    private int vidaSpellActual;
    private int framesEnSpellActual;

    private final int hitboxAnchoBoss;
    private final int hitboxAltoBoss;

    private Jugador jugador;

    public Boss(double inicioX, double inicioY,
                int ancho, int alto,
                int zonaX, int zonaY, int zonaXmax, int zonaYmax,
                List<SpellCard> spellCards) {

        super(inicioX, inicioY,
              0, 0,
              ancho, alto,
              1,
              zonaX, zonaY, zonaXmax, zonaYmax,
              null,
              5000,
              true,
              TipoItem.EXTEND);

        this.hitboxAnchoBoss = ancho;
        this.hitboxAltoBoss = alto;
        this.jugador = null;

        if (spellCards == null || spellCards.isEmpty()) {
            this.spellCards = new ArrayList<>();
        } else {
            this.spellCards = spellCards;
        }

        this.indiceSpellActual = 0;
        this.framesEnSpellActual = 0;

        if (!this.spellCards.isEmpty()) {
            SpellCard primera = this.spellCards.get(0);
            this.vidaSpellActual = primera.obtenerVidaInicial();
            inyectarJugadorEnSpell(primera);
            primera.iniciar(this);
        } else {
            this.vidaSpellActual = 0;
        }
    }

    @Override
    public void definirJugador(Jugador jugador) {
        this.jugador = jugador;

        if (!spellCards.isEmpty() && indiceSpellActual < spellCards.size()) {
            inyectarJugadorEnSpell(spellCards.get(indiceSpellActual));
        }
    }

    private void inyectarJugadorEnSpell(SpellCard spell) {
        if (jugador == null) return;
        if (spell instanceof SpellCardCompuesta) {
            ((SpellCardCompuesta) spell).definirJugador(jugador);
        } else if (spell instanceof SpellCardSimple) {
            ((SpellCardSimple) spell).definirJugador(jugador);
        }
    }

    @Override
    public void disparo(ArrayList<BalaEnemiga> listaBalasEnemigas) {
        if (!estaActivo()) return;
        if (spellCards.isEmpty()) return;

        SpellCard spellActual = spellCards.get(indiceSpellActual);
        spellActual.actualizar(this, listaBalasEnemigas, framesEnSpellActual);
        framesEnSpellActual++;

        if (framesEnSpellActual >= spellActual.obtenerDuracionMaxima()) {
            avanzarSpell(listaBalasEnemigas);
        }
    }

    @Override
    public void recibirDmg(int dmg) {
        if (!estaActivo()) return;
        if (spellCards.isEmpty()) return;

        vidaSpellActual -= dmg;
        if (vidaSpellActual <= 0) {

            SpellCard spellActual = spellCards.get(indiceSpellActual);
            if (spellActual instanceof SpellCardCompuesta) {
                ((SpellCardCompuesta) spellActual).marcarCapturada();
            }
            avanzarSpell(null);
        }
    }

    private void avanzarSpell(ArrayList<BalaEnemiga> listaBalas) {
        SpellCard spellSaliente = spellCards.get(indiceSpellActual);
        if (listaBalas != null) {
            spellSaliente.terminar(this, listaBalas);
        }

        indiceSpellActual++;
        framesEnSpellActual = 0;

        if (indiceSpellActual >= spellCards.size()) {

            super.recibirDmg(99999);
            return;
        }

        SpellCard nueva = spellCards.get(indiceSpellActual);
        vidaSpellActual = nueva.obtenerVidaInicial();
        inyectarJugadorEnSpell(nueva);
        nueva.iniciar(this);
    }

    @Override
    public void dibujar(Graphics g) {

        g.setColor(new Color(255, 200, 60));
        g.fillRect(obtenerX(), obtenerY(), hitboxAnchoBoss, hitboxAltoBoss);
        g.setColor(Color.WHITE);
        g.drawRect(obtenerX(), obtenerY(), hitboxAnchoBoss, hitboxAltoBoss);
    }

    public String obtenerNombreSpellActual() {
        if (spellCards.isEmpty() || indiceSpellActual >= spellCards.size()) return "";
        return spellCards.get(indiceSpellActual).obtenerNombre();
    }

    public int obtenerVidaSpellActual()    { return vidaSpellActual; }
    public int obtenerIndiceSpellActual()  { return indiceSpellActual; }
    public int obtenerCantidadSpells()     { return spellCards.size(); }
}
