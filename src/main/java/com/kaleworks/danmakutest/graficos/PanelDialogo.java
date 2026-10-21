package com.kaleworks.danmakutest.graficos;

import com.kaleworks.danmakutest.dialogo.CatalogoDialogos;
import com.kaleworks.danmakutest.dialogo.LadoDialogo;
import com.kaleworks.danmakutest.dialogo.LineaDialogo;
import com.kaleworks.danmakutest.dialogo.PersonajeEnEscena;
import com.kaleworks.danmakutest.dialogo.PosicionDialogo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.Comparator;

/**
 * PanelDialogo: renderizador del sistema de dialogo.
 *
 * Reglas de posicionamiento:
 *   1. Cada personaje tiene un lado fijo (IZQUIERDA / DERECHA).
 *   2. activo=true  -> posicion ADELANTE, z-order encima
 *      activo=false -> posicion ATRAS,    z-order debajo
 *      Opacidad siempre 1.0 — no hay oscurecimiento.
 *   3. Personajes inactivos del mismo lado se apilan con OFFSET_APILAMIENTO px.
 *   4. El lerp solo se dispara cuando cambia activo/inactivo entre lineas.
 *
 * Coordenadas de PosicionDialogo son RELATIVAS a la zona.
 * Al dibujar retratos se suma zonaX/zonaY para obtener coordenadas absolutas.
 */
public class PanelDialogo {

    // --- layout de caja ---
    private static final int   CAJA_ALTO       = 110;
    private static final int   CAJA_MARGEN     = 12;
    private static final int   FUENTE_NOMBRE   = 13;
    private static final int   FUENTE_TEXTO    = 12;
    private static final int   TEXTO_ANCHO_MAX = 384 - CAJA_MARGEN * 2 - 8;
    private static final int   FRAMES_POR_CHAR = 2;
    private static final int   FRAMES_FADE     = 20;
    private static final float LERP_SPEED      = 0.18f;
    private static final int   OFFSET_APILAMIENTO = 22;

    // --- estado del dialogo ---
    private CatalogoDialogos dialogoActual;
    private int              indiceLinea = 0;
    private boolean          activo      = false;
    private boolean          terminado   = false;

    // --- typewriter ---
    private int     charsVisibles   = 0;
    private int     frameTypewriter = 0;
    private boolean textoCompleto   = false;
    private int     frameContinuar  = 0;

    // --- fade del panel (solo afecta la caja de texto) ---
    private float   alphaPanel = 0f;
    private boolean cerrando   = false;
    private int     frameFade  = 0;

    // --- estado animado por personaje ---
    private int            numPersonajes  = 0;
    private ImagenEscena[] imagenes;
    private float[]        posXActual;
    private float[]        posYActual;
    private float[]        posXObj;
    private float[]        posYObj;
    private boolean[]      esActivo;
    private boolean[]      esActivoPrevio;

    private final Font fuenteNombre;
    private final Font fuenteTexto;

    public PanelDialogo() {
        fuenteNombre = new Font("Arial", Font.BOLD,  FUENTE_NOMBRE);
        fuenteTexto  = new Font("Arial", Font.PLAIN, FUENTE_TEXTO);
    }

    // =========================================
    // API publica
    // =========================================

    public void iniciar(CatalogoDialogos dialogo) {
        dialogoActual = dialogo;
        indiceLinea   = 0;
        activo        = true;
        terminado     = false;
        cerrando      = false;
        alphaPanel    = 0f;
        frameFade     = 0;
        imagenes      = null;
        cargarLinea(true);
    }

    public void actualizar(boolean pulsadoZ) {
        if (!activo) return;

        if (cerrando) {
            frameFade++;
            alphaPanel = 1f - Math.min(1f, frameFade / (float) FRAMES_FADE);
            if (alphaPanel <= 0f) { activo = false; terminado = true; }
            lerp();
            return;
        }

        if (alphaPanel < 1f) {
            frameFade++;
            alphaPanel = Math.min(1f, frameFade / (float) FRAMES_FADE);
        }

        lerp();

        if (!textoCompleto) {
            if (++frameTypewriter >= FRAMES_POR_CHAR) {
                frameTypewriter = 0;
                charsVisibles   = Math.min(charsVisibles + 1, lineaActual().texto.length());
                if (charsVisibles == lineaActual().texto.length()) textoCompleto = true;
            }
        }
        if (textoCompleto) frameContinuar++;

        if (pulsadoZ) {
            if (!textoCompleto) {
                charsVisibles = lineaActual().texto.length();
                textoCompleto = true;
            } else {
                indiceLinea++;
                if (dialogoActual.haTerminado(indiceLinea)) {
                    iniciarCierre();
                } else {
                    cargarLinea(false);
                }
            }
        }
    }

    public void dibujar(Graphics2D g2, int zonaX, int zonaY, int zonaAncho, int zonaAlto) {
        if ((!activo && !terminado) || alphaPanel <= 0f || imagenes == null) return;

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Composite compositeOrig = g2.getComposite();

        // retratos: posXActual/posYActual son relativas a la zona, sumar zonaX/zonaY
        for (int i : ordenDePintado()) {
            imagenes[i].setPosicion(
                    zonaX + Math.round(posXActual[i]),
                    zonaY + Math.round(posYActual[i]));
            imagenes[i].setOpacidad(1.0f);
            imagenes[i].dibujar(g2);
        }

        // caja de texto con fade
        int cajaX = zonaX;
        int cajaY = zonaY + zonaAlto - CAJA_ALTO;
        int cajaW = zonaAncho;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaPanel * 0.88f));
        g2.setColor(new Color(10, 10, 30));
        g2.fillRect(cajaX, cajaY, cajaW, CAJA_ALTO);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaPanel));
        g2.setColor(new Color(180, 160, 220));
        g2.drawLine(cajaX, cajaY, cajaX + cajaW, cajaY);

        LineaDialogo linea = lineaActual();
        g2.setFont(fuenteNombre);
        g2.setColor(new Color(255, 220, 100));
        g2.drawString(linea.hablante, cajaX + CAJA_MARGEN, cajaY + CAJA_MARGEN + FUENTE_NOMBRE);

        g2.setColor(new Color(100, 90, 140));
        int sepY = cajaY + CAJA_MARGEN + FUENTE_NOMBRE + 3;
        g2.drawLine(cajaX + CAJA_MARGEN, sepY, cajaX + cajaW - CAJA_MARGEN, sepY);

        g2.setFont(fuenteTexto);
        g2.setColor(new Color(230, 230, 240));
        dibujarTextoMultilinea(g2,
                linea.texto.substring(0, charsVisibles),
                cajaX + CAJA_MARGEN, sepY + FUENTE_TEXTO + 4,
                TEXTO_ANCHO_MAX, FUENTE_TEXTO + 4);

        if (textoCompleto && !cerrando) {
            float pulso = (float)(0.5 + 0.5 * Math.abs(Math.sin(frameContinuar * 0.08)));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaPanel * pulso));
            g2.setColor(new Color(255, 255, 160));
            int tx = cajaX + cajaW - CAJA_MARGEN - 10;
            int ty = cajaY + CAJA_ALTO - CAJA_MARGEN;
            g2.fillPolygon(new int[]{tx-6, tx+6, tx}, new int[]{ty-8, ty-8, ty}, 3);
        }

        g2.setComposite(compositeOrig);
    }

    public boolean haTerminado() { return terminado; }
    public boolean isActivo()    { return activo;    }

    // =========================================
    // helpers privados
    // =========================================

    private void cargarLinea(boolean saltoInstantaneo) {
        LineaDialogo linea     = lineaActual();
        PersonajeEnEscena[] ps = linea.personajes;
        int n                  = ps.length;

        charsVisibles   = 0;
        frameTypewriter = 0;
        textoCompleto   = false;
        frameContinuar  = 0;

        if (imagenes == null || imagenes.length != n) {
            imagenes       = new ImagenEscena[n];
            posXActual     = new float[n];
            posYActual     = new float[n];
            posXObj        = new float[n];
            posYObj        = new float[n];
            esActivo       = new boolean[n];
            esActivoPrevio = new boolean[n];
            numPersonajes  = n;
            saltoInstantaneo = true;
        }

        int acumIzq = 0, acumDer = 0;

        for (int i = 0; i < n; i++) {
            PersonajeEnEscena p = ps[i];

            esActivoPrevio[i] = esActivo[i];
            esActivo[i]       = p.activo;

            if (imagenes[i] == null) {
                imagenes[i] = new ImagenEscena(p.sprite, p.invertida);
            } else {
                imagenes[i].setSprite(p.sprite);
                imagenes[i].setInvertida(p.invertida);
            }

            boolean cambioEstado = (esActivo[i] != esActivoPrevio[i]);
            if (saltoInstantaneo || cambioEstado) {
                PosicionDialogo posBase = PosicionDialogo.resolver(p.lado, p.activo);
                float xObj = posBase.x;
                float yObj = posBase.y;

                if (!p.activo) {
                    int orden = (p.lado == LadoDialogo.IZQUIERDA) ? acumIzq : acumDer;
                    if (p.lado == LadoDialogo.IZQUIERDA) {
                        xObj -= OFFSET_APILAMIENTO * orden;
                    } else {
                        xObj += OFFSET_APILAMIENTO * orden;
                    }
                }

                posXObj[i] = xObj;
                posYObj[i] = yObj;

                if (saltoInstantaneo) {
                    posXActual[i] = xObj;
                    posYActual[i] = yObj;
                }
            }

            if (!p.activo) {
                if (p.lado == LadoDialogo.IZQUIERDA) acumIzq++;
                else                                  acumDer++;
            }
        }
    }

    private void lerp() {
        if (imagenes == null) return;
        for (int i = 0; i < numPersonajes; i++) {
            posXActual[i] = lerp(posXActual[i], posXObj[i], LERP_SPEED);
            posYActual[i] = lerp(posYActual[i], posYObj[i], LERP_SPEED);
        }
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    /** Inactivos primero (detras), activo al final (encima). */
    private Integer[] ordenDePintado() {
        Integer[] orden = new Integer[numPersonajes];
        for (int i = 0; i < numPersonajes; i++) orden[i] = i;
        Arrays.sort(orden, Comparator.comparingInt(i -> esActivo[i] ? 1 : 0));
        return orden;
    }

    private LineaDialogo lineaActual() {
        return dialogoActual.getLinea(indiceLinea);
    }

    private void iniciarCierre() {
        cerrando  = true;
        frameFade = 0;
    }

    private void dibujarTextoMultilinea(Graphics2D g2, String texto,
                                         int x, int y, int anchoMax, int altoLinea) {
        if (texto == null || texto.isEmpty()) return;
        FontMetrics fm = g2.getFontMetrics();
        String[] palabras = texto.split(" ");
        StringBuilder sb = new StringBuilder();
        int yActual = y;
        for (String palabra : palabras) {
            String candidato = sb.length() == 0 ? palabra : sb + " " + palabra;
            if (fm.stringWidth(candidato) <= anchoMax) {
                sb = new StringBuilder(candidato);
            } else {
                if (sb.length() > 0) { g2.drawString(sb.toString(), x, yActual); yActual += altoLinea; }
                sb = new StringBuilder(palabra);
            }
        }
        if (sb.length() > 0) g2.drawString(sb.toString(), x, yActual);
    }
}
