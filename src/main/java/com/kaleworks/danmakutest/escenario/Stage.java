package com.kaleworks.danmakutest.escenario;
import java.awt.Graphics;

public interface Stage {

    public void actualizar(boolean arriba, boolean abajo, boolean izquierda, boolean derecha,
                           boolean focus, boolean disparando, boolean bombaPulso);

    public void dibujar(Graphics g);

    public default boolean jugadorEstaMuerto() {
        return false;
    }
}