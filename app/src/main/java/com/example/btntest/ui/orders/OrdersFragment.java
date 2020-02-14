package com.example.btntest.ui.orders;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import android.view.ViewGroup.LayoutParams;

public class OrdersFragment extends Fragment {

    private OrdersViewModel ordersViewModel;
    DBHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordersViewModel =
                ViewModelProviders.of(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        TableLayout tableLayout = root.findViewById(R.id.orderList);
        dbHelper = new DBHelper(this.getActivity());
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_ORDERS, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int i = 0;
            int curId = cursor.getColumnIndex(DBHelper.ORDER_ID);
            int curItems = cursor.getColumnIndex(DBHelper.ORDER_ITEMS);
            int curSt = cursor.getColumnIndex(DBHelper.ORDER_STATUS);
            do {
                TableRow tableRow = new TableRow(this.getActivity());
                tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                TextView tv1 = new TextView(this.getActivity());
                TextView tv2 = new TextView(this.getActivity());
                TextView tv3 = new TextView(this.getActivity());
                tv1.setText(cursor.getString(curId));
                String[] itms = new String[]{String.valueOf(cursor.getInt(curItems))};
                Cursor c = database.query(DBHelper.TABLE_ITEMS, null, "_id = ?", itms, null, null, null);
                c.moveToFirst();
                tv2.setText(c.getString(c.getColumnIndex(DBHelper.ITEM_NAME)));
                c.close();
                tv3.setText(cursor.getString(curSt));
                tableRow.addView(tv1, 0);
                tableRow.addView(tv2, 1);
                tableRow.addView(tv3, 2);
                tableLayout.addView(tableRow, i);
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }

        return root;
    }
}
