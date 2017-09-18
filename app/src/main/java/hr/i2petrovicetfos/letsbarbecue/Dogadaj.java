package hr.i2petrovicetfos.letsbarbecue;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Dogadaj extends AppCompatActivity implements OnMapReadyCallback {

    int broj= 0;
    LatLng lokacija;
    DatabaseReference myRef;
    String infoID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogadaj);

        MapFragment mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fGoogleMap2);
        mMapFragment.getMapAsync(this);
    }

    public void bDolazim(View view){//metoda za button "Dolazim" koja broji koliko je button puta pritusnut
        broj=  broj+1;
        TextView tvBrojDolazaka = (TextView) findViewById(R.id.tvBrojDolazaka);
        tvBrojDolazaka.setText("Zainteresirani za dolazak: " + broj);;
    }

    public void bPrognoza(View view){//metoda koja pokreće novi aktivity za prikaz vremenske prognoze na osnovu JSON-a

        Intent j = new Intent(Dogadaj.this,Vrijeme.class);
        startActivity(j);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Intent i = getIntent();
        infoID = i.getStringExtra("informacije");
        myRef = FirebaseDatabase.getInstance().getReference("Info");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Info info = dataSnapshot.child(infoID).getValue(Info.class);

                TextView tvDialogImeDogadaja = (TextView) findViewById(R.id.tvDialogImeDogadaja);
                if (info != null) {
                    tvDialogImeDogadaja.setText(info.getImeDogađaja());
                } else return;

                TextView tvDialogDatum = (TextView) findViewById(R.id.tvDialogDatum);
                tvDialogDatum.setText("Datum: " + info.getDatum());

                TextView tvDialogVrijeme = (TextView) findViewById(R.id.tvDialogVrijeme);
                tvDialogVrijeme.setText("Vrijeme: " + info.getVrijeme());

                TextView tvDialogPoruka = (TextView) findViewById(R.id.tvDialogPoruka);
                tvDialogPoruka.setText("Poruka: " + info.getPoruka());

                Double lat = info.getLatitude();
                Double lng = info.getLongitude();

                lokacija = new LatLng(lat, lng);
                MarkerOptions newMarkerOptions = new MarkerOptions();
                newMarkerOptions.position(lokacija);
                googleMap.addMarker(newMarkerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokacija, 5));

                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setZoomGesturesEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Pogreška u učitavanju", Toast.LENGTH_LONG).show();
            }
        });

    }
}
