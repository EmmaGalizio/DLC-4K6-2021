package emma.galzio.tpidlcmotorbusquedaficheros.persistence.repository;

import emma.galzio.tpidlcmotorbusquedaficheros.persistence.entity.DocumentoIndexadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DocumentoIndexadoDaoJdbcTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean insertDocumento(String url){
        DocumentoIndexadoEntity documentoIndexadoEntity = new DocumentoIndexadoEntity();
        documentoIndexadoEntity.setUrl(url);
        String sql = "INSERT INTO documentoIndexado(url) VALUES(?)";
        PreparedStatementCallback<Boolean> preparedStatementCallback = new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                preparedStatement.setString(1,documentoIndexadoEntity.getUrl());
                return preparedStatement.execute();
            }
        };

        return jdbcTemplate.execute(sql, preparedStatementCallback);

    }
}
