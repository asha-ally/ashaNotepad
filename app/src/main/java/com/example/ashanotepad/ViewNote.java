package com.example.ashanotepad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ViewNote extends AppCompatActivity {
int noteId;
private static ImageView bitmap;
TextView tvTitle;
TextView tvNoteText;
Button btnDelete;
ImageView imageView;
Button btnEdit;
NetworkImageView imageViewB;


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
        btnEdit=findViewById(R.id.btnEdit);
        imageView=findViewById(R.id.imageView);
        imageViewB=findViewById(R.id.imageViewB);


        displayNote();


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(),"notes",null,1);
                databaseHelper.deleteNote(noteId);
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(),EditNote.class);
                intent.putExtra("NOTE_ID",noteId);
                startActivity(intent);

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
//        Bitmap bmp = BitmapFactory.decodeFile( imageRoot+ File.separator + "yourimage.jpg");
//        imageView.setImageBitmap(bmp);



        Log.d("ehee", "your image is"+R.drawable.scape);
        //imageViewB.setImageResource(R.drawable.scape);

//        try{
//            InputStream pic = getAssets().open(deFile);
//            Drawable d=Drawable.createFromStream(pic,null);
//            imageView.setImageDrawable(d);
//            pic.close();
//        } catch (IOException ex){
//            return;
//        }



        //imageView.setImageBitmap(BitmapFactory.decodeFile("assets/download.jpg"));


  }
//    static final String appDirectoryName = "Notepad";
//    static final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
//            Environment.DIRECTORY_PICTURES), appDirectoryName);

    }


