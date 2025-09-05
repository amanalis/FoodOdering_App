package com.example.foodapp.Helper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FirebaseHelper {

    private final FirebaseAuth mAuth;
    private final DatabaseReference myRef;

    public FirebaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("users");
    }

    //save users
    public void saveUser(String uid, Object user, OnCompleteListener<Void> listener) {
        myRef.child(uid).setValue(user).addOnCompleteListener(listener);
    }

    //get Current User
    public void getCurrentUserName(OnCompleteListener<DataSnapshot> listener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            myRef.child(currentUser.getUid()).child("name").get().addOnCompleteListener(listener);
        }
    }

    public void getUserByUid(String uid, OnCompleteListener<DataSnapshot> listener) {
        myRef.child(uid).get().addOnCompleteListener(listener);
    }

    public void updateUserDetails(String uid, Map<String, Object> updates, OnCompleteListener<Void> listener) {
        myRef.child(uid).updateChildren(updates).addOnCompleteListener(listener);
    }

}
