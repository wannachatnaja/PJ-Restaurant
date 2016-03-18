package poompunk.wannachat.kru.pjrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //ex
    private MyManage myManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request SQLite
        myManage = new MyManage(this);

        //test value
        //testAddValue();

        // deleteSQLite
        deleteSQLite();


    }//main method

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
