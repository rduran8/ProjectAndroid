package com.example.projecteandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Registre> items;
    private RegistreAdapter itemsAdapter;
    //private ArrayAdapter<Linea> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<Registre>();
        readItems();
        itemsAdapter = new RegistreAdapter(this,items);
        //itemsAdapter = new ArrayAdapter<Linea>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(new Registre(itemText));
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Registre registre = items.get(position);
                Intent intent = new Intent(getApplicationContext(),Mapa.class);
                intent.putExtra("LATITUDE",registre.getLatLng().latitude);
                intent.putExtra("LONGITUDE",registre.getLatLng().longitude);
                intent.putExtra("POSITION",position);
                intent.putExtra("TITUL",registre.getTitul());
                intent.setType("text/plain");
                startActivityForResult(intent,2);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            double latitude = data.getDoubleExtra("LATITUDE",0);
            double longitude = data.getDoubleExtra("LONGITUDE",0);
            int pos = data.getIntExtra("POSITION",0);
            try{
                items.set(pos,new Registre(new LatLng(latitude,longitude),items.get(pos).getTitul(),items.get(pos).getDescripcio(),items.get(pos).getData()));
            }catch(Error e){
                items.set(pos,new Registre(items.get(pos).getTitul()));
            }
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            ArrayList<String> items1 = new ArrayList<String>(FileUtils.readLines(todoFile));
            items = new ArrayList<Registre>();
            for (String linea : items1) {
                String[] separat = linea.split("\\|");
                items.add(new Registre(new LatLng(Double.valueOf(separat[0]),Double.valueOf(separat[1])),separat[2],separat[3],new Date(separat[4])));
            }
        } catch (IOException e) {
            items = new ArrayList<Registre>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
