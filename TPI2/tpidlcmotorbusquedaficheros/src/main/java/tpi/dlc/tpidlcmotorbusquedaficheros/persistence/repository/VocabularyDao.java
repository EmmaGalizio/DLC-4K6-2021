package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VocabularyDao implements BaseDao<VocabularyEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertBatch(List<VocabularyEntity> tokens){

        String sql = "INSERT INTO vocabulary(token, maxTf, nr) VALUES(?,?,?)";
        List<Object[]> batchArgsList = new ArrayList<>();

        for(VocabularyEntity token : tokens){
            Object[] objectArray = {token.getToken(), token.getMaxTf(), token.getNr()};
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    @Transactional
    public void updateBatch(List<VocabularyEntity> tokens){

        String sql = "UPDATE vocabulary SET maxTf=?, nr=? WHERE token = ? ";

        List<Object[]> batchArgsList = new ArrayList<>();

        for(VocabularyEntity token : tokens){
            Object[] objectArray = {token.getMaxTf(), token.getNr(), token.getToken()};
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

}
