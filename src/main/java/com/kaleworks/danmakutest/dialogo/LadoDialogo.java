package com.kaleworks.danmakutest.dialogo;

/**
 * LadoDialogo: lado de pantalla al que pertenece un personaje.
 *
 * Este es el unico dato de posicion que el catalogo necesita declarar.
 * El sistema calcula automaticamente si va ADELANTE o ATRAS segun activo.
 *
 * Cuando hay mas de un personaje en el mismo lado, el sistema los
 * escala y desplaza para que se vean apilados en profundidad.
 */
public enum LadoDialogo {
    IZQUIERDA,
    DERECHA
}
