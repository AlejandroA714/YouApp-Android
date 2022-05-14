package sv.com.udb.youapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.openid.appauth.AuthState;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivitySplashBinding;
import sv.com.udb.youapp.ui.client.home.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;
    private AuthStateManager authManager;
    private ActivitySplashBinding binding;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        authManager = AuthStateManager.getInstance(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        animation = AnimationUtils.loadAnimation(this, R.anim.pulse_animation);
        binding.logo.setAnimation(animation);
        binding.title1.setAnimation(animation);
        binding.title2.setAnimation(animation);
        new Handler().postDelayed(() -> {
            AuthState authState = authManager.getCurrent();
            Log.d("Authorization",authState.toString());
            Intent intent;
            if(authState.isAuthorized()){
                Toast.makeText(this,"Bienvenido!",Toast.LENGTH_LONG).show();
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }else{
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}