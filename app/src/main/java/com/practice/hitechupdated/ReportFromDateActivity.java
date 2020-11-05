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
import android.widget.TextView;
import android.widget.Toast;

public class ReportFromDateActivity extends AppCompatActivity {

    public static final String EXTRA_NO = "listNo";
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_from_date);

        //Get the List no. selected
        int listId = (Integer)getIntent().getExtras().get("listNo");

        //Get the username from the ReportActivity activity
        String username = getIntent().getStringExtra("username");
        TextView userOnline_tv = (TextView)findViewById(R.id.reportFromDate_userOnline_tv);
        userOnline_tv.setText(username + " online!");

        //Get the Date, Location, Activity Type, Feedback, Address and Remarks from the List id no. selected
        try{
            SQLiteOpenHelper hiTechDatabase = new HiTechDb1(this);
            SQLiteDatabase db = hiTechDatabase.getReadableDatabase();

            String query = "SELECT DATE, LOCATION, ACTTYPE, FEEDBACK, ADDRESS, REMARKS FROM ACTIVITY_ENTRY WHERE _id = "+ listId;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                String dateC = cursor.getString(0);
                String locationC = cursor.getString(1);
                String actTypeC = cursor.getString(2);
                String feedbackC = cursor.getString(3);
                String addressC = cursor.getString(4);
                String remarksC = cursor.getString(5);

                TextView date_tv = (TextView) findViewById(R.id.reportFromDate_dateGet);
                date_tv.setText(dateC);
                TextView locationC_tv = (TextView) findViewById(R.id.reportFromDate_locationGet);
                locationC_tv.setText(locationC);
                TextView actTypeC_tv = (TextView) findViewById(R.id.reportFromDate_actTypeGet);
                actTypeC_tv.setText(actTypeC);
                TextView feedback_tv = (TextView) findViewById(R.id.reportFromDate_feedbackGet);
                feedback_tv.setText(feedbackC);
                TextView address_tv = (TextView) findViewById(R.id.reportFromDate_addressGet);
                address_tv.setText(addressC);
                TextView remarks_tv = (TextView) findViewById(R.id.reportFromDate_remarksGet);
                remarks_tv.setText(remarksC);
            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onClickBack(View view) {
        String username = getIntent().getStringExtra("username");
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(ReportActivity.USERNAME, username);
        startActivity(intent);
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
                            Intent myIntent = new Intent(ReportFromDateActivity.this, Mobile.class);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_report_from_date, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
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
}
