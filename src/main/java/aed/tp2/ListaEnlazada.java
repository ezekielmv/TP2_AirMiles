package aed.tp2;

public class ListaEnlazada<T> {
    // Completar atributos privados
    private Nodo first;
    private Nodo last;
    private int cantidad;

    public class Nodo {
        T valor;
        Nodo siguiente = null;
        Nodo anterior = null;

        Nodo(T v) {valor = v;}
    }

    public ListaEnlazada() {
        this.first = null;
        this.last = null;
        this.cantidad = 0;
    }

    public int longitud() {
        return this.cantidad;
    }

    public void agregarAtras(T elem) {
        agregarAtrasYDevolverNodo(elem);
    }

    public T obtener(int i) {
        Nodo actual = this.first;
        
        for (int j = 0; j < i; j++){
            actual = actual.siguiente;
        }
        return actual.valor;
}

    /* Metodo para agregar al final y retornar el Nodo creado */
    public Nodo agregarAtrasYDevolverNodo(T elem) {
        Nodo nodo = new Nodo(elem);
        if (this.last == null) {
            this.last = nodo;
            if (this.first == null) {
                this.first = nodo;
            }
        } else {
            this.last.siguiente = nodo;
            nodo.anterior = this.last;
            this.last = nodo;
        }
        this.cantidad++;
        return nodo;
    }

    /* Metodo para eliminar un nodo específico en O(1) */
    public void eliminar(Nodo nodo) {
        if (nodo == null) return;

        if (nodo.anterior != null) {
            nodo.anterior.siguiente = nodo.siguiente;
        } else {
            this.first = nodo.siguiente; // Era el primer nodo
        }

        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        } else {
            this.last = nodo.anterior; // Era el último nodo
        }

        nodo.anterior = null;
        nodo.siguiente = null;
        this.cantidad--;
    }
    

	public class ListaIterador{
    	private Nodo actual;

        public ListaIterador() {
            this.actual = first;
        }

        public boolean haySiguiente() {
	        return actual != null;
        }

        public T siguiente() {
            T valor = actual.valor;
            actual = actual.siguiente;
            return valor;
        }
    }
	public ListaIterador iterador() {
	    return new ListaIterador();
    }
	
}
