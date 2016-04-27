package poompunk.wannachat.kru.pjrestaurant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrderActivity extends AppCompatActivity {

    //ex
    private TextView nameTextView;
    private ListView listView;
    private String[] userString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //bind wid
        nameTextView = (TextView) findViewById(R.id.textView6);
        listView = (ListView) findViewById(R.id.listView);
        userString = getIntent().getStringArrayExtra("Result");
        nameTextView.setText(" Welcome "+ userString[3]);

        //create
        ConnectJSON connectJSON = new ConnectJSON();
        connectJSON.execute();

    }//main method

    //Create inner class
    public class ConnectJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/pj/php_get_food_cupcake.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }//doinback

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                String[] foodStrings = new String[jsonArray.length()];
                String[] priceStrings = new String[jsonArray.length()];
                String[] iconStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    foodStrings[i] = jsonObject.getString("FoodSet");
                    priceStrings[i] = jsonObject.getString("Price");
                    iconStrings[i] = jsonObject.getString("URLicon");

                }//for

                OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this,
                        foodStrings, priceStrings, iconStrings);
                listView.setAdapter(orderAdapter);

            }catch (Exception e) {
                e.printStackTrace();
            }


        }//onpost
    }//ConJSON

    public void clickReadorder(View view) {

    }
}//main class
