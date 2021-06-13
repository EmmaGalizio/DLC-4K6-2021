package tpi.dlc.tpidlcmotorbusquedaficheros.persistence.repository;

import java.util.List;

public interface BaseDao<T> {

    void insertBatch(List<T> newValues);

    void updateBatch(List<T> modifiedValues);
}
