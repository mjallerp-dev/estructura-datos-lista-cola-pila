package com.mjallerp.estructuras;

public class Lista {
    private int cuenta;
    private Nodo primero;
    private Nodo ultimo;

    public Lista() {
        cuenta = 0;
        primero = null;
        ultimo = null;
    }

    public boolean esVacia() {
        return cuenta == 0;
    }

    public void agregar(Object dato) throws Exception {
        if (dato == null) {
            throw new Exception("El dato no puede ser nulo");
        }
        Nodo objetivo = new Nodo(dato);
        if (esVacia()) {
            objetivo.setIzquierda(null);
            objetivo.setDerecha(null);
            primero = objetivo;
            ultimo = objetivo;
        } else {
            ultimo.setDerecha(objetivo);
            objetivo.setIzquierda(ultimo);
            objetivo.setDerecha(null);
            ultimo = objetivo;
        }
        ++cuenta;
    }

    public void agregarAlInicio(Object dato) throws Exception {
        if (dato == null) {
            throw new Exception("El dato no puede ser nulo");
        }
        if (esVacia()) {
            agregar(dato);
            return;
        }
        Nodo objetivo = new Nodo(dato);
        objetivo.setIzquierda(null);
        objetivo.setDerecha(primero);
        primero.setIzquierda(objetivo);
        primero = objetivo;
        ++cuenta;
    }

    public void agregarEnPosicion(int indice, Object dato) throws Exception {
        if (dato == null) {
            throw new Exception("El dato no puede ser nulo");
        }
        if (indice < 0 || indice > cuenta) {
            throw new Exception("La posición " + indice + " está fuera del rango 0 - " + cuenta);
        }
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        if (indice == cuenta) {
            agregar(dato);
            return;
        }
        Nodo actual = buscar(indice);
        Nodo anterior = actual.getIzquierda();
        Nodo objetivo = new Nodo(dato);
        objetivo.setIzquierda(anterior);
        objetivo.setDerecha(actual);
        anterior.setDerecha(objetivo);
        actual.setIzquierda(objetivo);
        ++cuenta;
    }

    public Object eliminarPrimero() throws Exception {
        if (esVacia()) {
            throw new Exception("La lista está vacía");
        }
        Object dato = primero.getDato();
        if (cuenta == 1) {
            primero = null;
            ultimo = null;
        } else {
            primero = primero.getDerecha();
            primero.setIzquierda(null);
        }
        --cuenta;
        return dato;
    }

    public Object eliminarUltimo() throws Exception {
        if (esVacia()) {
            throw new Exception("La lista está vacía");
        }
        Object dato = ultimo.getDato();
        if (cuenta == 1) {
            primero = null;
            ultimo = null;
        } else {
            ultimo = ultimo.getIzquierda();
            ultimo.setDerecha(null);
        }
        --cuenta;
        return dato;
    }

    public Object eliminarEnPosicion(int indice) throws Exception {
        Nodo objetivo = buscar(indice);
        if (objetivo == primero) {
            return eliminarPrimero();
        }
        if (objetivo == ultimo) {
            return eliminarUltimo();
        }
        Nodo anterior = objetivo.getIzquierda();
        Nodo siguiente = objetivo.getDerecha();
        anterior.setDerecha(siguiente);
        siguiente.setIzquierda(anterior);
        objetivo.setIzquierda(null);
        objetivo.setDerecha(null);
        --cuenta;
        return objetivo.getDato();
    }

    private Nodo buscar(int indice) throws Exception {
        if (esVacia()) {
            throw new Exception("La lista está vacía");
        }
        if (indice < 0 || indice >= cuenta) {
            throw new Exception("La posición " + indice + " está fuera del rango 0 - " + (cuenta - 1));
        }
        Nodo actual = primero;
        for (int i = 0; i < cuenta; i++) {
            if (indice == i) {
                break;
            }
            actual = actual.getDerecha();
        }
        return actual;
    }

    private int buscar(Object dato) throws Exception {
        if (esVacia()) {
            throw new Exception("La lista está vacía");
        }
        Nodo actual = primero;
        for (int i = 0; i < cuenta; i++) {
            if (actual.getDato().equals(dato)) {
                return i;
            }
            actual = actual.getDerecha();
        }
        throw new Exception("El elemento no está en la lista");
    }

    public Object buscarDato(int indice) throws Exception {
        Nodo objetivo = buscar(indice);
        return objetivo.getDato();
    }

    public int buscarDato(Object dato) throws Exception {
        return buscar(dato);
    }

    public boolean contiene(Object dato) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getDerecha();
        }
        return false;
    }

    public int cuentaElementos() {
        return cuenta;
    }

    public void limpiar() {
        primero = null;
        ultimo = null;
        cuenta = 0;
    }

    public void mostrarAdelante() {
        if (esVacia()) {
            System.out.println("  (lista vacía)");
            return;
        }
        Nodo actual = primero;
        int i = 0;
        while (actual != null) {
            System.out.println("  [" + i + "] " + actual.getDato());
            actual = actual.getDerecha();
            i++;
        }
    }

    public void mostrarAtras() {
        if (esVacia()) {
            System.out.println("  (lista vacía)");
            return;
        }
        Nodo actual = ultimo;
        int i = cuenta - 1;
        while (actual != null) {
            System.out.println("  [" + i + "] " + actual.getDato());
            actual = actual.getIzquierda();
            i--;
        }
    }
}
