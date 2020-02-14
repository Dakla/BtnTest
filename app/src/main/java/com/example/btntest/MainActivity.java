package com.example.btntest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_catalog, R.id.navigation_constructor, R.id.navigation_orders, R.id.navigation_about)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        dbHelper = new DBHelper(this);
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(DBHelper.CLOTH_NAME, "ClTest");
//        cv.put(DBHelper.CLOTH_PRICE, 127.53);
//        database.insert(DBHelper.TABLE_CLOTH, null, cv);
//        cv.clear();
//        cv.put(DBHelper.CLOTH_NAME, "ClTest2");
//        cv.put(DBHelper.CLOTH_PRICE, 125);
//        database.insert(DBHelper.TABLE_CLOTH, null, cv);
//        cv.clear();
//
//        cv.put(DBHelper.FURNITURE_NAME, "FuTest");
//        cv.put(DBHelper.FURNITURE_PRICE, 127.53);
//        database.insert(DBHelper.TABLE_FURNITURE, null, cv);
//        cv.clear();
//        cv.put(DBHelper.FURNITURE_NAME, "FuTest2");
//        cv.put(DBHelper.FURNITURE_PRICE, 125);
//        database.insert(DBHelper.TABLE_FURNITURE, null, cv);
//        cv.clear();
//
//        cv.put(DBHelper.EDGING_NAME, "EdTest");
//        cv.put(DBHelper.EDGING_PRICE, 127.53);
//        database.insert(DBHelper.TABLE_EDGING, null, cv);
//        cv.clear();
//        cv.put(DBHelper.EDGING_NAME, "EdTest2");
//        cv.put(DBHelper.EDGING_PRICE, 125);
//        database.insert(DBHelper.TABLE_EDGING, null, cv);
//        cv.clear();
    }

    public void CreateCustom(View view) {
        TextView nameTW = findViewById(R.id.Name);
        TextView widthTW = findViewById(R.id.Width);
        TextView heightTW = findViewById(R.id.Height);
        Spinner cloth = findViewById(R.id.Cloth);
        Spinner furniture = findViewById(R.id.Furniture);
        Spinner edging = findViewById(R.id.edging);
        String name = nameTW.getText().toString().trim();
        double width = Double.parseDouble(widthTW.getText().toString());
        double height = Double.parseDouble(heightTW.getText().toString());
        int clothId = cloth.getSelectedItemPosition();
        int furnitureId = furniture.getSelectedItemPosition();
        int edgingId = edging.getSelectedItemPosition();
        if(!name.isEmpty() && width > 0 && height > 0 && clothId >= 0 && furnitureId >= 0 && edgingId >= 0) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBHelper.ITEM_NAME, name);
            cv.put(DBHelper.ITEM_WIDTH, width);
            cv.put(DBHelper.ITEM_HEIGHT, height);
            cv.put(DBHelper.ITEM_CLOTH, clothId);
            cv.put(DBHelper.ITEM_FURNITURE, furnitureId);
            cv.put(DBHelper.ITEM_EDGING, edgingId);
            database.insert(DBHelper.TABLE_ITEMS, null, cv);
            cv.clear();
            int idItem = 0;
            Cursor cursor = database.query(DBHelper.TABLE_ITEMS, null, null, null, null, null, null);
            if(cursor.moveToLast()) {
                int idLast = cursor.getColumnIndex(DBHelper.ITEM_ID);
                idItem = cursor.getInt(idLast);
            }
            cursor.close();
            cv.put(DBHelper.ORDER_STATUS, "Первый статус");
            cv.put(DBHelper.ORDER_ITEMS, idItem);
            database.insert(DBHelper.TABLE_ORDERS, null, cv);
        }

    }
}
