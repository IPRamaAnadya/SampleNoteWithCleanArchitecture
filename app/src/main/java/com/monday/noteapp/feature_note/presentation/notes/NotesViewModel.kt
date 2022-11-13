package com.monday.noteapp.feature_note.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monday.noteapp.feature_note.domain.model.Note
import com.monday.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    private val recentlyDeletedNote = MutableLiveData<Note?>()

    fun getNotes() = noteUseCases.getNotes.invoke()

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNote.invoke(note)
            recentlyDeletedNote.value = note
        }
    }

    fun restoreNote() {
        viewModelScope.launch {
            recentlyDeletedNote.value?.let { noteUseCases.addNote.invoke(it) }
            recentlyDeletedNote.value = null
        }
    }
}