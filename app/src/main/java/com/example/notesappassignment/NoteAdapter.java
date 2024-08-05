package com.example.notesappassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Note> notes;
    private final OnNoteClickListener onEditClickListener;
    private final OnNoteClickListener onDeleteClickListener;

    public interface OnNoteClickListener {
        void onClick(int position);
    }

    public NoteAdapter(List<Note> notes, OnNoteClickListener onEditClickListener, OnNoteClickListener onDeleteClickListener) {
        this.notes = notes;
        this.onEditClickListener = onEditClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void removeNoteAt(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notes.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNoteTitle.setText(note.getTitle());
        holder.buttonEditNote.setOnClickListener(v -> onEditClickListener.onClick(position));
        holder.buttonDeleteNote.setOnClickListener(v -> onDeleteClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNoteTitle;
        Button buttonEditNote;
        Button buttonDeleteNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNoteTitle = itemView.findViewById(R.id.text_view_note_title);
            buttonEditNote = itemView.findViewById(R.id.button_edit_note);
            buttonDeleteNote = itemView.findViewById(R.id.button_delete_note);
        }
    }
}
