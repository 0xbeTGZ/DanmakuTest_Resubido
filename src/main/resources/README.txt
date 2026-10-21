============================================================
  RECURSOS DE myowndanmakutest_v0.1
============================================================

Esta carpeta se incluye en el CLASSPATH al ejecutar el juego.
Cualquier archivo aqui dentro se accede con una ruta absoluta
empezando con "/", por ejemplo:

    /sprites/jugador/reimu.png
    /sounds/sfx/disparo.wav

------------------------------------------------------------
ESTRUCTURA SUGERIDA
------------------------------------------------------------

  sprites/
    jugador/    -> spritesheets de Reimu y Marisa (32x32 frames)
    balas/      -> balasJugador.png, balasEnemigas.png (16x16 frames)
    enemigos/   -> spritesheets de enemigos basicos (24x24 frames)
    bosses/     -> bossUno.png, bossDos.png ... (64x64 frames)
    items/      -> items.png en una sola fila con todos los TipoItem
    fondos/     -> fondos de cada stage (pueden ser PNG grandes)

  sounds/
    sfx/
      disparo.wav
      impactoEnemigo.wav
      itemRecogido.wav
      bombaActivada.wav
      jugadorMuere.wav
    bgm/
      menu.wav
      stage1.wav
      boss.wav

------------------------------------------------------------
FORMATO
------------------------------------------------------------
- Imagenes: PNG (recomendado por transparencia) o JPG.
- Sonidos:  WAV PCM 16-bit, 44.1 kHz. MP3 NO funciona con el
            JDK por defecto.

------------------------------------------------------------
COMO USAR
------------------------------------------------------------
- Imagenes -> ver javadoc de CargadorImagenes.java
- Sonidos  -> ver javadoc de CargadorSonidos.java

------------------------------------------------------------
EJECUTAR EL JUEGO INCLUYENDO ESTOS RECURSOS
------------------------------------------------------------
    cd /home/user/workspace/myowndanmakutest_v0.1
    rm -rf /tmp/db && mkdir -p /tmp/db
    find src/main/java -name "*.java" > /tmp/sources.txt
    javac -d /tmp/db -source 11 -target 11 @/tmp/sources.txt
    cp -r src/main/resources/* /tmp/db/    # copiar recursos al classpath
    java -cp /tmp/db com.kaleworks.danmakutest.DanmakuTest
