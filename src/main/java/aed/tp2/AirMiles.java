package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Integer,Usuario> usuarios;   
    private Trie<Promocion> Promociones;
    private MaxHeap<Double> ranking;
    private Usuario mayorCanjeador;
    private Usuario mayorAcumulador;    
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
        registrarNuevoUsuario(id);
    }

    private void registrarNuevoUsuario(int id){
        
        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }

        Usuario nuevoUsuario = new Usuario(id);
        usuarios.insertar(nuevoUsuario.id(), nuevoUsuario);
        nuevoUsuario.recibirBonificacion(500);
        chequearYActualizarMayorAcumulador(nuevoUsuario);

        UsuarioRankeable usuarioRankeable = new UsuarioRankeable(nuevoUsuario);
        nuevoUsuario.asignarHandle(ranking.agregar(usuarioRankeable));
    }
    
    private void chequearYActualizarMayorAcumulador(Usuario usuario){
        if(usuario.superaAlMasAcumulador(mayorAcumulador)){
            mayorAcumulador = usuario;
        }
    }
    
    public void registrarUsuarioConReferidor(int id, int ref) { 
        registrarNuevoUsuario(id);
        bonificarReferidor(ref);
    }
    
    private void bonificarReferidor(int ref){

        Usuario usuarioReferidor = usuarios.obtener(ref);
        if (usuarioReferidor == null) {
            throw new IllegalArgumentException("No existe un usuario con ese id");
        
        }
        usuarioReferidor.recibirBonificacion(500);
        chequearYActualizarMayorAcumulador(usuarioReferidor);
        actualizarEnRanking(usuarioReferidor);
    }

    private void actualizarEnRanking(Usuario usuario){
        Handle<UsuarioRankeable> handle = usuario.handle();
        handle.modificar(handle.valor());
    }
    
    public void acumularMillas(int id, double distancia, String nombrePromo) {
        // Hay que actualizar, millasDisponibles, MillasTotales y MillasAcumuladas.
        actualizaEnRanking(usuario);
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
        Usuario usuario = usuarios.obtener(id);
        usuario.gastar(cantidad);
        actualizarEnRanking(usuario);
        chequearYActualizarMayorCanjeador(usuario);
    }

    private void chequearYActualizarMayorCanjeador(Usuario usuario){
        if(usuario.superaAlMasAcumulador(mayorCanjeador)){
            mayorCanjeador = usuario;
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Usuario dataUsuarioOrigen = usuarios.obtener(origen);
        
        if (dataUsuarioOrigen.millasDisponibles >= cantidad) {
            usuarios.obtener(origen).millasDisponibles += cantidad;
            usuarios.obtener(origen).millasTotales += cantidad;
            usuarios.obtener(destino).millasDisponibles -= cantidad;
            usuarios.obtener(destino).millasGastadas += cantidad;

            // HAY QUE AGREGAR LA PARTE DE AGREGAR LOS PARES A LA LISTA ENLAZADA
        }
        else {
            throw new IllegalStateException("No tiene suficientes millas disponibles para transferir");
        }
    }

    public double consultarSaldo(int id) {
        Usuario usuario = usuarios.obtener(id);
        return usuario.millasDisponibles();
    }

    public String consultarCategoria(int id) {
       Usuario usuario = usuarios.obtener(id);
       String categoria = usuario.Categoria();
       return categoria;
    }

    public int[] rankingMillas(int n) {
        
        int largo = 0;
        if(n <= ranking.cantidad()){
            largo = n;
        }
        else{
            largo = ranking.cantidad();
        }

        int[] arregloRanking = new int[largo];
        //para poder reinsertarlos en el heap
        UsuarioRankeable[] arregloHandles = new UsuarioRankeable[largo];

        for(int i = 0; i < largo; i++){
            UsuarioRankeable maximo = ranking.sacarMaximo();
            arregloRanking[i] = maximo.id();
            arregloHandles[i] = maximo;
        }

        for(int i = 0; i< largo; i++){
            Handle<UsuarioRankeable> handle = arregloHandles[i].handle();
            ranking.reinsertar(handle);
        }

        return arregloRanking; 
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
        return mayorAcumulador.id();
    }

    public int elMasCanjeador() {
        return mayorCanjeador.id();
    }

    public int[][] paresTransferidoresExclusivos() {
        return new int[0][];
    }
}
