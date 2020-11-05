package com.practice.hitechupdated;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterAccountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

    }

    public void onClickRegisterUser(View view) {
        EditText username_et = (EditText) findViewById(R.id.registration_username);
        String username = username_et.getText().toString();

        EditText name_et = (EditText) findViewById(R.id.registration_name);
        String name = name_et.getText().toString();

        EditText passwd_et = (EditText) findViewById(R.id.registration_passwd);
        String passwd = passwd_et.getText().toString();

        EditText mobile_et = (EditText) findViewById(R.id.registration_mobile);
        String mobile = mobile_et.getText().toString();

        EditText email_et = (EditText) findViewById(R.id.registration_emailId);
        String email = email_et.getText().toString();

        EditText address_et = (EditText) findViewById(R.id.registration_address);
        String address = address_et.getText().toString();

        EditText post_et = (EditText) findViewById(R.id.registration_post);
        String post = post_et.getText().toString();

//        Spinner type_et = (Spinner) findViewById(R.id.registration_type);
//        String type = type_et.getSelectedItem().toString();

        //Get current date and time
        SimpleDateFormat sdf = new SimpleDateFormat("  yyyy-MM-dd HH:mm:ss");
        String currentDateNTime = sdf.format(new Date());

        //Check if Username field is empty
        if(username.equals("")){
            Toast.makeText(this, "Enter Username!", Toast.LENGTH_SHORT).show();
        }
        else {
            //Check if that username already exists
            try {
                SQLiteOpenHelper hiTechDatabase = new HiTechDb1(this);
                SQLiteDatabase db = hiTechDatabase.getReadableDatabase();
                String query = "SELECT USERNAME FROM USER_REG WHERE USERNAME = '" + username + "'";
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst()) {
                    String usernameC = cursor.getString(0);
                    Toast.makeText(this, "This username already exists!", Toast.LENGTH_SHORT).show();
                } else//Insert data
                {
                    HiTechDb1 db1 = new HiTechDb1(this);
                    //boolean isInserted = db1.insertReg(currentDateNTime, name, username, passwd, mobile, email, address, post, type);
                    boolean isInserted = db1.insertReg(currentDateNTime, name, username, passwd, mobile, email, address, post);
                    if (isInserted == true) {
                        //Toast.makeText(this, "Data is inserted", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, EntryActivityActivity.class);
                        intent.putExtra(EntryActivityActivity.USERNAME, username);
                        startActivity(intent);

                        //Notify the user registered in the notification service
                        Intent intentService = new Intent(this, UserRegisteredMessageService.class);
                        intentService.putExtra(UserRegisteredMessageService.EXTRA_MESSAGE,
                                //getResources().getString(R.string.cover_heading));
                                username + "Registered!");
                        startService(intentService);

                    } else {
                        //Toast.makeText(this, "Data is not inserted", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "User could not be registered", Toast.LENGTH_SHORT).show();
                    }
                    db1.close();
                }

                cursor.close();
                db.close();

            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }



    }

}
