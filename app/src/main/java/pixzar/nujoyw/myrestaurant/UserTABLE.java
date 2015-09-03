package pixzar.nujoyw.myrestaurant;

import android.content.ContentValues;
import android.content.Context;
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
    public static final String COLUMN_PASSWORD = "User";
    public static final 

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }//Constructor เป็นMethod ที่เดียวกับ Class
    public long addNewUser(String strUser, String strPassword, String strName) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);

    }

}//Main Class
