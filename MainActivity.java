package com.fit2081.assignment12081;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Username;

    EditText Password;

    EditText PasswordConfrimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.editTextUsername);
        Password = findViewById(R.id.editTextTextPassword);
        PasswordConfrimation = findViewById(R.id.editTextTextPasswordConfrimation);

    }
    public void onRegisterButtonClick(View view){

        String UsernameString = Username.getText().toString();
        String PasswordString = Password.getText().toString();
        String PasswordConfrimationString = PasswordConfrimation.getText().toString();

        if(UsernameString.isEmpty() || PasswordString.isEmpty() || PasswordConfrimationString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!PasswordString.equals(PasswordConfrimationString)) {
            Toast.makeText(this, "Error: Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }


        String message = String.format("Hi %s, your register details was successful ", UsernameString );


        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);





        saveDataToSharedPreference(UsernameString, PasswordString,PasswordConfrimationString);
    }

    private void saveDataToSharedPreference(String userValue, String passwordValue,String passwordConfirmedValue){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);

        // use .edit function to access file using Editor variable
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save key-value pairs to the shared preference file
        editor.putString(KeyStore.KEY_USERNAME_NAME, userValue);
        editor.putString(KeyStore.KEY_PASSWORD_ID, passwordValue);
        editor.putString(KeyStore.KEY_PASSWORD_CONFRIMED_ID, passwordConfirmedValue);

        // use editor.apply() to save data to the file asynchronously (in background without freezing the UI)
        // doing in background is very common practice for any File Input/Output operations
        editor.apply();

        // or
        // editor.commit()
        // commit try to save data in the same thread/process as of our user interface
    }
    public void onClearButtonClick(View view){
        Username.setText("");
        Password.setText("");
        PasswordConfrimation.setText("");
    }

    public void onRestoreButtonClick(View view){
        // initialise shared preference class variable to access Android's persistent storage
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);

        // save key-value pairs to the shared preference file
        String usernameRestored = sharedPreferences.getString(KeyStore.KEY_USERNAME_NAME, "DEFAULT VALUE");
        String passwordRestored = sharedPreferences.getString(KeyStore.KEY_PASSWORD_ID, "DEFAULT VALUE");
        String passwordConfrimationRestored = sharedPreferences.getString(KeyStore.KEY_PASSWORD_CONFRIMED_ID, "DEFAULT VALUE");

        // update the UI using retrieved values
        Username.setText(usernameRestored);
        Password.setText(passwordRestored);
        PasswordConfrimation.setText(passwordConfrimationRestored);
    }
    public void LaunchLogin(View view) {



            Intent intent = new Intent(this, MainActivity2.class);




            startActivity(intent);


    }




}