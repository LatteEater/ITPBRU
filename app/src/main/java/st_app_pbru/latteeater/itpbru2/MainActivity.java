package st_app_pbru.latteeater.itpbru2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private MyManager myManager;
    private static final String urlJSON = "http://swiftcodingthai.com/pbru2/get_user_master.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myManager = new MyManager(this);

        //Test Add New User
        //myManager.addNewUser("123","name","sur","User","Pass");

        //Delete ALL SQLite
        deleteAllSQLite();
        mySynJSON();

    } // nMain Method

    private void mySynJSON() {

        ConnectedUserTABLE connectedUserTABLE = new ConnectedUserTABLE(this);
        connectedUserTABLE.execute();

    }//nSynJSON

    //Create Inner Class
    private class ConnectedUserTABLE extends AsyncTask<Void, Void, String> {

        private Context context;
        private ProgressDialog progressDialog;

        public ConnectedUserTABLE(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressDialog = ProgressDialog.show(context, "Synchronize Server", "Please Wait...  Process Synchronize");
        }//nOnPre

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("7June","error DoIn==> "+e.toString()); //log Debugging
            }

            return null;
        }//nDoInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                progressDialog.dismiss();
                Log.d("7June", "JSON==>"+s);
            } catch (Exception e) {
            e.printStackTrace();
            }

        }//nOnPostExecute
    } //nConnected Class

    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManager.user_table, null, null);

    }//nDeleteALLSQLite

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }
} //nMAIN Class
