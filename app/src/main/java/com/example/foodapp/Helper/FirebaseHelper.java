package com.example.foodapp.Helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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

    //changing password and email in firebase Auth
    public void updatePasswordEmail(String oldPassword, String newPassword, String newEmail, OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String currentEmail = user.getEmail();
            if (currentEmail == null) {
                if (listener != null) {
                    listener.onComplete(Tasks.forException(new Exception("Current email not found")));
                }
                return;
            }

            // Step 1: Re-authenticate with old password
            AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, oldPassword);
            user.reauthenticate(credential).addOnCompleteListener(reauthTask -> {
                if (reauthTask.isSuccessful()) {
                    // Step 2: Update password
                    user.updatePassword(newPassword).addOnCompleteListener(passTask -> {
                        if (passTask.isSuccessful()) {
                            // Step 3: Update email
                            user.updateEmail(newEmail).addOnCompleteListener(emailTask -> {
                                if (listener != null) {
                                    listener.onComplete(emailTask); // final callback
                                }
                            });
                        } else {
                            if (listener != null) {
                                listener.onComplete(passTask); // failed at password step
                            }
                        }
                    });
                } else {
                    if (listener != null) {
                        listener.onComplete(reauthTask); // failed at re-auth
                    }
                }
            });
        } else {
            if (listener != null) {
                listener.onComplete(Tasks.forException(new Exception("No user logged in")));
            }
        }
    }

}
