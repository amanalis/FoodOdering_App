package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Domain.Users;
import com.example.foodapp.Helper.FirebaseHelper;
import com.example.foodapp.databinding.ActivitySignupBinding;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();

    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(view -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();

            if (password.length() < 6) {
                Toast.makeText(SignupActivity.this, "your password must be 6 character", Toast.LENGTH_SHORT).show();
                return;
            }

            // Need to switch if data added to Database then add to Auth.
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isSuccessful()) {

//                    DatabaseReference myref = database.getReference("users");
                    FirebaseHelper helper = new FirebaseHelper();

                    String name = binding.nameEdt.getText().toString();
                    String userEmail = binding.userEdt.getText().toString();
                    String userPass = binding.passEdt.getText().toString();

                    Users users = new Users(name, userEmail, userPass);

                    String uid = mAuth.getCurrentUser().getUid();

                    helper.saveUser(uid,users,task1 -> {
                        if(task1.isSuccessful()){
                            Log.i(TAG, "User Added To DB.");
                            Log.i(TAG, "onComplete: ");
                            Toast.makeText(this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(SignupActivity.this, MainActivity.class));
                        }
                    });
//                    myref.child(uid).setValue(users).addOnCompleteListener((OnCompleteListener<Void>) task12 -> {
//                        if (task12.isSuccessful()) {
//
//                        }
//                    });
                } else {
                    Log.i(TAG, "failure: " + task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                }
            });
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}