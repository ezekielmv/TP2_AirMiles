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
    public double factorCategoria() {
        throw new UnsupportedOperationException("No implementada aun");
    } 

    public void Categoria () {
        throw new UnsupportedOperationException("No implementada aun");
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
