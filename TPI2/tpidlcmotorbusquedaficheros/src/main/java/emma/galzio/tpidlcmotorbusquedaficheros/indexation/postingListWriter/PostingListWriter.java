package emma.galzio.tpidlcmotorbusquedaficheros.indexation.postingListWriter;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PostingListWriter {

    void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String,ModifiedToken> modifiedTokens, String postingListsUrl, String auxFileUrl) throws IOException;
}
