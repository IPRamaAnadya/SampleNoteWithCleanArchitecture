package com.monday.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val body: String,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
) {
}

class InvalidNoteException(message: String): Exception(message)