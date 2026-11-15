package com.club.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Turnos {

    public static final int DURACION = 90;           // minutos de cada turno
    private static final int APERTURA = 8 * 60;      // 8:00
    private static final int CIERRE = 23 * 60;       // 23:00

    public static List<LocalTime> inicios() {
        List<LocalTime> lista = new ArrayList<>();
        int minuto = APERTURA;
        while (minuto + DURACION <= CIERRE) {
            lista.add(LocalTime.of(minuto / 60, minuto % 60));
            minuto += DURACION;
        }
        return lista;
    }

    public static LocalTime fin(LocalTime inicio) {
        return inicio.plusMinutes(DURACION);
    }
}