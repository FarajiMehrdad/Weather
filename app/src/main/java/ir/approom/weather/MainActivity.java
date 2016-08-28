package ir.approom.weather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        FetchWeatherData fetchData = new FetchWeatherData();
        fetchData.execute();

    }



    private class FetchWeatherData extends AsyncTask<String,Integer , String>{

        @Override
        protected String doInBackground(String[] objects) {

            return fetchData();

        }


        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate(values);
        }

        String fetchData(){

            try {

                String url = "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=b8ed861af728e441d41f2cf4c1c1c2fa";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();

                Response response = client.newCall(request).execute();
                String jsonData = response.body().string();
                Log.d(LOG_TAG , "the response is " + jsonData);
                return parseJson(jsonData);

            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        String parseJson(String json){

            String tempText = "";

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject mainObject = jsonObject.getJSONObject("main");
                Double temp = mainObject.getDouble("temp");
                Double pressure = mainObject.getDouble("pressure");
                Double humidity = mainObject.getDouble("humidity");

                Log.d(LOG_TAG , "the temp is " + temp );
                tempText = "temp is " + temp;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return tempText;
        }


        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            textView.setText(data);
        }
    }

}
