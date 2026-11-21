package aed.tp2;

public class Promocion {
    private final String nombre;
    private double factor;
    private final double tope;
    private double _acumulado;

    
    public Promocion(String nombre, double factor, double tope) {
        this.nombre = nombre;
        this.factor = factor;
        this.tope = tope;
        this._acumulado = 0;
    }
    
    public double factor() {
        return factor;
    }

    public double tope() {
        return tope;
    }

    public double acumulado() {
        return _acumulado;
    }
    
    public boolean tieneTope() {
       return tope > 0;
    }
    
    public void actualizaFactor(double factor){
        this.factor = factor;
    }

    public void actualizaAcumulado(double acumulado){
        this._acumulado = acumulado;
    }

}


 
 
