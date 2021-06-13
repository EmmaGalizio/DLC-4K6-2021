package tpi.dlc.tpidlcmotorbusquedaficheros.indexation.postingListWriter;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.ModifiedToken;
import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;

import java.util.Map;

public interface PostingListWriter {

    void writeAndClean(Map<String, VocabularySlot> vocabulary, Map<String, ModifiedToken> modifiedTokens);
}
