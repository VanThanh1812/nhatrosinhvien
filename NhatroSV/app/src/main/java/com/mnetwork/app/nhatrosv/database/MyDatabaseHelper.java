package com.mnetwork.app.nhatrosv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mnetwork.app.nhatrosv.model.HouseOwner;
import com.mnetwork.app.nhatrosv.model.ImageRoom;
import com.mnetwork.app.nhatrosv.model.LatlngRoom;
import com.mnetwork.app.nhatrosv.model.MotelRoom;

import java.util.ArrayList;

/**
 * Created by vanthanhbk on 27/08/2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String TAG = "SQLite";
    // Phiên bản
    private static final int DATABASE_VERSION = 1;
    //tên database
    private static final String DATABASE_NAME = "HoangSaTruongSaVietNam";
    //tên bảng
    private static final String TABLE_HOUSEOWNER = "Table_HouseOwner";
    private static final String TABLE_IMAGEROOM = "Table_ImageRoom";
    private static final String TABLE_LATLOG = "Table_LatLog";
    private static final String TABLE_MOTELROOM = "Table_MotelRoom";

    // Table house owner

    public static final String OWNER_COLUMN_ID = "_id";
    public static final String OWNER_COLUMN_NAME = "owner_name";
    public static final String OWNER_COLUMN_AGE = "owner_age";
    public static final String OWNER_COLUMN_PHONE = "owner_phone";
    public static final String OWNER_COLUMN_EMAIL = "owner_email";
    public static final String OWNER_COLUMN_ADDRESS = "owner_address";


    String script_houseowner = "CREATE TABLE if not exists " + TABLE_HOUSEOWNER + " ("
            + OWNER_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + OWNER_COLUMN_NAME + " TEXT,"
            + OWNER_COLUMN_PHONE + " TEXT,"
            + OWNER_COLUMN_AGE + " INTEGER,"
            + OWNER_COLUMN_ADDRESS + " TEXT,"
            + OWNER_COLUMN_EMAIL + " TEXT"
            + ");";

    // Table image room

    public static final String IMAGE_COLUMN_ID = "_id";
    public static final String IMAGE_COLUMN_IMAGELINK = "image_link";
    public static final String IMAGE_COLUMN_ROOMID = "image_roomid";
    String script_imageroom = "CREATE TABLE if not exists " + TABLE_IMAGEROOM + " ("
            + IMAGE_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + IMAGE_COLUMN_IMAGELINK + " TEXT,"
            + IMAGE_COLUMN_ROOMID + " TEXT)";

    // Table latlog
    public static final String LATLOG_COLUMN_ID = "_id";
    public static final String LATLOG_COLUMN_LAT = "latlog_lat";
    public static final String LATLOG_COLUMN_LOG = "latlog_log";
    String script_latlogroom = "CREATE TABLE if not exists " + TABLE_LATLOG + " ("
            + LATLOG_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + LATLOG_COLUMN_LOG + " TEXT,"
            + LATLOG_COLUMN_LAT + " TEXT )";

    // Table motel room

    public static final String ROOM_COLUMN_ID = "_id";
    public static final String ROOM_COLUMN_ADDRESS = "room_address";
    public static final String ROOM_COLUMN_TYPE = "room_type";
    public static final String ROOM_COLUMN_PRICE = "room_price";
    public static final String ROOM_COLUMN_ELEC_PRICE = "room_elec_price";
    public static final String ROOM_COLUMN_WATER_PRICE = "room_water_price";
    public static final String ROOM_COLUMN_ACR = "room_acr";
    public static final String ROOM_COLUMN_DESCRIBE = "room_describe";
    public static final String ROOM_COLUMN_RATE = "room_rate";
    public static final String ROOM_COLUMN_STATUS = "room_status";
    public static final String ROOM_COLUMN_OWNER_ID = "room_id_owner";
    String script_motelroom = "CREATE TABLE if not exists " + TABLE_MOTELROOM + " ("
            + ROOM_COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL,"
            + ROOM_COLUMN_ADDRESS + " TEXT,"
            + ROOM_COLUMN_TYPE + " TEXT,"
            + ROOM_COLUMN_PRICE + " TEXT,"
            + ROOM_COLUMN_ELEC_PRICE + " TEXT,"
            + ROOM_COLUMN_WATER_PRICE + " TEXT,"
            + ROOM_COLUMN_ACR + " TEXT,"
            + ROOM_COLUMN_DESCRIBE + " TEXT,"
            + ROOM_COLUMN_RATE + " TEXT,"
            + ROOM_COLUMN_STATUS + " TEXT,"
            + ROOM_COLUMN_OWNER_ID + " INTEGER"
            + ")";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "CREATE DATABASE");
        // Chạy lệnh tạo bảng.
        sqLiteDatabase.execSQL(script_houseowner);
        sqLiteDatabase.execSQL(script_imageroom);
        sqLiteDatabase.execSQL(script_latlogroom);
        sqLiteDatabase.execSQL(script_motelroom);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HOUSEOWNER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGEROOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LATLOG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOTELROOM);
        // Và tạo lại.
        onCreate(sqLiteDatabase);
    }

    /*
    *
    * TODO: Thêm dữ liệu - vanthanh
    * */
    public void addHouseOwner (HouseOwner owner){
        Log.d(TAG,"them chu nha tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OWNER_COLUMN_ID,owner.getOwner_id());
        values.put(OWNER_COLUMN_ADDRESS,owner.getOwner_address());
        values.put(OWNER_COLUMN_AGE,owner.getOwner_age());
        values.put(OWNER_COLUMN_NAME,owner.getOwner_name());
        values.put(OWNER_COLUMN_EMAIL,owner.getOwner_email());
        values.put(OWNER_COLUMN_PHONE,owner.getOwner_phone());

        db.insert(TABLE_HOUSEOWNER,null,values);
        db.close();
        //Log.d("demo",String.valueOf(values.getAsInteger(OWNER_COLUMN_AGE)));
    }

    public void addImageRoom (ImageRoom imageRoom){
        Log.d(TAG,"them anh cho nha tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE_COLUMN_ID,imageRoom.getImage_id());
        values.put(IMAGE_COLUMN_IMAGELINK,imageRoom.getImage_link());
        values.put(IMAGE_COLUMN_ROOMID,imageRoom.getRoom_id());

        db.insert(TABLE_IMAGEROOM,null,values);
        db.close();
    }

    public void addLatLogRoom (LatlngRoom latlog) {
        Log.d(TAG,"them toa do nha tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LATLOG_COLUMN_ID,latlog.getLatlog_id());
        values.put(LATLOG_COLUMN_LOG,String.valueOf(latlog.getLatlog_log()));
        values.put(LATLOG_COLUMN_LAT,String.valueOf(latlog.getLatlog_lat()));

        db.insert(TABLE_LATLOG,null,values);
        db.close();
    }

    public void addMotelRoom (MotelRoom motelroom){
        Log.d(TAG,"them phong tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROOM_COLUMN_ID,motelroom.getRoom_id());
        values.put(ROOM_COLUMN_ADDRESS,motelroom.getRoom_address());
        values.put(ROOM_COLUMN_TYPE,motelroom.getRoom_type());
        values.put(ROOM_COLUMN_PRICE,motelroom.getRoom_price());
        values.put(ROOM_COLUMN_ELEC_PRICE,motelroom.getRoom_electric_price());
        values.put(ROOM_COLUMN_WATER_PRICE,motelroom.getRoom_water_price());
        values.put(ROOM_COLUMN_ACR,motelroom.getRoom_acreage());
        values.put(ROOM_COLUMN_DESCRIBE,motelroom.getRoom_describe());
        values.put(ROOM_COLUMN_STATUS,motelroom.getRoom_status());
        values.put(ROOM_COLUMN_RATE,motelroom.getRoom_rate());
        values.put(ROOM_COLUMN_OWNER_ID,motelroom.getRoom_id_owner());

        db.insert(TABLE_MOTELROOM,null,values);
        db.close();
    }

/*
*
*  TODO: Lấy dữ liệu - vanthanh
*
* */
    public HouseOwner getOwner (int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =db.query(TABLE_HOUSEOWNER,new String[]{OWNER_COLUMN_ID,
                OWNER_COLUMN_NAME,
                OWNER_COLUMN_PHONE,
                OWNER_COLUMN_AGE,
                OWNER_COLUMN_ADDRESS,
                OWNER_COLUMN_EMAIL
        },OWNER_COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null,null);

        if (cursor != null) cursor.moveToFirst();
        else Log.d(TAG,"khong co gia tri thoa man");

        HouseOwner owner = new HouseOwner(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5));

        return owner;
    }


    public ArrayList<ImageRoom> getListImageRoomForRoom (int id_room){
        ArrayList<ImageRoom> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor =db.query(TABLE_IMAGEROOM,new String[]{IMAGE_COLUMN_ID,
                IMAGE_COLUMN_IMAGELINK,
                IMAGE_COLUMN_ROOMID},IMAGE_COLUMN_ROOMID+"=?",new String[]{String.valueOf(id_room)},null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                ImageRoom image =new ImageRoom(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
                list.add(image);
            } while (cursor.moveToNext());
        }
        else Log.d(TAG,"ko co ");

        return list;
    }

    public ArrayList<LatlngRoom> getListLatlog_room (int id_room){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LatlngRoom> list = new ArrayList<>();

        Cursor cursor =db.query(TABLE_LATLOG,new String[]{LATLOG_COLUMN_ID,LATLOG_COLUMN_LOG,LATLOG_COLUMN_LAT},LATLOG_COLUMN_ID+"=?",
                new String[]{String.valueOf(id_room)},null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                LatlngRoom latlogRoom= new LatlngRoom(cursor.getInt(0),Double.parseDouble(cursor.getString(1)),Double.parseDouble(cursor.getString(2)));
                list.add(latlogRoom);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<MotelRoom> getListRoomByOwner (int id_owner){
        SQLiteDatabase db= this.getReadableDatabase();
        ArrayList<MotelRoom> list = new ArrayList<>();
        Cursor cursor =db.query(TABLE_MOTELROOM,new String[]{ROOM_COLUMN_ID,ROOM_COLUMN_ADDRESS,
            ROOM_COLUMN_TYPE,
            ROOM_COLUMN_PRICE,
            ROOM_COLUMN_ELEC_PRICE,
            ROOM_COLUMN_WATER_PRICE,
            ROOM_COLUMN_ACR,
            ROOM_COLUMN_DESCRIBE,
            ROOM_COLUMN_RATE,
            ROOM_COLUMN_STATUS,
            ROOM_COLUMN_OWNER_ID},
                ROOM_COLUMN_OWNER_ID +"=?",new String[]{String.valueOf(id_owner)},null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
            do {

                MotelRoom room = new MotelRoom(cursor.getInt(0),//id
                        cursor.getString(1),//address
                        cursor.getString(2),//type
                        Double.parseDouble(cursor.getString(3)),//gia phong
                        Double.parseDouble(cursor.getString(4)),//gia dien
                        Double.parseDouble(cursor.getString(5)),//gia nuoc
                        Double.parseDouble(cursor.getString(6)),//dien tich
                        cursor.getString(7),//mo ta
                        cursor.getInt(8),// danh gia
                        cursor.getString(9),//trang thai
                        cursor.getInt(10));//id phong tro
                list.add(room);
            } while (cursor.moveToNext());
        }
        else Log.d(TAG,"ko co ");


        return list;
    }

    public MotelRoom getMotelRoomById (int id_room ){
        MotelRoom room = new MotelRoom();

        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor =db.query(TABLE_MOTELROOM,new String[]{ROOM_COLUMN_ID,ROOM_COLUMN_ADDRESS,
                        ROOM_COLUMN_TYPE,
                        ROOM_COLUMN_PRICE,
                        ROOM_COLUMN_ELEC_PRICE,
                        ROOM_COLUMN_WATER_PRICE,
                        ROOM_COLUMN_ACR,
                        ROOM_COLUMN_DESCRIBE,
                        ROOM_COLUMN_RATE,
                        ROOM_COLUMN_STATUS,
                        ROOM_COLUMN_OWNER_ID},
                ROOM_COLUMN_ID +"=?",new String[]{String.valueOf(id_room)},null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
                room = new MotelRoom(cursor.getInt(0),//id
                        cursor.getString(1),//address
                        cursor.getString(2),//type
                        Double.parseDouble(cursor.getString(3)),//gia phong
                        Double.parseDouble(cursor.getString(4)),//gia dien
                        Double.parseDouble(cursor.getString(5)),//gia nuoc
                        Double.parseDouble(cursor.getString(6)),//dien tich
                        cursor.getString(7),//mo ta
                        cursor.getInt(8),// danh gia
                        cursor.getString(9),//trang thai
                        cursor.getInt(10));//id phong tro

        }
        else Log.d(TAG,"ko co ");



        return room;
    }

    public ArrayList<MotelRoom> getAllRoom (){
        ArrayList<MotelRoom> noteList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOTELROOM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {

                MotelRoom room = new MotelRoom(cursor.getInt(0),//id
                        cursor.getString(1),//address
                        cursor.getString(2),//type
                        Double.parseDouble(cursor.getString(3)),//gia phong
                        Double.parseDouble(cursor.getString(4)),//gia dien
                        Double.parseDouble(cursor.getString(5)),//gia nuoc
                        Double.parseDouble(cursor.getString(6)),//dien tich
                        cursor.getString(7),//mo ta
                        cursor.getInt(8),// danh gia
                        cursor.getString(9),//trang thai
                        cursor.getInt(10));//id phong tro
                noteList.add(room);
            } while (cursor.moveToNext());
        }
        else Log.d(TAG,"ko co room");


        return noteList;


    }
    /*
    * TODO: update du lieu - vanthanh
    * */
    public int updateOwner (HouseOwner owner){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OWNER_COLUMN_ID,owner.getOwner_id());
        values.put(OWNER_COLUMN_ADDRESS,owner.getOwner_address());
        values.put(OWNER_COLUMN_AGE,owner.getOwner_age());
        values.put(OWNER_COLUMN_NAME,owner.getOwner_name());
        values.put(OWNER_COLUMN_EMAIL,owner.getOwner_email());
        values.put(OWNER_COLUMN_PHONE,owner.getOwner_phone());

        return db.update(TABLE_HOUSEOWNER,values,OWNER_COLUMN_ID+" =?",new String[]{String.valueOf(owner.getOwner_id())});
    }

    public int updateImageRoom (ImageRoom imageRoom){
        Log.d(TAG,"them anh cho nha tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE_COLUMN_ID,imageRoom.getImage_id());
        values.put(IMAGE_COLUMN_IMAGELINK,imageRoom.getImage_link());
        values.put(IMAGE_COLUMN_ROOMID,imageRoom.getRoom_id());

        return db.update(TABLE_IMAGEROOM,values,IMAGE_COLUMN_ID+"=?",new String[]{String.valueOf(imageRoom.getImage_id())});
    }

    public int updateLatlogRoom (LatlngRoom latlog){
        Log.d(TAG,"them toa do nha tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LATLOG_COLUMN_ID,latlog.getLatlog_id());
        values.put(LATLOG_COLUMN_LOG,String.valueOf(latlog.getLatlog_log()));
        values.put(LATLOG_COLUMN_LAT,String.valueOf(latlog.getLatlog_lat()));

        return db.update(TABLE_LATLOG,values,LATLOG_COLUMN_ID+"=?",new String[]{String.valueOf(latlog.getLatlog_id())});
    }

    public int updateMotelRoom (MotelRoom motelroom){
        Log.d(TAG,"them phong tro");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROOM_COLUMN_ID,motelroom.getRoom_id());
        values.put(ROOM_COLUMN_ADDRESS,motelroom.getRoom_address());
        values.put(ROOM_COLUMN_TYPE,motelroom.getRoom_type());
        values.put(ROOM_COLUMN_PRICE,motelroom.getRoom_price());
        values.put(ROOM_COLUMN_ELEC_PRICE,motelroom.getRoom_electric_price());
        values.put(ROOM_COLUMN_WATER_PRICE,motelroom.getRoom_water_price());
        values.put(ROOM_COLUMN_ACR,motelroom.getRoom_acreage());
        values.put(ROOM_COLUMN_DESCRIBE,motelroom.getRoom_describe());
        values.put(ROOM_COLUMN_STATUS,motelroom.getRoom_status());
        values.put(ROOM_COLUMN_RATE,motelroom.getRoom_rate());
        values.put(ROOM_COLUMN_OWNER_ID,motelroom.getRoom_id_owner());

        return db.update(TABLE_MOTELROOM,values,ROOM_COLUMN_ID+"=?",new String[]{String.valueOf(motelroom.getRoom_id())});
    }

    /*
    * TODO: delete 1 truong
    * */
    public void deleteHouseOwnerById (int id){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(TABLE_HOUSEOWNER,OWNER_COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.delete(TABLE_LATLOG,LATLOG_COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteImagebyId (int id){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_IMAGEROOM,IMAGE_COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteMotelRoomById (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOTELROOM,ROOM_COLUMN_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
}
