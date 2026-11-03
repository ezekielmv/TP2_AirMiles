package aed.tp2;

class Usuario {
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
}
