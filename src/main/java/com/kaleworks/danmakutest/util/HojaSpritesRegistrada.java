package com.kaleworks.danmakutest.util;

import java.awt.image.BufferedImage;

public enum HojaSpritesRegistrada {

    REIMU_NORMAL("/sprites/plr1temp.png",25,25, 128, 256),
    REIMU_POSE("/sprites/plr1temp.png", 153,25, 128, 256),
    REIMU_ALEGRE ("/sprites/plr1temp.png", 306,25, 128, 256),
    REIMU_PENSIVE ("/sprites/plr1temp.png", 434,25, 128, 256),
    REIMU_ENOJADA("/sprites/plr1temp.png", 587, 25, 128, 256),
    REIMU_XD ("/sprites/plr1temp.png", 715,25, 128, 256),

    MARISA_NORMAL  ("/sprites/plr2temp.png", 25,25, 128, 256),
    MARISA_POSE  ("/sprites/plr2temp.png", 153,25, 128, 256),
    MARISA_ALEGRE("/sprites/plr2temp.png", 306, 25, 128, 256),
    MARISA_PENSIVE("/sprites/plr2temp.png", 434, 25, 128, 256),
    MARISA_ENOJADA("/sprites/plr2temp.png", 587, 25, 128, 256),
    MARISA_XD("/sprites/plr2temp.png", 715, 25, 128, 256),
    
    
    RUMIA_NORMAL("/sprites/bossestemp.png", 25, 25, 128, 256),
    RUMIA_ALEGRE("/sprites/bossestemp.png", 153, 25, 128, 256),
    RUMIA_PENSIVE("/sprites/bossestemp.png", 306, 25, 128, 256),
    RUMIA_ENOJADA("/sprites/bossestemp.png", 434, 25, 128, 256),
    
    CIRNO_NORMAL("/sprites/bossestemp.png", 25, 306, 128, 256),
    CIRNO_ENOJADA("/sprites/bossestemp.png", 153, 306, 128, 256),
    
    ELRANDOM_NORMAL("/sprites/plr1.png",25,25, 128, 256),
    ELRANDOM_POSE("/sprites/plr1.png", 153,25, 128, 256),
    ELRANDOM_ALEGRE ("/sprites/plr1.png", 306,25, 128, 256),
    ELRANDOM_PENSIVE ("/sprites/plr1.png", 434,25, 128, 256),
    ELRANDOM_ENOJADA("/sprites/plr1.png", 587, 25, 128, 256),
    ELRANDOM_XD ("/sprites/plr1.png", 715,25, 128, 256),

    BOSS1_NORMAL   ("/sprites/retratos/bosses.png",   0,   0, 128, 128),

    BOSS1_SPELL    ("/sprites/retratos/bosses.png", 128,   0, 128, 128),

    BOSS1_DERROTADO("/sprites/retratos/bosses.png", 256,   0, 128, 128),

    FONDO_SPELLCARD("/sprites/escenas/fondo_spellcard.png", 0, 0, -1, -1);

    private final String ruta;
    private final int x;
    private final int y;
    private final int ancho;
    private final int alto;

    HojaSpritesRegistrada(String ruta, int x, int y, int ancho, int alto) {
        this.ruta  = ruta;
        this.x     = x;
        this.y     = y;
        this.ancho = ancho;
        this.alto  = alto;
    }

    public BufferedImage obtenerImagen() {

        if (ancho == -1 || alto == -1) {
            return CargadorImagenes.cargar(ruta);
        }
        return CargadorImagenes.subimagen(ruta, x, y, ancho, alto);
    }

    public String getRuta()  { return ruta;  }
    public int    getX()     { return x;     }
    public int    getY()     { return y;     }
    public int    getAncho() { return ancho; }
    public int    getAlto()  { return alto;  }
}
