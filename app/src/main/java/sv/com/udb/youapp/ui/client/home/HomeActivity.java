package sv.com.udb.youapp.ui.client.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import net.openid.appauth.AuthState;

import java.util.ArrayList;
import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.DefaultAdapter;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivityHomeBinding;
import sv.com.udb.youapp.dto.Marca;
import sv.com.udb.youapp.ui.SplashActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private AuthStateManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authManager = AuthStateManager.getInstance(this);
        binding.btnLogout.setOnClickListener(this::onLogout);
        List<Marca> marcas = new ArrayList<>();
        marcas.add(new Marca("Juanito"));
        DefaultAdapter adapter = new DefaultAdapter(R.layout.activity_home,
                marcas);
        binding.recycer.setAdapter(adapter);
    }

    private void onLogout(View view){
        authManager.replace(new AuthState());
        Intent intent = new Intent(HomeActivity.this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}