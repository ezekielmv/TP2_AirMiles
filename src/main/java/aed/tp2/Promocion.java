package aed.tp2;

public class Promocion {
    private final String nombre;
    private double factor;
    private final double tope;
    private double _acumulado;

    //constructor sin tope
    public Promocion(String nombre, double factor) {
        this.nombre = nombre;
        this.factor = factor;
        this.tope = 0;
        this._acumulado = 0;
    }

    //constructor con tope
    public Promocion(String nombre, double factor, double tope) {
        this.nombre = nombre;
        this.factor = factor;
        this.tope = tope;
        this._acumulado = 0;
    }


    public String nombre() {
        return nombre;
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

    // Requiere: las millas a acumular no superan el tope (si tiene).
    public void acumular(double millas) {
        if (this.tieneTope() && _acumulado + millas > tope) {
            return;
        }
        _acumulado += millas;
    }

}
 
 
