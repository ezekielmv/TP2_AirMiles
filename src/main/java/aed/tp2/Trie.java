package aed.tp2;

public class Trie<T> {
    private Nodo<T> raiz;
    
    @SuppressWarnings("unchecked") 
    private static class Nodo<T> {
        private T dato;  // Aca va el objeto Promocion que tiene la info de la promocion correspondiente
        private Nodo<T>[] hijos;

        public Nodo(){
            dato = null;   // 128 para contener los caracteres ASCII
            hijos = new Nodo[128]; // es un arreglo de nodos donde el numero de cada posicion representa el caracter "A" = 65
        }
    }

    public Trie() {
        this.raiz = new Nodo<>();    
    }

    public void insertar(String clave, T valor) {
        Nodo<T> actual = raiz;

        for (int i = 0 ; i < clave.length(); i++){
            char letra = clave.charAt(i);           // quiero usar el valor de ese caracter como una posicion del arreglo

            if (actual.hijos[letra] == null) {  // Si ese caracter no existe, creo un nodo nuevo alli que lo contenga
                actual.hijos[letra] = new Nodo<>();
                actual = actual.hijos[letra];
            }

            else {
                actual = actual.hijos[letra];
            }
        }    
        actual.dato = valor;
    }

    public T buscar(String clave) {
        Nodo<T> actual = raiz;

        for (int i = 0 ; i < clave.length(); i++){
            char letra = clave.charAt(i);

            if (actual.hijos[letra] == null) {
                return null;
            }
            else {
                actual = actual.hijos[letra];
            }
        }
        return actual.dato;
    }

    public void eliminar (String clave) {
        Nodo<T> actual = raiz;

        for (int i=0; i < clave.length() ; i++) {
            char letra = clave.charAt(i);
            
            if (actual.hijos[letra] == null) {
                return ;
            }
            else {
                actual = actual.hijos[letra];
            }
        }

        actual.dato = null;
    }
}
