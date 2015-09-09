package com.rest.dao;

import java.util.List;

import com.rest.model.Note;

public interface NoteDao {

	Note findById(Integer id);
	
	List<Note> findAll();
	
	Long insert(Note note);
	
	List<Note> findByContent(String content);

}