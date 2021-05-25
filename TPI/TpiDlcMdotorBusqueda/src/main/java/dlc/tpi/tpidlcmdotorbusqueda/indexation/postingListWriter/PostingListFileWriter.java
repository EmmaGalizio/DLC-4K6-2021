package dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListWriter;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.ModifiedToken;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.PostingSlot;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.VocabularySlot;
import dlc.tpi.tpidlcmdotorbusqueda.utils.PostingListFilesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.List;
import java.util.Map;

@Service("postingListFileWriter")
public class PostingListFileWriter implements PostingListWriter {

    private PostingListFilesFactory postingListFilesFactory;
    private Logger logger;

    @Override
    public void writeAndClean(Map<String, VocabularySlot> vocabulary, List<ModifiedToken> modifiedTokens,
                                                            String postingListsUrl, String auxFileUrl) {

        if(modifiedTokens == null) return;

        if(logger == null) logger = LoggerFactory.getLogger(this.getClass());

        if(postingListFilesFactory == null) postingListFilesFactory = new PostingListFilesFactory();

        RandomAccessFile postingListsFile = postingListFilesFactory.getPostingListsFile(postingListsUrl);
        RandomAccessFile auxiliarPostingListFIle = postingListFilesFactory.getAuxiliarPostingListFile(auxFileUrl);

        try{
            for(ModifiedToken modifiedToken : modifiedTokens){
                logger.info("================================");
                logger.info("Iniciando escritura del token: " + modifiedToken.getToken());

                while(modifiedToken.getStartOfListIndex() != null &&
                            postingListsFile.getFilePointer() < modifiedToken.getStartOfListIndex()){

                    long listStartIndex = auxiliarPostingListFIle.getFilePointer();
                    String uneditedToken = postingListsFile.readUTF();
                    VocabularySlot uneditedVocabularySlot = vocabulary.get(uneditedToken);
                    uneditedVocabularySlot.setPostingListStartIndex(listStartIndex);

                    auxiliarPostingListFIle.writeUTF(uneditedToken);
                    for(int i = 0; i < uneditedVocabularySlot.getNr(); i++){
                        auxiliarPostingListFIle.writeUTF(postingListsFile.readUTF());
                        auxiliarPostingListFIle.writeInt(postingListsFile.readInt());
                    }

                    logger.info("Se escribieron los bytes de las listas de posteo sin anteriores modificar");
                }
                VocabularySlot vocabularySlot = vocabulary.get(modifiedToken.getToken());
                vocabularySlot.setPostingListStartIndex(auxiliarPostingListFIle.getFilePointer());

                auxiliarPostingListFIle.writeUTF(vocabularySlot.getToken());

                logger.info("Comienzo de escritura de las listas de posteo nuevas o modificadas");
                for(PostingSlot postingSlot: vocabularySlot.getPostingList()){
                    auxiliarPostingListFIle.writeUTF(postingSlot.getDocumentUrl());
                    auxiliarPostingListFIle.writeInt(postingSlot.getTokenFrecuency());
                }
                vocabularySlot.clearPostingList();
                logger.info("Fin de la escritura de las listas de posteo para " + modifiedToken.getToken());

            }
            logger.info("Se escribieron con éxito las listas de posteo modificadas");
            logger.info("==================================");
            logger.info("Se intentaran cerrar los archivos de las listas");
            postingListsFile.close();
            auxiliarPostingListFIle.close();
            logger.info("Se cerraron los archivos de las listas");
            logger.info("======================================");
            logger.info("Se intentará eliminar el archivo de listas original");
            File originalPostingListsFile = new File(postingListsUrl);
            if(!originalPostingListsFile.delete()){
                logger.info("No se pudo eliminar el archivo de listas original");
                throw new IOException("No se pudo eliminar el archivo de listas original");
            }
            logger.info("Se pudo eliminar el archivo de listas original");
            logger.info("===========================================");
            logger.info("Se intentará renombrar el archivo auxiliar");
            File newPostingListFile =  new File(auxFileUrl);
            newPostingListFile.renameTo(originalPostingListsFile);
            logger.info("Se renombró el archivo auxiliar");
            logger.info("=====================================");

        }catch(Exception e){
            logger.error("Ocurrio un error al escribi las listas de posteo: " + e.getMessage());
            e.printStackTrace();

        }

        /*
        * FORMA AUXILIAR DE COPIAR LAS LISTAS QUE SE MANTIENEN IGUALES:
        * while(postingListsFile.getFilePointer() < modifiedToken.getStartOfListIndex()){
                    auxiliarPostingListFIle.writeByte(postingListsFile.readByte());
                }
        *
        * */




    }
}
