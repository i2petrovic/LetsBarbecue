package hr.i2petrovicetfos.letsbarbecue;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TabHrana extends Fragment {

    DatabaseReference myRef;
    List<ListItem> products;
    ListView lvHrana;
    private ProgressDialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_hrana,container,false);

        lvHrana = (ListView) view.findViewById(R.id.lvHrana);
//Tab Hrana, Piće i Ostalo sastoje se od ListView-a u kojem popunjavamo listu objektima pozivajući se na oblik
// definira u klasi ListItem.

        myRef = FirebaseDatabase.getInstance().getReference("Products").child("Hrana"); //referenca na podatke iz baze
        products = new ArrayList<>();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {//event listener koji popunjava listu
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                products.clear();
                for (DataSnapshot productsSnap : dataSnapshot.getChildren()){//for petlja za dohvaćanje svih childova jedne tablice
                    ListItem listItem = productsSnap.getValue(ListItem.class);

                    products.add(listItem);
                }

                ProductsList adapter = new ProductsList(getActivity(),products);
                lvHrana.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Pogreška u učitavanju", Toast.LENGTH_LONG).show();
            }
        });
    }

}
