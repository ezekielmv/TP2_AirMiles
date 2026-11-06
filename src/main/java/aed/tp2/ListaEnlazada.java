package aed.tp2;

public class ListaEnlazada<T> {
    // Completar atributos privados
    private Nodo first;
    private Nodo last;
    private int cantidad;

    private class Nodo {
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

    public void agregarAdelante(T elem) {
        Nodo nodo = new Nodo(elem);
        
        if (this.first == null && this.last == null){
            this.first = nodo;
            this.last = nodo;
        } else {
            this.first.anterior = nodo;
            nodo.siguiente = this.first;
            this.first = nodo;
        }
        this.cantidad++;
    }

    public void agregarAtras(T elem) {
        Nodo nodo = new Nodo(elem);
        
        if (this.last == null){
            this.last = nodo;
            if (this.first == null){
                this.first = nodo;
            }
        } else {
            this.last.siguiente = nodo;
            nodo.anterior = this.last;
            this.last = nodo;
        }
        this.cantidad++;
    }

    public T obtener(int i) {
        Nodo actual = this.first;
        
        for (int j = 0; j < i; j++){
            actual = actual.siguiente;
        }
        return actual.valor;
}

    public void eliminar(int i) {
        if (i == 0) {
            this.first = this.first.siguiente;
        } else if (i == this.cantidad - 1) {
            this.last= this.last.anterior;

        } else {
            Nodo actual = this.first;
            for (int j = 0; j < i - 1; j++){
                actual = actual.siguiente;
            }
            actual.siguiente = actual.siguiente.siguiente;
        }
        this.cantidad--;
    }

    public void modificarPosicion(int indice, T elem) {        
        Nodo actual = this.first;
        for (int i = 0; i < indice; i++){
            actual = actual.siguiente;
        }
        actual.valor = elem;
    }


    public ListaEnlazada(ListaEnlazada<T> lista) {
        for (int i = 0; i<lista.cantidad; i++){
            this.agregarAtras(lista.obtener(i));
        }
    }
    
    @Override
    public String toString() {
        Nodo nodo = this.first;

        String str = "[";

        while (nodo != null) {
            str += nodo.valor;
            if (nodo.siguiente != null){
                str += ", ";
            }
            nodo = nodo.siguiente;
        }
        str += "]";

        return str;
    }

    public class ListaIterador{
    	private int dedito;
        private ListaEnlazada<T> lista;

        public ListaIterador(ListaEnlazada<T> lista) {
            this.lista = lista;
            this.dedito = 0;
        }

        public boolean haySiguiente() {
	        return (lista.cantidad != this.dedito);
        }
        
        public boolean hayAnterior() {
	        return dedito > 0;
        }

        public T siguiente() {
            if (haySiguiente()){
                int i = this.dedito;
                this.dedito++;
                return lista.obtener(i);
            }
            return null;
        }
        

        public T anterior() {
	        if (hayAnterior()){
                int i = this.dedito;
                this.dedito--;
                return lista.obtener(i-1);
            }
            return  null;
        }
    }

    public ListaIterador iterador() {
	    return new ListaIterador(this);
    }

}
