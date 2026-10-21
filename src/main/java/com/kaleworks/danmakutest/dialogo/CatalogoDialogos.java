package com.kaleworks.danmakutest.dialogo;

import static com.kaleworks.danmakutest.dialogo.LadoDialogo.DERECHA;
import static com.kaleworks.danmakutest.dialogo.LadoDialogo.IZQUIERDA;
import static com.kaleworks.danmakutest.dialogo.LineaDialogo.de;
import static com.kaleworks.danmakutest.dialogo.PersonajeEnEscena.de;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.BOSS1_DERROTADO;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.BOSS1_NORMAL;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.BOSS1_SPELL;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.CIRNO_ENOJADA;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.CIRNO_NORMAL;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.MARISA_ALEGRE;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.MARISA_NORMAL;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.MARISA_PENSIVE;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.REIMU_ALEGRE;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.REIMU_ENOJADA;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.REIMU_NORMAL;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.REIMU_PENSIVE;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.REIMU_POSE;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.RUMIA_ENOJADA;
import static com.kaleworks.danmakutest.util.HojaSpritesRegistrada.RUMIA_NORMAL;

/**
 * CatalogoDialogos: todos los dialogos del juego.
 *
 * Cada valor del enum es un dialogo completo.
 * Cada dialogo tiene un array de LineaDialogo en orden.
 *
 * Convencion por linea:
 *   - Exactamente UN personaje con activo=true (el que habla).
 *   - El resto con activo=false (escuchan, se ven atenuados y atras).
 *   - El lado (IZQUIERDA / DERECHA) es fijo para cada personaje en
 *     todo el dialogo — no cambia entre lineas.
 *   - invertida=true para personajes que entran por la derecha
 *     (asi miran hacia el centro de la pantalla).
 *
 * El sistema calcula automaticamente:
 *   - Si va ADELANTE o ATRAS segun activo.
 *   - El apilamiento cuando hay mas de un inactivo en el mismo lado.
 *   - La animacion lerp al cambiar de hablante.
 */
public enum CatalogoDialogos {

    // ------------------------------------------------------------------
    // PRUEBAS
    // ------------------------------------------------------------------

    PRUEBA_UN_PERSONAJE(
        de("Reimu", "ABCDE",
            de(REIMU_NORMAL,   IZQUIERDA, false, true)
        ),
        de("Reimu", "Loren Ipsum.",
            de(REIMU_PENSIVE,  IZQUIERDA, false, true)
        )
    ),

    PRUEBA_DOS_PERSONAJES(
        de("Reimu", "Loren Ipsum..",
            de(REIMU_NORMAL,      IZQUIERDA, false, true)
        ),
        de("???", "Kukuku... Loren Ipsum.",
            de(REIMU_PENSIVE,     IZQUIERDA, false, false),
            de(RUMIA_NORMAL,      DERECHA,   true,  true)
        ),
        de("Reimu", "Eres tu quien provoca Loren Ipsum.",
            de(REIMU_ENOJADA,     IZQUIERDA, false, true),
            de(RUMIA_NORMAL,      DERECHA,   true,  false)
        ),
        de("???", "Jaja, si quieres pasar... demuestra tu poder.",
            de(REIMU_ENOJADA,     IZQUIERDA, false, false),
            de(RUMIA_NORMAL,      DERECHA,   true,  true)
        )
    ),

    PRUEBA_CUATRO_PERSONAJES(
        de("RUMIA", "un combo",
            de(REIMU_NORMAL,    IZQUIERDA, true, false),
            de(MARISA_NORMAL,   IZQUIERDA, true, false),
            de(RUMIA_ENOJADA,    DERECHA,   false,  true),
            de(CIRNO_NORMAL,       DERECHA,   true,  false)
        ),
        de("Marisa", "Melo daze",
            de(REIMU_NORMAL,    IZQUIERDA, true, false),
            de(MARISA_ALEGRE,   IZQUIERDA, true, true),
            de(RUMIA_ENOJADA,    DERECHA,   false,  true),
            de(CIRNO_NORMAL,       DERECHA,   true,  false)
        ),
        de("Reimu", "sea seria aver.",
            de(REIMU_ENOJADA,   IZQUIERDA, true, true),
            de(MARISA_PENSIVE,  IZQUIERDA, true, false),
            de(RUMIA_ENOJADA,    DERECHA,   false,  true),
            de(CIRNO_NORMAL,    DERECHA,   true,  false)
        ),
        de("Cirno", "jajajaja",
            de(REIMU_ENOJADA,   IZQUIERDA, true, true),
            de(MARISA_PENSIVE,  IZQUIERDA, true, false),
            de(RUMIA_NORMAL,    DERECHA,   false,  false),
            de(CIRNO_ENOJADA,    DERECHA,   true,  true)
        )
    ),

    // ------------------------------------------------------------------
    // STAGE REALES
    // ------------------------------------------------------------------

    PROLOGO_STAGE1(
        de("Reimu", "Otra vez una anomalia en el Bosque de Bamboo...",
            de(REIMU_NORMAL,  IZQUIERDA, false, true)
        ),
        de("Reimu", "Supongo que soy la unica que puede arreglarlo.",
            de(REIMU_PENSIVE, IZQUIERDA, false, true)
        ),
        de("Reimu", "Alla voy.",
            de(REIMU_POSE,    IZQUIERDA, false, true)
        )
    ),

    ENCUENTRO_BOSS1(
        de("???", "Detente, miko.",
            de(REIMU_NORMAL,   IZQUIERDA, false, false),
            de(BOSS1_NORMAL,   DERECHA,   true,  true)
        ),
        de("Reimu", "De modo que eras tu.",
            de(REIMU_ENOJADA,  IZQUIERDA, false, true),
            de(BOSS1_NORMAL,   DERECHA,   true,  false)
        ),
        de("???", "No pasaras sin luchar.",
            de(REIMU_ENOJADA,  IZQUIERDA, false, false),
            de(BOSS1_SPELL,    DERECHA,   true,  true)
        )
    ),

    DERROTA_BOSS1(
        de("???", "Increible... perdiste.",
            de(REIMU_ALEGRE,      IZQUIERDA, false, false),
            de(BOSS1_DERROTADO,   DERECHA,   true,  true)
        ),
        de("Reimu", "La proxima vez no causes problemas.",
            de(REIMU_POSE,        IZQUIERDA, false, true),
            de(BOSS1_DERROTADO,   DERECHA,   true,  false)
        )
    );

    // ------------------------------------------------------------------

    private final LineaDialogo[] lineas;

    CatalogoDialogos(LineaDialogo... lineas) {
        this.lineas = lineas;
    }

    public LineaDialogo getLinea(int indice) {
        return lineas[indice];
    }

    public int totalLineas() {
        return lineas.length;
    }

    public boolean haTerminado(int indice) {
        return indice >= lineas.length;
    }
}
