package com.monday.noteapp.feature_note.presentation.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.monday.noteapp.databinding.ItemNoteBinding
import com.monday.noteapp.feature_note.domain.model.Note

class NotesAdapter(private val onAction: (Note, NoteAction) -> Unit): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val notes = mutableListOf<Note>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Note>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Note) {
            with(binding){
                tvNoteTitle.text = data.title
                tvNoteBody.text = data.body
                ivNoteDelete.setOnClickListener{ onAction(data, NoteAction.DELETE) }
            }
            itemView.setOnClickListener{ onAction(data, NoteAction.SHOW) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(notes[position])
    }

    override fun getItemCount(): Int = notes.size
}

enum class NoteAction{
    SHOW,
    DELETE
}