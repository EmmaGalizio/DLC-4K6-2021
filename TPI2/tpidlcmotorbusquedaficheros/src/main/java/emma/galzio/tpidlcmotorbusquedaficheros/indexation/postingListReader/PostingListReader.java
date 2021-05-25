package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListReader;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;

import java.io.IOException;

public interface PostingListReader {

    void loadPostingList(VocabularySlot vocabularySlot) throws IOException;

}
