package sv.com.udb.youapp.ui.client.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityForgetPasswordBinding;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;

    //Animation
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(this::btnNextListener);

        //Animation
        animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        binding.logo.setAnimation(animation);
        binding.title1.setAnimation(animation);
        binding.title2.setAnimation(animation);
        binding.email.setAnimation(animation);
        binding.btnNext.setAnimation(animation);
    }

    private void btnNextListener(View view){
        Intent i = new Intent(this, PasswordUpdateActivity.class);
        startActivity(i);
    }
}