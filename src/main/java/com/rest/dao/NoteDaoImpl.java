package com.rest.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.rest.model.Note;

@Repository
public class NoteDaoImpl implements NoteDao {
	
	private EmbeddedDatabase db;

	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Override
	public Note findById(Integer id) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        
		String sql = "SELECT * FROM notes WHERE id=:id";
		
        Note result = getNamedParameterJdbcTemplate().queryForObject(
                    sql,
                    params,
                    new NoteMapper());
                            
        return result;
        
	}

	@Override
	public List<Note> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		String sql = "SELECT * FROM notes";
		
        List<Note> result = getNamedParameterJdbcTemplate().query(sql, params, new NoteMapper());
        
        return result;
        
	}
	
	@Override
	public Long insert(Note note) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("body", note.getBody());
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT into notes(body) VALUES(:body)";
		int result = getNamedParameterJdbcTemplate().update(sql, paramSource, keyHolder);
		Long id = keyHolder.getKey().longValue();
		return id;
				
	}
	
	@Override
	public List<Note> findByContent(String content) {
		
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("content", "%" + content.toLowerCase() + "%");
        
		String sql = "SELECT * FROM notes WHERE LCASE(body) LIKE :content";
		
        List<Note> result = getNamedParameterJdbcTemplate().query(sql, params, new NoteMapper());
                            
        return result;
	}

	private static final class NoteMapper implements RowMapper<Note> {

		public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
			Note note = new Note();
			note.setId(rs.getLong("id"));
			note.setBody(rs.getString("body"));
			return note;
		}
	}
	
	private NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		if (db == null) {
			db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("db/sql/create-db.sql")
				.build();
			namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(db);
		}
		return namedParameterJdbcTemplate;
	}

}