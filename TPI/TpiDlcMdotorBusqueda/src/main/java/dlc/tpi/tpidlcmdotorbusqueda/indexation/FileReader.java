/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc.tpi.tpidlcmdotorbusqueda.indexation;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author a2
 */
@Service
public class FileReader {
    //Charset de codificacion del archivo a leer Usar ISO_8859_1 porque asi esta programado
    private static final Charset ENCODING = StandardCharsets.ISO_8859_1;
    
    //BufferedReader para leer el archivo a procesar
    private BufferedReader reader;

    
    public FileReader() {
        
    }

    
    /**
     * Instancia la variable reader con el BufferedReader del archivo
     * cuya direccion se pasa por paramtro
     * @param filePath - direccion del archivo a abrir
     */
    public void openFile(String filePath) {
        Path logFile = Paths.get(filePath);
        try {
            reader = Files.newBufferedReader(logFile, ENCODING);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    
    /**
     * Cierra el BufferedReader
     * @throws IOException 
     */
    public void closeFile() throws IOException{
        reader.close();
    }

    
    /**
     * lee una linea del archivo y la retorna como string
     * @return String compuesto por la linea leida del archivo o null
     * sino pudo leer o no hay mas contenido en el archivo
     */
    public String readLine() {
        String line = null;
        try {
            if(!reader.ready()) {throw new IOException();}
            line = reader.readLine();
        } catch (IOException ex) {
            
        }
        return line;
    }
    
    
    /**
     * lee X cantidad de lineas del archivo y las retorna
     * @param x - cantidad de lineas a leer
     * @return String compuesto por las lineas leidas del arhchivo
     */
    public String readLnes(int x) {
        StringBuilder sb = new StringBuilder("");
        while(x > 0){
            x--;
            sb.append(readLine());
        }
        return sb.toString();
    }

    
    /**
     * lee un caracter del archivo
     * @return el caracter leido del archivo
     */
    public String readChar(){
        String character = "";
        try{
            if(!reader.ready()) { throw new IOException();}
            character += (char)reader.read();
        } catch (IOException ex){
            
        }
        return character;
    }
    
    
    /**
     * lee el proximo caracter del archivo y lo retorna por su codigo ascii en
     * ISO_8859_1
     * @return el numero que representa en ascii al caracter leido
     */
    private int readCharCode(){
        int charCode = -1;
        try{
            if(!reader.ready()) { throw new IOException();}
            charCode = reader.read();
        } catch (IOException ex){
            
        }
        return charCode;
    }
    /*
    public String readNextWordOld(){
        StringBuilder sb = new StringBuilder("");
        int letra = this.readCharCode();
        while(letra <= 32){
            letra = this.readCharCode();
        }
        
        while(letra >32 && letra != -1){
            if((letra > 48 && letra < 57) || (letra > 64 && letra < 90) || ( letra > 95 && letra < 122) || (letra > 192 && letra < 255)){
                sb.append((char)letra);
            }
            letra = this.readCharCode();
        }
        return sb.toString();
    }*/
    
    
    /**
     * Lee la proxima palabra del archivo, aca ya se eliminan todos los espacios 
     * saltos de linea o simbolos que no forman parte de la palabra
     * como simbolos de interrogacion admiracion y demas
     * exceptuando simbolos internos de la palabra en palabras como
     * www.example.com - re-use - apostrofes acentos etc.
     * @return la proxima palabra procesada que encuentre en el archivo
     */
    public String readNextWord(){
        StringBuilder sb = new StringBuilder("");
        boolean symbolFlag = false;
        int letra = this.readCharCode();
        
        if(letra == -1){
            return sb.toString();
        }
        
        while( !isLetter(letra) ){
            letra = this.readCharCode();
            if(letra == -1){
                return sb.toString();
            }
        }
        
        while(letra > 32){
            sb.append((char)letra);
            if(!isLetter(letra)){
                symbolFlag = true;
            }
            letra = this.readCharCode();
            if((letra <= 32 || !isLetter(letra)) && symbolFlag){
                sb.deleteCharAt(sb.length()-1);
                break;
            }
            symbolFlag = false;
        }
        return sb.toString();
    }
    
    
    /**
     * comprueba si el numero int ingresado es una letra o no.
     * @param letra
     * @return 
     */
    private boolean isLetter(int letra){
        return (letra > 47 && letra < 58) /*nums*/ || (letra > 64 && letra < 91) /*mayus*/||  (letra > 96 && letra < 123)/*mins*/ ||  (letra > 191 && letra < 256) /*acentos*/;
    }
    
    
    /**
     * comprueba si hay mas para leer o no.
     * @return true si puede seguir leyendo false sino
     */
    public boolean hasNextToRead(){
        try {
            if(reader.ready()){
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
        return false;
    }
}
