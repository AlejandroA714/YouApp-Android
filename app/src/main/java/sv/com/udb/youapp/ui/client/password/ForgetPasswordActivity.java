package sv.com.udb.youapp.ui.client.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityForgetPasswordBinding;
import sv.com.udb.youapp.enums.HttpFactory;
import sv.com.udb.youapp.services.api.ForgotPasswordService;
import sv.com.udb.youapp.services.api.RetrofitFactory;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;
    private ForgotPasswordService passService;

    //Animation
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        passService = RetrofitFactory.getInstance(HttpFactory.AUTHENTICATION,ForgotPasswordService.class);

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
        if("".equals(binding.email.getText().toString())){
            Toast.makeText(this,"Email Invalido",Toast.LENGTH_LONG).show();
            return;
        }
        Call<Void> call = passService.resetPassword(binding.email.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Music Service","Sucess?");
                Intent i = new Intent(ForgetPasswordActivity.this, PasswordUpdateActivity.class);
                startActivity(i);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
}