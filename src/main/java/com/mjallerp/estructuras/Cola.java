package com.mjallerp.estructuras;

public class Cola {
    private int cuenta;
    private Nodo frente;
    private Nodo fin;

    public Cola() {
        cuenta = 0;
        frente = null;
        fin = null;
    }

    public boolean esVacia() {
        return cuenta == 0;
    }

    public void encolar(Object dato) throws Exception {
        if (dato == null) {
            throw new Exception("El dato no puede ser nulo");
        }
        Nodo nuevo = new Nodo(dato);
        if (esVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setDerecha(nuevo);
            nuevo.setIzquierda(fin);
            fin = nuevo;
        }
        ++cuenta;
    }

    public Object desencolar() throws Exception {
        if (esVacia()) {
            throw new Exception("La cola está vacía");
        }
        Object dato = frente.getDato();
        frente = frente.getDerecha();
        if (frente != null) {
            frente.setIzquierda(null);
        } else {
            fin = null;
        }
        --cuenta;
        return dato;
    }

    public Object peek() throws Exception {
        if (esVacia()) {
            throw new Exception("La cola está vacía");
        }
        return frente.getDato();
    }

    public int tamanio() {
        return cuenta;
    }

    public boolean contiene(Object dato) {
        Nodo actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getDerecha();
        }
        return false;
    }

    public void limpiar() {
        frente = null;
        fin = null;
        cuenta = 0;
    }

    public void mostrar() {
        if (esVacia()) {
            System.out.println("  (cola vacía)");
            return;
        }
        Nodo actual = frente;
        int turno = 1;
        while (actual != null) {
            System.out.println("  " + turno + ". " + actual.getDato());
            actual = actual.getDerecha();
            turno++;
        }
    }
}
