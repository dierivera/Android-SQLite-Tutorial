package com.dierivera.ubp.unidaddos.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dierivera.ubp.unidaddos.Models.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dierivera on 6/17/17.
 */

public class DatabaseHelper {

    // Logcat
    private static final String TAG = "database";

    public static final String DATABASE_NAME = "clients.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CLIENTS = "clients";



    //String that holds the table creation
    private static final String CREATE_DATABASE = "CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTS + " (" +
            Constants.KEY_ID + " INTEGER PRIMARY KEY," +
            Constants.KEY_FIRST_NAME + " TEXT," +
            Constants.KEY_LAST_NAME + " TEXT," +
            Constants.KEY_PHONE_NUMBER + " TEXT," +
            Constants.KEY_EMAIL + " TEXT" + ")";

    private DbHelper _dbHelper;

    public DatabaseHelper(Context c) {
        _dbHelper = DbHelper.getInstance(c);
    }

    public void close() {
        _dbHelper.close();
    }

    public List<Client> getAll( ) {
        return _dbHelper.getAllClientsRows();
    }

    public long createClient(Client client){
        return _dbHelper.createClient(client);
    }

    public long updateClient(Client client){
        return _dbHelper.updateClient(client);
    }

    public int deleteClient(Client client){
        try {
            _dbHelper.deleteEntry(client.getId());
            return 0;
        }catch (Exception e){
            return -1;
        }
    }


    //CLASE QUE TRABAJA LA BASE DE DATOS
    public static class DbHelper extends SQLiteOpenHelper {
        static DbHelper instance = null;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public static DbHelper getInstance( Context cnt ) {
            if( instance == null ) {
                instance = new DbHelper( cnt );
            }
            return instance;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL( CREATE_DATABASE );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }

        public long createClient(Client client) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.KEY_FIRST_NAME, client.getName());
            values.put(Constants.KEY_LAST_NAME, client.getLastName());
            values.put(Constants.KEY_EMAIL, client.getEmail());
            values.put(Constants.KEY_PHONE_NUMBER, client.getPhoneNumber());
            // insert row
            return  db.insert(TABLE_CLIENTS, null, values);
        }

        public long updateClient (Client client){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.KEY_FIRST_NAME, client.getName());
            values.put(Constants.KEY_LAST_NAME, client.getLastName());
            values.put(Constants.KEY_EMAIL, client.getEmail());
            values.put(Constants.KEY_PHONE_NUMBER, client.getPhoneNumber());
            String whereClause = Constants.KEY_ID + "=" + client.getId();
            // update row
            int rowsAffected = db.update(TABLE_CLIENTS, values, whereClause, null); //returns the number of rows affected when edited (1 or 0 because id being unique id)
            return rowsAffected;
        }

        //get all clients
        public List<Client> getAllClientsRows() {
            List<Client> rows = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_CLIENTS + " ORDER BY " + Constants.KEY_ID + " ASC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor  cursor = db.rawQuery(selectQuery,null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID));
                    String firstName = cursor.getString(cursor.getColumnIndex(Constants.KEY_FIRST_NAME));
                    String lastName = cursor.getString(cursor.getColumnIndex(Constants.KEY_LAST_NAME));
                    String email = cursor.getString(cursor.getColumnIndex(Constants.KEY_EMAIL));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(Constants.KEY_PHONE_NUMBER));
                    Client client = new Client(id, firstName, lastName, email, phoneNumber);
                    // adding to rows list
                    rows.add(client);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return rows;
        }

        //deletes a client
        public void deleteEntry(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] whereArgs = new String[] { id + "" };
            db.delete(TABLE_CLIENTS, Constants.KEY_ID + "=?", whereArgs);
        }

    }//ENDS DbHelper

}
