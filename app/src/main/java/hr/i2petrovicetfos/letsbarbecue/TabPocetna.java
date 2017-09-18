package hr.i2petrovicetfos.letsbarbecue;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TabPocetna extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    private GoogleMap.OnMapClickListener myListener;
    LatLng lokacija;
    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_pocetna,container,false);

        myRef = FirebaseDatabase.getInstance().getReference("Info");//referenca na tražene podatke u bazi

        final EditText etImeDogadaja = (EditText) view.findViewById(R.id.etImeDogagaja);
        final EditText etDatum = (EditText) view.findViewById(R.id.etDatum);
        final EditText etVrijeme = (EditText) view.findViewById(R.id.etVrijeme);
        final EditText etPoruka = (EditText) view.findViewById(R.id.etPoruka);

        SupportMapFragment fra = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fGoogleMap);
        fra.getMapAsync(this);

        this.myListener = new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                MarkerOptions newMarkerOptions = new MarkerOptions();

                newMarkerOptions.position(latLng);
                mGoogleMap.clear();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mGoogleMap.addMarker(newMarkerOptions);

                lokacija = latLng;
            }
        };

        Button bUnosInformacija = (Button) view.findViewById(R.id.bUnosInformacija);
        bUnosInformacija.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (etImeDogadaja.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "molimo unesite ime dogadaja", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etDatum.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "molimo unesite datum", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etVrijeme.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "molimo unesite vrijeme", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(lokacija == null )
                {
                    Toast.makeText(getContext(), "molimo odaberite lokaciju", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    {
                        String ImeDogađaja = etImeDogadaja.getText().toString().trim();
                        String Datum = etDatum.getText().toString().trim();
                        String Vrijeme = etVrijeme.getText().toString().trim();
                        String Poruka = etPoruka.getText().toString().trim();
                        Double lat = lokacija.latitude;
                        Double lng = lokacija.longitude;


                        String id = myRef.push().getKey();//stvaranje jedinstvenog id ključa za spremanje u bazu
                        Info novaInfo = new Info(id,ImeDogađaja,Datum,Vrijeme,Poruka,lat,lng);//objekt koji se sprema u bazu

                        myRef.child(id).setValue(novaInfo);

                        Toast.makeText(getContext(), "info added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {//metoda omogućava rad s mapom kad je spremna za korištenje

        this.mGoogleMap = googleMap;
        UiSettings uiSettings = this.mGoogleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        this.mGoogleMap.setOnMapClickListener(this.myListener);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.mGoogleMap.setMyLocationEnabled(true);
    }
}
