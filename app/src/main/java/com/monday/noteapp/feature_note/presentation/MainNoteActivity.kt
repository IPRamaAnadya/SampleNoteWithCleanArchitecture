package com.monday.noteapp.feature_note.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.monday.noteapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_note)
    }
}