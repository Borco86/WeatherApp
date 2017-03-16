package com.example.rent.weatherapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.weather_icon)
    ImageView weatherIcon;
    @BindView(R.id.sky_text)
    TextView skyText;
    @BindView(R.id.city_name_edit_text)
    TextInputEditText cityNameEditText;

    private Retrofit retrofit;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://weathers.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        search("Warszawa");

    }

    private void search(String searchQuery) {
        WeatherService weatherService = retrofit.create(WeatherService.class);
        weatherService.getWeather(searchQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataContainer -> {
                    WeatherDetail weatherDetail = dataContainer.getData();
                    location.setText(weatherDetail.getLocation());
                    temperature.setText(weatherDetail.getTemerature() + "\u00b0 C");
                    day.setText(weatherDetail.getDay());

                    showImageBySkyText(weatherDetail.getSkyText());

                    if (progressDialog != null) {
                        progressDialog.hide();
                        showNotofication(searchQuery);
                    }

                });
    }

    private void showNotofication(String searchQuery){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.sunny_day);

        Notification notification=new NotificationCompat.Builder(this)
                //obowiązkowo ikona i tekst
                .setContentText("Załadowano informacje pogodowe dla miasta"+searchQuery)
                .setSmallIcon(R.drawable.sunny_day)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(searchQuery.hashCode(),notification);


        SimpleModel regularSimpleModel = new SimpleModel("xxx","ccc","vvv","bbb");

        // korzystanie z wzorca Builder
        SimpleModel simpleModel=new SimpleModel.Builder()
                .withName("Tomasz")
                .withSurname("Borejko")
                .withAddress("Pruszków")
                .withPhone("696-666-999")
                .build();
    }

    @OnClick(R.id.search_button)
    void onSearch() {
        progressDialog = ProgressDialog.show(this, "ładowanko", "ładuję", true);
        search(cityNameEditText.getText().toString());
    }


    private void showImageBySkyText(String skyText) {
        if ("Sky is clear".equalsIgnoreCase(skyText)) {
            weatherIcon.setImageResource(R.drawable.sunny_day);
        } else if ("Few clouds".equalsIgnoreCase(skyText)) {
            weatherIcon.setImageResource(R.drawable.stormy_day);
        } else {
            weatherIcon.setImageResource(R.drawable.rainy_day);
        }
    }
}
