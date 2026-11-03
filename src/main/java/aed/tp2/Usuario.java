package aed.tp2;

class Usuario implements Comparable<Usuario> {
  private final int id;
  private double millasDisponibles;
  private double millasTotales;
  private double millasGastadas;

    public Usuario(int id, double disponibles, double gastadas, double totales) {
        this.id = id;
        this.millasDisponibles = disponibles;
        this.millasGastadas = gastadas;
        this.millasTotales = totales;
    }

    public double factorCategoria() {
        throw new UnsupportedOperationException("No implementada aun");
    } 

    public void Categoria () {
        throw new UnsupportedOperationException("No implementada aun");
    }

    @Override
    public int compareTo(Usuario otro) {
        return Integer.compare(this.id, otro.id);
    }
    // Podriamos implementar compareTo para comparar por millas disponibles, millas totales o id.
}