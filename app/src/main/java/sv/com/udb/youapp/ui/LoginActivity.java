package sv.com.udb.youapp.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.Preconditions;
import net.openid.appauth.TokenResponse;


import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.auth.IAuthService;
import sv.com.udb.youapp.auth.OAuth2AuthorizationService;
import sv.com.udb.youapp.databinding.ActivityLoginBinding;
import sv.com.udb.youapp.ui.client.RegisterActivity;
import sv.com.udb.youapp.ui.client.home.MainActivity;
import sv.com.udb.youapp.ui.client.password.ForgetPasswordActivity;
import sv.com.udb.youapp.R;

public class LoginActivity extends AppCompatActivity {


    private ActivityLoginBinding binding;
    private AuthorizationService authService;
    private AuthStateManager stateManager;
    private ActivityResultLauncher<Intent> loginLauncher;
    private IAuthService oAuth2Service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Listener
        binding.btnOlvide.setOnClickListener(this::btnOlvideListener);
        binding.btnAccder.setOnClickListener(this::btnLogin);
        binding.btnRegister.setOnClickListener(this::btnRegister);
        //Services
        stateManager = AuthStateManager.getInstance(this);
        oAuth2Service = new OAuth2AuthorizationService.Builder(getApplicationContext()).build();
        boolean sd = stateManager.getCurrent().isAuthorized();
        //ActivityResult
        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onLoginResult);
        if(sd){
            logged();
        }
    }

    private void logged(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onLoginResult(ActivityResult result){
        Intent intentResult = result.getData();
        AuthorizationException exception = AuthorizationException.fromIntent(intentResult);
        if(null != exception){
            showToast(exception.getMessage());
            return;
        }
        try{
            AuthorizationResponse authResponse = AuthorizationResponse.fromIntent(intentResult);
            Preconditions.checkNotNull(authResponse,"Unknown error");
            oAuth2Service.exchangeAccessToken(authResponse.createTokenExchangeRequest(),
               (TokenResponse response,AuthorizationException ex) -> {
                if (null != response){
                    stateManager.updateAfterTokenResponse(response,ex);
                    showToast("Login Sucess!");
                    logged();
                }else{
                    ex.printStackTrace();
                    showToast("Login Failed!");
                }
            });
        }catch (Exception e){
            showToast(e.getMessage());
        }
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void btnOlvideListener(View view){
        Intent i = new Intent(this, ForgetPasswordActivity.class);
        startActivity(i);
    }

    private void btnLogin(View view){
        loginLauncher.launch(oAuth2Service.createRequestIntent());
    }

    private void btnRegister(View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

}