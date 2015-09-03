package pixzar.nujoyw.myrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SCIENCE on 9/3/2015.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit คือการประกาศตัวแปร เพื่อบอกว่า Class ที่คุณเรียกใช้ ต้องใช้ ตัวแปรอะไรบ้าง
    // ค่าคงที่ที่ไม่สามารถเปลี่ยนแปลงได้
    // สร้างฐานข้อมูลชื่อ Restaurant.db
    private static final String DATABASE_NAME = "Restaurant.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTABLE (_id integer primary key, User text, Password text, Name text);";
    private static final String CREATE_FOOD_TABLE = "create table foodTABLE (_id integer primary key, Food text, Source text, Price text);";
    // private static final String CREATE_ORDER_TABLE = "create table orderTABLE (_id integer primary key, officer text, Desk text, Food text, Item text);";

    //Constructor คือตัวที่ถูกเรียกใช้อัตโนมัติ มันจึงร้องขอให้ต่อท่อ และสร้าง context มองหาตัวแปรที่ชื่อว่า DATABASE NAME และ DATABASE_VERSION
    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }//Contructor

    //เมื่อไหร่ก็ตามที่มีการ oncreate ให้สร้างตารางฐานข้อมูลตามนี้น๊ะจ๊ะ
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}//Main Class
