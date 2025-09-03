package com.example.foodapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        splashScreen();
        getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));

    }

    private void splashScreen() {

        View[] views = {binding.imageView5, binding.textView5, binding.textView6, binding.signupBtn, binding.loginbtn};

        int delay = 0;
        for (View v : views) {
            v.setAlpha(0f);
            v.setTranslationY(300f); // small distance
            v.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(1000)
                    .setStartDelay(delay)
                    .start();
            delay += 400; // stagger timing
        }
    }

    private void setVariable() {
        binding.loginbtn.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() != null) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, SignupActivity.class)));
    }
}