package com.monday.noteapp.feature_note.domain.use_case

import com.monday.noteapp.feature_note.domain.model.InvalidNoteException
import com.monday.noteapp.feature_note.domain.model.Note
import com.monday.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {

        if(note.title.isBlank()) {
            throw InvalidNoteException("the title of the note can't be empty.")
        }

        if(note.body.isBlank()) {
            throw InvalidNoteException("the body of the note can't be empty.")
        }

        repository.insertNote(note)
    }
}