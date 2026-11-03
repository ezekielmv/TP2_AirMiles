package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Usuario> Usuarios;  
    //private Trie<Promocion> Promociones;
    //private Heap<MillasDisponibles,ID> ranking;
    private int elMasCanjeador;         //?cambiar nombre? 
    private int elMasAcumulador;        //?cambiar nombre?  
    private ListaEnlazada<Transferencia> listaParesTransferidores;


    public AirMiles() {
        this.Usuarios = new ABB<Usuario>();
        //this.Promociones = new Trie<Promocion>();
        //this.ranking = new Heap<MillasDisponibles,ID>();
        this.elMasCanjeador = 0;
        this.elMasAcumulador = 0;
        this.listaParesTransferidores = new ListaEnlazada<Transferencia>();
    }

    public void registrarUsuario(int id) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        throw new UnsupportedOperationException("Sin implementar");
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
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasCanjeador() {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int[][] paresTransferidoresExclusivos() {
        throw new UnsupportedOperationException("Sin implementar");
    }
}
