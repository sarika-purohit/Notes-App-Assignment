package com.example.notesappassignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class AddEditNoteFragment extends Fragment {

    private static final String ARG_NOTE_ID = "note_id";
    private static final String ARG_NOTE_TITLE = "note_title";
    private NoteDAO noteDAO;
    private long noteId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_note, container, false);
    }

    public static AddEditNoteFragment newInstance(Long noteId, String noteTitle) {
        AddEditNoteFragment fragment = new AddEditNoteFragment();
        Bundle args = new Bundle();
        if (noteId != null) {
            args.putLong(ARG_NOTE_ID, noteId);
        }
        if (noteTitle != null) {
            args.putString(ARG_NOTE_TITLE, noteTitle);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteDAO = new NoteDAO(requireContext());
        noteDAO.open();

        EditText editTextNoteTitle = view.findViewById(R.id.edit_text_note_title);
        Button buttonSave = view.findViewById(R.id.button_save_note);

        if (getArguments() != null) {
            noteId = getArguments().getLong(ARG_NOTE_ID, -1);
            String noteTitle = getArguments().getString(ARG_NOTE_TITLE, "");
            editTextNoteTitle.setText(noteTitle);
        }

        buttonSave.setOnClickListener(v -> {
            String title = editTextNoteTitle.getText().toString();
            if (noteId == -1) {
                noteDAO.addNote(title);
            } else {
                noteDAO.updateNote(noteId, title);
            }
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noteDAO.close();
    }
}