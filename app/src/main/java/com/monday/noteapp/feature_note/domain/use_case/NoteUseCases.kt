package com.monday.noteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val addNote: AddNote,
    val deleteNote: DeleteNote
)