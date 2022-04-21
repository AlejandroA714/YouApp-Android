package sv.com.udb.youapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import sv.com.udb.youapp.databinding.ActivityLoginBinding;
import sv.com.udb.youapp.databinding.ActivityPasswordUpdateBinding;


public class PasswordUpdateActivity extends AppCompatActivity {

    private ActivityPasswordUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        binding = ActivityPasswordUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAccder.setOnClickListener(this::btnLoginListener);

    }

    private void btnLoginListener(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}