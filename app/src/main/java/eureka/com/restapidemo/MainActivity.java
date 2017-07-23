package eureka.com.restapidemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    EditText _name;
    EditText _email;
    EditText _password;
    Button _btn;

    ImageView _img;
    private String imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _img = (ImageView) findViewById(R.id.imageView2);


      /*  AsyncHttpClient client = new AsyncHttpClient();
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
        });*/

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


      //Parsing Json Data

        String json = loadJSONFromAsset();

        try {
            JSONObject object = new JSONObject(json);
            JSONArray data = object.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject ob = data.getJSONObject(i);

                JSONArray xyz = ob.getJSONArray("actions");

                String msg = ob.getString("message");

                JSONObject from = data.getJSONObject(i).getJSONObject("from");
                String name = from.getString("name");


                Toast.makeText(this, ""+msg+ " " +name, Toast.LENGTH_SHORT).show();
            }



            //Log.d("json",object.toString());
            Log.d("data",data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ImagePicker.Builder(MainActivity.this)
                        .mode(ImagePicker.Mode.GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);

             imagepath = mPaths.get(0).trim();
            Toast.makeText(this, ""+imagepath, Toast.LENGTH_SHORT).show();

            //Your Code
        }
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


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("facebook.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
