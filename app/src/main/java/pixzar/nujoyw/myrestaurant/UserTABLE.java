package pixzar.nujoyw.myrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SCIENCE on 9/3/2015.
 */
public class UserTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }//Constructor เป็นMethod ที่เดียวกับ Class เชื่อมต่อ Mysql เพื่อให้โยนข้อมูลเข้าไปได้

    //สร้าง Method ที่มีการรับค่า อาร์กิวเมนต
    // โยนค่ากลับเป็น Array
    public String[] searchUser(String strUser) {


        try {

            String[] strResult = null;
            //ดึงฐานข้อมูลทั้งหมดไปไว้ที่ ram ก่อน แก้ไข โยนกลับไปทับ
            Cursor objCursor = readSqLiteDatabase.query(USER_TABLE,
                    new String[]{COLUMN_ID_USER, COLUMN_USER, COLUMN_PASSWORD, COLUMN_NAME},
                    COLUMN_USER + "=?",// =? คืออะไรก็ได้
                    new String[]{String.valueOf(strUser)},//หาค่าจาก่ไหน
                    null,null,null,null);
            if (objCursor != null) { //ไม่ได้เอาค่าว่างเปล่ามาให้มันหานะ ไม่งั้นerror
                if (objCursor.moveToFirst()) {
                    strResult = new String[4];
                    strResult[0] = objCursor.getString(0);//หมายถึง id
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);
                }
            }
            objCursor.close();
            return strResult;

        } catch (Exception e) {
            return null;
        }


       // return new String[0];
    }


    public long addNewUser(String strUser, String strPassword, String strName) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_NAME, strName);

        return readSqLiteDatabase.insert(USER_TABLE, null, objContentValues);
    }




}//Main Class
