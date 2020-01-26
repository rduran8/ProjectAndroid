package com.example.projecteandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RegistreAdapter extends ArrayAdapter<Registre> {

        private Context lContext;
        private List<Registre> lineaList = new ArrayList<>();

        public RegistreAdapter(@NonNull Context context, ArrayList<Registre> list) {
            super(context, 0 , list);
            lContext = context;
            lineaList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(lContext).inflate(R.layout.list_item,parent,false);

            Registre currentLinea = lineaList.get(position);

            TextView nom = (TextView) listItem.findViewById(R.id.textView_nom);
            nom.setText(currentLinea.getTitul());
            return listItem;
        }
    }
