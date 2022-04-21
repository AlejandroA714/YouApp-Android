package sv.com.udb.youapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import sv.com.udb.youapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    //Animation
    Animation topAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        binding.btnOlvide.setOnClickListener(this::btnOlvideListener);

        binding.logo.setAnimation(topAnimation);
        binding.title1.setAnimation(middleAnimation);
        binding.btnAccder.setAnimation(middleAnimation);
        binding.btnOlvide.setAnimation(middleAnimation);
        binding.btnRegister.setAnimation(middleAnimation);

    }

    private void btnOlvideListener(View view){
        Intent i = new Intent(this, ForgetPasswordActivity.class);
        startActivity(i);
    }
}