package pixzar.nujoyw.myrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;

    private EditText userEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ผูกตัวแปรที่ทำการประกาศ useredittext กับ passwordedit text
        //Bind Widget
        bindWidget();


        //สร้างเมธอดขึ้นมา เพื่อติดต่อฐานข้อมูล Create & Connected Database
        createAndConnectedDatabase();

        //TestInsertDB();

        //Delete All data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();


    }//onCreate

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

    }


    //เพิ่ม code ใต้ เมธอดหลัก
    public void clickLogin(View view) {
        String strUser = userEditText.getText().toString().trim();//edit text ที่ get มาให้เป็น string และตัดช่องว่างให้ด้วย
        String strPassword = passwordEditText.getText().toString().trim();

        //Check zero
        //ค่าของ strUser มันเท่ากับ ค่าว่างเปล่าหรือเปล่า
        if (strUser.equals("") || strPassword.equals("")) {

            //Have Space
            errorDialog("Have Space", "Please Fill All Blank");

        } else {

            //No Space
            try {

                String[] strMyResult = objUserTABLE.searchUser(strUser);
                if (strPassword.equals(strMyResult[2])) {
                    welcomeDialog(strMyResult[3]); // เอาชื่อไปใช้ใน ยินดีต้อนรับ นาย....

                } else {
                    errorDialog("Password ผิด", "กรุณาใส่ password ให้ถูกต้องนะคะ");
                }

            } catch (Exception e) {
                errorDialog("ไม่มี User", "ไม่มี " + strUser + "ในฐานข้อมูลของฉัน");
            }

        }


    }//clickLogin

    private void welcomeDialog(final String strName) {

        final AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.restaurant_icon);
        objBuilder.setTitle("Welcome Officer");
        objBuilder.setMessage("ยินดีต้อนรับ" + strName + "\n" + "สู่ระบบของฉัน");
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //ย้ายการทำงานไปที่ order activity //Intent to OrderActivity
                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);//จาก MainActivity ไปที่ OrderActivity
                objIntent.putExtra("Officer", strName);
                startActivity(objIntent);

                finish();//ปิดหน้า activity
                
                dialogInterface.dismiss();
            }

        });
        objBuilder.show();
    }

    private void errorDialog(String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);//เครื่องจะมี undo บน home ห้าม cancle น๊ะจ๊ะ
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); //ให้ popup หายไป

            }
        });
        objBuilder.show();
    }//errorDialog


    private void synJSONtoSQLite() {

        //Change Policy ถ้าไม่ทำจะโกรธมาก
        //ไม่ให้ android ติดต่อ http ได้โดยตรง
        StrictMode.ThreadPolicy objPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //สร้าง object objpolicy มาเพื่อ bulid policy
        StrictMode.setThreadPolicy(objPolicy);
        //จะทำการต่อเน็ตได้เรียบร้อยค่ะ

        int intTimes = 0;
        while (intTimes <= 1) {

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
                if (intTimes != 1) {//ครั้งที่ 1 โยนมาเป็น USer ครั้งที่ 2 โยนมาเป็น Food
                    objHttpPost = new HttpPost(strUserURL);//เมื่อไหร่ก็ตามที่มีการคอนเน็ค
                } else {
                    objHttpPost = new HttpPost(strFoodURL);

                }
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();
            } catch (Exception e) {
                Log.d("Rest", "Input ==> " + e.toString());//ให้โชว์ข้อความของ error อยู่ในรูปแบบของ log catch

            }


            //2. Create strJSON  เปลี่ยนตัวไหมพรมให้เป็น String 1 ตัว
            try {
                //bufferedReader คือ เอากองเชือก 10 kg ยกคนเดียวไม่ได้ ต้องตัดเป็น 1 เมตร แล้วค่อยๆ โยนตรงนู้น
                //ดึง data มาเท่าไหน จึงเริ่มตัด เสมือน ดู youtube
                // การดึง JSON ถ้าอยู่เมืองนอก ไม่ต้องใช้ UTF-8 แต่ไทยต้องใช้

                BufferedReader objBufferedReader = new BufferedReader(new BufferedReader(new InputStreamReader(objInputStream, "UTF-8")));//
                //ดึงตัดเสร็จ ต้องมีคนเก่งๆ คนนึงมาต่อเชือกมาให้ ให้ยาวเป็นเหมือนเดิม
                StringBuilder objStringBuilder = new StringBuilder();
                //strLine จะหิ้วเชือกทีละเส้น ไปให้ stringBuilder ต่อให้
                String strLine = null;
                //ทำการ carry ก่อน
                while ((strLine = objBufferedReader.readLine()) != null) {//ถ้ามันไม่เท่ากับความว่างเปล่า คือเชือกยังไม่หมด ถ้า null เชือกหมดแล้ว
                    objStringBuilder.append(strLine);//ให้ผูกเชือกไปเรื่อยๆ
                }//While
                //เชือกหมด
                objInputStream.close();//ไม่ต้องโหลดล่ะ ปิดเลย
                strJSON = objStringBuilder.toString();//เทคทั้งหมดให้เป็น string เส้นเดียว

            } catch (Exception e) {
                Log.d("Rest", "JSON ==>" + e.toString());

            }
            //3. Update to SQLite
            try {

                final JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject jsonObject = objJsonArray.getJSONObject(i);
                    if (intTimes != 1) {
                        String strUser = jsonObject.getString("User");
                        String strPassword = jsonObject.getString("Password");
                        String strName = jsonObject.getString("Name");
                        objUserTABLE.addNewUser(strUser, strPassword, strName);


                    } else {
                        String strFood = jsonObject.getString("Food");
                        String strSource = jsonObject.getString("Source");
                        String strPrice = jsonObject.getString("Price");
                        objFoodTABLE.addNewFood(strFood, strSource, strPrice);

                    }
                }//for
            } catch (Exception e) {
                Log.d("Rest", "Update ==>" + e.toString());
            }


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
