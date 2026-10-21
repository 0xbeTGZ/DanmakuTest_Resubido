package com.kaleworks.danmakutest.escenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StageManager {

    private final List<EventoProgramado> agenda;

    private int cursor;

    public StageManager() {
        this.agenda = new ArrayList<>();
        this.cursor = 0;
    }

    public void programar(int frameDisparo, EventoStage evento) {
        agenda.add(new EventoProgramado(frameDisparo, evento));

        Collections.sort(agenda, Comparator.comparingInt(e -> e.frame));
    }

    public void tickear(StagePlantilla stage, int frameActual) {
        while (cursor < agenda.size() && agenda.get(cursor).frame <= frameActual) {
            agenda.get(cursor).evento.ejecutar(stage);
            cursor++;
        }
    }

    public void reiniciar() {
        cursor = 0;
    }

    public boolean agendaTerminada() {
        return cursor >= agenda.size();
    }

    private static class EventoProgramado {
        final int frame;
        final EventoStage evento;

        EventoProgramado(int frame, EventoStage evento) {
            this.frame = frame;
            this.evento = evento;
        }
    }
}
