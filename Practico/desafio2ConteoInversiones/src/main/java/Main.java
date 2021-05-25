
import reader.ArrayFileReader;


import java.io.File;

public class Main {

    private static final String ROOT_DIRECTORY = "D:\\Repositorio DLC-4K6-2021\\DLC-4K6-2021\\Practico\\lotesDesafio2";

    public static void main(String[] args) {

        File rootDirectory = new File(Main.ROOT_DIRECTORY);
        if(!rootDirectory.isDirectory()) throw new IllegalArgumentException("No es un directorio");

        File[] files = rootDirectory.listFiles();
        InversionSlot[] inversionesDeArchivos = new InversionSlot[files.length];
        int count = 0;
        for(File file: files){
            ArrayFileReader fileReader = new ArrayFileReader(file.getAbsolutePath());
            Integer v[] = fileReader.readFile();
            Arreglo arreglo = new Arreglo(v);
            arreglo.ordenar();
            InversionSlot inversionSlot = new InversionSlot(file.getName(), arreglo.countInvertions());
            inversionesDeArchivos[count] = inversionSlot;
            count++;
        }

        for(InversionSlot inversionSlot : inversionesDeArchivos){
            System.out.println(inversionSlot);
        }

    }
}
