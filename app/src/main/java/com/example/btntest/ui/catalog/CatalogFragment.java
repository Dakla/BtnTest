package com.example.btntest.ui.catalog;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.btntest.DBHelper;
import com.example.btntest.R;

public class CatalogFragment extends Fragment {

    private CatalogViewModel catalogViewModel;
    DBHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        catalogViewModel =
                ViewModelProviders.of(this).get(CatalogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_catalog, container, false);
        TableLayout tableLayout = root.findViewById(R.id.CatalogTable);

        dbHelper = new DBHelper(this.getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_ITEMS, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int i = 0;
            int id = cursor.getColumnIndex(DBHelper.ITEM_ID);
            int name = cursor.getColumnIndex(DBHelper.ITEM_NAME);
            int width = cursor.getColumnIndex(DBHelper.ITEM_WIDTH);
            int height = cursor.getColumnIndex(DBHelper.ITEM_HEIGHT);
            int cloth = cursor.getColumnIndex(DBHelper.ITEM_CLOTH);
            int furniture = cursor.getColumnIndex(DBHelper.ITEM_FURNITURE);
            int edging = cursor.getColumnIndex(DBHelper.ITEM_EDGING);
            i = setRow(i, tableLayout, "Название", "Ширина", "Длина", "Ткань", "Фурнитура", "Окантовка", 0);
            do {
                String[] itms1 = new String[]{String.valueOf(cursor.getInt(cloth) + 1)};
                String[] itms2 = new String[]{String.valueOf(cursor.getInt(furniture) + 1)};
                String[] itms3 = new String[]{String.valueOf(cursor.getInt(edging) + 1)};
                Cursor c1 = database.query(DBHelper.TABLE_CLOTH, null, "_id = ?", itms1, null, null, null);
                Cursor c2 = database.query(DBHelper.TABLE_FURNITURE, null, "_id = ?", itms2, null, null, null);
                Cursor c3 = database.query(DBHelper.TABLE_EDGING, null, "_id = ?", itms3, null, null, null);
                c1.moveToFirst();
                c2.moveToFirst();
                c3.moveToFirst();
                i = setRow(i, tableLayout, cursor.getString(name),
                        cursor.getString(width), cursor.getString(height),
                        c1.getString(c1.getColumnIndex(DBHelper.CLOTH_NAME)),
                        c2.getString(c2.getColumnIndex(DBHelper.FURNITURE_NAME)),
                        c3.getString(c3.getColumnIndex(DBHelper.EDGING_NAME)),
                        cursor.getInt(id)
                );
                c1.close();
                c2.close();
                c3.close();
            } while (cursor.moveToNext());
            cursor.close();
        }

        return root;
    }

    private int setRow(int i, TableLayout tableLayout, String t1, String t2, String t3, String t4, String t5, String t6, int id) {
        TableRow tableRow = new TableRow(this.getActivity());
        tableRow.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tv1 = new TextView(this.getActivity());
        TextView tv2 = new TextView(this.getActivity());
        TextView tv3 = new TextView(this.getActivity());
        TextView tv4 = new TextView(this.getActivity());
        TextView tv5 = new TextView(this.getActivity());
        TextView tv6 = new TextView(this.getActivity());
        tv1.setText(t1);
        tv2.setText(t2);
        tv3.setText(t3);
        tv4.setText(t4);
        tv5.setText(t5);
        tv6.setText(t6);
        tableRow.addView(tv1, 0);
        tableRow.addView(tv2, 1);
        tableRow.addView(tv3, 2);
        tableRow.addView(tv4, 3);
        tableRow.addView(tv5, 4);
        tableRow.addView(tv6, 5);
        if(i != 0) {
            Button button = new Button(this.getActivity());
            button.setText("Офорить заказ");
            button.setOnClickListener(mCorkyListener);
            button.setId(id);
            tableRow.addView(button, 6);
        }
        tableLayout.addView(tableRow, i);
        return ++i;
    }

    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            int id = v.getId();
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.ORDER_STATUS, "Первый статус");
            cv.put(DBHelper.ORDER_ITEMS, id);
            database.insert(DBHelper.TABLE_ORDERS, null, cv);
        }
    };

}
