package com.mjallerp.pruebas;

import com.mjallerp.estructuras.Lista;

public class PruebaLista {
    public static void main(String[] args) {
        Lista lista = new Lista();
        try {
            System.out.println("Elementos: " + lista.cuentaElementos());
            lista.agregar("AV9301");
            lista.agregar("LA4052");
            lista.agregarAlInicio("CM0805");
            lista.agregarEnPosicion(1, "AV0120");
            System.out.println("Después de agregar, agregarAlInicio y agregarEnPosicion(1): " + lista.cuentaElementos());
            lista.mostrarAdelante();
            System.out.println("Recorrido hacia atrás:");
            lista.mostrarAtras();

            System.out.println("En la posición 2: " + lista.buscarDato(2));
            System.out.println("LA4052 está en la posición: " + lista.buscarDato("LA4052"));
            System.out.println("¿Contiene AV9301? " + lista.contiene("AV9301"));
            System.out.println("¿Contiene XX0000? " + lista.contiene("XX0000"));

            System.out.println("eliminarPrimero: " + lista.eliminarPrimero());
            System.out.println("eliminarUltimo: " + lista.eliminarUltimo());
            System.out.println("eliminarEnPosicion(1): " + lista.eliminarEnPosicion(1));
            lista.mostrarAdelante();

            lista.limpiar();
            System.out.println("Después de limpiar: " + lista.cuentaElementos());
            System.out.println(lista.buscarDato(0));
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
