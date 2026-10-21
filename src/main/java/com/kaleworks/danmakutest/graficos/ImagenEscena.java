package com.kaleworks.danmakutest.graficos;

import com.kaleworks.danmakutest.util.HojaSpritesRegistrada;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImagenEscena {

    private BufferedImage imagen;
    private int   x;
    private int   y;
    private float opacidad;
    private float escala;
    private boolean visible;
    private boolean invertida;

    // --- constructores ---

    public ImagenEscena(HojaSpritesRegistrada sprite) {
        this(sprite.obtenerImagen(), false);
    }

    public ImagenEscena(HojaSpritesRegistrada sprite, boolean invertida) {
        this(sprite.obtenerImagen(), invertida);
    }

    public ImagenEscena(BufferedImage imagen) {
        this(imagen, false);
    }

    public ImagenEscena(BufferedImage imagen, boolean invertida) {
        this.imagen    = imagen;
        this.x         = 0;
        this.y         = 0;
        this.opacidad  = 1.0f;
        this.escala    = 1.0f;
        this.visible   = true;
        this.invertida = invertida;
    }

    // --- setters encadenables ---

    public ImagenEscena setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ImagenEscena setOpacidad(float opacidad) {
        this.opacidad = Math.max(0f, Math.min(1f, opacidad));
        return this;
    }

    public ImagenEscena setEscala(float escala) {
        this.escala = Math.max(0f, escala);
        return this;
    }

    public ImagenEscena setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public ImagenEscena setInvertida(boolean invertida) {
        this.invertida = invertida;
        return this;
    }

    public ImagenEscena setSprite(HojaSpritesRegistrada sprite) {
        this.imagen = sprite.obtenerImagen();
        return this;
    }

    public ImagenEscena setSprite(BufferedImage imagen) {
        this.imagen = imagen;
        return this;
    }

    // --- getters ---

    public int     getX()          { return x;        }
    public int     getY()          { return y;        }
    public float   getOpacidad()   { return opacidad; }
    public float   getEscala()     { return escala;   }
    public boolean isVisible()     { return visible;  }
    public boolean isInvertida()   { return invertida;}

    public int getAnchoPantalla() {
        return imagen == null ? 0 : Math.round(imagen.getWidth()  * escala);
    }

    public int getAltoPantalla() {
        return imagen == null ? 0 : Math.round(imagen.getHeight() * escala);
    }

    // --- render ---

    public void dibujar(Graphics2D g) {
        if (!visible || imagen == null) return;

        Composite compositeOriginal = g.getComposite();

        if (opacidad < 1.0f) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidad));
        }

        int w = getAnchoPantalla();
        int h = getAltoPantalla();

        if (invertida) {
            // espejo horizontal: dx1 > dx2 invierte la imagen sin crear BufferedImage extra
            g.drawImage(imagen, x + w, y, x, y + h,
                        0, 0, imagen.getWidth(), imagen.getHeight(), null);
        } else if (escala == 1.0f) {
            g.drawImage(imagen, x, y, null);
        } else {
            g.drawImage(imagen, x, y, w, h, null);
        }

        g.setComposite(compositeOriginal);
    }
}
