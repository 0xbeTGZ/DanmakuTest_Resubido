package com.kaleworks.danmakutest.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class CargadorSonidos {

    public static final class SFX {
        private SFX() { }

        public static final String DISPARO          = "/sounds/sfx/plst00.wav";
        public static final String IMPACTO_ENEMIGO  = "/sounds/sfx/damage00.wav";
        public static final String ENEMIGO_MUERTO = "/sounds/sfx/enep00.wav";
        public static final String JUGADOR_GOLPEADO = "/sounds/sfx/jugadorGolpeado.wav";
        public static final String JUGADOR_MUERE    = "/sounds/sfx/pldead00.wav";
        public static final String BOMBA            = "/sounds/sfx/cat00.wav";
        public static final String ITEM             = "/sounds/sfx/item00.wav";
        public static final String EXTEND           = "/sounds/sfx/extend.wav";
        public static final String GRAZE            = "/sounds/sfx/graze.wav";
    }

    public static final class BGM {
        private BGM() { }

        public static final String MENU   = "/sounds/bgm/titulo.wav";
        public static final String STAGE1 = "/sounds/bgm/nivel1.wav";
        public static final String BOSS   = "/sounds/bgm/jefe1.wav";
    }

    private static final int TAMANO_POOL = 6;

    private static final Map<String, Clip[]> POOL_SFX = new HashMap<>();

    private static final Map<String, Integer> CURSOR_SFX = new HashMap<>();

    private static Clip clipBGMActual = null;

    private static String rutaBGMActual = null;

    private static float volumenSFX = -8.0f;
    private static float volumenBGM = 0.0f;

    private CargadorSonidos() { }

    private static Clip abrirClip(String ruta) {
        try (InputStream in = CargadorSonidos.class.getResourceAsStream(ruta)) {
            if (in == null) {
                LogJuego.error("Sonido no encontrado en classpath: " + ruta, null);
                return null;
            }

            BufferedInputStream buffered = new BufferedInputStream(in);
            AudioInputStream audio = AudioSystem.getAudioInputStream(buffered);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            return clip;
        } catch (UnsupportedAudioFileException e) {
            LogJuego.error("Formato de audio no soportado: " + ruta, e);
        } catch (IOException e) {
            LogJuego.error("Fallo al leer sonido: " + ruta, e);
        } catch (LineUnavailableException e) {
            LogJuego.error("Linea de audio no disponible para: " + ruta, e);
        }
        return null;
    }

    private static void aplicarVolumenAlClip(Clip clip, float dB) {
        if (clip == null) return;
        try {
            FloatControl ctrl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float clamped = Math.max(ctrl.getMinimum(),
                                     Math.min(ctrl.getMaximum(), dB));
            ctrl.setValue(clamped);
        } catch (IllegalArgumentException e) {
            LogJuego.aviso("La linea de audio no soporta MASTER_GAIN");
        }
    }

    public static void ajustarVolumenSFX(float dB) {
        volumenSFX = dB;

        for (Clip[] pool : POOL_SFX.values()) {
            for (Clip c : pool) {
                aplicarVolumenAlClip(c, volumenSFX);
            }
        }
    }

    public static void ajustarVolumenBGM(float dB) {
        volumenBGM = dB;
        aplicarVolumenAlClip(clipBGMActual, volumenBGM);
    }

    public static void reproducirSFX(String ruta) {
        Clip[] pool = POOL_SFX.get(ruta);
        if (pool == null) {

            pool = new Clip[TAMANO_POOL];
            for (int i = 0; i < TAMANO_POOL; i++) {
                pool[i] = abrirClip(ruta);
                if (pool[i] != null) {
                    aplicarVolumenAlClip(pool[i], volumenSFX);
                }
            }
            POOL_SFX.put(ruta, pool);
            CURSOR_SFX.put(ruta, 0);
        }

        int idx = CURSOR_SFX.get(ruta);
        CURSOR_SFX.put(ruta, (idx + 1) % TAMANO_POOL);

        Clip clip = pool[idx];
        if (clip == null) return;

        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public static void reproducirBGM(String ruta) {

        if (ruta.equals(rutaBGMActual) && clipBGMActual != null
                && clipBGMActual.isRunning()) {
            return;
        }

        detenerBGM();

        Clip clip = abrirClip(ruta);
        if (clip == null) return;

        aplicarVolumenAlClip(clip, volumenBGM);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clipBGMActual = clip;
        rutaBGMActual = ruta;
    }

    public static void pausarBGM() {
        if (clipBGMActual != null && clipBGMActual.isRunning()) {
            clipBGMActual.stop();
        }
    }

    public static void reanudarBGM() {
        if (clipBGMActual != null && !clipBGMActual.isRunning()) {
            clipBGMActual.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void detenerBGM() {
        if (clipBGMActual != null) {
            try {
                if (clipBGMActual.isRunning()) clipBGMActual.stop();
                clipBGMActual.close();
            } catch (Exception e) {
                LogJuego.error("Fallo al detener BGM", e);
            }
            clipBGMActual = null;
            rutaBGMActual = null;
        }
    }

    public static void liberarTodo() {
        detenerBGM();
        for (Clip[] pool : POOL_SFX.values()) {
            for (Clip c : pool) {
                if (c != null) {
                    try { c.close(); } catch (Exception ignorado) { }
                }
            }
        }
        POOL_SFX.clear();
        CURSOR_SFX.clear();
    }
}
