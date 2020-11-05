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
import android.widget.TextView;
import android.widget.Toast;

public class Mobile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.

            new AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setMessage("Would you like to close application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // logout codes
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

    public void onClickSignIn(View view) {
        EditText username_et = (EditText) findViewById(R.id.cover_login_username_et);
        String username = username_et.getText().toString();

        EditText passwd_et = (EditText) findViewById(R.id.cover_login_passwd_et);
        String passwd = passwd_et.getText().toString();

        TextView error_tv = (TextView) findViewById(R.id.cover_error_tv);

//        error_tv.setText(username + " " + passwd);
//        error_tv.setVisibility(View.VISIBLE);

        //Check if Username field is empty
        if(username.equals("")){
            Toast.makeText(this, "Enter Username!", Toast.LENGTH_SHORT).show();
        }
        else {
            try{
                SQLiteOpenHelper hiTechDatabase = new HiTechDb1(this);
                SQLiteDatabase db = hiTechDatabase.getReadableDatabase();
                //Check the username and password and do accordingly
                String query = "SELECT USERNAME, PASSWORD FROM USER_REG WHERE USERNAME = '"+username+"' AND PASSWORD = '"+passwd+"'";
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst()){
                    String usernameC = cursor.getString(0);
                    String passwdC = cursor.getString(1);

                    //Check the username and password and do accordingly
                    //if (username.equals(usernameC) && passwd.equals(passwdC))
                    {
                        //error_tv.setText("Username and/or password is correct!"+" "+usernameC+" "+passwdC+":: "+username+" "+passwd);
                        //error_tv.setVisibility(View.VISIBLE);
                        //Toast.makeText(this, "Sign successful!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, EntryActivityActivity.class);
                        //intent.putExtra("message", username);
                        intent.putExtra(EntryActivityActivity.USERNAME, username);
                        startActivity(intent);
                    }
                    //else
                    {
                        //error_tv.setText("Incorrect Username and/or password!" + " "+username+" "+passwd);
                        //error_tv.setVisibility(View.VISIBLE);
                        //Toast.makeText(this, "Incorrect Username and/or password!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Incorrect Username and/or password!", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Db unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

    }

    public void onClickCreateNewAccount(View view) {
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        //Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }
}
