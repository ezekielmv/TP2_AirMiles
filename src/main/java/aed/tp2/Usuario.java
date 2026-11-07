package aed.tp2;

public class Usuario implements Comparable<Usuario> {
    private final int id;
    private double millasDisponibles;
    private double millasAcumuladas;
    private double millasGastadas;
    private double millasTotales;

    public Usuario(int id) {
        this.id = id;
        this.millasDisponibles = 0.0;
        this.millasAcumuladas = 0.0;
        this.millasGastadas = 0.0;
        this.millasTotales = 0.0;
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

    public double millasAcumuladas(){
        return millasAcumuladas;
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
        if(mayorAcumulador == null){
            return true;
        }
        return millasTotales() > mayorAcumulador.millasTotales();
    }
    
    //para usar cada vez que alguien gasta millas
    //se usa y si da true, mayorCanjeador = usuario que acumuló
    public boolean superaAlMasCanjeador(Usuario mayorCanjeador){
        if(mayorCanjeador == null){
            return true;
        }
        return millasGastadas() > mayorCanjeador.millasGastadas();
    }
    
    public double factorCategoria() {
        double factor = 1;
        if (millasTotales >= 10000 && millasTotales <= 49999) {
            factor = 1.5;
        }
        else if (millasTotales >= 50000 && millasTotales <= 999999) {
            factor = 2;
        }
        else if (millasTotales >= 1000000) {
            factor = 1;
        }

        return factor;
    } 

    public void Categoria () {
        String categoria = "Bronce";
        if (this.factorCategoria() == 1.5) {
            categoria = "Plata";
        }
        else if (this.factorCategoria() == 2) {
            categoria = "Oro";
        }
        else if (millasTotales >= 1000000) {
            categoria = "Platino";
        }

        return categoria; 
    }

    public void acumularMillas(double millas) {
        this.millasDisponibles += millas;
        this.millasTotales += millas;
    }


    @Override
    public int compareTo(Usuario otro) {
        return Integer.compare(this.id, otro.id);
    }
    // Podriamos implementar compareTo para comparar por millas disponibles, millas totales o id.
}
