package com.example.chaithra.scribd;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "65197d49b3932d6c1f930ec1f72c5aaa";

    private RecyclerView recyclerView;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actv =  (AutoCompleteTextView)findViewById(R.id.etCityName);
        setautotextcitylist();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);
        findViewById(R.id.btnWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityname = actv.getText().toString();
                if(cityname == null || cityname.isEmpty()){
                    Toast.makeText(MainActivity.this,"City Name is invalid",Toast.LENGTH_SHORT).show();
                }else{
                    findViewById(R.id.pbWeather).setVisibility(View.VISIBLE);
                    getCurrentData(actv.getText().toString());
                }
            }
        });
    }

    private void setautotextcitylist() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,citynames);
        //Getting the instance of AutoCompleteTextView

        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);
        actv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                findViewById(R.id.pbWeather).setVisibility(View.VISIBLE);
                getCurrentData(citynames[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    String[] citynames ={
            "München,DE",
            "Bangalore,IN",
            "Santa Clara, US",
            "Mountainview, US",
            "Palo Alto, US",
            "San Jose, US"
    };

    void getCurrentData(String cityname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPi service = retrofit.create(WeatherAPi.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(cityname, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    findViewById(R.id.pbWeather).setVisibility(View.GONE);
                    WeatherResponse weatherResponse = response.body();
                    weatherAdapter mAdapter = new weatherAdapter(weatherResponse.getList());
                    recyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
            }
        });
    }

}
