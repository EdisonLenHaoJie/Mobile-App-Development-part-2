package com.fit2081.assignment12081;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import android.widget.Switch;


import com.fit2081.assignment12081.provider.CategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;



public class NewEventCategory extends AppCompatActivity {


    ArrayList<AddCategoryNavDrawer> data = new ArrayList<>();


    EditText CategoryId;

    EditText CategoryName;

    EditText EventCount;

    Switch isActive;

    EditText CategoryLocation;

    Gson gson = new Gson();


    private CategoryViewModel categoryViewModel;



















    MyBroadCastReceiver myBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        CategoryId = findViewById(R.id.editTextCategoryId);
        CategoryName = findViewById(R.id.editTextCategoryName);
        EventCount = findViewById(R.id.editTextEventCount);
        isActive = findViewById(R.id.switch1);
        CategoryLocation = findViewById(R.id.editTextCategoryLocation);


        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);


        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);


        myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(EventCategoryReceiver.SMS_FILTER), RECEIVER_EXPORTED);




        //restoreArrayListAsText();




    }



    public void onSaveCategoryButtonClick(View view){

        String CategoryIdString = generateCategoryId();
        String CategoryNameString = CategoryName.getText().toString();
        String EventCountString = EventCount.getText().toString();
        String CategoryLocationString = CategoryLocation.getText().toString();
        boolean isActiveBool = isActive.isChecked();


        if (!checkValidName(CategoryNameString)) {
            Toast.makeText(this, "Invalid category name.", Toast.LENGTH_SHORT).show();
            return;
        }


        if(CategoryNameString.isEmpty() || EventCountString.isEmpty() ) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int CountNonNegative;
        try {
            CountNonNegative = Integer.parseInt(EventCountString);
            if (CountNonNegative < 0) {
                Toast.makeText(this, "Invalid event count: Numbers must be zero or positive.", Toast.LENGTH_SHORT).show();
                EventCount.setText("0");
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid event count.", Toast.LENGTH_SHORT).show();
            return;
        }



        CategoryId.setText(CategoryIdString);



        String message = String.format(" category details was saved successfully,  %s  ", CategoryIdString);


        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        //saveDataToSharedPreference(CategoryIdString,CategoryNameString,CountNonNegative,isActiveBool,CategoryLocationString );



        AddCategoryNavDrawer addCategoryNavDrawer= new AddCategoryNavDrawer(CategoryIdString,CategoryNameString,CountNonNegative,isActiveBool,CategoryLocationString);
        data.add(addCategoryNavDrawer);



        //saveArrayListAsText();

        categoryViewModel.insert(addCategoryNavDrawer);








        finish();
    }



    private void saveArrayListAsText() {

        // convert array list to String
        String arrayListString = gson.toJson(data);

        // Save to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KeyStore.KEY_ARRAY_LIST_ID, arrayListString);
        editor.apply();


    }

    private void restoreArrayListAsText() {
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);

        // restore data from SharedPreferences
        String arrayListStringRestored = sharedPreferences.getString(KeyStore.KEY_ARRAY_LIST_ID, "[]");

        // Convert the restored string back to ArrayList
        Type type = new TypeToken<ArrayList<AddCategoryNavDrawer>>() {}.getType();
        data = gson.fromJson(arrayListStringRestored,type);




    }

    private String generateCategoryId() {
        Random random = new Random();
        StringBuilder categoryId = new StringBuilder("C");
        for (int i = 0; i < 2; i++) { // Generate 2 random alphanumeric characters
            char chr = (char) ('A' + random.nextInt(26));
            categoryId.append(chr);
        }
        categoryId.append("-");
        for (int i = 0; i < 4; i++) { // Generate 4 random digits
            char chr = (char) ('0' + random.nextInt(10));
            categoryId.append(chr);
        }
        return categoryId.toString();
    }


    public boolean checkValidName(String str) {
        boolean hasAlphabet = false;
        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                hasAlphabet = true;
            } else if (!Character.isDigit(c) && c != ' ') {
                return false;
            }
        }
        return hasAlphabet;
    }

    private void saveDataToSharedPreference(String categoryIdValue, String catergoryNameValue , int eventCountValue, boolean isActiveBool,String categoryLocationValue){

        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KeyStore.KEY_CATEGORY_ID, categoryIdValue);
        editor.putString(KeyStore.KEY_CATEGORY_NAME_ID, catergoryNameValue);
        editor.putInt(KeyStore.KEY_EVENT_COUNT_ID,  eventCountValue);
        editor.putString(KeyStore.KEY_LOCATION_ID,  categoryLocationValue);
        editor.putBoolean(KeyStore.KEY_BOOLEAN_ID,  isActiveBool);




        editor.apply();


    }






    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override


        public void onReceive(Context context, Intent intent) {


            /*
             * Retrieve the message from the intent
             * */
            String fullMsg = intent.getStringExtra(EventCategoryReceiver.SMS_MSG_KEY);


            if (fullMsg != null && fullMsg.startsWith("category:")) {
                // Remove the prefix to ease parsing
                String msg = fullMsg.substring("category:".length());

                // Split the message on semicolons
                String[] parts = msg.split(";", -1); // -1 to include trailing empty strings

                // Check if we have at least 1 part (category name)
                if (parts.length == 3 && !parts[0].isEmpty()) {
                    String categoryName = parts[0];
                    String eventCount = parts[1];
                    String isItActive = parts[2];

                    

                    // Try to parse eventCount as an integer if it's not empty
                    if (!eventCount.isEmpty()) {
                        try {
                            int parsedEventCount = Integer.parseInt(eventCount);
                            if (parsedEventCount <= 0) {


                                // If parsedEventCount is not positive, show a Toast message
                                Toast.makeText(context, "EventCount must be a positive integer: " + eventCount, Toast.LENGTH_LONG).show();
                                return;
                            }

                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "EventCount is not a valid integer: " + eventCount, Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        EventCount.setText(""); // Clear or set to a default value
                    }

                    // Check isItActive and set the checkbox
                    if (isItActive.equalsIgnoreCase("TRUE")) {
                        isActive.setChecked(true);
                    } else if (isItActive.equalsIgnoreCase("FALSE") || isItActive.isEmpty()) {
                        isActive.setChecked(false);
                    } else {
                        Toast.makeText(context, "Invalid IsActive value: " + isItActive, Toast.LENGTH_LONG).show();
                        return;
                    }
                    EventCount.setText(eventCount);
                    CategoryName.setText(categoryName);
                } else {
                    Toast.makeText(context, "Invalid or incomplete message received: " + fullMsg, Toast.LENGTH_LONG).show();
                }
            } else {
                // Show a Toast message for any unrecognized or incomplete SMS received
                Toast.makeText(context, "Unrecognized message format", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadCastReceiver);
    }

}