package eureka.com.restapidemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    EditText _name;
    EditText _email;
    EditText _password;
    Button _btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(MainActivity.this, "https://blistered-groans.000webhostapp.com/test/select.php",null, new AsyncHttpResponseHandler() {


            @Override
            public void onStart() {
                super.onStart();
                pd = new ProgressDialog(MainActivity.this);
                pd.setTitle("Fetching");
                pd.setMessage("Please wait your file is being download to the server.");
                pd.setIndeterminate(false);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = Utils.getResponse(responseBody);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String res = Utils.getResponse(responseBody);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();

                pd.dismiss();

            }
        });

       //=================================================================================


      /*  _name = (EditText) findViewById(R.id.name);
        _email = (EditText) findViewById(R.id.email);
        _password = (EditText) findViewById(R.id.password);
        _btn = (Button) findViewById(R.id.button);


        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSignup(_name.getText().toString(),_email.getText().toString(),_password.getText().toString());

            }
        });*/




    }


    private void UserSignup(String name, String email, String password) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",name);
        params.put("email",email);
        params.put("password",password);

        client.post(MainActivity.this, "https://blistered-groans.000webhostapp.com/test/insert.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                pd = new ProgressDialog(MainActivity.this);
                pd.setTitle("Fetching");
                pd.setMessage("Please wait your file is being download to the server.");
                pd.setIndeterminate(false);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String res = Utils.getResponse(responseBody);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();

                _name.setText("");
                _email.setText("");
                _password.setText("");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String res = Utils.getResponse(responseBody);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish() {
                super.onFinish();
                pd.dismiss();
            }
        });
    }
}
