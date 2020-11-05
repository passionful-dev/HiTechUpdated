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
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    public static final String USERNAME = "username";
    private SQLiteDatabase db1;
    private Cursor cursor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        TextView userOnline_tv = (TextView)findViewById(R.id.report_userOnline_tv);
        userOnline_tv.setText(username + " online!");

        //ListView listDates = getListView();
        ListView listDates = (ListView) findViewById(R.id.report_listReportByDate);

        //Get the report data on the ListView
        //Get the user id of the one who just registered
        try{
            SQLiteOpenHelper hiTechDatabase = new HiTechDb1(this);
            SQLiteDatabase db = hiTechDatabase.getReadableDatabase();

            //Get the username from RegisterAccountActivity
            String query = "SELECT _id FROM USER_REG WHERE USERNAME = '"+ username +"'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()){
                int userIdC = cursor.getInt(0) ;
                String userIdCToString = Integer.toString(userIdC);
//                TextView id_tv = (TextView) findViewById(R.id.entry_id);
                //id_tv.setText("User Id: " + userIdCToString);
//                id_tv.setText(userIdCToString);

                //Get the report dates on the ListView
                try{
                    SQLiteOpenHelper hiTechDatabase1 = new HiTechDb1(this);
                    db1 = hiTechDatabase1.getReadableDatabase();

                    String query1 = "SELECT _id, DATE FROM ACTIVITY_ENTRY WHERE USERID = '"+ userIdC +"' ORDER BY DATE DESC";
                    cursor1 = db1.rawQuery(query1, null);

                    //Populate on ListView
                    CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                            cursor1, new String[]{"DATE"}, new int[]{android.R.id.text1}, 0);
                    listDates.setAdapter(listAdapter);

                    //Make it usable to go on respective place when clicked on the ListView
                    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                        public void onItemClick (AdapterView<?> listView, View v, int position, long id) {
                            //if (position == 0)
                            {
                                Intent intent2 = getIntent();
                                String username = intent2.getStringExtra("username");

                                //Sending more than 1 values to another activity
                                Intent intent = new Intent(ReportActivity.this, ReportFromDateActivity.class);
                                intent.putExtra(ReportFromDateActivity.EXTRA_NO, (int)id); //Will get _id of the ACTIVITY_ENTRY table
                                intent.putExtra(ReportFromDateActivity.USERNAME, username);
                                startActivity(intent);
                            }
                        }
                    };
                    listDates.setOnItemClickListener(itemClickListener);

                } catch (SQLiteException e) {
                    Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor1.close();
        db1.close();
    }

    public void onClickReportBack(View view) {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Intent intent1 = new Intent(this, EntryActivityActivity.class);
        intent1.putExtra(EntryActivityActivity.USERNAME, username);
        startActivity(intent1);
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
                            Intent myIntent = new Intent(ReportActivity.this, Mobile.class);
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
//    public void onBackPressed(View v) {
//        new AlertDialog.Builder(this)
//                .setTitle("Logout")
//                .setMessage("Would you like to logout?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // logout codes
//                        Intent myIntent = new Intent(ReportActivity.this, MobileActivity.class);
//                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
//                        startActivity(myIntent);
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // user doesn't want to logout
//                    }
//                })
//                .show();
//
//
//    }

//    @Override
//    public void onListItemClick(ListView listView,
//                                View itemView,
//                                int position,
//                                long id) {
//
//        Intent intent = new Intent(this, ReportFromDateActivity.class);
//        intent.putExtra(ReportFromDateActivity.EXTRA_NO, (int)id);
//        startActivity(intent);
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_report, menu);
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
