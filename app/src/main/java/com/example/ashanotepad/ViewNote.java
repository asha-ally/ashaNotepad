package com.example.ashanotepad;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;

public class ViewNote extends AppCompatActivity {
int noteId;
TextView tvTitle;
TextView tvNoteText;
Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getNoteId();
        tvTitle=findViewById(R.id.tvTitle);
        tvNoteText=findViewById(R.id.tvNoteText);
        btnDelete=findViewById(R.id.btnDelete);
        displayNote();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes",null,1);
                databaseHelper.deleteNote(noteId);
                finish();
            }
        });

    }
    public void getNoteId(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            noteId=bundle.getInt("NOTE_ID",0);

        }

    }
    public void displayNote(){
        DatabaseHelper databaseHelper= new DatabaseHelper(getBaseContext(),"notes",null,1);
        Note note= databaseHelper.getNoteById(noteId);
        tvTitle.setText(note.getTitle());
        tvNoteText.setText(note.getNoteText());
  }

}
