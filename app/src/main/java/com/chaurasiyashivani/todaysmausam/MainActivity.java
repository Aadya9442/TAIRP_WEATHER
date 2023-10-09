package com.chaurasiyashivani.todaysmausam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    EditText et;
    TextView tv2, tvhigh, tvhumidity, tvlow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        tv2 = findViewById(R.id.texttemp);
        tvhigh = findViewById(R.id.tvhigh);
        tvhumidity = findViewById(R.id.tvhumidity);
        tvlow = findViewById(R.id.tvpressure);

    }


    public void getResult(View view) {
        String api_key = "4c6123d025c84570e7009fd6f49994f8";
        String city = et.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + api_key;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    double temperatureKelvin = main.getDouble("temp");
                    double temperatureCelsius = temperatureKelvin - 273.15;
                    String temperature = String.format("%.2f°C", temperatureCelsius);

                    tv2.setText(temperature);

                    double highKelvin = main.getDouble("temp_max");
                    double highCelsius = highKelvin - 273.15;
                    String high = String.format("High: %.2f°C", highCelsius);
                    tvhigh.setText(high);

                    String pressureKelvin = main.getString("feels_like");
                    double pressureCelsius = Double.parseDouble(pressureKelvin) - 271.15;
                    String pressure = String.format("FeelsLike: %.2f°C", pressureCelsius);
                    tvlow.setText(pressure);

                    String humidity = main.getString("humidity");
                    tvhumidity.setText("Humidity: " + humidity + "%");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}