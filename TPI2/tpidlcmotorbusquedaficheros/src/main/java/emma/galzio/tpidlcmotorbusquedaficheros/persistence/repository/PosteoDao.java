package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.PosteoEntity;
import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.VocabularyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosteoDao implements BaseDao<PosteoEntity> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertBatch(List<PosteoEntity> posteos){

        String sql = "INSERT INTO posteo(termino, documento, tf) VALUES(?,?,?)";
        List<Object[]> batchArgsList = new ArrayList<>();

        for(PosteoEntity posteo : posteos){
            Object[] objectArray =
                    {posteo.getId().getTermino(), posteo.getId().getDocumento(), posteo.getTf()};
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    @Transactional
    public void updateBatch(List<PosteoEntity> posteos){

        String sql = "UPDATE posteo SET tf=? WHERE termino = ? and documento = ? ";

        List<Object[]> batchArgsList = new ArrayList<>();

        for(PosteoEntity posteo : posteos){
            Object[] objectArray =
                    {posteo.getTf(), posteo.getId().getTermino(), posteo.getId().getDocumento()};
            batchArgsList.add(objectArray);
        }
        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

}
