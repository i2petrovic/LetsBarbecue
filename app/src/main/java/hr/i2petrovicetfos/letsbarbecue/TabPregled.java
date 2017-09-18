package hr.i2petrovicetfos.letsbarbecue;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;




public class TabPregled extends Fragment {

    ListView lvPregled;
    DBAdapter myDB;
    TextView tvUkupno;
    Button bStaviNaListu, bObrisiListu, bObjaviDogadaj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_pregled,container,false);
        lvPregled = (ListView) view.findViewById(R.id.lvPregled);

        popuniListu();

        tvUkupno = (TextView) view.findViewById(R.id.tvUkupno);
        bStaviNaListu = (Button) view.findViewById(R.id.bPrikaziListu);
        bObrisiListu = (Button) view.findViewById(R.id.bObrisi);
        bObjaviDogadaj = (Button) view.findViewById(R.id.bObjaviDogadaj);
        bStaviNaListu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuniListu();
                izracun();
//omogucava se korisniku osvjezavanje i prikazivanje napravljene liste te izračun cjelokupnog troška željenog

            }
        });

        //svaki od nadolazećih buttona izvršava određenu radnnju u samooj bazi podataka te popunjava
        // ponovno listview i vrsi ponovni izračun ukupnog "troška"
        bObrisiListu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteAll();
                popuniListu();
                izracun();
            }
        });
        lvPregled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myDB.deleteRow(id);
                popuniListu();
                izracun();
            }
        });

        bObjaviDogadaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),PregledDogadaja.class);

                startActivity(i);

            }
        });

        return view;

    }

    private void izracun(){

        float rezultat = myDB.getCalculated();
        tvUkupno.setText("Ukupno: " + Float.toString(rezultat) + " kn");
    }


    public void popuniListu() {


        openDB();

        Cursor cursor = myDB.getAllRows();
        String[] pokupiOd = new String[]{DBAdapter.VRSTA_ITEMA, DBAdapter.CIJENA_ITEMA, DBAdapter.JEDINICA, DBAdapter.KOLICINA};
        int[] spremiU = new int[]{R.id.tvVrstaItema, R.id.tvCijenaItema, R.id.tvJedinicaItema, R.id.tvOdabranaKolicina};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.database_list_view_item, cursor, pokupiOd, spremiU, 0);

        lvPregled.setAdapter(myCursorAdapter);

    }

    private void openDB(){
        myDB = new DBAdapter(getContext());
        myDB.open();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB(){
        myDB.close();
    }

}
