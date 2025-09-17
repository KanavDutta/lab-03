package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }

    private AddCityDialogListener listener;
    private City cityToEdit = null;
    private int editPosition = -1;

    // Empty constructor for adding new cities
    public AddCityFragment() {
    }

    // Static method for editing existing cities
    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if we're editing or adding
        Bundle args = getArguments();
        final boolean isEditing;
        if (args != null) {
            cityToEdit = (City) args.getSerializable("city");
            editPosition = args.getInt("position");
            isEditing = true;

            // Pre-populate fields for editing
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        } else {
            isEditing = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEditing ? "Edit City" : "Add a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEditing ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (isEditing) {
                        // Update existing city
                        City updatedCity = new City(cityName, provinceName);
                        listener.editCity(updatedCity, editPosition);
                    } else {
                        // Add new city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}