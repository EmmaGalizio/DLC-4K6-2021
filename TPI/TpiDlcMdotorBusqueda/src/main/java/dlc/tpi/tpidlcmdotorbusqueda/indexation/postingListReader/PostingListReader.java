package dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListReader;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.VocabularySlot;

import java.io.IOException;

public interface PostingListReader {

    void loadPostingList(VocabularySlot vocabularySlot, String postingListUrl) throws IOException;

}
