package com.example.foodapp.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Helper.FirebaseHelper;
import com.example.foodapp.databinding.ActivityProfileBinding;
import com.google.firebase.database.DataSnapshot;

public class ProfileActivity extends BaseActivity {
    private ActivityProfileBinding binding;
    private String titleTxt;
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserInfo();
        setVariable();
        getIntentExtra();
    }

    private void getIntentExtra() {
        titleTxt = getIntent().getStringExtra("CategoryName");
        binding.titleTxt.setText(titleTxt);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());



        binding.updateBtn.setOnClickListener(v -> {
            String oldPassword = binding.passwordTxt.getText().toString().trim();
            String newPassword = binding.newpasswordTxt.getText().toString().trim();
            String newEmail = binding.emailTxt.getText().toString().trim();
            String name = binding.nameTxt.getText().toString().trim();

            if (oldPassword.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

        });

    }

    private void getUserInfo() {

        binding.progressBar2.setVisibility(View.VISIBLE);

        //adding timer
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (binding.progressBar2.getVisibility() == View.VISIBLE) {
                binding.progressBar2.setVisibility(View.GONE);
                Toast.makeText(this, "Request Timeout, try again", Toast.LENGTH_SHORT).show();
            }
        }, 5000);

        String uid = mAuth.getCurrentUser().getUid();

        firebaseHelper.getUserByUid(uid, task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot snapshot = task.getResult();
                binding.nameTxt.setText(snapshot.child("name").getValue(String.class));
                binding.emailTxt.setText(snapshot.child("email").getValue(String.class));
                binding.passwordTxt.setText(snapshot.child("password").getValue(String.class));

                binding.progressBar2.setVisibility(View.GONE);
            }
        });
    }
}