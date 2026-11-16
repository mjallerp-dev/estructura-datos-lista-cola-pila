package com.mjallerp;

import com.mjallerp.consola.LectorConsola;
import com.mjallerp.consola.MenuAeropuerto;
import com.mjallerp.datos.DatosEjemplo;
import com.mjallerp.sistema.SistemaAeropuerto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LectorConsola lector = new LectorConsola(scanner);
        SistemaAeropuerto sistema = new SistemaAeropuerto();

        if (lector.confirmar("¿Desea cargar 8 pasajeros de ejemplo?")) {
            try {
                DatosEjemplo.cargar(sistema);
                System.out.println("Se cargaron " + sistema.totalRegistrados() + " pasajeros de ejemplo.");
            } catch (Exception e) {
                System.out.println("No se pudieron cargar los datos de ejemplo: " + e.getMessage());
            }
        }
        new MenuAeropuerto(lector, sistema).mostrar();
    }
}
