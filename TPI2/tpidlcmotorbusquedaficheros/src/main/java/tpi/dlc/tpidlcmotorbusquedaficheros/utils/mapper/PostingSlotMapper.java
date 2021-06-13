package emma.galzio.tpidlcmotorbusquedaficheros.utils.mapper;

import emma.galzio.tpidlcmotorbusquedaficheros.indexation.structure.PostingSlot;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntityId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("postingSlotMaper")
public class PostingSlotMapper implements EntityMapper<PosteoEntity, PostingSlot> {

    public PosteoEntity mapToEntity(PostingSlot model){

        //DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
        //documentoIndexadoEntity.setUrl(model.getDocumentUrl());

        PosteoEntity posteoEntity = new PosteoEntity();
        //posteoEntity.setTermino(model.getToken());
        posteoEntity.setTf(model.getTokenFrecuency());
        //posteoEntity.setDocumentoIndexadoEntity(documentoIndexadoEntity);

        PosteoEntityId posteoEntityId = new PosteoEntityId();
        posteoEntityId.setDocumento(model.getDocumentUrl());
        posteoEntityId.setTermino(model.getToken());

        posteoEntity.setId(posteoEntityId);
        return posteoEntity;
    }

    @Override
    public List<PosteoEntity> mapAllToEntity(List<PostingSlot> models) {

        return models.stream().map((postingSlot) -> this.mapToEntity(postingSlot))
                                                    .collect(Collectors.toList());
    }

    @Override
    public PostingSlot mapFromEntity(PosteoEntity entity) {

        PostingSlot postingSlot = new PostingSlot();
        postingSlot.setDocumentUrl(entity.getId().getDocumento());
        postingSlot.setToken(entity.getId().getTermino());
        postingSlot.setTokenFrecuency(entity.getTf());

        return postingSlot;
    }

    @Override
    public List<PostingSlot> mapAllFromEntity(List<PosteoEntity> entities) {

        return entities.stream().map((posteoEntity) -> this.mapFromEntity(posteoEntity))
                                                            .collect(Collectors.toList());
    }
}
