package emma.galzio.tpidlcmotorbusquedaficheros.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Service("utfFileReader")
public class UTF8FileReader implements FileReader {

    private Charset charset;
    private BufferedReader reader;
    private String validWordMiddleSymbols;
    private Logger logger;
    private StringNormalizer stringNormalizer;

    public UTF8FileReader() {
        charset = StandardCharsets.UTF_8;
        validWordMiddleSymbols = "@-._/*";
        logger = LoggerFactory.getLogger(this.getClass());
        stringNormalizer = new StringNormalizer();

    }

    @Override
    public String readNextWord() {
        //TENGO QUE HARDCODEAR LA DETECCION DE APOSTROFES!!

        StringBuilder stringBuilder = new StringBuilder("");
        //boolean symbolFlag = false;
        boolean previousDigit = false, digitSeparator = false;
        boolean previousLetter = false;
        int charCode, lastCharRead = -2;


        try{
            charCode = reader.read();
            if(charCode == -1) return null;


            while(!this.isLetterOrDigit(charCode)){
                charCode = reader.read();
                if(charCode == -1) return null;
            }

            while(this.isLetterOrDigit(charCode) || (this.isDigitSeparator((char) charCode) && previousDigit)
                                                    ||(this.isApostrophe((char) charCode)) && previousLetter){
                 previousDigit = false;
                 previousLetter = false;
                stringBuilder.append((char) charCode);
                if(Character.isDigit(charCode)){
                    previousDigit = true;
                }
                if(Character.isLetter(charCode)) previousLetter = true;
                charCode = reader.read();
            }
            if(this.isDigitSeparator(stringBuilder.charAt(stringBuilder.length()-1))
                || this.isApostrophe(stringBuilder.charAt(stringBuilder.length()-1))){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            }
            /*
            while(Character.isAlphabetic(charCode) || this.isValidSymbol((char) charCode)){

                if(this.isValidSymbol((char) charCode)){
                    if(symbolFlag){
                        stringBuilder.deleteCharAt(stringBuilder.length()-1);
                        return stringBuilder.toString();
                    }
                    symbolFlag = true;
                }
                if(Character.isAlphabetic(charCode)){
                    symbolFlag = false;
                }
                stringBuilder.append((char) charCode);
            }

             */
            return stringNormalizer.unaccent(stringBuilder.toString());
        } catch(IOException e){
            logger.info("Ultimo caracter leido: " + lastCharRead);
            logger.info("Última subpalabra leida: " + stringBuilder.toString());
            e.printStackTrace();
        }

        return null;
    }

    private boolean isLetterOrDigit(int charcode){
        return Character.isAlphabetic(charcode) || Character.isDigit(charcode);
    }

    private boolean isValidSymbol(char charchode) {
        for(int i = 0; i< this.validWordMiddleSymbols.length(); i++){
            if(this.validWordMiddleSymbols.charAt(i) == charchode) return true;
        }
        return false;
    }
    private boolean isApostrophe(char character){
        return character == '\'';
    }

    private boolean isDigitSeparator(char character){
        return character == '.' || character == ',';
    }

    @Override
    public boolean isReady() {
        try {
            if(reader.ready()){
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
        return false;
    }

    @Override
    public void openFile(String path) throws IllegalArgumentException {
        //Path filePath = Paths.get(path);
        try {
            //reader = Files.newBufferedReader(filePath, charset);
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf-8"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Provided path doesn't match with any existing file");
        }
    }

    @Override
    public void closeFile() throws IOException {
        reader.close();
    }
}
