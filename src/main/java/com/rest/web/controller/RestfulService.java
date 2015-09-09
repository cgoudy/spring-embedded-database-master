package com.rest.web.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rest.dao.NoteDao;
import com.rest.dao.NoteDaoImpl;
import com.rest.model.Note;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


@Path("/api/notes")
public class RestfulService {

    public static final String RESOURCE_URL = "api/notes";

    static NoteDao dao = new NoteDaoImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getNotes() {
        List<Note> notes = dao.findAll();
        return notes;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Note getNote(@PathParam("id") Integer id) {
    	Note note = dao.findById(id);
        return note;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(Note note, @Context UriInfo uriInfo) {
    	Long id = dao.insert(note);
        URI createdURI = URI.create(uriInfo.getBaseUri().toString() + RESOURCE_URL + "/" + id);
        note.setId(id);
        return Response
                .status(Status.CREATED)
                .contentLocation(createdURI)
                .entity(note)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/content") //you need this or else you get ambiguous path with getNotes()
    public List<Note> getNotesByContent(@QueryParam("content") String content) {
    	List<Note> notes = dao.findByContent(content);
        return notes;
    }

    /* PUT to overwrites (updates) a pre-existing task.  Optionally,
       it can store a new task as well.
    */
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{id}")
//    public Response doPut(@PathParam("id") Integer id, Task task, @Context UriInfo uriInfo) {
//
//        if (model.isTaskSaved(id)) {
//            // In case of a disagreement, the pathParam overrides
//            // any id that may be set in the POST body
//            if (id > 0) {
//                task.setId(id);
//            }
//            return updateExistingTask(task);
//        }
//        else {
//            return saveNewTask(task, uriInfo);
//        }
//    }

//    private Response updateExistingTask(Task task) {
//        model.update(task);
//        return Response
//                .status(200)
//                .entity(task)
//                .build();
//    }

//    @DELETE
//    @Path("{id}")
//    public Response deleteTask(@PathParam("id") Integer id) {
//        model.delete(id);
//        return Response.accepted().build();
//    }
}