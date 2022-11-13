package com.monday.noteapp.feature_note.domain.use_case

import androidx.lifecycle.LiveData
import com.monday.noteapp.feature_note.domain.model.Note
import com.monday.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotes(
    private val repository: NoteRepository
) {

    operator fun invoke(): LiveData<List<Note>> {
        return repository.getNote()
    }
}