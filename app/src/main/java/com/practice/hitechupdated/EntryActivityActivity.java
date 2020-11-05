package com.practice.hitechupdated;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryActivityActivity extends AppCompatActivity {


    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateNTime = sdf.format(new Date());

        TextView dtNow_tv = (TextView)findViewById(R.id.entry_dateTime);
        dtNow_tv.setText(currentDateNTime);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView welcomeUser_tv = (TextView)findViewById(R.id.entry_welcomeUser_tv);
        welcomeUser_tv.setText("Welcome "+ username +"!");

        //Get the user id of the one who just registered
        try{
            SQLiteOpenHelper hiTechDatabase = new HiTechDb1(this);
            SQLiteDatabase db = hiTechDatabase.getReadableDatabase();

            //Get the username from RegisterAccountActivity
            String query = "SELECT _id FROM USER_REG WHERE USERNAME = '"+ username +"'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                int userIdC = cursor.getInt(0) ;
                TextView id_tv = (TextView) findViewById(R.id.entry_id);
                String userIdCToString = Integer.toString(userIdC);
                //id_tv.setText("User Id: " + userIdCToString);
                id_tv.setText(userIdCToString);
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.

            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Would you like to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // logout codes
                            Intent myIntent = new Intent(EntryActivityActivity.this, Mobile.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                            startActivity(myIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // user doesn't want to logout
                        }
                    })
                    .show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClickEnterActivity(View view) {
        //Get the userId from TextView
        TextView id_tv = (TextView) findViewById(R.id.entry_id);
        String id = id_tv.getText().toString();
        int userId = Integer.parseInt(id);

        //Get date/time from TextView
        TextView dateTime_tv = (TextView) findViewById(R.id.entry_dateTime);
        String dateTime = dateTime_tv.getText().toString();

        //Get location from TextView
        TextView location_tv = (TextView) findViewById(R.id.entry_location);
        String location = location_tv.getText().toString();

        //Get Activity Type from Spinner
        Spinner activityType_sp = (Spinner) findViewById(R.id.entry_activityType);
        String activityType = activityType_sp.getSelectedItem().toString();

        //Get Feedback from Spinner
        Spinner feedback_sp = (Spinner) findViewById(R.id.entry_feedback);
        String feedback = String.valueOf(feedback_sp.getSelectedItem());

        //Get Address from EditText
        EditText address_et = (EditText) findViewById(R.id.entry_address);
        String address = address_et.getText().toString();

        //Get Remarks from EditText
        EditText remarks_et = (EditText) findViewById(R.id.entry_remarks);
        String remarks = remarks_et.getText().toString();

        HiTechDb1 db = new HiTechDb1(this);
        long isInserted = db.activityEntry(userId, dateTime, location, activityType, feedback, address, remarks);
        if (isInserted >= 0){
            //Toast.makeText(this, "User activity is inserted", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "User activity entered!", Toast.LENGTH_SHORT).show();

            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            Intent intent1 = new Intent(this, ReportActivity.class);
            intent1.putExtra(ReportActivity.USERNAME, username);
//            Intent intent1 = new Intent(this, ReportActivity.class);
//            intent1.putExtra("message", userId);
            startActivity(intent1);
        }
        else {
            //Toast.makeText(this, "User activity is not inserted", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "User activity could not be entered", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
