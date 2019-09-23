package com.example.ashanotepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashanotepad.DataBase.DatabaseHelper;
import com.example.ashanotepad.DataBase.Note;
import com.example.ashanotepad.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.example.ashanotepad.MainActivity.imageRoot;

public class AddNoteActivity extends AppCompatActivity {
    private static ImageView bitmap;
    private EditText etTitle;
    private EditText etNote;
    private Button btnAddPhoto;
    private Button btnAddVoiceNote;
    private Button btnSave;
    private String title;
    private String noteText;
    private String imageUrl;
    private ImageView imgView;
    private String NOTES_API_URL="https://akirachixnotesapi.herokuapp.com/api/v1/notes";
    private String TAG="NOTES_API_RESPONSE";


    private static final int CAPTURE_IMAGE_REQUEST_CODE = 500;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etTitle = findViewById(R.id.etTitle);
        etNote = findViewById(R.id.etNote);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        btnAddVoiceNote = findViewById(R.id.btnAddVoiceNote);
        btnSave = findViewById(R.id.btnSave);
        imgView = findViewById(R.id.imgView);


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                imageRoot.mkdirs();
                final File image = new File(imageRoot, "image1.jpg");
            }
        });
        btnAddVoiceNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),VoiceRecord.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = etTitle.getText().toString();
                noteText = etNote.getText().toString();
                imageUrl=imageRoot.getAbsolutePath();
                postNote(title,noteText);
//                sendBroadcast(new Intent(MediaStore.ACTION_IMAGE_CAPTURE,
//                        Uri.parse("file://" + Environment.getExternalStorageDirectory())));

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle=data.getExtras();
            Bitmap bitmap=(Bitmap)bundle.get("data");
            imgView.setImageBitmap(bitmap);

        }

    }
    private  void postNote(final String title, final String noteText){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, NOTES_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG,s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int id=jsonObject.getInt("id");
                    String title= jsonObject.getString("title");
                    String noteText=jsonObject.getString("noteText");
                    Note note = new Note(id,title,noteText,imageUrl);
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext(), "notes", null, 1);
                    long rows=databaseHelper.addNote(note);
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,volleyError.getMessage());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<String, String>();
                params.put("title",title);
                params.put("noteText",noteText);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);

    }


    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}