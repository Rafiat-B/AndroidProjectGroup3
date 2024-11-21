package com.vy.androidgroup3shoppingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MySqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shoppingApp.db";

    // User table creation SQL
    private static final String CREATE_USER_TABLE = "CREATE TABLE User (" +
            "userid INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "email TEXT UNIQUE NOT NULL, " +
            "phone_number TEXT);";

    // Product details table creation SQL
    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE productdetails (" +
            "productid INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "product_name TEXT NOT NULL, " +
            "category TEXT NOT NULL, " +
            "details TEXT, " +
            "price DECIMAL NOT NULL, " +
            "image TEXT);"; // Image will store the image name (or path)

    // Order table creation SQL
    private static final String CREATE_ORDER_TABLE = "CREATE TABLE ordertable (" +
            "orderid INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userid INTEGER NOT NULL, " +
            "orderstatus TEXT DEFAULT 'Pending', " +
            "FOREIGN KEY (userid) REFERENCES User(userid));";

    // OrderItems table creation SQL (New Table)
    private static final String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE orderitems (" +
            "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "orderid INTEGER NOT NULL, " +
            "productid INTEGER NOT NULL, " +
            "quantity INTEGER DEFAULT 1, " +
            "FOREIGN KEY (orderid) REFERENCES ordertable(orderid), " +
            "FOREIGN KEY (productid) REFERENCES productdetails(productid));";

    // Payment table creation SQL
    private static final String CREATE_PAYMENT_TABLE = "CREATE TABLE payment (" +
            "paymentid INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userid INTEGER NOT NULL, " +
            "orderid INTEGER NOT NULL, " +
            "address TEXT NOT NULL, " +
            "totalamount DECIMAL NOT NULL, " +
            "paymentmethod TEXT DEFAULT 'Visa', " +
            "paymentstatus TEXT DEFAULT 'Pending', " +
            "FOREIGN KEY (userid) REFERENCES User(userid), " +
            "FOREIGN KEY (orderid) REFERENCES ordertable(orderid));";

    // Constructor
    public MySqlHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);
        db.execSQL(CREATE_PAYMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all tables if they exist and create new ones
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS productdetails");
        db.execSQL("DROP TABLE IF EXISTS ordertable");
        db.execSQL("DROP TABLE IF EXISTS orderitems"); // Drop OrderItems table if exists
        db.execSQL("DROP TABLE IF EXISTS payment");
        onCreate(db);
    }
}
