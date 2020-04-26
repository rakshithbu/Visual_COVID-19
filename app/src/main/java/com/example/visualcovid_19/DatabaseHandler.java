package com.example.visualcovid_19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE =
                "CREATE TABLE " +  Util.TABLE_NAME + "("
                        + Util.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Util.KEY_NAME + " TEXT" +")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public ArrayList<String> allStarredCountries() {

        ArrayList<String> countries = new ArrayList<>();
        String query = "SELECT  "+Util.KEY_NAME+ " FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                countries.add((cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        return countries;
    }

    public void addStarredCounties(String countryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, countryName);
        // insert
        db.insert(Util.TABLE_NAME,null, values);
        db.close();
    }

    public void deleteStarredCountry(String countryName){
        SQLiteDatabase db = this.getWritableDatabase();
        String table = Util.TABLE_NAME;
        String whereClause = Util.KEY_NAME+"=?";
        String[] whereArgs = new String[] { String.valueOf(countryName) };
        db.delete(table, whereClause, whereArgs);
    }


}
