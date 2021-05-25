package emma.galzio.tpidlcmotorbusquedaficheros.utils;

import java.io.IOException;

public interface FileReader {

    String readNextWord();
    boolean isReady();
    void openFile(String path) throws IllegalArgumentException;
    void closeFile() throws IOException;
}
