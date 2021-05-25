package dlc.tpi.tpidlcmdotorbusqueda.indexation.postingListWriter;

import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.ModifiedToken;
import dlc.tpi.tpidlcmdotorbusqueda.indexation.structure.VocabularySlot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PostingListWriter {

    void writeAndClean(Map<String, VocabularySlot> vocabulary, List<ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException;
}
