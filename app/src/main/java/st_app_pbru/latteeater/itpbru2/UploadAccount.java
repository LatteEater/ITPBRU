package st_app_pbru.latteeater.itpbru2;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class UploadAccount extends AppCompatActivity {
    //Explicit
    private TextView inoutTextView;
    private TextView nameTextView;
    private Spinner spinner;
    private EditText editText;
    private String[] loginStrings;
    private int inOutAnInt;
    private String[] inOutStrings = new String[]{"List Income", "List Outcome"};
    private String[] inComeStrings = new String[]{"Salary", "OT", "ServerARK"};
    private String[] outComeStrings = new String[]{"Food", "KCAH", "GAME", "Learning"};
    private ArrayAdapter<String> stringArrayAdapter;  //ArrayAdapter
    private String categoryString, amountString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_account);

        //Bind widget
        inoutTextView = (TextView) findViewById(R.id.textView6);
        nameTextView = (TextView) findViewById(R.id.textView7);
        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText7);

        //Receive value form Intent
        loginStrings = getIntent().getStringArrayExtra("Login");
        inOutAnInt = getIntent().getIntExtra("InOut", 0);

        //Show View
        inoutTextView.setText(inOutStrings[inOutAnInt]);
        nameTextView.setText(loginStrings[1]+" "+loginStrings[2]);

        //Create Spinner
        createSpinner();

    }//n Main Method

    private void createSpinner() {

        switch (inOutAnInt) {

            case 0:
                stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inComeStrings);
                break;
            case 1:
                stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, outComeStrings);
                break;

        }// nSwitch

        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                categoryString = findCategory(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                categoryString = findCategory(0);

            }
        });

    } // nCreateSpinner

    private String findCategory(int i) {
        String strResuit = null;

        switch (inOutAnInt) {
            case 0:
                strResuit = inComeStrings[i];
                break;
            case 1:
                strResuit = outComeStrings[i];
                break;
        }

        return strResuit;
    }

    public void clickCancel(View view) {
        finish();
    }//n ClickCancel

    public void clickUpload(View view) {

        amountString = editText.getText().toString().trim();

        if (amountString.equals("")) {
            MyALERT myALERT = new MyALERT();
            myALERT.myDalog(this, "Null Amount", "Please Input cash");

        } else {
            uploadValueToServer();
        }

    }//n ClickUpload

    private void uploadValueToServer() {

        Log.d("8June", "ID User =>"+loginStrings[0]);
        Log.d("8June", "Date =>"+getIntent().getStringExtra("Date"));
        Log.d("8June", "In_Out =>"+inOutAnInt);
        Log.d("8June", "Category =>"+categoryString);
        Log.d("8June", "Amount =>" + amountString);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder().add("isAdd", "true").add("id_user", loginStrings[0])
                .add("Date", getIntent().getStringExtra("Date")).add("In_Out", Integer.toString(inOutAnInt))
                .add("Category", categoryString).add("Amount", amountString).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://www.swiftcodingthai.com/pbru2/add_account_master.php")
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
            }
        });

    }// n UploadValueToServer

}//n MainClass
