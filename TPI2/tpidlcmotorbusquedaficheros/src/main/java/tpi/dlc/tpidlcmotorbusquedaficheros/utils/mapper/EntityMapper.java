package tpi.dlc.tpidlcmotorbusquedaficheros.utils.mapper;

import java.util.List;

public interface EntityMapper<E, M> {

    E mapToEntity(M model);
    List<E> mapAllToEntity(List<M> models);
    M mapFromEntity(E entity);
    List<M> mapAllFromEntity(List<E> entities);

}
