package emma.galzio.tpidlcmotorbusquedaficheros.indexation;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository.DocumentoIndexadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentController implements IDocumentController{

    @Autowired
    private DocumentoIndexadoRepository repository;

    @Override
    public List<String> listAllIndexedDocuments() {

        List<DocumentoIndexadoEntity> documentoIndexadoEntities = repository.findAll();

        if(documentoIndexadoEntities != null && !documentoIndexadoEntities.isEmpty()) {
            return documentoIndexadoEntities.stream().map((documento) -> documento.getUrl())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();

    }

    @Override
    public Integer countDocuments() {
        return repository.countAll();
    }
}
