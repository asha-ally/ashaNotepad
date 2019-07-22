package com.example.ashanotepad;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;

import java.io.File;

import static com.example.ashanotepad.MainActivity.imageRoot;

public class EditNote extends AppCompatActivity {
   EditText etTitle;
   EditText etNote;
   Button btnAddPhoto;
   int noteId;
   Button btnEdit;
   String title;
   String  noteText;
   String imageUrl;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getNoteId();
        etTitle=findViewById(R.id.etTitle);
        etNote=findViewById(R.id.etNote);
        btnEdit=findViewById(R.id.btnEdit);
        btnAddPhoto=findViewById(R.id.btnAddPhoto);
        displayNote();


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title=etTitle.getText().toString();
                noteText=etNote.getText().toString();
                imageUrl=imageRoot.getAbsolutePath();

                Note note=new Note(title,noteText,imageUrl);
                DatabaseHelper databaseHelper=new DatabaseHelper(getBaseContext(),"notes",null,1);
                long rows=databaseHelper.updateNote(note);
                finish();

            }

        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                imageRoot.mkdirs();
                final File image = new File(imageRoot, "image1.jpg");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getNoteId(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            noteId=bundle.getInt("NOTE_ID",0);
        }
    }

    public void displayNote(){
        DatabaseHelper databaseHelper= new DatabaseHelper(getBaseContext(),"notes",null,1);
        Note note= databaseHelper.getNoteById(noteId);
        etTitle.setText(note.getTitle());
        etNote.setText(note.getNoteText());
    }

}
