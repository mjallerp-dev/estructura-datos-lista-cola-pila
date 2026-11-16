package com.mjallerp.estructuras;

public class Nodo {
    private Object dato;
    private Nodo izquierda;
    private Nodo derecha;

    public Nodo() {
    }

    public Nodo(Object dato) {
        this.dato = dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public void setIzquierda(Nodo vecino) {
        this.izquierda = vecino;
    }

    public void setDerecha(Nodo vecino) {
        this.derecha = vecino;
    }

    public Object getDato() {
        return this.dato;
    }

    public Nodo getIzquierda() {
        return this.izquierda;
    }

    public Nodo getDerecha() {
        return this.derecha;
    }
}
