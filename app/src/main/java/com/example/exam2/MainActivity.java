package com.example.exam2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.exam2.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.loginButton.setEnabled(false);

        textWatcher();

        binding.loginButton.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ModeActivity.class));
        });
    }

    public void textWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = binding.emailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();

                binding.loginButton.setEnabled(
                        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                                && password.length() >= 6
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.emailInput.addTextChangedListener(watcher);
        binding.passwordInput.addTextChangedListener(watcher);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity();
        } else {
            Snackbar.make(binding.getRoot(), R.string.press_back_again, Snackbar.LENGTH_SHORT).show();
            backPressedTime = System.currentTimeMillis();
        }
    }
}