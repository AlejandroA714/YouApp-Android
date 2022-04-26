package sv.com.udb.youapp.ui.client.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.ui.LoginActivity;

import sv.com.udb.youapp.databinding.ActivityPasswordUpdateBinding;


public class PasswordUpdateActivity extends AppCompatActivity {

    private ActivityPasswordUpdateBinding binding;

    //Animation
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        binding = ActivityPasswordUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAccder.setOnClickListener(this::btnLoginListener);

        //Animation
        animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //binding.title1.setAnimation(animation);
        //binding.image.setAnimation(animation);
        //binding.slogan.setAnimation(animation);
        //binding.btnAccder.setAnimation(animation);
        binding.update.setAnimation(animation);

    }

    private void btnLoginListener(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}