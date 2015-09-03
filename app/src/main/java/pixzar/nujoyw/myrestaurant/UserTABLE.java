package pixzar.nujoyw.myrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by SCIENCE on 9/3/2015.
 */
public class UserTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }//Constructor เป็นMethod ที่เดียวกับ Class
}//Main Class
