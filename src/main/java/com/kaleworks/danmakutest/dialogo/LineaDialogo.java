package com.kaleworks.danmakutest.dialogo;

/**
 * LineaDialogo: una linea de dialogo con su hablante y los personajes en escena.
 *
 * Uso en CatalogoDialogos:
 *   LineaDialogo.de("Reimu", "Texto aqui.",
 *       PersonajeEnEscena.de(REIMU_NORMAL, IZQUIERDA, false, true),
 *       PersonajeEnEscena.de(BOSS1_NORMAL, DERECHA,   true,  false)
 *   )
 *
 * Invariante: exactamente UN personaje debe tener activo=true por linea.
 * El nombre del hablante debe coincidir con el nombre del activo.
 */
public class LineaDialogo {

    public final String               hablante;
    public final String               texto;
    public final PersonajeEnEscena[]  personajes;

    private LineaDialogo(String hablante, String texto, PersonajeEnEscena... personajes) {
        this.hablante   = hablante;
        this.texto      = texto;
        this.personajes = personajes;
    }

    /** Factory estatico para sintaxis fluida en el catalogo. */
    public static LineaDialogo de(String hablante, String texto, PersonajeEnEscena... personajes) {
        return new LineaDialogo(hablante, texto, personajes);
    }
}
