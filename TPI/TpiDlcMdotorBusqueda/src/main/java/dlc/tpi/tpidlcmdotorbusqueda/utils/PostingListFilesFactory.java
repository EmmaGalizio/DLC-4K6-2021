package dlc.tpi.tpidlcmdotorbusqueda.utils;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.IndexationEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class PostingListFilesFactory {

    public RandomAccessFile getPostingListsFile(String postingListFileUrl){
        Logger logger  = LoggerFactory.getLogger(this.getClass());
        try {
            RandomAccessFile postingListsFile = new RandomAccessFile(postingListFileUrl, "rw");
            logger.info("File was created successfully");
            return postingListsFile;
        } catch (FileNotFoundException e) {
            logger.info("The file path does not match with an existing file, or cant be created");
            e.printStackTrace();
            return null;
        }
    }

    public RandomAccessFile getAuxiliarPostingListFile(String auxPath){
        try{
            File file = new File(auxPath);
            if(file.exists()) file.delete();
            RandomAccessFile auxiliarPostingListFile = new RandomAccessFile(auxPath, "rw");
            return auxiliarPostingListFile;

        } catch(FileNotFoundException e){
            System.out.println("Error con archivo auxiliar!");
            e.printStackTrace();
            return null;

        }
    }

}
