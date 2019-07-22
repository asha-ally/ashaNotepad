package com.example.ashanotepad.DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class Note {
    private int id;
    private String title;
    private String noteText;
    private  String image;

    public Note(int id, String title, String noteText, String image) {
        this.id = id;
        this.title = title;
        this.noteText = noteText;
        this.image = image;
    }

    public Note() {
    }

    public Note(String title, String noteText,String image) {
        this.title = title;
        this.noteText = noteText;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}

    public String getNote_all(){
        ArrayList<HashMap<String,String>> noteArray= new ArrayList<>();
        HashMap<String,String> currentNote= new HashMap<>();

        currentNote.put("id", String.valueOf(getId())+ "|");
        currentNote.put("title",getTitle()+"|");
        currentNote.put("noteText",getNoteText()+"|");
        currentNote.put("image",getImage()+"|");

        noteArray.add(currentNote);
        return noteArray.toString();
    }
}
