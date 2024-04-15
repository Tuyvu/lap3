package com.example.baikt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String TableName = "ContactTable";
    public static final String SBD = "SBD";
    public static final String Name = "Name";
    public static final String Toan = "Toan";
    public static final String Ly = "Ly";
    public static final String Hoa = "Hoa";
    public static final String TongD = "Tong";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TableName + " ("
                + SBD + " INTEGER PRIMARY KEY, "
                + Name + " TEXT, "
                + Toan + " FLOAT, "
                + Ly + " FLOAT, "
                + Hoa + " FLOAT,"
                + TongD + " FLOAT"
                + ")";

        // Chạy câu lệnh SQL để tạo bảng
        db.execSQL(sqlCreate);
    }
    public  void addContact(Thisinh p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SBD, p.getSBD());
        values.put(Name, p.getHoten());
        values.put(Toan, p.getT());
        values.put(Ly, p.getL());
        values.put(Hoa, p.getH());
        values.put(TongD, p.TinhdiemTB());
        db.insert(TableName, null,values);
        db.close();

    }
    public ArrayList<Thisinh> getAllContact(){
        ArrayList<Thisinh> list = new ArrayList<>();
        String sql = "Select * from "  + TableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Thisinh TS = new Thisinh(cursor.getString(0),cursor.getString(1),
                        cursor.getFloat(2),cursor.getFloat(3),cursor.getFloat(4));
                list.add(TS);
            }
        }
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cập nhật cơ sở dữ liệu nếu cần khi cơ sở dữ liệu được nâng cấp.
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }
    public void updateContact(String id, Thisinh updatedThisinh){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, updatedThisinh.getHoten());
        values.put(SBD, updatedThisinh.getSBD());
        values.put(Toan, updatedThisinh.getT());
        values.put(Ly, updatedThisinh.getL());
        values.put(Hoa, updatedThisinh.getH());
        values.put(TongD, updatedThisinh.TinhdiemTB());
        db.update(TableName, values,SBD + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
