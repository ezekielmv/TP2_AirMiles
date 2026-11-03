package aed.tp2;

public class Transferencia {
    private final int idOrigen;
    private final int idDestino;
    private final double cantidad;

    public Transferencia(int idOrigen, int idDestino, double cantidad) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.cantidad = cantidad;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public double getCantidad() {
        return cantidad;
    }
}