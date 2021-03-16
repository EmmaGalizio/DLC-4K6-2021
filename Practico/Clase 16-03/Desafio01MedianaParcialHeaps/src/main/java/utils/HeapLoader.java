package utils;

import heap.TSBHeap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HeapLoader {

    private TSBHeap<Integer> majorOfMinors;
    private TSBHeap<Integer> minorOfMajors;
    private float[] results;


    private BufferedReader bufferedReader;

    public HeapLoader(){
        majorOfMinors = new TSBHeap<>(false);
        minorOfMajors = new TSBHeap<>(true);
        innitBufferedReader();
        results = new float[4];
    }

    public float[] getMedianasParciales(){

        int count = 0;

        try{
            while(bufferedReader.ready()){

                String line = bufferedReader.readLine();
                Integer number = Integer.parseInt(line.trim());

                int countMajors = minorOfMajors.size();
                int countMinors = majorOfMinors.size();

                if(minorOfMajors.isEmpty()){
                    minorOfMajors.add(number);
                    continue;
                }
                if(countMinors < countMajors){
                    if(minorOfMajors.get().compareTo(number) > 0){
                        majorOfMinors.add(number);
                    } else{

                        Integer aux = minorOfMajors.remove();
                        minorOfMajors.add(number);
                        majorOfMinors.add(aux);
                    }
                } else{

                    if(majorOfMinors.get().compareTo(number) < 0){
                        minorOfMajors.add(number);
                    } else{
                        Integer aux = majorOfMinors.remove();
                        majorOfMinors.add(number);
                        minorOfMajors.add(aux);
                    }
                }
                count++;
                switch (count){
                    case (25000-1):
                        results[0] = this.calcularMediana(majorOfMinors, minorOfMajors);
                        break;
                    case (50000-1):
                        results[1] = this.calcularMediana(majorOfMinors, minorOfMajors);
                        break;
                    case (75000-1):
                        results[2] = this.calcularMediana(majorOfMinors, minorOfMajors);
                        break;
                    case (100000-1):
                        System.out.println("Entro a los 100000");
                        System.out.println("Ultima Linea: " + line);
                        results[3] = this.calcularMediana(majorOfMinors, minorOfMajors);
                        break;
                }

            }

        } catch(IOException e){
            System.err.println("Se produjo un error al leer el archivo");
        }

        return results;
    }

    private float calcularMediana(TSBHeap<Integer> majorOfMinors, TSBHeap<Integer> minorOfMajors){
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


    private void innitBufferedReader(){
        String fileName = "list[100000].txt";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

    }


}
