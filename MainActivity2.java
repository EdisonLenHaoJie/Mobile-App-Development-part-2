package com.fit2081.assignment12081;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    EditText Username;

    EditText Password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Username = findViewById(R.id.editTextText);
        Password = findViewById(R.id.editTextTextPassword2);

        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);
        String usernameRestored = sharedPreferences.getString(KeyStore.KEY_USERNAME_NAME, "");

        Username.setText(usernameRestored);


    }


    public void onLoginButtonClick(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);
        String usernameRestored = sharedPreferences.getString(KeyStore.KEY_USERNAME_NAME, "DEFAULT VALUE");
        String passwordRestored = sharedPreferences.getString(KeyStore.KEY_PASSWORD_ID, "DEFAULT VALUE");

        String username = Username.getText().toString();
        String password = Password.getText().toString();

        if (username.equals(usernameRestored) && password.equals(passwordRestored)){

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, AddEvent.class);


            startActivity(intent);

        } else {

            Toast.makeText(this, "Authentication failure: Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void onGoBackButtonClick(View view){

        finish();
    }
}