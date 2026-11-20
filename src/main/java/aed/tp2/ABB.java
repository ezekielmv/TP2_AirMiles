package aed.tp2;

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
  
}

