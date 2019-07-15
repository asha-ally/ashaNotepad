package com.example.ashanotepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;
import com.example.ashanotepad.adapters.NotesAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity<item> extends AppCompatActivity {
ListView listView;
List<Note> noteList;
ImageView fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=findViewById(R.id.ListView);
        fab=findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),AddNoteActivity.class));
            }
        });
    }

    private void displayNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
        noteList = new ArrayList<Note>();
        noteList = databaseHelper.getNotes();
        Log.d("myNotes", "My database has" + noteList.size() + "notes");
        NotesAdapter notesAdapter=new NotesAdapter(getBaseContext(),0,noteList);
        listView.setAdapter(notesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note clickedNote= noteList.get(i);
                Intent intent=new Intent(getBaseContext(),ViewNote.class);
                intent.putExtra("NOTE_ID",clickedNote.getId());
                startActivity(intent);

            }
        });
    }

public  void displayNames(){
        List<String>nameList=new ArrayList<String>();
        nameList.add("Osman Shariff");
        nameList.add("Asha Ali");
        nameList.add("Mustafa Ramadhan");
        nameList.add("Joan Aluka");
    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nameList);
    listView.setAdapter(arrayAdapter);

}
    @Override
    protected void onResume() {
        super.onResume();
        displayNotes();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    static final String appDirectoryName = "Notepad";
    static final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), appDirectoryName);
}



//    @Override
//    public boolean onOptionsItemSelected MenuItem MenuItem android.view.MenuItem item;
//        item;
//        item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


