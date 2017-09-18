package hr.i2petrovicetfos.letsbarbecue;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import hr.i2petrovicetfos.letsbarbecue.data.Channel;
import hr.i2petrovicetfos.letsbarbecue.data.Item;
import hr.i2petrovicetfos.letsbarbecue.service.WeatherServiceCallback;
import hr.i2petrovicetfos.letsbarbecue.service.YahooWeatherService;

public class Vrijeme extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView ivIkona;
    private TextView tvTemperatura, tvCondition, tvLokacija;

    private YahooWeatherService service;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrijeme);

        ivIkona = (ImageView) findViewById(R.id.ivIkona);
        tvTemperatura = (TextView) findViewById(R.id.tvTemperatura);
        tvCondition = (TextView) findViewById(R.id.tvCondition);
        tvLokacija = (TextView) findViewById(R.id.tvLokacija);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Osijek, hr");
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/s"+ item.getCondition().getCode(),null,getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        ivIkona.setImageDrawable(weatherIconDrawable);

        tvTemperatura.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        tvCondition.setText(item.getCondition().getDescription());
        tvLokacija.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exeption) {
        dialog.hide();
        Toast.makeText(this,exeption.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}


