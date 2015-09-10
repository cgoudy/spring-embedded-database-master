package com.rest.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.rest.model.Note;

public class NoteDaoTest {

    private EmbeddedDatabase db;

    NoteDao noteDao;
    
    @Before
    public void setUp() {
    	db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.HSQL)
    		.addScript("db/sql/create-db.sql")
    		.addScript("db/sql/insert-data.sql")
    		.build();
    }

//    @Test
//	public void testFindByname() {
//		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
//		NoteDaoImpl noteDao = new NoteDaoImpl();
//		noteDao.setNamedParameterJdbcTemplate(template);
//		
//		Note note = noteDao.findById(1);
//	
//		Assert.assertNotNull(note);
//		Assert.assertEquals(1, note.getId().intValue());
//		Assert.assertEquals("Pickup the milk.", note.getBody());
//	
//	}

    @After
    public void tearDown() {
        db.shutdown();
    }

}