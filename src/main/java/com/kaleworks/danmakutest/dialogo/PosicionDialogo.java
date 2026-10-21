package com.kaleworks.danmakutest.dialogo;

/**
 * PosicionDialogo: coordenadas BASE relativas a la zona de juego (origen en zonaX, zonaY).
 *
 * La zona de juego es 384x448 px.
 * Retratos de 128x256 px.
 * Caja de texto: ocupa los ultimos 110px de alto -> los retratos deben terminar en y=338.
 * Por tanto y ideal para que el pie del retrato toque la caja: 448 - 110 - 256 = 82.
 *
 * Coordenadas en este enum son RELATIVAS a la zona (no absolutas de pantalla).
 * PanelDialogo suma zonaX/zonaY al dibujar.
 *
 *   ADELANTE (activo=true):  mas abajo y mas al centro.
 *   ATRAS    (activo=false): mas arriba y mas al borde.
 */
public enum PosicionDialogo {

    /** Izquierda primer plano — personaje activo del lado izquierdo. */
    IZQ_ADELANTE(10, 82),

    /** Izquierda segundo plano — personaje(s) inactivo del lado izquierdo. */
    IZQ_ATRAS(-10, 62),

    /** Derecha primer plano — personaje activo del lado derecho. */
    DER_ADELANTE(246, 82),

    /** Derecha segundo plano — personaje(s) inactivo del lado derecho. */
    DER_ATRAS(266, 62);

    public final int x;
    public final int y;

    PosicionDialogo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Resuelve la posicion base segun lado y si esta activo. */
    public static PosicionDialogo resolver(LadoDialogo lado, boolean activo) {
        if (lado == LadoDialogo.IZQUIERDA) {
            return activo ? IZQ_ADELANTE : IZQ_ATRAS;
        } else {
            return activo ? DER_ADELANTE : DER_ATRAS;
        }
    }
}
