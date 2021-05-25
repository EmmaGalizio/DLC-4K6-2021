package dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListReader;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.PostingSlot;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.VocabularySlot;
import dlc.tpi.tpidlcmdotorbusqueda.utils.PostingListFilesFactory;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

@Service("postingListFileReader")
public class PostingListFileReader implements PostingListReader {


    public void loadPostingList(VocabularySlot vocabularySlot, String postingListUrl) throws IOException {

        PostingListFilesFactory postingListFilesFactory = new PostingListFilesFactory();
        RandomAccessFile postingListsFile = postingListFilesFactory.getPostingListsFile(postingListUrl);

        List<PostingSlot> postingSlotList = new ArrayList<>();
        postingListsFile.seek(vocabularySlot.getPostingListStartIndex());
        int slotCount = 0;

        for(int i = 0; i < vocabularySlot.getNr(); i++){
            String token = postingListsFile.readUTF(); //Este atributo sirve solo para buscar el token a modificar al momento de grabar la lista

            String documentUrl = postingListsFile.readUTF();
            Integer tf = postingListsFile.readInt();
            PostingSlot postingSlot = new PostingSlot(documentUrl, tf);
            postingSlotList.add(postingSlot);
            //El archivo no se cierra, se tendrá que cerrar cuando se verifique que el índice está al día
        }
        vocabularySlot.setPostingList(postingSlotList);
    }

}
