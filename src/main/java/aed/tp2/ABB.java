package aed.tp2;

import java.util.*;
// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<K extends Comparable<K>, V> {
    private Nodo raiz;
    private int cardinal;
    
    private class Nodo {
        K clave;          // clave va a ser el ID
        V valor;          // valor va a ser la clase Usuario      
        Nodo izquierda;
        Nodo derecha;
        
        Nodo(K c, V v) {
            this.clave = c;
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

    public V minimo(){
        return minimo_recursivo(this.raiz).valor;
    }

    private Nodo minimo_recursivo(Nodo arbol) {
        if (arbol.izquierda == null) {
            return arbol;
        } 
        return minimo_recursivo(arbol.izquierda);
    }


    public V maximo(){
        return maximo_recursivo(this.raiz).valor;
    }

    private Nodo maximo_recursivo(Nodo arbol){
        if (arbol.derecha == null) {
            return arbol;
        } 
        
        return maximo_recursivo(arbol.derecha);
    }

    public void insertar(K c, V v){
        if (!pertenece(c)) {
            this.raiz = insertar_recursivo(c,v, this.raiz);
            this.cardinal++;
        }
    }

    private Nodo insertar_recursivo(K c, V v, Nodo arbol) {
        if (arbol == null) return new Nodo(c,v);

        if (arbol.clave.compareTo(c) > 0) {
            arbol.izquierda = insertar_recursivo(c, v, arbol.izquierda);
        } else {
            arbol.derecha = insertar_recursivo(c, v, arbol.derecha);
        } return arbol;
    }

    public boolean pertenece(K c){
        return pertenece_recursivo(this.raiz, c);
    }

    private boolean pertenece_recursivo(Nodo arbol, K c) {
        if (arbol == null) return false; 

        if (c.compareTo(arbol.clave) == 0) {
            return true;
        } else if (c.compareTo(arbol.clave) < 0) {
            return pertenece_recursivo(arbol.izquierda, c);
        } else {
            return pertenece_recursivo(arbol.derecha, c);
        }
    }

    public V obtener(K c){
        return obtener_recursivo(this.raiz, c);
    }

    private V obtener_recursivo(Nodo arbol, K c) {
        if (arbol == null) return null;

        if (c.compareTo(arbol.clave) == 0) {
            return arbol.valor;
        } else if (c.compareTo(arbol.clave) < 0) {
            return obtener_recursivo(arbol.izquierda, c);
        } else {
            return obtener_recursivo(arbol.derecha, c);
        }
    }


    public void eliminar(K c){
        if (!pertenece(c)) return;
        this.raiz = eliminar_recursivo(this.raiz, c);
        this.cardinal--;
    }

    private Nodo eliminar_recursivo(Nodo arbol, K c) {
        if (arbol == null) return null;

        if (c.compareTo(arbol.clave) < 0) {
            arbol.izquierda = eliminar_recursivo(arbol.izquierda,c);
        } else if (c.compareTo(arbol.clave) > 0) {
            arbol.derecha = eliminar_recursivo(arbol.derecha,c);
        } else {
            if (arbol.izquierda == null && arbol.derecha == null) return null;
            if (arbol.izquierda == null) return arbol.derecha;
            if (arbol.derecha == null) return arbol.izquierda;

            Nodo minimo = minimo_recursivo(arbol.derecha);
            arbol.valor = minimo.valor;
            arbol.derecha = eliminar_recursivo(arbol.derecha, minimo.clave);
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
    
        public V siguiente() {
            Nodo actual = pila.pop();
            apilarIzquierdos(actual.derecha);
            return actual.valor;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }
}

