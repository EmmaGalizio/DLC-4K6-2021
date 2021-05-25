package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListReader;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import emma.galzio.tpidlcmotorbusquedaficheros.utils.PostingListFilesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Service("postingListFileReader")
public class PostingListFileReader implements PostingListReader {

    @Autowired
    private PostingListFilesFactory postingListFilesFactory;


    public void loadPostingList(VocabularySlot vocabularySlot) throws IOException {


        RandomAccessFile postingListsFile = postingListFilesFactory.getPostingListsFile();

        List<PostingSlot> postingSlotList = new ArrayList<>();
        postingListsFile.seek(vocabularySlot.getPostingListStartIndex());
        int slotCount = 0;
        String token = postingListsFile.readUTF(); //Este atributo sirve solo para buscar el token a modificar al momento de grabar la lista

        for(int i = 0; i < vocabularySlot.getNr(); i++){


            String documentUrl = postingListsFile.readUTF();
            Integer tf = postingListsFile.readInt();
            PostingSlot postingSlot = new PostingSlot(documentUrl, tf);
            postingSlotList.add(postingSlot);
            //El archivo no se cierra, se tendrá que cerrar cuando se verifique que el índice está al día
        }
        //postingListsFile.close();
        vocabularySlot.setPostingList(postingSlotList);
    }

}
