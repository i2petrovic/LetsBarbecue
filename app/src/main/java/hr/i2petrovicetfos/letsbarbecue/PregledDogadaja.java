package hr.i2petrovicetfos.letsbarbecue;



import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class PregledDogadaja extends AppCompatActivity {

    ListView lvPopisDogadaja;

    DatabaseReference myRef;
    FirebaseListAdapter<Info> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregled_dogadaja);

        lvPopisDogadaja = (ListView) findViewById(R.id.lvPopisDogadaja);
        myRef = FirebaseDatabase.getInstance().getReference("Info");

        //adapter za jednostavno popunjavanje liste iz Firebase-a
        mAdapter = new FirebaseListAdapter<Info>(PregledDogadaja.this, Info.class, R.layout.dogadaji_list_item, myRef) {
            @Override
            protected void populateView(View v, Info info, int position) {
                ((TextView) v.findViewById(R.id.tvNaslovDogadaja)).setText(info.getImeDogađaja());
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "pogreška u učitavanju", Toast.LENGTH_LONG).show();
            }
        });

        lvPopisDogadaja.setAdapter(mAdapter);

        lvPopisDogadaja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Info info = (Info) lvPopisDogadaja.getItemAtPosition(position);//spremanje dohvaćenih podataka u novi objekt
                String infoID = info.getInfoID();//dohvaćanje jedinstvenog id-a za prijenos u idući activity
                Intent i = new Intent(PregledDogadaja.this,Dogadaj.class);
                i.putExtra("informacije",infoID);
                startActivity(i);
            }
        });

        lvPopisDogadaja.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //dugi click otvara standardni dialog preko kojeg se omogućava brisanje događaja sa liste a tako i iz baze podataka
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PregledDogadaja.this);
                dialogBuilder.setTitle("Oprez!");
                dialogBuilder.setMessage("Želite obrisati događaj?");
                dialogBuilder.setPositiveButton("Ne", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    } });

                dialogBuilder.setNegativeButton("Da", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Info info = (Info) lvPopisDogadaja.getItemAtPosition(position);
                        String infoID = info.getInfoID();
                        DatabaseReference deleteRef = FirebaseDatabase.getInstance().getReference("Info").child(infoID);
                        deleteRef.removeValue();//uklanjanje podataka iz baze na dohvaćenom id-u odnosno pritisnutom objektu iz liste
                    } });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });
    }
}
