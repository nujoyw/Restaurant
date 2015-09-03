package pixzar.nujoyw.myrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

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

        int intTimes = 0;
        while(intTimes <=1) {

            InputStream objInputStream = null; //
            String strJSON = null;  //
            String strUserURL = "http://swiftcodingthai.com/3sep/pixzar/php_get_data_pixzar.php";
            String strFoodURL = "http://swiftcodingthai.com/3sep/php_get_data_food.php";

            HttpPost objHttpPost = null; //HTTP POST เป็น scheba ที่เถึยงกันอยู่ เพราะยังใช้มันอยู่

            //1. กระบวนการ Create InputStream
            //การเขียนโค้ดที่เสี่ยงกับการ error ให้ทำงานต่อ แล้วขึ้น error ใน log catch แทน จะใช้ try catch
            //server ล่ม
            //URL ผิด
            //exception การ error ที่ยอมรับได้
            try {

                HttpClient objHttpClient = new DefaultHttpClient();//
                if (intTimes !=1) {//ครั้งที่ 1 โยนมาเป็น USer ครั้งที่ 2 โยนมาเป็น Food
                    objHttpPost = new HttpPost(strUserURL);//เมื่อไหร่ก็ตามที่มีการคอนเน็ค
                } else {
                    objHttpPost = new HttpPost(strFoodURL);

                }
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();
            }catch (Exception e) {
                Log.d("Rest", "Input ==> "+ e.toString());//ให้โชว์ข้อความของ error อยู่ในรูปแบบของ log catch

            }
            //2. Create strJSON
            //3. Update to SQLite




            intTimes += 1;
        }



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
