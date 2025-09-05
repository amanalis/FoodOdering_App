package com.example.foodapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.loginbtn.setOnClickListener(view -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();

            if(!email.isEmpty() && !password.isEmpty()){

                binding.progressBar.setVisibility(View.VISIBLE);
                //adding timer
                new Handler(Looper.getMainLooper()).postDelayed(() ->{
                    if(binding.progressBar.getVisibility() == View.VISIBLE){
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Request Timeout, try again", Toast.LENGTH_SHORT).show();
                    }
                },10000);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, task -> {
                    binding.progressBar.setVisibility(View.GONE); // ✅ Hide progress bar after response
                    if(task.isSuccessful()){
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity( new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Please fill username and password", Toast.LENGTH_SHORT).show();
            }
        });

        binding.signupRedirectText.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }
}