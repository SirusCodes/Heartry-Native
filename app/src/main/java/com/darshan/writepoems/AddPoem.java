package com.darshan.writepoems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.darshan.writepoems.model.PoemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPoem extends AppCompatActivity {

    EditText title, poem;
    String userID, key;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_poem);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        title = findViewById(R.id.title);
        poem = findViewById(R.id.main_poem);

        Intent intent = getIntent();
        userID = intent.getStringExtra("UserID");

        FloatingActionButton fab = findViewById(R.id.save_poem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToDB(view);
            }
        });
    }

    private void writeToDB(final View view) {
        if (key == null)
            key = ref.child(userID).child("poems").push().getKey();

        String title = this.title.getText().toString();
        String poem = this.poem.getText().toString();
        PoemModel model = new PoemModel(title, poem);

        ref.child(userID).child("poems").child(key).setValue(model)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Something went wrong can't save poem", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar = Snackbar.make(view, "Poem Saved", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });


    }


}
