package pixzar.nujoyw.myrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //สร้างเมธอดขึ้นมา เพื่อติดต่อฐานข้อมูล Create & Connected Database
        createAndConnectedDatabase();

        //TestInsertDB();

        //Delete All data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }//onCreate

    private void synJSONtoSQLite() {

        //Change Policy ถ้าไม่ทำจะโกรธมาก
        //ไม่ให้ android ติดต่อ http ได้โดยตรง
        StrictMode.ThreadPolicy objPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //สร้าง object objpolicy มาเพื่อ bulid policy
        StrictMode.setThreadPolicy(objPolicy);
        //จะทำการต่อเน็ตได้เรียบร้อยค่ะ

        



    }//synJSONtoSQLite

    private void deleteAllData() {
        SQLiteDatabase objSQLiteDatabase = openOrCreateDatabase("Restaurant.db", MODE_PRIVATE, null);
        objSQLiteDatabase.delete("userTABLE", null, null);
        objSQLiteDatabase.delete("foodTABLE", null, null);
    }


    private void TestInsertDB() {

        objUserTABLE.addNewUser("test", "1234", "pixzar");
        objFoodTABLE.addNewFood("testFood", "testSource", "testPrice");
    }


    //Auto หลังกด Alt enter

    private void createAndConnectedDatabase() {
        objUserTABLE = new UserTABLE(this);//เรียกใช้เมธอดที่เป็น constructor คือ This นั่นเอง
        objFoodTABLE = new FoodTABLE(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}//Main Class
