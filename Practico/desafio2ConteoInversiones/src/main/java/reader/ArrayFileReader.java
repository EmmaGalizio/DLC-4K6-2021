package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ArrayFileReader {

    private String url;
    private File file;
    private BufferedReader bufferedReader;

    public ArrayFileReader(String url){
        this.url = url;
        file = new File(url);
    }

    private void openFile(){
        Path path = Paths.get(url);
        try{
            bufferedReader = Files.newBufferedReader(path);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public Integer[] readFile(){
        this.openFile();
        ArrayList<Integer> array = new ArrayList<>();

        try {
            while(bufferedReader.ready()){
                String line = bufferedReader.readLine();
                array.add(Integer.parseInt(line.trim()));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        Integer[] arreglo = array.toArray(new Integer[0]);
        System.out.println("Tamaño arreglo archivo " + file.getName() + ": " + arreglo.length);
        this.closeFile();
        return arreglo;
    }

    private void closeFile(){
        try {
            bufferedReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }



}
