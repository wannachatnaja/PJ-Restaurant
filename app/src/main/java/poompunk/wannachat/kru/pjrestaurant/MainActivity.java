package poompunk.wannachat.kru.pjrestaurant;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    //ex
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String  userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widget
        bindWidget();

        //Request SQLite
        myManage = new MyManage(this);

        //test value
        //testAddValue();

        // deleteSQLite
        deleteSQLite();

        //synchronize Json to sqlite
        synJSONtoSQLite();


    }//main method

    public void clickSignupmain(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }


    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

    }

    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check space
        if (userString.equals("") || passwordString.equals("")) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(MainActivity.this, "กรุณากรอกให้ครบ ทุกช่อง");

        } else {
            checkUser();
        }

    }

    private void checkUser() {

        try {

            String[] resultStrings = myManage.searchUser(userString);
            if (passwordString.equals(resultStrings[2])) {
                //passture
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);
                finish();
            } else {
                //passFalse
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(MainActivity.this, "Password ผิด");
            }

        } catch (Exception e) {
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(MainActivity.this, "ไม่มี " + userString + " ในฐาน");
        }

    } //checkUser

    private void synJSONtoSQLite() {

        //connected http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        int intTimes = 0;
        while (intTimes <=1) {
            //1 create inputstream
            InputStream inputStream = null;
            String[] urlStrings = {"http://swiftcodingthai.com/pj/php_get_user_cupcake.php",
                    "http://swiftcodingthai.com/pj/php_get_food_cupcake.php"};

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlStrings[intTimes]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            }catch (Exception e) {
                Log.d("pj", "InputStream ==> " + e.toString());
            }

            //2 create json
            String strJSON = null;
            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine= bufferedReader.readLine()) !=null) {
                    stringBuilder.append(strLine);
                }
                inputStream.close();
                strJSON = stringBuilder.toString();
            }catch (Exception e) {
                Log.d("pj", "JSON String ==> " + e.toString());
            }


            //3 update to sqlite
            try {

                JSONArray jsonArray = new JSONArray(strJSON);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    switch (intTimes) {
                        case 0:

                            String strUser = jsonObject.getString(myManage.column_User);
                            String strPassword = jsonObject.getString(myManage.column_Password);
                            String strName = jsonObject.getString(myManage.column_Name);
                            String strAddress = jsonObject.getString(myManage.column_Address);
                            String strPhone = jsonObject.getString(myManage.column_Phone);

                            myManage.addUser(strUser, strPassword, strName, strAddress, strPhone);

                            break;
                        case 1:

                            String strFoodSet= jsonObject.getString(myManage.column_FoodSet);
                            String strDescrip= jsonObject.getString(myManage.column_Description);
                            String strPrice= jsonObject.getString(myManage.column_Price);
                            String strURLicon= jsonObject.getString(myManage.column_URLicon);
                            String strURLimage= jsonObject.getString(myManage.column_URLimage);

                            myManage.addFood(strFoodSet, strDescrip, strPrice, strURLicon, strURLimage);

                            break;
                    }
                }

            }catch (Exception e) {
                Log.d("pj", "Update SQLite ==> " + e.toString());
            }


            intTimes += 1;
        }//while

    }//synJson

    private void deleteSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(myManage.User_table, null, null);
        sqLiteDatabase.delete(myManage.Food_table, null, null);
        sqLiteDatabase.delete(myManage.Order_table, null, null);
    }

    private void testAddValue() {

        myManage.addUser("user", "pass", "name", "address","phone");
        myManage.addFood("food", "descrip", "price", "icon", "image");
        myManage.addOrder("date", "name", "address", "phone", "foodset", "amount", "total");


    }
}//mian class
