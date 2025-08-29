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
        binding.imageView5.setTranslationY(-10000f);
        binding.textView5.setTranslationY(10000f);
        binding.textView6.setTranslationY(10000f);
        binding.signupBtn.setTranslationY(10000f);
        binding.loginbtn.setTranslationY(10000f);

        if((binding.imageView5.getTranslationY() == -10000f) && (binding.textView5.getTranslationY() == 10000f)){
            binding.imageView5.animate().translationYBy(+10000f).setDuration(2000);
            binding.textView5.animate().translationYBy(-10000f).setDuration(3000);
            binding.textView6.animate().translationYBy(-10000f).setDuration(4000);
            binding.signupBtn.animate().translationYBy(-10000f).setDuration(5000);
            binding.loginbtn.animate().translationYBy(-10000f).setDuration(5000);

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