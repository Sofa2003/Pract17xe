package com.example.pract17xe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText AnimalBox;
    EditText NameBox;
    EditText SizeBox;
    EditText WeightBox;

    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> animalList;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimalBox = findViewById(R.id.Animal);
        NameBox = findViewById(R.id.Name);
        SizeBox = findViewById(R.id.Size);
        WeightBox = findViewById(R.id.weight);

        Button btnAdd=(Button) findViewById(R.id.btnAdd);
        Button btnLoad=(Button) findViewById(R.id.btnLoad);
        Button btnDel=(Button) findViewById(R.id.btnDel);
        btnAdd.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnDel.setOnClickListener(this);

        listView = findViewById(R.id.listAnimal);
        sqlHelper = new DatabaseHelper(this);
        animalList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animalList);
        listView.setAdapter(adapter);

    }
    @Override
    public  void onClick(View view) {
        String animal = AnimalBox.getText().toString().trim();
        String name = NameBox.getText().toString().trim();
        String sizeStr = SizeBox.getText().toString().trim();
        String weightStr = WeightBox.getText().toString().trim();

        db = sqlHelper.getWritableDatabase();
        if (view.getId() == R.id.btnAdd) {
            int size = Integer.parseInt(sizeStr);
            int weight = Integer.parseInt(weightStr);

            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COLUMN_Animal, animal);
            cv.put(DatabaseHelper.COLUMN_NAME, name);
            cv.put(DatabaseHelper.COLUMN_Size, size);
            cv.put(DatabaseHelper.COLUMN_Weight, weight);

            db.insert(DatabaseHelper.TABLE, null, cv);
            AnimalBox.setText("");
            NameBox.setText("");
            SizeBox.setText("");
            WeightBox.setText("");
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        }
        if (view.getId() == R.id.btnLoad) {
            animalList.clear();
            userCursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);
            if (userCursor.moveToFirst()) {

                int AnimalIndex = userCursor.getColumnIndex(DatabaseHelper.COLUMN_Animal);
                int NameIndex = userCursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
                int SizeIndex = userCursor.getColumnIndex(DatabaseHelper.COLUMN_Size);
                int WeightIndex = userCursor.getColumnIndex(DatabaseHelper.COLUMN_Weight);
                do {
                    Log.d("mLog","Animal ="+userCursor.getString(AnimalIndex)+
                            ", Name="+ userCursor.getString(NameIndex)+
                            ",Size="+userCursor.getInt(SizeIndex)+
                            ",Wight="+userCursor.getInt(WeightIndex));


                }
                while (userCursor.moveToNext());
            }
            userCursor.close();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Data Load", Toast.LENGTH_SHORT).show();

        }

        if (view.getId() == R.id.btnDel) {
            // Удаление всех данных из базы данных
            db.delete(DatabaseHelper.TABLE, null, null);

            // Очистка списка
            animalList.clear();
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "All data deleted", Toast.LENGTH_SHORT).show();
            AnimalBox.setText("");
            NameBox.setText("");
            SizeBox.setText("");
            WeightBox.setText("");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userCursor != null) {
            userCursor.close();
        }
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}

