package emma.galzio.tpidlcmotorbusquedaficheros.utils;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.IndexationEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Service
@Scope("singleton")
public class PostingListFilesFactory {

    private RandomAccessFile postingListsFile;


    public RandomAccessFile getPostingListsFile(){

        Logger logger  = LoggerFactory.getLogger(this.getClass());
        try {
            if(postingListsFile != null && postingListsFile.getFD().valid()) return postingListsFile;
            postingListsFile = new RandomAccessFile(IndexationEngine.POSTING_LISTS_FILE_PATH, "rw");
            logger.info("File was created successfully");
            return postingListsFile;
        } catch (IOException e) {
            logger.info("The file path does not match with an existing file, or cant be created");
            e.printStackTrace();
            return null;
        }
    }

    public RandomAccessFile getAuxiliarPostingListFile(String auxPath){
        try{
            File file = new File(auxPath);
            if(!file.exists()) file.createNewFile();
            //file.createNewFile();
            RandomAccessFile auxiliarPostingListFile = new RandomAccessFile(auxPath, "rw");
            return auxiliarPostingListFile;

        } catch(IOException e){
            System.out.println("Error con archivo auxiliar!");
            e.printStackTrace();
            return null;

        }
    }

}
