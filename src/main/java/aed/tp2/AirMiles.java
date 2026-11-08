package aed.tp2;
import java.util.*;

public class AirMiles implements SistemaAirMiles {
    private ABB<Integer,Usuario> usuarios;   
    private Trie<Promocion> promociones;
    private MaxHeap<UsuarioRankeable> ranking;
    private Usuario mayorCanjeador;
    private Usuario mayorAcumulador;    
    //private ListaEnlazada<Transferencia> listaParesTransferidores;
    private ListaEnlazada<Par> paresEsclusivos;

    public AirMiles() {
        this.usuarios = new ABB<Integer,Usuario>();
        this.promociones = new Trie<Promocion>();
        Promocion nuevaPromo = new Promocion("NoPromo", 1, 0);
        promociones.insertar("NoPromo",nuevaPromo);
        this.ranking = new MaxHeap<UsuarioRankeable>(10);
        this.mayorCanjeador = null;
        this.mayorAcumulador = null;
        this.paresEsclusivos = new ListaEnlazada<Par>();
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
        Usuario usuario = usuarios.obtener(id);
        Double millasAcumuladas = calculaMillasYActualizaPromo(usuario, distancia, nombrePromo);
        usuario.volar(millasAcumuladas);

        actualizarEnRanking(usuario);
        chequearYActualizarMayorAcumulador(usuario);
    }

    private double calculaMillasYActualizaPromo(Usuario usuario, double distancia, String nombrePromo) {
        double millasBase = usuario.factorCategoria() * distancia;
        Promocion datosPromo = promociones.buscar(nombrePromo);

        if (datosPromo == null) {                        // Si no existe la promo, acumula sin el factor promo
            return millasBase;
        }

        double factorPromo = datosPromo.factor();
        double millasAacumular = millasBase * factorPromo;

        if (!datosPromo.tieneTope()) {
            return millasAacumular;
        }

        double nuevoAcumulado = datosPromo.acumulado() + millasAacumular;

        if (nuevoAcumulado < datosPromo.tope()) {             // Usa la promo con su factor y actualiza el acumulado de la promo
            datosPromo.actualizaAcumulado(nuevoAcumulado);
            return millasAacumular;

        } else if (nuevoAcumulado == datosPromo.tope()) {     // Usa la promo, llega al tope asi que actualiza su acumulado y factor
            datosPromo.actualizaAcumulado(nuevoAcumulado);
            datosPromo.actualizaFactor(1.0);
            return millasAacumular;

        } else {
            return millasBase;                                // Las millas a acumular superan las disponibles, no usa la promo
        }
    }

    public void canjearMillas(int id, double cantidad) {
        Usuario usuario = usuarios.obtener(id);
        usuario.gastar(cantidad);
        actualizarEnRanking(usuario);
        chequearYActualizarMayorCanjeador(usuario);
    }

    private void chequearYActualizarMayorCanjeador(Usuario usuario){
        if(usuario.superaAlMasCanjeador(mayorCanjeador)){
            mayorCanjeador = usuario;
        }
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        // Por requiere sabemos que ambos usuarios existen en el sistema y que el origen tiene suficientes millas disponibles para transferir.
        Usuario usuario_origen = usuarios.obtener(origen);        
        Usuario usuario_destino = usuarios.obtener(destino);
        
        usuario_origen.gastar(cantidad);
        usuario_destino.recibirTransferencia(cantidad);
        chequearYActualizarMayorCanjeador(usuario_origen);
        actualizarEnRanking(usuario_origen);
        actualizarEnRanking(usuario_destino);


        actualizarParesExclusivos(usuario_origen, usuario_destino);
    }

    private void actualizarParesExclusivos(Usuario origen, Usuario destino) {
        // Registramos que origen realizó una transferencia a destino y origen.
        origen.registrarTransferencia(destino);
        destino.registrarTransferencia(origen);

        if (origen.parExclusivo() != null && !origen.esTransferidorValidoUnico()) {
            romperParExclusivo(origen);
        }

        if (destino.parExclusivo() != null && !destino.esTransferidorValidoUnico()) {
            romperParExclusivo(destino);
        }

        // Si ambos cumplen la condición de pares exclusivos, formamos un nuevo par. Esto solo ocurre si ambos usuarios no tenían un par antes.
        if (origen.parExclusivo() == null && destino.parExclusivo() == null) {
            formarParExclusivo(origen, destino);
        }
    }

    private void romperParExclusivo(Usuario usuario) {
        Par par_a_romper = usuario.parExclusivo();

        // Identificamos quién es el otro integrante del par
        Usuario otroUsuario;
        
        if (par_a_romper.usuarioA().id() == usuario.id()) {
            otroUsuario = par_a_romper.usuarioB();
        } else {
            otroUsuario = par_a_romper.usuarioA();
        }

        // Eliminamos el nodo de la lista enlazada en O(1)
        paresEsclusivos.eliminar(par_a_romper.nodoEnLista());

        // Desvinculamos la referencia del par en ambos usuarios
        usuario.setParExclusivo(null);
        otroUsuario.setParExclusivo(null);
    }

    private void formarParExclusivo(Usuario origen, Usuario destino) {
        if (origen.esTransferidorValidoUnico() && destino.esTransferidorValidoUnico()) {
            if (origen.unicoDestino().id() == destino.id() &&
                    destino.unicoDestino().id() == origen.id()) {

                Par nuevoPar = new Par(origen, destino);
                ListaEnlazada<Par>.Nodo nodo = paresEsclusivos.agregarAtrasYDevolverNodo(nuevoPar);
                nuevoPar.asignarNodo(nodo);

                origen.setParExclusivo(nuevoPar);
                destino.setParExclusivo(nuevoPar);
            }
        }
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
        promociones.insertar(nombre,nuevaPromo);
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        Promocion nuevaPromo = new Promocion(nombre, factor, tope);
        promociones.insertar(nombre,nuevaPromo);
    }

    public void finalizarPromocion(String nombre) {
        promociones.eliminar(nombre);
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
        int n = paresEsclusivos.longitud();
        int[][] resultado = new int[n][2];

        for (int i = 0; i < n; i++) {
            Par par = paresEsclusivos.obtener(i);
            resultado[i][0] = par.idA();
            resultado[i][1] = par.idB();
        }

        return resultado;
    }
}
