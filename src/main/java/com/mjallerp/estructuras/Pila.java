package com.mjallerp.estructuras;

public class Pila {
    private int cuenta;
    private Nodo cima;

    public Pila() {
        cuenta = 0;
        cima = null;
    }

    public boolean esVacia() {
        return cuenta == 0;
    }

    public void apilar(Object dato) throws Exception {
        if (dato == null) {
            throw new Exception("El dato no puede ser nulo");
        }
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            cima = nuevo;
        } else {
            nuevo.setIzquierda(cima);
            cima.setDerecha(nuevo);
            cima = nuevo;
        }
        ++cuenta;
    }

    public Object desapilar() throws Exception {
        if (esVacia()) {
            throw new Exception("La pila está vacía");
        }
        Object dato = cima.getDato();
        cima = cima.getIzquierda();
        if (cima != null) {
            cima.setDerecha(null);
        }
        --cuenta;
        return dato;
    }

    public Object peek() throws Exception {
        if (esVacia()) {
            throw new Exception("La pila está vacía");
        }
        return cima.getDato();
    }

    public int tamanio() {
        return cuenta;
    }

    public boolean contiene(Object dato) {
        return buscar(dato) != -1;
    }

    public int buscar(Object dato) {
        Nodo actual = cima;
        int posicion = 1;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return posicion;
            }
            actual = actual.getIzquierda();
            posicion++;
        }
        return -1;
    }

    public void limpiar() {
        cima = null;
        cuenta = 0;
    }

    public void mostrar() {
        if (esVacia()) {
            System.out.println("  (pila vacía)");
            return;
        }
        Nodo actual = cima;
        int posicion = 1;
        while (actual != null) {
            System.out.println("  " + posicion + (posicion == 1 ? " (cima). " : ". ") + actual.getDato());
            actual = actual.getIzquierda();
            posicion++;
        }
    }
}
