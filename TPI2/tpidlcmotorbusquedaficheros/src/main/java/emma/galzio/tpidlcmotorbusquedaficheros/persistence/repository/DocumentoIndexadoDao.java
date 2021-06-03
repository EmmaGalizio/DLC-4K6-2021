package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentoIndexadoDao implements BaseDao<DocumentoIndexadoEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertBatch(List<DocumentoIndexadoEntity> documentos){

        String sql = "INSERT INTO documentoIndexado(url) VALUES(?)";
        List<Object[]> batchArgsList = new ArrayList<>();

        for(DocumentoIndexadoEntity documento : documentos){
            Object[] objectArray = {documento.getUrl()};
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    @Override
    @Transactional
    public void updateBatch(List<DocumentoIndexadoEntity> modifiedValues) {

    }
}
