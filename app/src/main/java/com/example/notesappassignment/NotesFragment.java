package com.example.notesappassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotesFragment extends Fragment {

    private NoteDAO noteDAO;
    private NoteAdapter noteAdapter;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteDAO = new NoteDAO(requireContext());
        noteDAO.open();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<Note> notes = noteDAO.getAllNotes();
        noteAdapter = new NoteAdapter(notes, this::onEditNote, this::onDeleteNote);
        recyclerView.setAdapter(noteAdapter);

        Button buttonAddNote = view.findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showAddEditNoteFragment(null, null);
        });
    }

    private void onEditNote(int position) {
        Note note = noteAdapter.getNoteAt(position);
        ((MainActivity) requireActivity()).showAddEditNoteFragment(note.getId(), note.getTitle());
    }

    private void onDeleteNote(int position) {
        Note note = noteAdapter.getNoteAt(position);
        noteDAO.deleteNote(note.getId());
        noteAdapter.removeNoteAt(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noteDAO.close();
    }
}
