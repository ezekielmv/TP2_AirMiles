package aed.tp2;

import java.util.*;
// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> {
    private Nodo raiz;
    private int cardinal;
    
    private class Nodo {
        T valor;
        Nodo izquierda;
        Nodo derecha;
        
        Nodo(T v) {
            this.valor = v;
            this.izquierda = null;
            this.derecha = null;
        }
    }

    public ABB() {
        this.raiz = null;       // Nodo raiz nulo
        this.cardinal = 0;      // Iniciamos con cero elementos
    }

    public int cardinal() {
        return this.cardinal;
    }

    public T minimo(){
        return minimo_recursivo(this.raiz).valor;
    }

    private Nodo minimo_recursivo(Nodo arbol) {
        if (arbol.izquierda == null) {
            return arbol;
        } 
        return minimo_recursivo(arbol.izquierda);
    }


    public T maximo(){
        return maximo_recursivo(this.raiz).valor;
    }

    private Nodo maximo_recursivo(Nodo arbol){
        if (arbol.derecha == null) {
            return arbol;
        } 
        
        return maximo_recursivo(arbol.derecha);
    }

    public void insertar(T elem){
        if (!pertenece(elem)) {
            this.raiz = insertar_recursivo(elem, this.raiz);
            this.cardinal++;
        }
    }

    private Nodo insertar_recursivo(T elem, Nodo arbol) {
        if (arbol == null) return new Nodo(elem);

        if (arbol.valor.compareTo(elem) > 0) {
            arbol.izquierda = insertar_recursivo(elem, arbol.izquierda);
        } else {
            arbol.derecha = insertar_recursivo(elem, arbol.derecha);
        } return arbol;
    }

    public boolean pertenece(T elem){
        return pertenece_recursivo(this.raiz, elem);
    }

    private boolean pertenece_recursivo(Nodo arbol, T elemento) {
        if (arbol == null) return false; 

        if (elemento.compareTo(arbol.valor) == 0) {
            return true;
        } else if (elemento.compareTo(arbol.valor) < 0) {
            return pertenece_recursivo(arbol.izquierda, elemento);
        } else {
            return pertenece_recursivo(arbol.derecha, elemento);
        }
    }

    public void eliminar(T elem){
        if (!pertenece(elem)) return;
        this.raiz = eliminar_recursivo(this.raiz, elem);
        this.cardinal--;
    }

    private Nodo eliminar_recursivo(Nodo arbol, T elem) {
        if (arbol == null) return null;

        if (elem.compareTo(arbol.valor) < 0) {
            arbol.izquierda = eliminar_recursivo(arbol.izquierda,elem);
        } else if (elem.compareTo(arbol.valor) > 0) {
            arbol.derecha = eliminar_recursivo(arbol.derecha,elem);
        } else {
            if (arbol.izquierda == null && arbol.derecha == null) return null;
            if (arbol.izquierda == null) return arbol.derecha;
            if (arbol.derecha == null) return arbol.izquierda;

            Nodo minimo = minimo_recursivo(arbol.derecha);
            arbol.valor = minimo.valor;
            arbol.derecha = eliminar_recursivo(arbol.derecha, minimo.valor);
        }
        return arbol;
    }

public String toString() {
        StringBuilder resBuilder = new StringBuilder("{");
        ABB_Iterador iter = this.iterador();
        while (iter.haySiguiente()) {
            resBuilder.append(iter.siguiente());
            if (iter.haySiguiente()) {
                resBuilder.append(",");
            }
        }
        resBuilder.append("}");
        return resBuilder.toString();
        }

    public class ABB_Iterador {
        private Stack<Nodo> pila;

        public ABB_Iterador() {
            pila = new Stack<>();
            apilarIzquierdos(raiz);
        }

        private void apilarIzquierdos(Nodo n) {
            while (n != null) {
                pila.push(n);
                n = n.izquierda;
            }
        }

        public boolean haySiguiente() {            
            return !pila.isEmpty();
        }
    
        public T siguiente() {
            Nodo actual = pila.pop();
            apilarIzquierdos(actual.derecha);
            return actual.valor;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }
}
