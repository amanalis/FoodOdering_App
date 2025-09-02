package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodapp.Domain.Users;
import com.example.foodapp.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

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

                    DatabaseReference myref = database.getReference("users");

                    String name = binding.nameEdt.getText().toString();
                    String userEmail = binding.userEdt.getText().toString();
                    String userPass = binding.passEdt.getText().toString();

                    Users users = new Users(name, userEmail, userPass);

                    String uid = mAuth.getCurrentUser().getUid();

                    myref.child(uid).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "User Added To DB.");
                                Log.i(TAG, "onComplete: ");

                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("password", password);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            }
                        }
                    });
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