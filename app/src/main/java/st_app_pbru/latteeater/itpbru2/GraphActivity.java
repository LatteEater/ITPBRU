package st_app_pbru.latteeater.itpbru2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class GraphActivity extends AppCompatActivity {

    //Explicit
    private String urlJSON = "http://www.swiftcodingthai.com/pbru2/get_account_where.php";
    private String[] loginStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd","true").add("id_user",loginStrings[0]).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlJSON).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("8June","JSON =>"+response.body());
            }
        });


    }//n Main Method



} //n MainClass
