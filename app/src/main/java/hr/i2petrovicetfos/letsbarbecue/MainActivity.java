package hr.i2petrovicetfos.letsbarbecue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bKreirajDogadaj(View view){
        Intent i = new Intent(this,TabActivity.class);
        startActivity(i);
    }

    public void bPregledDogadaja(View view) {
        Intent j = new Intent(this, PregledDogadaja.class);
        startActivity(j);
    }
}
