package com.example.ashanotepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ashanotepad.rest.ApiClient;
import com.example.ashanotepad.rest.ApiInterface;
import com.example.ashanotepad.rest.RegistrationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    EditText email;
    EditText password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);

        email=findViewById(R.id.username);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(email.getText().toString(),password.getText().toString());


            }
        });


//        setSupportActionBar(toolbar);

    }
    public void registerUser(String email,String password){
        ApiInterface apiInterface= ApiClient.createRetrofit().create(ApiInterface.class);
        Call<RegistrationResponse>registrationCall = apiInterface.registerUser(email, password);
        registrationCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()){
                    RegistrationResponse registrationResponse = response.body();
                    SharedPreferences msharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = msharedPreferences.edit();
                    editor.putInt("USER_ID",registrationResponse.getId());
                    editor.putString("AUTH_TOKEN",registrationResponse.getToken());
                    editor.commit();
                    Intent intent =new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.e("failure",t.getMessage());

            }
        });


    }

}
