package com.darshan.writepoem;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AddPoem extends AppCompatActivity {

    EditText title, poem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_poem);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        title = findViewById(R.id.title);
        poem = findViewById(R.id.main_poem);

        FloatingActionButton fab = findViewById(R.id.save_poem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Poem Saved", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }


}
