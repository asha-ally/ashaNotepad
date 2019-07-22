package com.example.ashanotepad.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ashanotepad.ViewNote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // onUpgrade();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY ,title TEXT,noteText TEXT, imageUrl TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            //sqLiteDatabase.execSQL("ALTER TABLE notes ADD COLUMN imageUrl INTEGER DEFAULT 0");

    }


    public long addNote(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",note.getId());
        contentValues.put("title", note.getTitle());
        contentValues.put("noteText", note.getNoteText());
        contentValues.put("imageUrl", note.getImage());
        long insert = sqLiteDatabase.insert("notes", null, contentValues);
        sqLiteDatabase.close();
        return insert;
    }

    public List<Note> getNotes() {


        Log.w("rage", "oya oya niaje");

        List<Note> noteList = new ArrayList<Note>();
        String query = "SELECT*FROM notes";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst() == true) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                note.setNoteText(cursor.getString(cursor.getColumnIndex("noteText")));
                noteList.add(note);
            }
            while (cursor.moveToNext() == true);
        }
        sqLiteDatabase.close();

        return noteList;
    }

    public Note getNoteById(int id) {
        Note note = new Note();
        String query = "SELECT* FROM notes WHERE id=?";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst() == true) {
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            note.setNoteText(cursor.getString(cursor.getColumnIndex("noteText")));
        }
        sqLiteDatabase.close();
        return note;


    }

    public void deleteNote(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String tableName = "notes";
        String whereClause = "id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        sqLiteDatabase.delete(tableName, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public int updateNote(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("noteText", note.getNoteText());
        String tableName = "notes";
        String whereClause = "id=?";


        return sqLiteDatabase.update(tableName, contentValues, whereClause,
                new String[]{String.valueOf(note.getId())});
    }



    }














