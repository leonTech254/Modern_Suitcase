package com.leonteqsecurity.suitcasenew.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import com.leonteqsecurity.suitcasenew.Models.User;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vacation_database";
    private static final int DATABASE_VERSION = 15;
    private static final String TABLE_NAME = "vacation_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_IMAGE_BACKGROUND = "image_background";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String TABLE_NAME_VACATION_ITEMS = "vacation_items";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_DESCRIPTION = "item_description";
    private static final String COLUMN_ITEM_PRICE = "item_price";
    private static final String COLUMN_IS_ITEM_PURCHASED = "is_item_purchased";
    private static final String COLUMN_COUNTRY_NAME = "country_name";
    private static final String COLUMN_PRODUCT_IMAGE = "productImage";

    // New fields for user table
    private static final String TABLE_NAME_USERS = "user_data";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_LOCATION + " TEXT, "
                    + COLUMN_IMAGE_BACKGROUND + " TEXT, "
                    + COLUMN_DESCRIPTION + " TEXT)";

    private static final String CREATE_TABLE_VACATION_ITEMS =
            "CREATE TABLE " + TABLE_NAME_VACATION_ITEMS + "("
                    + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ITEM_NAME + " TEXT, "
                    + COLUMN_ITEM_DESCRIPTION + " TEXT, "
                    + COLUMN_ITEM_PRICE + " TEXT, "
                    + COLUMN_IS_ITEM_PURCHASED + " INTEGER DEFAULT 0, "
                    + COLUMN_PRODUCT_IMAGE + " TEXT, "
                    + COLUMN_COUNTRY_NAME + " TEXT)";

    // New table creation query for user data
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_NAME_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_USERNAME + " TEXT, "
                    + COLUMN_FIRST_NAME + " TEXT, "
                    + COLUMN_LAST_NAME + " TEXT, "
                    + COLUMN_EMAIL + " TEXT, "
                    + COLUMN_PASSWORD + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_VACATION_ITEMS);
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_VACATION_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        onCreate(db);
    }

    // CRUD operations for vacation_table

    public long insertVacation(CardItemVacations vacation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, vacation.getVacationLocation());
        values.put(COLUMN_IMAGE_BACKGROUND, vacation.getImageBackground());
        values.put(COLUMN_DESCRIPTION, vacation.getDescription());
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
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
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

    // CRUD operations for vacation_items

    // Create
    public long insertVacationItem(VacationItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_DESCRIPTION, item.getItemDescription());
        values.put(COLUMN_ITEM_PRICE, item.getItemPrice());
        values.put(COLUMN_IS_ITEM_PURCHASED, item.isItemPurchased() ? 1 : 0);
        values.put(COLUMN_COUNTRY_NAME, item.getContryname());
        values.put(COLUMN_PRODUCT_IMAGE, item.getProductImage());

        long newRowId = db.insert(TABLE_NAME_VACATION_ITEMS, null, values);
        db.close();
        return newRowId;
    }

    // Read all items
    @SuppressLint("Range")
    public List<VacationItem> getAllVacationItems() {
        List<VacationItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_VACATION_ITEMS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                VacationItem vacationItem = new VacationItem();
                vacationItem.setItemName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                vacationItem.setItemDescription(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DESCRIPTION)));
                vacationItem.setItemPrice(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PRICE)));
                vacationItem.setItemPurchased(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ITEM_PURCHASED)) == 1);
                vacationItem.setProductImage(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMAGE)));
                vacationItem.setContryname(cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY_NAME)));

                itemList.add(vacationItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

    // Update
    public int updateVacationItem(VacationItem item, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_DESCRIPTION, item.getItemDescription());
        values.put(COLUMN_ITEM_PRICE, item.getItemPrice());
        values.put(COLUMN_PRODUCT_IMAGE, item.getProductImage());
        values.put(COLUMN_IS_ITEM_PURCHASED, item.isItemPurchased() ? 1 : 0);

        return db.update(
                TABLE_NAME_VACATION_ITEMS,
                values,
                COLUMN_ITEM_DESCRIPTION + " = ?",
                new String[]{description}
        );
    }

    // Delete
    public void deleteVacationItem(String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_VACATION_ITEMS, COLUMN_ITEM_DESCRIPTION + " = ?", new String[]{String.valueOf(description)});
        db.close();
    }

    // CRUD operations for user_data

    // Create
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long newRowId = db.insert(TABLE_NAME_USERS, null, values);
        db.close();
        return newRowId;
    }

    // Read all users
    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_USERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    // Update
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        return db.update(TABLE_NAME_USERS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    // Delete
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_USERS, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }



    


}
