package com.example.btntest.ui.constructor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.btntest.DBHelper;
import com.example.btntest.MainActivity;
import com.example.btntest.R;

import java.util.ArrayList;

public class ConstructorFragment extends Fragment {
    private ConstructorViewModel constructorViewModel;
    DBHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        constructorViewModel =
                ViewModelProviders.of(this).get(ConstructorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_constructor, container, false);
        Spinner cloth = root.findViewById(R.id.Cloth);
        Spinner furniture = root.findViewById(R.id.Furniture);
        Spinner edging = root.findViewById(R.id.edging);
        dbHelper = new DBHelper(this.getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        addItemsInSpinner(cloth, DBHelper.TABLE_CLOTH, DBHelper.CLOTH_NAME, database);
        addItemsInSpinner(furniture, DBHelper.TABLE_FURNITURE, DBHelper.FURNITURE_NAME, database);
        addItemsInSpinner(edging, DBHelper.TABLE_EDGING, DBHelper.EDGING_NAME, database);


        return root;
    }

    private void addItemsInSpinner(Spinner spinner, String table, String nameColumn, SQLiteDatabase database) {
        Cursor cursor = database.query(table, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int name = cursor.getColumnIndex(nameColumn);
            ArrayList<String> items = new ArrayList<>();
            do {
                items.add(cursor.getString(name));
            } while (cursor.moveToNext());
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, items);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
        cursor.close();
    }
}
