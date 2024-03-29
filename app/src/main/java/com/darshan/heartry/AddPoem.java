package com.darshan.heartry;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.darshan.heartry.model.PoemModel;
import com.darshan.heartry.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPoem extends AppCompatActivity {

    boolean update;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("users");
    private EditText title, poem;
    private String userID, key = null, name;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_buttons, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;
            case R.id.delete:
                delete();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_poem);
        ActionBar actionBar = getSupportActionBar();


        title = findViewById(R.id.title);
        poem = findViewById(R.id.main_poem);


        Intent intent = getIntent();
        userID = intent.getStringExtra("UserID");
        name = intent.getStringExtra("name");

        update = intent.getExtras().getBoolean("UPDATE");
        if (update) {
            actionBar.setTitle("Edit Poem");
            key = intent.getStringExtra("PoemKEY");
            String title = intent.getStringExtra("title");
            this.title.setText(title);
            String poem = intent.getStringExtra("poem");
            this.poem.setText(poem);
        } else
            actionBar.setTitle("Add Poem");

        FloatingActionButton fab = findViewById(R.id.save_poem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToDB(view);
            }
        });

        setNameParm();
    }

    private void writeToDB(final View view) {
        if (this.key == null)
            this.key = ref.child(userID).child("poems").push().getKey();

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

    private void delete() {
        if (key != null)
            ref.child(userID).child("poems").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finish();
                }
            });
        else {
            finish();
        }
    }

    private void share() {
        if (this.key == null)
            this.key = ref.child(userID).child("poems").push().getKey();

        String title = this.title.getText().toString();
        String poem = this.poem.getText().toString();
        PoemModel model = new PoemModel(title, poem);

        ref.child(userID).child("poems").child(key).setValue(model);

        String message = String.format("*%s*\n%s\n\n~%s", title, poem, name);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);

        startActivity(shareIntent);
    }

    private void setNameParm() {
        DatabaseReference nameRef = db.getReference("users").child(userID);
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name = user.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
