package com.example.notesappassignment;
public class Note {
    private long id;
    private String title;

    public Note(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

