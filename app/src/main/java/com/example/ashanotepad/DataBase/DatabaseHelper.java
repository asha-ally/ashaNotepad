package com.example.ashanotepad.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,noteText TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addNote(Note note) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("noteText", note.getNoteText());
        long insert = sqLiteDatabase.insert("notes", null, contentValues);
        sqLiteDatabase.close();
        return insert;
    }
    public List<Note>getNotes(){
        List<Note>noteList=new ArrayList<Note>();
        String query="SELECT*FROM notes";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        if (cursor.moveToFirst()==true){
            do {
                Note note=new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex("id")));
                note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                note.setNoteText(cursor.getString(cursor.getColumnIndex("noteText")));
                noteList.add(note);
            }
            while (cursor.moveToNext()==true);
        }
        sqLiteDatabase.close();
        return noteList;
    }
    public Note getNoteById(int id){
        Note note= new Note();
        String query="SELECT* FROM notes WHERE id=?";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()==true){
            note.setId(cursor.getInt(cursor.getColumnIndex("id")));
            note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            note.setNoteText(cursor.getString(cursor.getColumnIndex("noteText")));
        }
        sqLiteDatabase.close();
        return note;


    }

    public void deleteNote (int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String tableName = "notes";
        String whereClause = "id=?";
        String[]whereArgs= new String[]{String.valueOf(id)};
        sqLiteDatabase.delete(tableName,whereClause,whereArgs);
    }
}



