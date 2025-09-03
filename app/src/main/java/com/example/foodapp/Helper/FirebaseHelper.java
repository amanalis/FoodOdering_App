package com.example.foodapp.Helper;

import android.media.MediaPlayer;

import com.example.foodapp.Activity.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper  {

    private final FirebaseAuth mAuth;
    private final DatabaseReference myRef;

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");
    }

    //save users
    public void saveUser(String uid, Object user, OnCompleteListener<Void> listener){
        myRef.child(uid).setValue(user).addOnCompleteListener(listener);
    }

    //get Current User
    public void getCurrentUserName(OnCompleteListener<DataSnapshot> listener){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            myRef.child(currentUser.getUid()).child("name").get().addOnCompleteListener(listener);
        }
    }
}
