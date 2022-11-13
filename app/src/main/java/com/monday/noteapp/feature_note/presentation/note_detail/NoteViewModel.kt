package com.monday.noteapp.feature_note.presentation.note_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monday.noteapp.feature_note.domain.model.Note
import com.monday.noteapp.feature_note.domain.use_case.AddNote
import com.monday.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    suspend fun getNote(id: Int) = noteUseCases.getNote.invoke(id)

    suspend fun addNote(note: Note) = noteUseCases.addNote.invoke(note)
}