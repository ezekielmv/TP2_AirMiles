package aed.tp2;

/* subclase necesaria para que el comparador que usa el heap trabaje en base
a las millas disponibles, y no a los id, como está
implementado en el  compareTo de Usuario. 
la única función de esta subclase/envoltorio es eso ^.^
*/
public class UsuarioRankeable implements Comparable<UsuarioRankeable>{
        private Usuario usuario;

    public UsuarioRankeable(Usuario usuario){
        this.usuario = usuario;
    }

    private double millas(){
        return this.usuario.millasDisponibles();
    }

    public int id(){
        return usuario.id();
    }

    public Handle<UsuarioRankeable> handle(){
        return usuario.handle();
    }

    @Override 
    // todo por esta bella parte 
    public int compareTo(UsuarioRankeable otro){
        double misMillas = this.millas();
        double otrasMillas = otro.millas();

        if(misMillas < otrasMillas){
            return -1;
        }
        else if(misMillas > otrasMillas){
            return 1;
        }
        else{
            return 0;
        }
    }
}
