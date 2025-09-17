package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {

    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;

        // Check if convertView is null (no recycled view available)
        if (convertView == null) {
            // Inflate the custom layout for this list item
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        } else {
            // Reuse the recycled view
            view = convertView;
        }

        // Get the City object at this position
        City city = getItem(position);

        // Find the TextViews in the layout
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        // Set the text for both TextViews
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        return view;
    }
}