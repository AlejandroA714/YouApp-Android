package sv.com.udb.youapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;
import net.openid.appauth.browser.AnyBrowserMatcher;


import java.util.HashMap;
import java.util.Map;

import sv.com.udb.youapp.oauth.HttpConnectionBuilder;
import sv.com.udb.youapp.ui.client.password.ForgetPasswordActivity;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "youapp";
    private static final String AUTHORIZE_URI = "http://192.168.101.17:8083/oauth2/authorize";
    private static final String TOKEN_URI = "http://192.168.101.17:8083/oauth2/token";
    private static final String REDIRECT_URI = "youapp://oauth";
    private ActivityLoginBinding binding;
    private AuthorizationService authService;
    private AuthState stateManager;

    //Animation
    Animation topAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppAuthConfiguration appAuthConfiguration = new AppAuthConfiguration.Builder()
                .setSkipIssuerHttpsCheck(true)
                .setConnectionBuilder(HttpConnectionBuilder.INSTANCE).build();
        authService = new AuthorizationService(this,appAuthConfiguration);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        binding.btnOlvide.setOnClickListener(this::btnOlvideListener);
        binding.btnAccder.setOnClickListener(this::btnLogin);

        //binding.logo.setAnimation(topAnimation);
        //binding.title1.setAnimation(middleAnimation);
        //binding.btnAccder.setAnimation(middleAnimation);
        //binding.btnOlvide.setAnimation(middleAnimation);
        //binding.btnRegister.setAnimation(middleAnimation);

    }

    private void btnOlvideListener(View view){
        Intent i = new Intent(this, ForgetPasswordActivity.class);
        startActivity(i);
    }

    private void btnLogin(View view){
        AuthorizationServiceConfiguration authorizationServiceConfiguration = new AuthorizationServiceConfiguration(
                Uri.parse(AUTHORIZE_URI),
                Uri.parse(TOKEN_URI));
        AuthorizationRequest authorizationRequest =  new AuthorizationRequest.Builder(
                authorizationServiceConfiguration,
                CLIENT_ID,
                ResponseTypeValues.CODE,
                Uri.parse(REDIRECT_URI))
                .setScopes("openid","profile","email").build();
        Intent authIntent = authService.getAuthorizationRequestIntent(authorizationRequest);
        startActivityForResult(authIntent,100);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            TokenRequest re = resp.createTokenExchangeRequest();
            ClientAuthentication clientAuth = new ClientSecretPost("9d[?hr%[Y>w~nV3_");
            authService.performTokenRequest(re,clientAuth,
                    (TokenResponse response,AuthorizationException ex1) -> {
                        System.out.println("Hola");
             });
        }

    }

}