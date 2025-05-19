package com.spring.springboot.notes.service;

import com.spring.springboot.notes.pojo.Notes;
import com.spring.springboot.notes.pojo.NotesListByUserRequestPojo;
import com.spring.springboot.notes.pojo.NotesListRequestPojo;
import com.spring.springboot.response.ResponseCode;

import java.util.List;

public interface NotesService
{
    List<Notes> getNotesList(NotesListRequestPojo notesListRequest);
    int getNotesCount(NotesListRequestPojo notesListRequest);
    int updateNote(Notes note);
    List<Notes> getNotesListByUserUuid(NotesListByUserRequestPojo notesListByUserRequest);
    int getNotesCountByUserUuid(NotesListByUserRequestPojo notesListByUserRequest);
    int addNote(Notes note);
    int deleteNote(String uuid,String userUuid);
    int deleteNoteData(String uuid);
    int editNote(Notes note,String userUuid);
}
