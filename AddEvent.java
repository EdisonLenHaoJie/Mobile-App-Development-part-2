package com.fit2081.assignment12081;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Switch;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;




import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.fit2081.assignment12081.provider.CategoryViewModel;
import com.fit2081.assignment12081.provider.EventViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class AddEvent extends AppCompatActivity {

    EditText EventId;

    EditText EventName;

    EditText CategoryAddId;

    EditText TicketCount;

    Switch isAddActive;

    ArrayList<EventNavDrawer> data2 = new ArrayList<>();

    Gson gson = new Gson();

    ArrayList<Header> listHeader= new ArrayList<>();


    headerAdapter recycleradapter;
    private RecyclerView recyclerViewheader;

    RecyclerView.LayoutManager layoutManager;


    MyBroadCastReceiver myBroadCastReceiver;

    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;


    private EventViewModel eventViewModel;

    private CategoryViewModel categoryViewModel;

    View touchPadHere;

    TextView commandTvHere;

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.drawer_activity_main);

        EventId = findViewById(R.id.editTextEventId);
        EventName = findViewById(R.id.editTextEventName);
        CategoryAddId = findViewById(R.id.editTextCategoryAddId);
        TicketCount = findViewById(R.id.editTextTicket);
        isAddActive = findViewById(R.id.switch2);

        touchPadHere = findViewById(R.id.Touchpad);

        commandTvHere = findViewById(R.id.commandtv);


        recyclerViewheader = findViewById(R.id.recyclerHeader);

        layoutManager = new LinearLayoutManager(this);

        recyclerViewheader.setLayoutManager(layoutManager);

        recycleradapter = new headerAdapter();
        recycleradapter.setData3(listHeader);

        recyclerViewheader.setAdapter(recycleradapter);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

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


        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);


        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new AddEvent.MyNavigationListener());

        //restoreArrayListAsText();


        MyGestureListener listener = new MyGestureListener();
        mDetector = new GestureDetector(this, listener);
        touchPadHere.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }

        });





    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            commandTvHere.setText("OnLongPress");
            EventId.setText("");
            EventName.setText("");
            CategoryAddId.setText("");
            TicketCount.setText("");
            isAddActive.setChecked(false);


        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {
            commandTvHere.setText("OnDoubleTap");
            onSaveCategoryButtonClick(findViewById(android.R.id.content));
            return true;
        }
    }

    public void removeAllCategories() {
        categoryViewModel.deleteAll();
    }

    public void removeAllEvents() {
        eventViewModel.deleteAll();
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_refresh) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
            if (fragment instanceof FragmentListCategory) {
                ((FragmentListCategory) fragment).refreshData();


            }
            registerReceiver(myBroadCastReceiver, new IntentFilter(EventCategoryReceiver.SMS_FILTER), RECEIVER_EXPORTED);
            return true;

        } else if (item.getItemId() == R.id.Clear_Event_Form) {
            // if method not found, create a new one to clear all the fields
            EventId.setText("");
            EventName.setText("");
            CategoryAddId.setText("");
            TicketCount.setText("");
            isAddActive.setChecked(false);

        }  else if (item.getItemId() == R.id.Delete_All_Categories) {

            //SharedPreferences sharedPreferences = this.getSharedPreferences(KeyStore.FILE_NAME, Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();

            //editor.putString(KeyStore.KEY_ARRAY_LIST_ID, "[]");

            //editor.apply();
            removeAllCategories();

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView3);
            if (fragment instanceof FragmentListCategory) {
                ((FragmentListCategory) fragment).refreshData();
            }



        }  else if (item.getItemId() == R.id.Delete_All_Events) {

            //SharedPreferences sharedPreferences = this.getSharedPreferences(KeyStore.FILE_NAME, Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor = sharedPreferences.edit();

            //editor.putString(KeyStore.KEY_EVENT_LIST_ID, "[]");

            //editor.apply();
            removeAllEvents();
            data2.clear();


        }

        return super.onOptionsItemSelected(item);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.View_All_Categories){
                Intent intent = new Intent(AddEvent.this, ListCategoryActivity.class);


                startActivity(intent);
            }

            if (id == R.id.Add_Category){
                Intent intent = new Intent(AddEvent.this, NewEventCategory.class);


                startActivity(intent);
            }

            if (id == R.id.View_All_Events){

                Intent intent = new Intent(AddEvent.this, ListEventActivity.class);


                startActivity(intent);

            }else if (id == R.id.Log_out) {
                finish();
            }
            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    public void onSaveCategoryButtonClick(View view){

        String EventIdString = generateEventId();
        String EventNameString = EventName.getText().toString();
        String CategoryAddIdString = CategoryAddId.getText().toString();
        String TicketCountString = TicketCount.getText().toString();
        boolean isActiveBool = isAddActive.isChecked();





        //SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, Context.MODE_PRIVATE);

        //String addCategoryList = sharedPreferences.getString(KeyStore.KEY_ARRAY_LIST_ID, "[]");
        //Gson gson = new Gson();

        //Type type = new TypeToken<ArrayList<AddCategoryNavDrawer>>() {}.getType();
        //ArrayList<AddCategoryNavDrawer> data  = gson.fromJson(addCategoryList,type);

        //boolean idFound = false;
        //for (AddCategoryNavDrawer category : data) {
        //    if (category.getCategoryNavId().equals(CategoryAddIdString)){
        //        idFound = true;
        //        category.setEventNavCount(category.getEventNavCount() + 1);
//
//
        //        String arrayListString = gson.toJson(data);
//
        //        SharedPreferences.Editor editor = sharedPreferences.edit();
//
        //        editor.putString(KeyStore.KEY_ARRAY_LIST_ID, arrayListString);
        //        editor.apply();
//
        //        break;
//
//
//
//
        //    }
//
        //}
//
        //if (!idFound) {
        //    Toast.makeText(this, "Category Id do not exist.", Toast.LENGTH_SHORT).show();
        //    return;
        //}



        if (!checkValidName(EventNameString)) {
            Toast.makeText(this, "Invalid event name.", Toast.LENGTH_SHORT).show();
            return;
        }



        if(EventNameString.isEmpty() || CategoryAddIdString.isEmpty()  ) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        int CountNonNegative;
        try {
            if (TicketCountString.isEmpty()) {
                TicketCount.setText("0");
                return;
            }
            CountNonNegative = Integer.parseInt(TicketCountString);
            if (CountNonNegative < 0) {
                Toast.makeText(this,"Invalid 'Tickets available': Numbers must be zero or positive." , Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Ticket count.", Toast.LENGTH_SHORT).show();
            return;
        }



        EventId.setText(EventIdString);

        //String message = String.format(" event saved : %s to  %s  ", EventIdString,CategoryAddIdString);


        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        //saveDataToSharedPreference(EventIdString,EventNameString,CategoryAddIdString,CountNonNegative,isActiveBool );

        EventNavDrawer eventNavDrawer= new EventNavDrawer(EventIdString,EventNameString,CategoryAddIdString,CountNonNegative,isActiveBool);
        data2.add(eventNavDrawer);

        //eventViewModel.insert(eventNavDrawer);

       categoryViewModel.addEventIfCategoryExists(eventNavDrawer, this, getApplicationContext());

       boolean flag = categoryViewModel.flag.get();









        //saveArrayListAsText();

        // Show Snackbar with UNDO option
        if (flag){

            Snackbar.make(view, "Event saved", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Undo the save operation
                            data2.remove(eventNavDrawer); // Remove the last added event


                            categoryViewModel.noEventIfCategoryExists(eventNavDrawer, AddEvent.this, getApplicationContext());
                            eventViewModel.deleteEvent(eventNavDrawer.getEventNavId());


                            // Find the category and decrement the event count
                            //    for (AddCategoryNavDrawer category : data) {
                            //        if (category.getCategoryNavId().equals(CategoryAddIdString)) {
                            //            // Decrement the event count safely
                            //            int currentCount = category.getEventNavCount();
                            //            if (currentCount > 0) {
                            //                category.setEventNavCount(currentCount - 1);
                            //            }
                            //            break;
                            //        }
//
//
                            //    }




                            // Serialize the updated category list and save it back to SharedPreferences
                            //String arrayListString = new Gson().toJson(data);
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString(KeyStore.KEY_ARRAY_LIST_ID, arrayListString);
                            //editor.apply();

                            // saveArrayListAsText(); // Save the modified list back to preferences
                            Toast.makeText(AddEvent.this, "Event not saved", Toast.LENGTH_SHORT).show();
                        }


                    }).show();

        }




    }



    private void saveArrayListAsText() {

        // convert array list to String
        String arrayListString = gson.toJson(data2);

        // Save to shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KeyStore.KEY_EVENT_LIST_ID, arrayListString);
        editor.apply();


    }

    private void restoreArrayListAsText() {
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);

        // restore data from SharedPreferences
        String arrayListStringRestored = sharedPreferences.getString(KeyStore.KEY_EVENT_LIST_ID, "[]");

        // Convert the restored string back to ArrayList
        Type type = new TypeToken<ArrayList<AddCategoryNavDrawer>>() {}.getType();
        data2 = gson.fromJson(arrayListStringRestored,type);









    }

    private String generateEventId() {
        Random random = new Random();
        StringBuilder eventId = new StringBuilder("E");
        for (int i = 0; i < 2; i++) { // Generate 2 random alphanumeric characters
            char chr = (char) ('A' + random.nextInt(26));
            eventId.append(chr);
        }
        eventId.append("-");
        for (int i = 0; i < 5; i++) { // Generate 4 random digits
            char chr = (char) ('0' + random.nextInt(10));
            eventId.append(chr);
        }
        return eventId.toString();
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

    private void saveDataToSharedPreference(String eventIdValue, String eventNameValue, String categoryAddValue, int ticketCountValue, boolean isActiveBool){

        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KeyStore.KEY_EVENT_ID, eventIdValue);
        editor.putString(KeyStore.KEY_EVENT_NAME, eventNameValue);
        editor.putString(KeyStore.KEY_CATEGORY_ADD_ID, categoryAddValue);
        editor.putInt(KeyStore.KEY_TICKET_COUNT_ID,  ticketCountValue);
        editor.putBoolean(KeyStore.KEY_BOOLEAN_ADD_ID,  isActiveBool);



        editor.apply();


    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override


        public void onReceive(Context context, Intent intent) {
            // Tokenize received message here

            /*
             * Retrieve the message from the intent
             * */
            String fullMsg = intent.getStringExtra(EventCategoryReceiver.SMS_MSG_KEY);


            if (fullMsg != null && fullMsg.startsWith("event:")) {
                // Remove the prefix to ease parsing
                String msg = fullMsg.substring("event:".length());

                // Split the message on semicolons
                String[] parts = msg.split(";", -1); // -1 to include trailing empty strings



                if (parts.length == 4 && !parts[0].isEmpty()) {
                    String eventName = parts[0];
                    String categoryAdd = parts[1] ;
                    String ticketCount =  parts[2] ;
                    String isAddedActive = parts[3]  ;

                    SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_NAME, MODE_PRIVATE);
                    String categoryIdFromNewEvent = sharedPreferences.getString(KeyStore.KEY_CATEGORY_ID, "DEFAULT VALUE");

                    if (!categoryAdd.equals(categoryIdFromNewEvent)) {
                        Toast.makeText(context, "Category Id do not match from save storage .", Toast.LENGTH_LONG).show();
                        return;
                    }


                    // Attempt to parse ticketCount as an integer
                     if(!ticketCount.isEmpty()) {
                        try {
                            int parsedTicketCount = Integer.parseInt(ticketCount);
                            if (parsedTicketCount <= 0) {

                                // If parsedEventCount is not positive, show a Toast message
                                Toast.makeText(context, "TicketCount cannot be 0 or negative: " + ticketCount, Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "TicketCount is not a valid integer: " + ticketCount, Toast.LENGTH_LONG).show();
                            return;
                        }
                     } else {
                    TicketCount.setText(""); // Clear or set to a default value
                     }


                // Validate isItActive
                     if (isAddedActive.equalsIgnoreCase("TRUE")) {
                         isAddActive.setChecked(true);
                     } else if ((isAddedActive.equalsIgnoreCase("FALSE")) || isAddedActive.isEmpty()) {
                        isAddActive.setChecked(false);

                    } else{
                    Toast.makeText(context, "Invalid IsAddedActive value: " + isAddedActive, Toast.LENGTH_LONG).show();
                    return;
                    }
                    EventName.setText(eventName);
                    CategoryAddId.setText(categoryAdd);
                    TicketCount.setText(ticketCount);
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
        registerReceiver(myBroadCastReceiver, new IntentFilter(EventCategoryReceiver.SMS_FILTER), RECEIVER_EXPORTED);
        unregisterReceiver(myBroadCastReceiver);
    }






}