package com.monday.noteapp.feature_note.domain.repository

import androidx.lifecycle.LiveData
import com.monday.noteapp.feature_note.domain.model.Note

interface NoteRepository {

    fun getNote(): LiveData<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}