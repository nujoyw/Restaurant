package pixzar.nujoyw.myrestaurant;

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

        TestInsertDB();
    }//onCreate

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
