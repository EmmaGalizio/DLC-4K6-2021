import heap.TSBHeap;
import utils.HeapLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hola Mundo");

        HeapLoader heapLoader = new HeapLoader();
        float [] results = heapLoader.getMedianasParciales();
        System.out.println("Medias Parciales a 25000 elementos: " + results[0]);
        System.out.println("Medias Parciales a 50000 elementos: " + results[1]);
        System.out.println("Medias Parciales a 75000 elementos: " + results[2]);
        System.out.println("Medias Parciales a 100000 elementos: " + results[3]);
/*
        Integer [] c =  {10, 7, 1, 5, 10, 6, 7, 8, 4, 6, 3, 9, 5, 12, 2, 3, 11, 10, 9, 4};

        TSBHeap<Integer> minorOfMajors = new TSBHeap<>(true); //De la mitad para arriba, la raiz es el menor de los mayores
        TSBHeap<Integer> majorOfMinors = new TSBHeap<>(false); //De la mitad para abajo, la raiz es el mayor de los menores

        float medianaCincoElem = 0, medianaDiezElem = 0, medianaQuinceElem = 0;

        for(int i = 0; i < c.length; i++){

            if(i==0) {
                minorOfMajors.add(c[i]);
                continue;
            }

            int countMajors = minorOfMajors.size();
            int countMinors = majorOfMinors.size();

            if(countMinors < countMajors){
                if(minorOfMajors.get().compareTo(c[i]) > 0){
                    majorOfMinors.add(c[i]);
                } else{
                    //if(minorOfMajors.get().compareTo(c[i]) == 0){

                    //} else {
                        Integer aux = minorOfMajors.remove();
                        minorOfMajors.add(c[i]);
                        majorOfMinors.add(aux);
                    //}
                }
            } else{
                if(majorOfMinors.get().compareTo(c[i]) < 0){
                    minorOfMajors.add(c[i]);
                } else{
                    Integer aux = majorOfMinors.remove();
                    majorOfMinors.add(c[i]);
                    minorOfMajors.add(aux);
                }

            }
            switch (i){
                case 4:
                    medianaCincoElem = Main.calcularMediana(majorOfMinors, minorOfMajors);
                    break;
                case 9:
                    medianaDiezElem = Main.calcularMediana(majorOfMinors, minorOfMajors);
                    break;
                case 14:
                    medianaQuinceElem = Main.calcularMediana(majorOfMinors, minorOfMajors);
                    break;
            }

        }

        float medianaTotal = Main.calcularMediana(majorOfMinors, minorOfMajors);

        System.out.println("Mediana Total: " + medianaTotal);
        System.out.println("Mediana a los 5 elementos: " + medianaCincoElem);
        System.out.println("Mediana a los 10 elementos: " + medianaDiezElem);
        System.out.println("Mediana a los 15 elementos: " + medianaQuinceElem);
*/




    }

    /*
    public static float calcularMediana(TSBHeap<Integer> majorOfMinors, TSBHeap<Integer> minorOfMajors){
        float mediana;
        if(majorOfMinors.size() == minorOfMajors.size()){
            mediana = (float)(majorOfMinors.get() + minorOfMajors.get()) / 2;
        } else{
            if(majorOfMinors.size() > minorOfMajors.size()){
                mediana = majorOfMinors.get();
            } else{
                mediana = minorOfMajors.get();
            }
        }
        return mediana;
    }

     */

}
