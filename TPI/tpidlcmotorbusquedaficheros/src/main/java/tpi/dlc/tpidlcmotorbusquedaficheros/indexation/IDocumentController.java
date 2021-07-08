package tpi.dlc.tpidlcmotorbusquedaficheros.indexation;

import java.util.List;

public interface IDocumentController {

    List<String> listAllIndexedDocuments();

    Integer countDocuments();
}
