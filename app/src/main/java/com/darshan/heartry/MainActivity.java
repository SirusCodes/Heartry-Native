package com.darshan.heartry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.darshan.heartry.database.DatabaseHelper;
import com.darshan.heartry.model.PoemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity implements RecyclerView_Config.OnPoemListener {

    private RecyclerView listView;
    private List<PoemModel> poemList;
    private List<String> keyList;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        userID = intent.getStringExtra("UserID");

        listView = findViewById(R.id.poem_list);
        new DatabaseHelper(userID).getPoems(new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PoemModel> poems, List<String> keys) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                poemList = poems;
                keyList = keys;
                new RecyclerView_Config().setConfig(listView, MainActivity.this, poems, keys, MainActivity.this);
            }
        });


        FloatingActionButton fab = findViewById(R.id.add_poem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, AddPoem.class);
                myIntent.putExtra("UserID", userID);
                myIntent.putExtra("UPDATE", false);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onPoemClick(int position) {
        Intent intent = new Intent(MainActivity.this, AddPoem.class);
        intent.putExtra("UserID", userID);
        intent.putExtra("title", poemList.get(position).getTitle());
        intent.putExtra("poem", poemList.get(position).getPoem());
        intent.putExtra("PoemKEY", keyList.get(position));
        intent.putExtra("UPDATE", true);
        startActivity(intent);
    }
}
