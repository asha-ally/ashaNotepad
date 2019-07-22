package com.example.ashanotepad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;
import com.example.ashanotepad.adapters.NotesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity<item> extends AppCompatActivity {
    ListView listView;
    List<Note> noteList;
    ImageView fab;
    private DrawerLayout drawer;
    private String title;
    private String noteText;
    private String imageUrl;
    private String GET_NOTES_API_URL = "https://akirachixnotesapi.herokuapp.com/api/v1/notes";
    private String TAG = "NOTES_API_RESPONSE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.ListView);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddNoteActivity.class));
            }
        });

        String url = "https://akirachixnotesapi.herokuapp.com/api/v1/notes/";


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displayNotes() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
        noteList = new ArrayList<Note>();
        noteList = databaseHelper.getNotes();
        Log.d("myNotes", "My database has" + noteList.size() + "notes");
        NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(), 0, noteList);
        listView.setAdapter(notesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note clickedNote = noteList.get(i);
                Intent intent = new Intent(getBaseContext(), ViewNote.class);
                intent.putExtra("NOTE_ID", clickedNote.getId());
                startActivity(intent);

            }
        });
    }

    public void displayNames() {
        List<String> nameList = new ArrayList<String>();
        nameList.add("Osman Shariff");
        nameList.add("Asha Ali");
        nameList.add("Mustafa Ramadhan");
        nameList.add("Joan Aluka");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        displayNotes();
        getNotes();

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

    private List<Note> getNotes(){
        noteList=new ArrayList<Note>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, GET_NOTES_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.d("tag",s);

                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        int id=jsonObject.getInt("id");
                        String title=jsonObject.getString("title");
                        String noteText=jsonObject.getString("noteText");
                        Note note=new Note(id,title,noteText,"");
                        noteList.add(note);

                    }
                    NotesAdapter notesAdapter = new NotesAdapter(getBaseContext(), 0, noteList);
                    listView.setAdapter(notesAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Note clickedNote = noteList.get(i);
                            Intent intent = new Intent(getBaseContext(), ViewNote.class);
                            intent.putExtra("NOTE_ID", clickedNote.getId());
                            startActivity(intent);

                        }
                    });
                }
                catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("error",volleyError.getMessage());
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);
        return noteList;
    }


}

