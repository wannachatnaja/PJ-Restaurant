package poompunk.wannachat.kru.pjrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    //Ex
    private EditText userEditText, passwordEditText, nameEditText,
            addressEditText, telEditText;
    private String userString, passwordString, nameString,
            addressString, telString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //bind widget
        bindwidget();

    }//mian method

    private void bindwidget() {
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        nameEditText = (EditText) findViewById(R.id.editText5);
        addressEditText = (EditText) findViewById(R.id.editText6);
        telEditText = (EditText) findViewById(R.id.editText7);

    }


    public void clicksSignUpSign(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();
        nameString = nameEditText.getText().toString().trim();
        addressString = addressEditText.getText().toString().trim();
        telString = telEditText.getText().toString().trim();
        //check space
        if (checkSpace()) {
            //have space
        } else {
            //no space
        }


    }//clicksign

    private boolean checkSpace() {

        boolean bolResult = true;

        bolResult = userString.equals("") ||
                passwordString.equals("") ||
                nameString.equals("") ||
                addressString.equals("") ||
                telString.equals("");

        return bolResult;
    }
}//main class
