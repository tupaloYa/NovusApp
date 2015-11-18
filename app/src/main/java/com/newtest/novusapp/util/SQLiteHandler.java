package com.newtest.novusapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by ROOT-PC on 26.09.2015.
 */
public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
//                + KEY_ID + " INTEGER PRIMARY KEY,"
//                + KEY_EMAIL + " TEXT UNIQUE," + KEY_PHONE + "TEXT UNIQUE," +  KEY_UID + " TEXT,"
//                + KEY_CREATED_AT + " TEXT," +KEY_FIRSTNAME +"TEXT,"+ KEY_PATRNAME +"TEXT,"+KEY_LASTNAME+"TEXT,"+ KEY_GENDER+"TEXT," + KEY_BIRTHDAY + "TEXT"+");";
//        db.execSQL(CREATE_LOGIN_TABLE);
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_EMAIL + " TEXT UNIQUE," +  KEY_UID + " TEXT," +KEY_CREATED_AT + " TEXT);";
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String email,String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, name); // Name

        values.put(KEY_EMAIL, email); // Email
//        values.put(KEY_PHONE,phone);
        values.put(KEY_UID, uid); //
        values.put(KEY_CREATED_AT, created_at); // Created At
//        values.put(KEY_FIRSTNAME, firstName); // Email
//        values.put(KEY_PATRNAME,patrName);
//        values.put(KEY_LASTNAME, lastName); //
//        values.put(KEY_GENDER, gender); // Created At
//        values.put(KEY_BIRTHDAY,birthday);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void storeUser(String email,String phone, String uid, String created_at,String firstName,
                        String patrName,String lastName,String gender,String bday) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, name); // Name

        values.put(KEY_EMAIL, email); // Email
//        values.put(KEY_PHONE,phone);
        values.put(KEY_UID, uid); //
        values.put(KEY_CREATED_AT, created_at); // Created At
//        values.put(KEY_FIRSTNAME, firstName); // Email
//        values.put(KEY_PATRNAME,patrName);
//        values.put(KEY_LASTNAME, lastName); //
//        values.put(KEY_GENDER, gender); // Created At
//        values.put(KEY_BIRTHDAY,birthday);

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
           // user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(1));
            user.put("uid", cursor.getString(2));
            user.put("created_at", cursor.getString(3));
//            user.put("first_name", cursor.getString(4));
//            user.put("patr_name", cursor.getString(5));
//            user.put("last_name", cursor.getString(6));
//            user.put("gender", cursor.getString(7));
//            user.put("birthday", cursor.getString(8));

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public HashMap<String, String> getUserPhone(){

        String selectQuery = "SELECT "+KEY_PHONE+" FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        HashMap<String, String> phone = new HashMap<String, String>();

        if (cursor.getCount() > 0){
            phone.put("phone",cursor.getString(0));


        }
        cursor.close();
        db.close();
        return phone;
    }

    public  HashMap<String, String> getUserEmail(){
        String selectQuery = "SELECT "+KEY_EMAIL+" FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        HashMap<String, String> email = new HashMap<String, String>();

        if (cursor.getCount() > 0){

            email.put("email",cursor.getString(0));
        }
        else {
            Log.d("oops","no email found");
        }
        cursor.close();
        db.close();
        return email;
    }

}
