package aed.tp2;

public class MaxHeap<T extends Comparable<T>>{
    private HandleH[] heap;
    private int cantidad;
    private int capacidad;
    
    @SuppressWarnings("unchecked") 
    /* esto ^ hay que agregarlo porque sino java en
    naranjita se va a quejar sobre el uso de la clase interna
    HandleH como tipo para un arreglo.
    al haberlo implementado de 0, no hay problema porque no va
    a aparecer nada dentro del arreglo que no sea
    del tipo HandleH :)
    */
    public MaxHeap(int capacidad){
        this.heap = (HandleH[]) new MaxHeap.HandleH[capacidad];
        this.capacidad = capacidad;
        this.cantidad = 0;
    }

    private class HandleH implements Handle<T>, Comparable<Handle<T>>{
        T valor;
        int indice;

        HandleH(T valor, int indice){
            this.valor = valor;
            this.indice = indice;
        }

        @Override 
        public T valor(){
            return valor;
        }

        @Override 
        public void modificar(T nuevoValor){
            if(indice == -1){
                throw new IllegalStateException("El handle fue eliminado");
             }
            valor = nuevoValor;
            reacomodar(indice);
        }

        @Override
        public void eliminar() {
            if(indice == -1){
                throw new IllegalStateException("El handle ya fue eliminado");
            }
            eliminarHandle(indice);
            
        }

        @Override 
        public boolean equals(Handle<T> otroHandle){
            HandleH otroHandleH = (HandleH) otroHandle;
            return this.valor().equals(otroHandleH.valor);
        }
        
        @Override 
        public int compareTo(Handle<T> otroHandle){
            T miValor = this.valor();
            T otroValor = otroHandle.valor();
            return miValor.compareTo(otroValor);
            
        }
    }

    private int padre(int i){
        return (i-1)/2;
    }

    private int hijoIzq(int i){
        return 2 * i + 1;
    }

    private int hijoDer(int i){
        return 2 * i + 2;
    }

    private void intercambiar(int i, int j){

        HandleH temporal = heap[i];
        heap[i] = heap[j];
        heap[j] = temporal;

        heap[i].indice = i;
        heap[j].indice = j;
    }

    private void subir(int i){

        while(i > 0 && heap[i].compareTo(heap[padre(i)]) > 0){
            intercambiar(i, padre(i));
            i = padre(i);
        }
    }

    private void bajar(int i){

        int max = i;
        int izq = hijoIzq(i);
        int der = hijoDer(i);

        if(izq < cantidad && heap[izq].compareTo(heap[max]) > 0){
            max = izq;
        }

        if(der < cantidad && heap[der].compareTo(heap[max]) > 0){
            max = der;
        }

        if(max != i){
            intercambiar(i, max);
            bajar(max);
        }
    }

    private void reacomodar(int i){
        if(i > 0 && heap[i].compareTo(heap[padre(i)]) > 0){
            subir(i);
        }
        else{
            bajar(i);
        }
    }

    public int cantidad(){
        return cantidad;
    }

    public int capacidad(){
        return capacidad;
    }

    public Handle<T> agregar(T elem){

        if(cantidad==capacidad){
            heap = redimensionar(heap);
        }
        HandleH nuevo = new HandleH(elem, cantidad);
        insertarHandle(nuevo);
        return nuevo;
        
    }
    
    @SuppressWarnings("unchecked")
    private HandleH[] redimensionar(HandleH[] actual){

        HandleH [] nuevo =  (HandleH[]) new MaxHeap.HandleH[2 * capacidad];
        for(int i = 0; i < cantidad; i++){
            nuevo[i] = actual[i];
        }
        capacidad = 2 * capacidad;
        return nuevo;
    }

    private void insertarHandle(HandleH h){
        h.indice = cantidad;
        heap[cantidad] = h;
        cantidad++;
        subir(cantidad-1);
    }

    public void reinsertar(Handle<T> exHandle){   

        HandleH h = (HandleH) exHandle;
        if(h.indice != -1){
            throw new IllegalStateException("El handle ya está en el heap");
        }

        insertarHandle(h);
    }

    public T maximo(){

        if(cantidad == 0){
            throw new IllegalStateException("El heap está vacío");
        }

        return heap[0].valor();   
    }

    public T sacarMaximo(){

        if(cantidad == 0){
            throw new IllegalStateException("El heap está vacío");
        }
        HandleH maximo = heap[0];
        maximo.indice = -1;
        eliminarHandle(0);

        return maximo.valor;
    }
    
    private void eliminarHandle(int i){
        
        if(i != cantidad - 1){
            intercambiar(i, cantidad - 1);
        }
        heap[cantidad - 1].indice = -1;
        heap[cantidad - 1] = null; 
        cantidad--;

        if(i < cantidad){
            reacomodar(i);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void heapify(T[] conjunto){

        if(cantidad>0){
            throw new IllegalStateException("Ya hay un heap creado");
        }
        capacidad = conjunto.length;
        cantidad = conjunto.length;
        heap = (HandleH[]) new MaxHeap.HandleH[capacidad];
        for(int i = 0; i < cantidad; i++){
            heap[i] = new HandleH(conjunto[i], i);
        }

        for(int i =(cantidad/2) - 1; i >= 0; i--){
            bajar(i);
        }
    }
}
