package com.mjallerp.pruebas;

import com.mjallerp.estructuras.Cola;

public class PruebaCola {
    public static void main(String[] args) {
        try {
            Cola cola = new Cola();
            cola.encolar("Ana");
            cola.encolar("Carlos");
            cola.encolar("Laura");

            System.out.println("Pasajeros en la cola: " + cola.tamanio());
            cola.mostrar();
            System.out.println("Frente (peek): " + cola.peek());
            System.out.println("¿Contiene Laura? " + cola.contiene("Laura"));

            System.out.println("Embarcando: " + cola.desencolar());
            System.out.println("Embarcando: " + cola.desencolar());

            System.out.println("Pasajeros en la cola: " + cola.tamanio());
            System.out.println("Frente actual: " + cola.peek());

            cola.limpiar();
            System.out.println("¿Vacía después de limpiar? " + cola.esVacia());
            cola.desencolar();
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}
