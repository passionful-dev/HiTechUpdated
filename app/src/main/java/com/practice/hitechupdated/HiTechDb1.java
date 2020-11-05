package com.practice.hitechupdated;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Krish on 26/01/2016.
 */

class HiTechDb1 extends SQLiteOpenHelper {

    private static final String DB_NAME = "hiTechDb1";
    private static final int DB_VERSION = 1;

    HiTechDb1(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //Creating table for the User Registration
            db.execSQL("CREATE TABLE USER_REG (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT, "
                    + "NAME TEXT, "
                    + "USERNAME TEXT, "
                    + "PASSWORD TEXT, "
                    + "MOBILE TEXT, "
                    + "EMAIL TEXT, "
                    + "ADDRESS TEXT, "
                    + "POST TEXT);");
//                        + "POST TEXT, "
//                        + "TYPE TEXT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            //Creating table for the Activity Entry
            db.execSQL("CREATE TABLE ACTIVITY_ENTRY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "USERID INTEGER, "
                    + "DATE TEXT, "
                    + "LOCATION TEXT, "
                    + "ACTTYPE TEXT, "
                    + "FEEDBACK TEXT, "
                    + "ADDRESS TEXT, "
                    + "REMARKS TEXT);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+"USER_REG");
        db.execSQL("DROP TABLE IF EXISTS "+"ACTIVITY_ENTRY");
        onCreate(db);
    }

    //Inserting the User Registration
//        public boolean insertReg(String date, String name, String username, String passwd,
//                                 String mobile, String email, String address, String post, String type) {
    public boolean insertReg(String date, String name, String username, String passwd,
                             String mobile, String email, String address, String post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("DATE", date);
        values.put("NAME", name);
        values.put("USERNAME", username);
        values.put("PASSWORD", passwd);
        values.put("MOBILE", mobile);
        values.put("EMAIL", email);
        values.put("ADDRESS", address);
        values.put("POST", post);
        //values.put("TYPE", type);
        long result = db.insert("USER_REG", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Inserting the Activity Entry
    public long activityEntry(int userId, String date, String location, String activityType,
                              String feedback, String address, String remarks) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("USERID", userId);
        values.put("DATE", date);
        values.put("LOCATION", location);
        values.put("ACTTYPE", activityType);
        values.put("FEEDBACK", feedback);
        values.put("ADDRESS", address);
        values.put("REMARKS", remarks);
        return db.insert("ACTIVITY_ENTRY", null, values);
//        long result = db.insert("ACTIVITY_ENTRY", null, values);
//        if (result == -1)
//            return false;
//        else
//            return true;
    }

}

