package emma.galzio.tpidlcmotorbusquedaficheros.utils;


import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service("scannerFileReader")
public class ScannerFileReader implements FileReader {


    private Scanner scanner;


    @Override
    public String readNextWord() {
        return scanner.next();
    }

    @Override
    public boolean isReady() {
        return scanner.hasNext();
    }

    @Override
    public void openFile(String path) throws IllegalArgumentException {
        try {
            scanner = new Scanner(new FileInputStream(path), StandardCharsets.UTF_8);
            scanner.useDelimiter(Pattern.compile("[-.;\":\\\\/_\t*\\)$\\(#\\[!\\]?¿¡\\s+]"));
        } catch (FileNotFoundException e){
            throw new IllegalArgumentException("The provided path doesn't match with any existent file");
        }

    }

    @Override
    public void closeFile() throws IOException {
        scanner.close();
    }
}
