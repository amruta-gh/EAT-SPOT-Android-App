package com.example.amruta.csuffoodie.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.amruta.csuffoodie.model.MenuItem;
import com.example.amruta.csuffoodie.model.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String LOG = DatabaseHandler.class.getName();
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EatSpotDB.db";
    //private static final String DB_Path = "/data/data/com.example.amruta.admin/";

    private Context myContext = null;
    //database
    //SQLiteDatabase db;

    // Table Names
    private static final String TABLE_MENU_ITEM = "MenuItem";
    private static final String TABLE_RESTAURANT = "Restaurant";
    private static final String TABLE_USER_INFO = "UserInfo";
    private static final String KEY_ID = "Id";

    // Table Create Statements
    /*
    CREATE TABLE "Restaurant" (
        "Id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,
        "Name" VARCHAR NOT NULL ,
        "Description" VARCHAR NOT NULL ,
        "Address" VARCHAR NOT NULL ,
        "Loc_Lat" DOUBLE,
        "Loc_Long" DOUBLE,
        "Timing" VARCHAR,
        "Image" VARCHAR NOT NULL  DEFAULT default_image
        )
     */
    /*
    CREATE TABLE "MenuItem"
        ("Id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,
        "RestaurantId" INTEGER NOT NULL ,
        "Price" DOUBLE NOT NULL ,
        "ItemName" VARCHAR NOT NULL ,
        "Available" BOOL NOT NULL  DEFAULT True,
        "Image" VARCHAR NOT NULL  DEFAULT default_image)
     */

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        /*try {
            this.openDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        myContext = context;
        //db = openDatabase();
        //onCreate(null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create a new instance if db is null
        if (db == null) {
            db = this.getWritableDatabase();
        }
        // creating required tables
        Log.e("DatabaseHandler: ", "Creating the database tables ... ");
        String create_restaurant = "CREATE TABLE Restaurant " +
                "(Id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "Name VARCHAR NOT NULL , " +
                "Description VARCHAR NOT NULL , " +
                "Address VARCHAR NOT NULL , " +
                "Loc_Lat DOUBLE, " +
                "Loc_Long DOUBLE, " +
                "Timing VARCHAR, " +
                "Image VARCHAR NOT NULL  DEFAULT default_rest_image , "+
                "Phone VARCHAR)";
        db.execSQL(create_restaurant);

        String create_menu_item = "CREATE TABLE MenuItem " +
                "(Id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                "RestaurantId INTEGER NOT NULL , " +
                "Price DOUBLE NOT NULL , " +
                "ItemName VARCHAR NOT NULL , " +
                "Available BOOL NOT NULL  DEFAULT 1,  " +
                "Image VARCHAR NOT NULL  DEFAULT default_menu_image)";
        db.execSQL(create_menu_item);

        String create_user_info = "CREATE TABLE UserInfo" +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , " +
                 "Username VARCHAR NOT NULL , " +
                 "Password TEXT NOT NULL , " +
                 "EmailId  TEXT NOT NULL UNIQUE , " +
                 "PhoneNo TEXT NOT NULL UNIQUE)";
        db.execSQL(create_user_info);

        /*String create_restaurant = "CREATE TABLE \"Restaurant\" " +
                "(\"Id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                "\"Name\" VARCHAR NOT NULL , " +
                "\"Description\" VARCHAR NOT NULL , " +
                "\"Address\" VARCHAR NOT NULL , " +
                "\"Loc_Lat\" DOUBLE, " +
                "\"Loc_Long\" DOUBLE, " +
                "\"Timing\" VARCHAR, " +
                "\"Image\" VARCHAR NOT NULL  DEFAULT default_image)";
        db.execSQL(create_restaurant);

        String create_menu_item = "CREATE TABLE \"MenuItem\" " +
                "(\"Id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                "\"RestaurantId\" INTEGER NOT NULL , " +
                "\"Price\" FLOAT NOT NULL , " +
                "\"ItemName\" VARCHAR NOT NULL , " +
                "\"Available\" BOOL NOT NULL  DEFAULT True,  " +
                "\"Image\" VARCHAR NOT NULL  DEFAULT default_image)";
        db.execSQL(create_menu_item); */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ITEM);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER_INFO);
        // create new tables
        onCreate(db);
    }

    public void openDatabase() throws SQLException {
        String myPath = myContext.getFilesDir() + DATABASE_NAME;
        Log.e("DataaseHandler, path: ", myPath);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                myPath,
                null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    // ------------------------ "UserInformation" table methods ----------------//

    public Cursor getUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Log.e("SQLiteDatabase","" +db);
        String[] columns = {"UserName","Password"};
        //  Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        Cursor cursor = db.query(TABLE_USER_INFO,columns,null,null,null,null,null);
        return cursor;
    }

    public boolean insertUserInfo(
            String userName,
            String password,
            String emailId,
            String phoneNo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username",userName);
        contentValues.put("Password",password);
        contentValues.put("EmailId",emailId);
        contentValues.put("PhoneNo", phoneNo);
        Long result = db.insert(TABLE_USER_INFO, null, contentValues);
        if(result == -1) {
            return false;
        }
        else{
            return true;
        }
    }

    // ------------------------ "Restaurant" table methods ----------------//

     //Creating a restuarant
    public long createRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, -1);
        values.put("Name", restaurant.getName());
        values.put("Description", restaurant.getDescription());
        values.put("Address", restaurant.getAddress());
        values.put("Image", restaurant.getImage());
        //TODO: add more fields ...

        // insert row
        long res_id = db.insert(TABLE_RESTAURANT, null, values);

        return res_id;
    }

     //get single Restaurant Record
    public Restaurant getRestaurant(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + KEY_ID + " = " + id;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        restaurant.setName((c.getString(c.getColumnIndex("Name"))));
        restaurant.setDescription(c.getString(c.getColumnIndex("Description")));
        restaurant.setImage(c.getString(c.getColumnIndex("Image")));
        //TODO: add more fields

        return restaurant;
    }

     // getting all Restaurants
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantsList = new ArrayList<Restaurant>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                restaurant.setName((c.getString(c.getColumnIndex("Name"))));
                restaurant.setDescription(c.getString(c.getColumnIndex("Description")));
                restaurant.setImage(c.getString(c.getColumnIndex("Image")));
                // TODO: add more fields

                restaurantsList.add(restaurant);
            } while (c.moveToNext());
        }

        return restaurantsList;
    }

    // ------------------------ "MenuItem" table methods ----------------//

    //creates a menu item from the menu for a particular restaurant
    public boolean createMenuItemForRestaurant(MenuItem menuItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean insert_successful = true;

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, -1); // auto increment key
        values.put("RestaurantId", menuItem.getRestaurantId());
        values.put("Price", menuItem.getPrice());
        values.put("ItemName", menuItem.getItemName());
        values.put("Available", menuItem.isAvailable());
        values.put("Image", menuItem.getImage());

        // insert row
        long menu_item_id = db.insert(TABLE_MENU_ITEM, null, values);
        if (menu_item_id > 0)
            insert_successful = true;
        else
            insert_successful = false;

        return insert_successful;
    }

     //get single MenuItem Record for menu id
    public MenuItem getMenuItem(int restaurantId, int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MENU_ITEM
                + " WHERE " + KEY_ID + " = " + id
                + " and RestaurantId" + " = " + restaurantId;
        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        MenuItem menuItem = new MenuItem();
        menuItem.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        menuItem.setRestaurantId(c.getInt(c.getColumnIndex("RestaurantId")));
        menuItem.setItemName(c.getString(c.getColumnIndex("ItemName")));
        menuItem.setPrice(c.getFloat(c.getColumnIndex("Price")));
        if (c.getType(c.getColumnIndex("Available")) > 0)
            menuItem.setAvailable(true);
        else
            menuItem.setAvailable(false);
        menuItem.setImage(c.getString(c.getColumnIndex("Image")));
        //TODO: add more fields

        return menuItem;
    }

    /**
     * getting all the menu items for one restaurant
     * i.e. Menu for that restaurant
     * */
    public List<MenuItem> getRestaurantMenuItems(int restaurantId) {
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENU_ITEM + " WHERE "
                + "RestaurantId" + " = " + restaurantId;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                menuItem.setRestaurantId(c.getInt(c.getColumnIndex("RestaurantId")));
                menuItem.setItemName(c.getString(c.getColumnIndex("ItemName")));
                menuItem.setPrice(c.getDouble(c.getColumnIndex("Price")));
                if (c.getInt(c.getColumnIndex("Available")) > 0)
                    menuItem.setAvailable(true);
                else
                    menuItem.setAvailable(false);
                menuItem.setImage(c.getString(c.getColumnIndex("Image")));

                menuItemList.add(menuItem);
            } while (c.moveToNext());
        }

        return menuItemList;
    }

    /**
     * getting all the menu items for one restaurant and selected menu items
     * i.e. Menu for that restaurant
     * */
    public List<MenuItem> getSelectedRestaurantMenuItems(int restaurantId,
                                                         List<Integer> itemIds)
    {
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENU_ITEM + " WHERE "
                + "RestaurantId" + " = " + restaurantId;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MenuItem menuItem = new MenuItem();
                menuItem.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                menuItem.setRestaurantId(c.getInt(c.getColumnIndex("RestaurantId")));
                menuItem.setItemName(c.getString(c.getColumnIndex("ItemName")));
                menuItem.setPrice(c.getDouble(c.getColumnIndex("Price")));
                if (c.getInt(c.getColumnIndex("Available")) > 0)
                    menuItem.setAvailable(true);
                else
                    menuItem.setAvailable(false);
                menuItem.setImage(c.getString(c.getColumnIndex("Image")));

                menuItemList.add(menuItem);
            } while (c.moveToNext());
        }

        return menuItemList;
    }

    //deletes a menu item from the menu
    public boolean deleteMenuItem(int menu_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MENU_ITEM, KEY_ID + " = ?",
                new String[]{String.valueOf(menu_id)});

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

     // Updating a menuitem
    public int updateMenuItem(MenuItem menuItem) {
        Log.e("DatabaseHandler: ", "menuItem"+menuItem.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_ID, menuItem.getId()); //primary key
        values.put("RestaurantId", menuItem.getRestaurantId());
        values.put("Price", menuItem.getPrice());
        values.put("ItemName", menuItem.getItemName());
        if (menuItem.isAvailable()) {
            values.put("Available", 1);
        } else {
            values.put("Available", 0);
        }
        values.put("Image", menuItem.getImage());

        // updating row
        int value =  db.update(TABLE_MENU_ITEM, values, KEY_ID + " = ?",
                new String[]{String.valueOf(menuItem.getId())});

        Log.e("DatabaseHandler: ", "value"+value);
        return value;
    }
}