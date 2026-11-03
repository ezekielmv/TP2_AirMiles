package aed.tp2;

public class Transferencia {
    private final int idOrigen;
    private final int idDestino;
    private final double monto;

    public Transferencia(int idOrigen, int idDestino, double monto) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.monto = monto;         //Monto de millas transferidas
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public double getMonto() {
        return monto;
    }
}