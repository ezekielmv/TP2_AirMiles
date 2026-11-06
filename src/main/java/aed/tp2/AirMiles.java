package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Usuario> usuarios;  
    //private Trie<Promocion> Promociones;
    private MaxHeap<Double> ranking;
    private int elMasCanjeador;         //?cambiar nombre? 
    private int elMasAcumulador;        //?cambiar nombre?  
    private ListaEnlazada<Transferencia> listaParesTransferidores;


    public AirMiles() {
        this.usuarios = new ABB<Usuario>();
        //this.Promociones = new Trie<Promocion>();
        this.ranking = new MaxHeap<UsuarioRankeable>(10);
        this.mayorCanjeador = null;
        this.mayorAcumulador = null;
        this.listaParesTransferidores = new ListaEnlazada<Transferencia>();
    }


    public void registrarUsuario(int id) {
        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }
        /*Por requiere sabemos que el usuario no existe en el árbol */
        Usuario nuevoUsuario = new Usuario(id);
        this.usuarios.insertar(nuevoUsuario);       //Complejiad O(log n) por el ABB la cosa es verificar si la complejidad es la correcta.
        //lo envuelve en la subclase para...
        UsuarioRankeable usuarioRankeable = new UsuarioRankeable(nuevoUsuario);
        //...asignarle un handle, generado en el maxheap
        nuevoUsuario.asignarHandle(ranking.agregar(usuarioRankeable));

    }

    public void registrarUsuarioConReferidor(int id, int ref) {

        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }
        Usuario referidor = usuarios.obtener(new Usuario(ref, 0, 0, 0));
         //lo envuelve en la subclase para...
        UsuarioRankeable usuarioRankeable = new UsuarioRankeable(nuevoUsuario);
        //...asignarle un handle, generado en el maxheap
        nuevoUsuario.asignarHandle(ranking.agregar(usuarioRankeable));
        /*falta agregarlo al abb --> se agrega el nuevoUsuario, no el rankeable!¡  
        falta sumarle al ref las 500 disponibles (creo que sólo disponibles, no totales
        --> no debería subir de categoria al referir)
        */
        // La especificación garantiza que el referidor existe.
        // Esta idea funciona porque hacemos el compareTo de Usuario solo con el id, entonces podemos crear un Usuario temporal con el id del referidor y buscarlo en el ABB.

        referidor.acumularMillas(500); // Acumula 500 millas por referir a un nuevo usuario
        this.usuarios.insertar(new Usuario(id, 500, 0, 500));
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void canjearMillas(int id, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public double consultarSaldo(int id) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public String consultarCategoria(int id) {
       throw new UnsupportedOperationException("Sin implementar");
    }

    public int[] rankingMillas(int n) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void finalizarPromocion(String nombre) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasAcumulador() {
        return this.elMasAcumulador;
    }

    public int elMasCanjeador() {
        return this.elMasCanjeador;
    }

    public int[][] paresTransferidoresExclusivos() {
        return new int[0][];
    }
}
