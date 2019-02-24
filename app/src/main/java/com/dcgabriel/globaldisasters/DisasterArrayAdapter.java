package com.dcgabriel.globaldisasters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DisasterArrayAdapter extends ArrayAdapter<Disaster> {
    public DisasterArrayAdapter(Context context, ArrayList<Disaster> disasters) {
        super(context, 0, disasters);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.disaster_list_entry, parent, false);
        }

        final Disaster currentDisaster = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.entry_disaster_name_textView);
        TextView typeTextView = listItemView.findViewById(R.id.entry_disaster_type_textView);
        TextView countryTextView = listItemView.findViewById(R.id.entry_disaster_countries_textView);
        TextView dateTextView = listItemView.findViewById(R.id.entry_disaster_date_textView);

        nameTextView.setText(currentDisaster.getName());
        typeTextView.setText(currentDisaster.getType());
        countryTextView.setText(currentDisaster.getCountries());
        dateTextView.setText(currentDisaster.getDate());

        LinearLayout entryLinearLayout = listItemView.findViewById(R.id.entry_outer_linearLayout);
        entryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWeb = new Intent(Intent.ACTION_VIEW);
                openWeb.setData(Uri.parse(currentDisaster.getUrl()));
                getContext().startActivity(openWeb);
            }
        });
        return listItemView;
    }
}
