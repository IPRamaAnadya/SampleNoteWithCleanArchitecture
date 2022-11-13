package com.monday.noteapp.feature_note.presentation.notes

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.monday.noteapp.R
import com.monday.noteapp.databinding.FragmentNotesBinding
import com.monday.noteapp.feature_note.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding
    private val notesViewModel: NotesViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        notesAdapter = NotesAdapter { note, action ->
            when(action) {
                NoteAction.DELETE -> deleteNote(note)
                NoteAction.SHOW -> showDetail(note.id)
            }
        }

        with(binding.rvNotes) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = notesAdapter
            setHasFixedSize(true)
        }

        notesViewModel.getNotes().observe(viewLifecycleOwner) {
            notesAdapter.setData(it)
        }

        binding.btnAddNote.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_notesFragment_to_noteDetailFragment)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    private fun showDetail(id: Int?) {
        val action = NotesFragmentDirections.actionNotesFragmentToNoteDetailFragment(id ?: return)
        Navigation.findNavController(binding.root).navigate(action)
    }

    private fun deleteNote(note: Note) {
        notesViewModel.deleteNote(note)
        val deleteSnackbar = Snackbar.make(requireContext(), binding.root, "Note deleted", 3000)
        deleteSnackbar.setAction("Undo") {
            notesViewModel.restoreNote()
        }
        deleteSnackbar.show()
    }

    companion object {
        const val TAG = "NotesFragment"
    }


}