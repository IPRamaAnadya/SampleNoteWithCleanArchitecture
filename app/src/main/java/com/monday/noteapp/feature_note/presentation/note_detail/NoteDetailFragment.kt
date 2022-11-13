package com.monday.noteapp.feature_note.presentation.note_detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.monday.noteapp.R
import com.monday.noteapp.databinding.FragmentNoteDetailBinding
import com.monday.noteapp.feature_note.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private val noteViewModel: NoteViewModel by viewModels()
    val args: NoteDetailFragmentArgs by navArgs()
    private var id: Int? = null
    private var currentNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.id != 0) {
            id = args.id
            setupNote()
        }

        binding.btnSaveNote.setOnClickListener {
            val title = binding.etNoteTitle.text.toString().trim()
            val body = binding. etNoteBody.text.toString().trim()

            if (title.isBlank() || body.isBlank()) {
                Toast.makeText(requireContext(), "Title or body can't be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(title, body, System.currentTimeMillis(), id)

            GlobalScope.launch {
                noteViewModel.addNote(note)
                backToNotes()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val title = binding.etNoteTitle.text.toString().trim()
                val body = binding. etNoteBody.text.toString().trim()

                if (title.isNotBlank() && body.isNotBlank()) {

                    if (currentNote?.title == title && currentNote?.body == body) {
                        backToNotes()
                        return
                    }

                    val builder1 = AlertDialog.Builder(context)
                    builder1.setMessage("Simpan note?")
                    builder1.setCancelable(true)

                    builder1.setPositiveButton(
                        "Yes"
                    ) { dialog, _ ->
                        val note = Note(title, body, System.currentTimeMillis(), id)
                        dialog.dismiss()
                        GlobalScope.launch {
                            noteViewModel.addNote(note)
                            backToNotes()
                        }
                    }

                    builder1.setNegativeButton(
                        "No"
                    ) { dialog, _ ->
                        dialog.dismiss()
                        backToNotes()
                    }

                    val alert11: AlertDialog = builder1.create()
                    alert11.show()
                } else {
                    backToNotes()
                }
            }
        })
    }

    private fun backToNotes() {
        activity?.runOnUiThread {
            Navigation.findNavController(binding.root).navigate(R.id.action_noteDetailFragment_to_notesFragment)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun setupNote() {
        GlobalScope.launch {
            id?.let {
                noteViewModel.getNote(it).also { note ->
                    currentNote = note
                    activity?.runOnUiThread {
                        with(binding) {
                            etNoteTitle.setText(note?.title ?: "")
                            etNoteBody.setText(note?.body ?: "")
                        }
                    }
                }
            }
        }
    }

}