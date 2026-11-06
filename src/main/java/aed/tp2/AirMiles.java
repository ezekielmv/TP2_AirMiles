package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Integer,Usuario> usuarios;   
    //private Trie<Promocion> Promociones;
    private MaxHeap<Double> ranking;
    private int elMasCanjeador;         //?cambiar nombre? 
    private int elMasAcumulador;        //?cambiar nombre?  
    private ListaEnlazada<Transferencia> listaParesTransferidores;


    public AirMiles() {
        this.usuarios = new ABB<Integer,Usuario>();
        this.Promociones = new Trie<Promocion>();
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
        usuarios.insertar(nuevoUsuario.id(), nuevoUsuario);      //Complejiad O(log n) por el ABB la cosa es verificar si la complejidad es la correcta.
        //lo envuelve en la subclase para...
        UsuarioRankeable usuarioRankeable = new UsuarioRankeable(nuevoUsuario);
        //...asignarle un handle, generado en el maxheap
        nuevoUsuario.asignarHandle(ranking.agregar(usuarioRankeable));

    }

    public void registrarUsuarioConReferidor(int id, int ref) {

        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }
        Usuario nuevoUsuario = new Usuario(id);
        nuevoUsuario.millasDisponibles = 500;
        usuarios.insertar(nuevoUsuario.id(), nuevoUsuario);
        
        Usuario usuarioReferidor = usuarios.obtener(ref);
        usuarioReferidor.millasDisponibles += 500;
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
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {
        // Hay que actualizar, millasDisponibles, MillasTotales y MillasAcumuladas.
        // Hay que actualizar tambien el Heap.
    }

    private double calculaMillasYActualizaPromo (Usuario usuario, double distancia, String nombrePromo) {
        Promocion datosPromo = Promociones.buscar(nombrePromo);
        double factorPromo = datosPromo.factor();
        double millas = usuario.factorCategoria() * distancia * factorPromo;
        double nuevoAcumuladoPromo = datosPromo.acumulado();

        if (datosPromo.tieneTope()){ // tiene tope
            nuevoAcumuladoPromo = nuevoAcumuladoPromo + millas;
            if (nuevoAcumuladoPromo > datosPromo.tope()) {           // supera lo que puede cambiar, no usa la promo
                factorPromo = 1;
            }
            else {
                if (nuevoAcumuladoPromo == datosPromo.tope()) {     // usa la promo, llega al tope asi que tiene que eliminarla
                    Promociones.eliminar(nombrePromo);
                }
                else {                                             // no llega al tope, usa la promo asi que solo actualiza el acumulado de la promo
                    nuevoAcumuladoPromo += millas;
                    datosPromo._acumulado = nuevoAcumuladoPromo;
                }
            }
        }

        millas = usuario.factorCategoria() * distancia * factorPromo;

        return millas;
    }

    public void canjearMillas(int id, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public double consultarSaldo(int id) {
        Usuario dataUsuario = usuarios.obtener(id);
        double saldo = dataUsuario.millasDisponibles;
        return saldo;
    }

    public String consultarCategoria(int id) {
       throw new UnsupportedOperationException("Sin implementar");
    }

    public int[] rankingMillas(int n) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {
        Promocion nuevaPromo = new Promocion(nombre, factor,0);
        Promociones.insertar(nombre,nuevaPromo);
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        Promocion nuevaPromo = new Promocion(nombre, factor, tope);
        Promociones.insertar(nombre,nuevaPromo);
    }

    public void finalizarPromocion(String nombre) {
        Promociones.eliminar(nombre);
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
