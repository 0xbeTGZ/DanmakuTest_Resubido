package com.kaleworks.danmakutest.dialogo;

import com.kaleworks.danmakutest.util.HojaSpritesRegistrada;

/**
 * PersonajeEnEscena: snapshot del estado visual de un personaje
 * en una linea de dialogo concreta.
 *
 * El catalogo solo necesita declarar:
 *   sprite    — expresion en este momento
 *   lado      — IZQUIERDA o DERECHA (fijo para cada personaje en el dialogo)
 *   invertida — espejo horizontal
 *   activo    — true: habla (adelante, opacidad 1.0)
 *               false: escucha (atras, opacidad 0.45)
 *
 * El PanelDialogo calcula coordenadas, apilamiento y animacion.
 */
public class PersonajeEnEscena {

    public static final float OPACIDAD_INACTIVO = 0.45f;

    public final HojaSpritesRegistrada sprite;
    public final LadoDialogo           lado;
    public final boolean               invertida;
    public final boolean               activo;

    private PersonajeEnEscena(HojaSpritesRegistrada sprite,
                               LadoDialogo lado,
                               boolean invertida,
                               boolean activo) {
        this.sprite    = sprite;
        this.lado      = lado;
        this.invertida = invertida;
        this.activo    = activo;
    }

    /**
     * Factory estatico para uso conciso en CatalogoDialogos.
     *
     * Ejemplo:
     *   de(REIMU_NORMAL, IZQUIERDA, false, true)
     */
    public static PersonajeEnEscena de(HojaSpritesRegistrada sprite,
                                       LadoDialogo lado,
                                       boolean invertida,
                                       boolean activo) {
        return new PersonajeEnEscena(sprite, lado, invertida, activo);
    }
}
