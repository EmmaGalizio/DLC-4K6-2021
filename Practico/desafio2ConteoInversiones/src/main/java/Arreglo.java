

public class Arreglo {

    private Integer[] array;
    private long inversionCount;
    private int temp [];

    public Arreglo(Integer[] array){
        if(array == null) throw new IllegalArgumentException("Arrego nulo");
        this.array = array;
        inversionCount = 0;
    }

    // ordenamiento Merge Sort
    public void ordenar(){
        int n = array.length;
        temp = new int[n];
        sort(0, n - 1);
    }

    private void sort(int izq, int der){
        if(izq < der){
            int centro = (izq + der) / 2;
            sort(izq, centro);
            sort(centro + 1, der);
            merge(izq, centro, der);
        }
    }

    private void merge(int izq, int centro, int der){

        for(int i = izq; i <= der; i++) { temp[i] = array[i]; }

        int i = izq, j = centro + 1, k = izq;
        while(i <= centro && j <= der){

            if(temp[i] <= temp[j]){

                array[k] = temp[i];
                i++;
            }
            else{
                array[k] = temp[j];
                inversionCount+= ((centro+1)-i);
                j++;
            }
            k++;
        }
        while(i <= centro){

            array[k] = temp[i];

            k++;
            i++;
        }
    }

    public long countInvertions(){
        return inversionCount;
    }

}
