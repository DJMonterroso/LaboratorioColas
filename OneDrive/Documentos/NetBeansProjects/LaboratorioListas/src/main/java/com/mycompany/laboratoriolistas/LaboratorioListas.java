package com.mycompany.laboratoriolistas; 

import java.util.NoSuchElementException;

public class LaboratorioListas {

    public static void main(String[] args) {
        
        System.out.println("--- PRUEBAS LISTA SIMPLE ---");
        ListaSimple<Integer> ls = new ListaSimple<>();
        ls.agregarInicio(10);
        ls.agregarInicio(5);
        System.out.println("Lista: " + ls.imprimir() + ", size=" + ls.size());
        
        ls.agregarFinal(20);
        System.out.println("Lista tras agregar final: " + ls.imprimir());
        
        System.out.println("Buscar 10: " + ls.buscarValor(10));
        System.out.println("Buscar 99: " + ls.buscarValor(99));
        
        System.out.println("Removido: " + ls.removerInicio() + " -> " + ls.imprimir());
        System.out.println("Removido: " + ls.removerInicio());
        System.out.println("Removido: " + ls.removerInicio());
        
        // forzando el error de lista vacia
        try {
            ls.removerInicio();
        } catch (NoSuchElementException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n--- PRUEBAS LISTA DOBLE ---");
        DobleLista<String> ld = new DobleLista<>();
        ld.agregarFinal("A");
        ld.agregarFinal("B");
        ld.agregarInicio("Z");
        System.out.println("Lista Doble: " + ld.imprimir());
        
        System.out.println("Removido final: " + ld.removerFinal() + " -> " + ld.imprimir());
        System.out.println("Removido final: " + ld.removerFinal());
        System.out.println("Removido final: " + ld.removerFinal());
        
        try {
            ld.removerFinal();
        } catch (NoSuchElementException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println("\n--- PRUEBAS PILA ---");
        Pila<Integer> pila = new Pila<>();
        pila.push(1);
        pila.push(2);
        pila.push(3);
        
        System.out.println("Peek (tope): " + pila.peek());
        System.out.println("Pop: " + pila.pop());
        System.out.println("Pop: " + pila.pop());
        System.out.println("Pop: " + pila.pop()); // se vacia
        
        System.out.println("Vacia? " + pila.isEmpty());
        
        try {
            pila.pop();
        } catch (NoSuchElementException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
    }
}


// Clases de los nodos
class Nodo<T> {
    T dato;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

class NodoDoble<T> {
    T dato;
    NodoDoble<T> siguiente;
    NodoDoble<T> anterior;

    public NodoDoble(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}


// Implementacion Lista Simple
class ListaSimple<T> {
    private Nodo<T> cabeza;
    private Nodo<T> cola; // guardo la cola para hacer el agregarFinal mas rapido (O(1))
    private int tamano;

    public ListaSimple() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    public void agregarInicio(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }
        tamano++;
    }

    public void agregarFinal(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo; // movemos el puntero al nuevo ultimo
        }
        tamano++;
    }

    public T removerInicio() {
        if (cabeza == null) {
            throw new NoSuchElementException("Lista vacia");
        }
        T temp = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamano--;
        
        if (cabeza == null) {
            cola = null; // si se vacio, reseteamos la cola tambien
        }
        return temp;
    }

    public boolean buscarValor(T valor) {
        Nodo<T> aux = cabeza;
        while (aux != null) {
            if (aux.dato.equals(valor)) {
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }

    public int size() {
        return tamano;
    }

    public String imprimir() {
        if (cabeza == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        Nodo<T> aux = cabeza;
        while (aux != null) {
            sb.append(aux.dato);
            if (aux.siguiente != null) {
                sb.append(" -> ");
            }
            aux = aux.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}


// Implementacion Lista Doble
class DobleLista<T> {
    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola;
    private int tamano;

    public DobleLista() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    public void agregarInicio(T valor) {
        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        }
        tamano++;
    }

    public void agregarFinal(T valor) {
        NodoDoble<T> nuevo = new NodoDoble<>(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
        tamano++;
    }

    public T removerFinal() {
        if (cola == null) {
            throw new NoSuchElementException("Lista vacia");
        }
        T temp = cola.dato;
        
        // caso donde solo hay un nodo
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        } else {
            cola = cola.anterior;
            cola.siguiente = null; // cortamos el enlace
        }
        tamano--;
        return temp;
    }

    public int size() {
        return tamano;
    }

    public String imprimir() {
        if (cabeza == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        NodoDoble<T> aux = cabeza;
        while (aux != null) {
            sb.append(aux.dato);
            if (aux.siguiente != null) {
                sb.append(" <-> ");
            }
            aux = aux.siguiente;
        }
        sb.append("]");
        return sb.toString();
    }
}


// Implementacion Pila
class Pila<T> {
    private Nodo<T> tope; // usamos la logica de LIFO por la cabeza
    private int tamano;

    public Pila() {
        tope = null;
        tamano = 0;
    }

    public void push(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Pila vacia");
        }
        T temp = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return temp;
    }

    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Pila vacia");
        }
        return tope.dato;
    }

    public boolean isEmpty() {
        return tope == null;
    }

    public int size() {
        return tamano;
    }
}