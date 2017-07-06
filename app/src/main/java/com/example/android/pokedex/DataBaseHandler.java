package com.example.android.pokedex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajish on 05-07-2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pokeManager";
    private static final String TABLE_POKE = "poke";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_URL = "url";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POKE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + KEY_NAME + " TEXT NOT NULL UNIQUE,"
                + KEY_URL + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POKE);
        onCreate(db);
    }

    public void addPoke(poke_mini poke){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poke.getMname());
        values.put(KEY_URL, poke.getMurl());
        db.insert(TABLE_POKE, null, values);
        db.close();

    }



    public void deletePoke(poke_mini poke){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POKE, KEY_NAME + " = ?",
                new String[] { poke.getMname() });
        db.close();

    }

    public ArrayList<poke_mini> getAllPOke(){

        ArrayList<poke_mini> pokeList = new ArrayList<poke_mini>();
        String selectQuery = "SELECT  * FROM " + TABLE_POKE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String url = cursor.getString(cursor.getColumnIndex(KEY_URL));

                pokeList.add(new poke_mini(name,url));
            } while (cursor.moveToNext());
        }

        return pokeList;

    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_POKE);
    }
}
