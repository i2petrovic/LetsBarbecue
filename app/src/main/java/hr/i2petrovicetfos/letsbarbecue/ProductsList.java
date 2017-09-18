package hr.i2petrovicetfos.letsbarbecue;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ProductsList extends ArrayAdapter<ListItem> {//adapter za baratanje listitem-ima te rad nad tipkom za unos u lokalnu bazu
    private Activity context;
    private List<ListItem> productsList;

    public ProductsList(Activity context, List<ListItem> productsList){
        super(context,R.layout.listview_item,productsList);
        this.context  = context;
        this.productsList = productsList;
    }
    DBAdapter myDB;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listview_item,null, true);

        final TextView tvItem = (TextView) listViewItem.findViewById(R.id.tvItem);
        final TextView tvCijena = (TextView) listViewItem.findViewById(R.id.tvCijena);
        final TextView tvJedinica = (TextView) listViewItem.findViewById(R.id.tvJedinica);
        final EditText etKolicina = (EditText) listViewItem.findViewById(R.id.etKolicina);

        ListItem listItem = productsList.get(position);

        tvItem.setText(listItem.getListItem());
        tvCijena.setText(listItem.getCijena().toString());
        tvJedinica.setText(listItem.getJedinica());

        Button bAdd = (Button) listViewItem.findViewById(R.id.bAdd);
        bAdd.setTag(position);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDB();
                myDB.insertRow(tvItem.getText().toString(),tvCijena.getText().toString(),tvJedinica.getText().toString(),etKolicina.getText().toString());
                Toast.makeText(getContext(), "dodano na listu", Toast.LENGTH_SHORT).show();
                closeDB();
            }
        });

        return listViewItem;
    }

    private void openDB(){
        myDB = new DBAdapter(getContext());
        myDB.open();

    }
    private void closeDB(){
        myDB.close();
    }
}
