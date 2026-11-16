package com.mjallerp.pruebas;

import com.mjallerp.estructuras.Pila;

public class PruebaPila {
    public static void main(String[] args) {
        try {
            Pila pila = new Pila();
            pila.apilar("Ana");
            pila.apilar("Carlos");
            pila.apilar("Laura");

            System.out.println("Embarcados en la pila: " + pila.tamanio());
            pila.mostrar();
            System.out.println("Cima (peek): " + pila.peek());
            System.out.println("Ana está en la posición " + pila.buscar("Ana") + " desde la cima");
            System.out.println("¿Contiene Jorge? " + pila.contiene("Jorge") + " (buscar = " + pila.buscar("Jorge") + ")");

            System.out.println("Deshaciendo: " + pila.desapilar());
            System.out.println("Deshaciendo: " + pila.desapilar());

            System.out.println("Embarcados en la pila: " + pila.tamanio());
            System.out.println("Cima actual: " + pila.peek());

            pila.limpiar();
            System.out.println("¿Vacía después de limpiar? " + pila.esVacia());
            pila.desapilar();
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
