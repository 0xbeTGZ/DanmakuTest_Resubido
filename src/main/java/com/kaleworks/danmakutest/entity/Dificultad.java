package com.kaleworks.danmakutest.entity;

public enum Dificultad {

    FACIL   (0.5, 0.7, 0.8, 0.8),
    NORMAL  (1.0, 1.0, 1.0, 1.0),
    DIFICIL (1.5, 1.4, 1.2, 1.2),
    LUNATIC (2.0, 1.8, 1.4, 1.5);

    private final double multiplicadorDmgRecibido;
    private final double multiplicadorVidaEnemigos;
    private final double multiplicadorVelocidadBala;
    private final double multiplicadorSpawnRate;

    private static Dificultad actual = NORMAL;

    Dificultad(double dmgRecibido, double vidaEnemigos, double velocidadBala, double spawnRate) {
        this.multiplicadorDmgRecibido  = dmgRecibido;
        this.multiplicadorVidaEnemigos = vidaEnemigos;
        this.multiplicadorVelocidadBala = velocidadBala;
        this.multiplicadorSpawnRate    = spawnRate;
    }

    public double multiplicadorDmgRecibido()   { return multiplicadorDmgRecibido; }
    public double multiplicadorVidaEnemigos()  { return multiplicadorVidaEnemigos; }
    public double multiplicadorVelocidadBala() { return multiplicadorVelocidadBala; }
    public double multiplicadorSpawnRate()     { return multiplicadorSpawnRate; }

    public static Dificultad actual() {
        return actual;
    }

    public static void setActual(Dificultad nueva) {
        if (nueva != null) {
            actual = nueva;
        }
    }
}
