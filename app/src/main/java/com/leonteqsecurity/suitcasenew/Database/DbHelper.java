package com.leonteqsecurity.suitcasenew.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "vacation_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "vacation_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_IMAGE_BACKGROUND = "image_background";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_LOCATION + " TEXT, "
                    + COLUMN_IMAGE_BACKGROUND + " TEXT"
                    + ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertVacation(CardItemVacations vacation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, vacation.getVacationLocation());
        values.put(COLUMN_IMAGE_BACKGROUND, vacation.getImageBackground());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<CardItemVacations> getAllVacations() {
        List<CardItemVacations> vacationList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                 CardItemVacations vacation = new CardItemVacations(
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_BACKGROUND)),
                         cursor.getInt(cursor.getColumnIndex(COLUMN_ID))

                );
                vacationList.add(vacation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return vacationList;
    }

    public int updateVacation(CardItemVacations vacation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, vacation.getVacationLocation());
        values.put(COLUMN_IMAGE_BACKGROUND, vacation.getImageBackground());
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(vacation.getId())});
    }

    public void deleteVacation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
