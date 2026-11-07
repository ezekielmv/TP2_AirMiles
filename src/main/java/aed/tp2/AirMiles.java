package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Integer,Usuario> usuarios;   
    private Trie<Promocion> Promociones;
    private MaxHeap<UsuarioRankeable> ranking;
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
        registrarNuevoUsuarioSinRefe(id);
    }

    private void registrarNuevoUsuarioSinRefe(int id){
        
        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }

        Usuario nuevoUsuario = new Usuario(id);
        usuarios.insertar(nuevoUsuario.id(), nuevoUsuario);
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
        registrarNuevoUsuarioConRefe(id);
        bonificarReferidor(ref);
    }

    private void registrarNuevoUsuarioConRefe(int id){

        if(id < 0){
            throw new IllegalStateException("El id debe ser un número válido (id >= 0)");
        }

        Usuario nuevoUsuario = new Usuario(id);
        nuevoUsuario.recibirBonificacion(500);
        usuarios.insertar(nuevoUsuario.id(), nuevoUsuario);
        chequearYActualizarMayorAcumulador(nuevoUsuario);

        UsuarioRankeable usuarioRankeable = new UsuarioRankeable(nuevoUsuario);
        nuevoUsuario.asignarHandle(ranking.agregar(usuarioRankeable));
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
        // Por requiere sabemos que ambos usuarios existen en el sistema y que el origen tiene suficientes millas disponibles para transferir.
        Usuario usuario_origen = usuarios.obtener(origen);        
        Usuario usuario_destino = usuarios.obtener(destino);
        
        usuario_origen.gastar(cantidad);
        usuario_destino.recibirTransferencia(cantidad);
    }

    public double consultarSaldo(int id) {
        Usuario usuario = usuarios.obtener(id);
        return usuario.millasDisponibles();
    }

    public String consultarCategoria(int id) {
       return usuarios.obtener(id).categoria();
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
        if(mayorAcumulador == null) return -1;
        return mayorAcumulador.id();
    }

    public int elMasCanjeador() {
        if(mayorCanjeador == null) return -1;
        return mayorCanjeador.id();
    }

    public int[][] paresTransferidoresExclusivos() {
        return new int[0][];
    }
}
