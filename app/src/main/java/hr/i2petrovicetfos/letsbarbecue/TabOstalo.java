package hr.i2petrovicetfos.letsbarbecue;


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


public class TabOstalo extends Fragment {

    DatabaseReference myRef;
    List<ListItem> products;
    ListView lvOstalo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_ostalo,container,false);

        lvOstalo = (ListView) view.findViewById(R.id.lvOstalo);

        myRef = FirebaseDatabase.getInstance().getReference("Products").child("Ostalo");
        products = new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                products.clear();
                for (DataSnapshot productsSnap : dataSnapshot.getChildren()){
                    ListItem listItem = productsSnap.getValue(ListItem.class);

                    products.add(listItem);
                }

                ProductsList adapter = new ProductsList(getActivity(),products);
                lvOstalo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Pogreška u učitavanju", Toast.LENGTH_LONG).show();
            }
        });
    }
}
