package com.darshan.writepoems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darshan.writepoems.database.DatabaseHelper;
import com.darshan.writepoems.model.PoemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("UserID");

        listView = findViewById(R.id.poem_list);
        new DatabaseHelper(userID).getPoems(new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PoemModel> poems, List<String> keys) {
                new RecyclerView_Config().setConfig(listView, MainActivity.this, poems, keys);
            }
        });


        FloatingActionButton fab = findViewById(R.id.add_poem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddPoem.class);
                myIntent.putExtra("UserID", userID);
                startActivity(myIntent);
            }
        });
    }

}
