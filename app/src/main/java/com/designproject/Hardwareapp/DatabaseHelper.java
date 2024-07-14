package com.designproject.Hardwareapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_SMART_ORCHID = "BuildMate.db";

    //User accounts related database variables
    public static final String TABLE_ACCOUNTS = "accounts_table";
    public static final String ACCOUNT_ID = "ID";
    public static final String ACCOUNT_USER_NAME = "USER_NAME";
    public static final String ACCOUNT_EMAIL = "EMAIL";
    public static final String ACCOUNT_PHONE = "PHONE";
    public static final String ACCOUNT_PASSWORD = "PASSWORD";
    public static final String ACCOUNT_REMEMBER = "REMEMBER";
    public static final String ACCOUNT_LOGIN = "IS_LOGGED";

    public static final String TABLE_REMEMBER = "remember_me_table";
    public static final String REMEMBER_ID = "ID";
    public static final String REMEMBER_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String REMEMBER_STATUS = "REMEMBER_ME";


    public static final String TABLE_CART = "cart_table";

    public static final String TABLE_ORDERS = "cart_table";

    public static final String TABLE_SETTINGS = "cart_table";

    public static final String TABLE_NOTIFICATIONS = "cart_table";

    public DatabaseHelper ( Context context) {
        super(context, DATABASE_SMART_ORCHID, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create accounts table
        String CREATE_ACCOUNTS_TABLE_QUERY = "CREATE TABLE " + TABLE_ACCOUNTS + " (" +
                ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACCOUNT_USER_NAME + " TEXT, " +
                ACCOUNT_EMAIL + " TEXT, " +
                ACCOUNT_PHONE + " TEXT, " +
                ACCOUNT_PASSWORD + " TEXT, " +
                ACCOUNT_REMEMBER + " INTEGER, " +
                ACCOUNT_LOGIN + " INTEGER)";
        db.execSQL(CREATE_ACCOUNTS_TABLE_QUERY);

        //create remember me table
        String CREATE_REMEMBER_TABLE_QUERY = "CREATE TABLE " + TABLE_REMEMBER + " (" +
                REMEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REMEMBER_ACCOUNT_ID + " INTEGER, " +
                REMEMBER_STATUS + " INTEGER)";
        db.execSQL(CREATE_REMEMBER_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 2 && newVersion >= 2) {
//            String sql = "ALTER TABLE "+ TABLE_ACCOUNTS +" ADD COLUMN IS_LOGGED INTEGER;";
//            db.execSQL(sql);
//        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(db);
    }

    //insert new user details when registering
    public boolean registerUser(String userName, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_USER_NAME, userName);
        contentValues.put(ACCOUNT_EMAIL, email);
        contentValues.put(ACCOUNT_PHONE, phone);
        contentValues.put(ACCOUNT_PASSWORD, password);
        contentValues.put(ACCOUNT_REMEMBER, String.valueOf(0));
        contentValues.put(ACCOUNT_LOGIN, String.valueOf(0));
        long result = db.insert(TABLE_ACCOUNTS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatePassword(String id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNT_PASSWORD, password);
        long count = db.update(TABLE_ACCOUNTS, contentValues,
                "ID = ?", new String[]{id});
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateLoginStatus(String id , int status){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_LOGIN, status);
        db.update(TABLE_ACCOUNTS , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public boolean deleteAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id > -1) {
            long is_deleted = db.delete(TABLE_ACCOUNTS, "ID = ? ", new String[]{String.valueOf(id)});
            return is_deleted > 0;
        }
        return false;
    }

    //get all the data from relevant table
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS, null);
        return result;
    }

    //get all the data of specific user from relevant table
    public Cursor getUserData(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_USER_NAME + " = ?", new String[]{userName});
        return result;
    }

    public Cursor getUserDataById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_ID + " = ?", new String[]{id});
        return result;
    }

    public boolean checkUserName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_USER_NAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkUserNamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_USER_NAME + " = ? AND " + ACCOUNT_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_PHONE + " = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkUserNamePhone(String username, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + ACCOUNT_USER_NAME + " = ? AND " + ACCOUNT_PHONE + " = ?", new String[]{username, phone});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void updateRemember(String id , int remember){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_REMEMBER, remember);
        db.update(TABLE_ACCOUNTS , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public Cursor checkRememberMe() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_REMEMBER, null);
        return result;
    }

    public void insertRemember(String accId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMEMBER_ACCOUNT_ID, accId);
        contentValues.put(REMEMBER_STATUS, String.valueOf(1));
        long result = db.insert(TABLE_REMEMBER, null, contentValues);

    }

    public void deleteRemember(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (id > -1) {
            db.delete(TABLE_REMEMBER, "ACCOUNT_ID = ? ", new String[]{String.valueOf(id)});
        }
    }

    public void updateRememberTableRemember(int id, int status) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(REMEMBER_STATUS, status);
        db.update(TABLE_REMEMBER , values , "ACCOUNT_ID=?" , new String[]{String.valueOf(id)});

    }

    public Cursor findAccountIdRememberTable(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_REMEMBER + " WHERE " + REMEMBER_ACCOUNT_ID + " = ?", new String[]{id});
        return result;
    }

}
