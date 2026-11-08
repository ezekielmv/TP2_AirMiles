package aed.tp2;

public class Usuario implements Comparable<Usuario> {
    private final int id;
    private double millasDisponibles;
    private double millasGastadas;
    private double millasTotales;
    private Handle<UsuarioRankeable> handleHeap;
    private Usuario unicoDestinoTransferencias;
    private boolean realizoTransferenciasMultiples;
    private Par parExclusivo;

    public Usuario(int id) {
        this.id = id;
        this.millasDisponibles = 0.0;
        this.millasGastadas = 0.0;
        this.millasTotales = 0.0;
        this.handleHeap = null;
        this.unicoDestinoTransferencias = null;
        this.realizoTransferenciasMultiples = false;
        this.parExclusivo = null;
    }

    public int id(){
            return id;
    }

    public Handle<UsuarioRankeable> handle(){
        return handleHeap;
    }

    public double millasDisponibles(){
        return this.millasDisponibles;
    }

    public double millasGastadas(){
        return millasGastadas;
    }

    public double millasTotales(){
        return millasTotales;
    }

    public void asignarHandle(Handle<UsuarioRankeable> handle){
        handleHeap = handle;
    }

    //si se registra solo, se le suman al usuario.
    //si es con refe, se les suma a los dos
    public void recibirBonificacion(double cantidad){
        millasDisponibles += cantidad;
        millasTotales += cantidad;
    }

    public void volar(double cantidad){
        millasDisponibles += cantidad;
        millasTotales += cantidad;
    }

    public void recibirTransferencia(double cantidad){
        millasDisponibles += cantidad;
    }

    //para canjear o para enviar transferencia
    public void gastar(double cantidad){
        millasDisponibles -= cantidad;
        millasGastadas += cantidad;
    }
    //para usar cada vez que alguien suma millas totales
    //se usa y si da true, mayorAcumulador = usuario que acumuló
    public boolean superaAlMasAcumulador(Usuario mayorAcumulador){
        if(mayorAcumulador == null) return true;
        return millasTotales() > mayorAcumulador.millasTotales();
    }
    
    //para usar cada vez que alguien gasta millas
    //se usa y si da true, mayorCanjeador = usuario que acumuló
    public boolean superaAlMasCanjeador(Usuario mayorCanjeador){
        if(mayorCanjeador == null) return true;
        return millasGastadas() > mayorCanjeador.millasGastadas();
    }
    
    public double factorCategoria() {
        String categoria = this.categoria();
        if(categoria.equals("Platino")) return 0.0;
        if(categoria.equals("Oro")) return 2.0;
        if(categoria.equals("Plata")) return 1.5;
        
        return 1.0;
    } 

    public String categoria () {
        if(millasTotales >= 1000000) return "Platino";
        if(millasTotales >= 50000) return "Oro";
        if(millasTotales >= 10000) return "Plata";
        
        return "Bronce";
    }

    // Metodos para el uso de transferencias.
    public Usuario unicoDestino() {
        return unicoDestinoTransferencias;
    }

    public boolean esTransferidorValidoUnico() {
        return !realizoTransferenciasMultiples && unicoDestinoTransferencias != null;
    }

    public Par parExclusivo() {
        return parExclusivo;
    }

    public void setParExclusivo(Par par) {
        this.parExclusivo = par;
    }

    public void registrarTransferencia(Usuario destino) {
        if (realizoTransferenciasMultiples) {
            return;
        }

        if (unicoDestinoTransferencias == null) {
            // Primera transferencia realizada por este usuario
            unicoDestinoTransferencias = destino;
        } else if (unicoDestinoTransferencias.id() != destino.id()) {
            // Se le transfirio a un segundo usuario diferente -> pierde exclusividad para siempre
            realizoTransferenciasMultiples = true;
            unicoDestinoTransferencias = null;
        }
    }
            // Si unicoDestinoTransferencias.id() == destino.id(), se mantiene la exclusividad


    @Override
    public int compareTo(Usuario otro) {
        return Integer.compare(this.id, otro.id);
    }
    // Podriamos implementar compareTo para comparar por millas disponibles, millas totales o id.
}
