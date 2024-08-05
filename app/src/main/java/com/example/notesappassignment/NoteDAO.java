package com.example.notesappassignment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private SQLiteDatabase database;
    private final SQLiteOpenHelper dbHelper;

    public NoteDAO(Context context) {
        dbHelper = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " + TABLE_NOTES + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
                onCreate(db);
            }
        };
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addNote(String title) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        return database.insert(TABLE_NOTES, null, values);
    }

    public void updateNote(long id, String title) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        database.update(TABLE_NOTES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteNote(long id) {
        database.delete(TABLE_NOTES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NOTES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                notes.add(new Note(id, title));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }
}
