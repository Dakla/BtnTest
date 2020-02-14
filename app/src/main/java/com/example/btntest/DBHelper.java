package com.example.btntest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "WS";
    public static final String TABLE_ITEMS = "items";
    public static final String TABLE_CLOTH = "cloth";
    public static final String TABLE_FURNITURE = "furniture";
    public static final String TABLE_EDGING = "edging";
    public static final String TABLE_ORDERS = "user_orders";

    public static final String ITEM_ID = "_id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_WIDTH = "width";
    public static final String ITEM_HEIGHT = "height";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_CLOTH = "cloth";
    public static final String ITEM_EDGING = "edging";
    public static final String ITEM_FURNITURE = "furniture";

    public static final String CLOTH_ID = "_id";
    public static final String CLOTH_NAME = "name";
    public static final String CLOTH_PRICE = "price";

    public static final String FURNITURE_ID = "_id";
    public static final String FURNITURE_NAME = "name";
    public static final String FURNITURE_PRICE = "price";

    public static final String EDGING_ID = "_id";
    public static final String EDGING_NAME = "name";
    public static final String EDGING_PRICE = "price";

    public static final String ORDER_ID = "_id";
    public static final String ORDER_ITEMS = "items";
    public static final String ORDER_TOTAL_PRICE = "total_price";
    public static final String ORDER_STATUS = "order_status";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CLOTH + " ("
                + CLOTH_ID + " INTEGER PRIMARY KEY, "
                + CLOTH_NAME + " TEXT NOT NULL, "
                + CLOTH_PRICE + " REAL NOT NULL)"
        );
        db.execSQL("CREATE TABLE " + TABLE_EDGING + " ("
                + EDGING_ID + " INTEGER PRIMARY KEY, "
                + EDGING_NAME + " TEXT NOT NULL, "
                + EDGING_PRICE + " REAL NOT NULL)"
        );
        db.execSQL("CREATE TABLE " + TABLE_FURNITURE + " ("
                + FURNITURE_ID + " INTEGER PRIMARY KEY, "
                + FURNITURE_NAME + " TEXT NOT NULL, "
                + FURNITURE_PRICE + " REAL NOT NULL)"
        );
        db.execSQL("CREATE TABLE " + TABLE_ITEMS + " ("
                + ITEM_ID + " INTEGER PRIMARY KEY, "
                + ITEM_NAME + " TEXT NOT NULL, "
                + ITEM_HEIGHT + " REAL NOT NULL, "
                + ITEM_WIDTH + " REAL NOT NULL, "
                + ITEM_PRICE + " REAL,"
                + ITEM_CLOTH + " INTEGER NOT NULL, "
                + ITEM_FURNITURE + " INTEGER, "
                + ITEM_EDGING + " INTEGER," +
                "FOREIGN KEY (" + ITEM_CLOTH + ") REFERENCES " + TABLE_CLOTH + "(" + CLOTH_ID + "), " +
                "FOREIGN KEY (" + ITEM_FURNITURE + ") REFERENCES " + TABLE_FURNITURE + "(" + FURNITURE_ID + "), " +
                "FOREIGN KEY (" + ITEM_EDGING + ") REFERENCES " + TABLE_EDGING + "(" + EDGING_ID + ")" +
                ")"
        );
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " ("
            + ORDER_ID + " INTEGER PRIMARY KEY, "
            + ORDER_ITEMS + " INTEGER, "
            + ORDER_TOTAL_PRICE + " REAL, "
            + ORDER_STATUS + " TEXT, " +
            "FOREIGN KEY (" + ORDER_ITEMS + ") REFERENCES " + TABLE_ITEMS + "(" + ITEM_ID + ")" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EDGING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FURNITURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

        onCreate(db);
    }
}
