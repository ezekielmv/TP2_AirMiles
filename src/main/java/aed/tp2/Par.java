package aed.tp2;

public class Par {
    private final Usuario usuario_a;
    private final Usuario usuario_b;
    private ListaEnlazada<Par>.Nodo nodoEnLista;
    // Tener ListaEnlazada<Par>.Nodo nos ayuda a referencias al nodo en la lista de pares, para poder eliminarlo en O(1) cuando se necesite.


    public Par(Usuario a, Usuario b) {
        // Guardamos los usuarios manteniendo un orden por ID (menor id primero)
        if (a.id() < b.id()) {
            this.usuario_a = a;
            this.usuario_b = b;
        } else {
            this.usuario_a = b;
            this.usuario_b = a;
        }
    }

    public Usuario usuarioA() { return usuario_a; }

    public Usuario usuarioB() { return usuario_b; }

    public int idA() {return usuario_a.id(); }

    public int idB() { return usuario_b.id(); }

    public void asignarNodo(ListaEnlazada<Par>.Nodo nodo) {
        this.nodoEnLista = nodo;
    }

    public ListaEnlazada<Par>.Nodo nodoEnLista() {
        return nodoEnLista;
    }

}