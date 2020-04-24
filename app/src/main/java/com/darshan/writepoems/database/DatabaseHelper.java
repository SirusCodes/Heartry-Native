package com.darshan.writepoems.database;

import androidx.annotation.NonNull;

import com.darshan.writepoems.model.PoemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private List<PoemModel> poemList = new ArrayList<>();

    public DatabaseHelper(String userID) {
//        db.setPersistenceEnabled(true);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("users").child(userID).child("poems");
    }

    public void getPoems(final DataStatus dataStatus) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                poemList.clear();
                List<String> keyList = new ArrayList<>();
                for (DataSnapshot poem : dataSnapshot.getChildren()) {
                    keyList.add(poem.getKey());
                    PoemModel poemModel = poem.getValue(PoemModel.class);
                    poemList.add(poemModel);
                }
                dataStatus.DataIsLoaded(poemList, keyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface DataStatus {
        void DataIsLoaded(List<PoemModel> poems, List<String> keys);
    }

}
