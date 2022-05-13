package sv.com.udb.youapp.ui.client.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.openid.appauth.AuthState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.MusicAdapter;
import sv.com.udb.youapp.adapter.PlaylistAdapter;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivityHomeBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.enums.HttpFactory;
import sv.com.udb.youapp.services.api.MusicApi;
import sv.com.udb.youapp.services.api.RetrofitFactory;
import sv.com.udb.youapp.ui.SplashActivity;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private AuthStateManager authManager;
    private MusicApi musicApiService;
    private MusicAdapter musicAdapter;
    private PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authManager = AuthStateManager.getInstance(this);
        binding.btnLogout.setOnClickListener(this::onLogout);
        authManager = AuthStateManager.getInstance(getApplicationContext());
        musicApiService = RetrofitFactory.getInstance(HttpFactory.STORAGE,MusicApi.class);
        init();
        playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        musicAdapter = new MusicAdapter(new ArrayList<>());
        binding.rv1.setAdapter(playlistAdapter);
        binding.rv2.setAdapter(musicAdapter);
    }

    private void init(){
       final Call<List<Music>> call = musicApiService.getSongs("Bearer " + authManager.getCurrent().getAccessToken());
       call.enqueue(new Callback<List<Music>>() {
           @Override
           public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(null != response.body()){
                    List<Music> music = response.body();
                    musicAdapter.update(music);
                    playlistAdapter.update(music);
                    Log.d("Music Service","Sucess?");
                }
           }
           @Override
           public void onFailure(Call<List<Music>> call, Throwable t) {
               t.printStackTrace();
               Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
           }
       });
    }

    private void onLogout(View view){
        authManager.replace(new AuthState());
        Intent intent = new Intent(HomeActivity.this,SplashActivity.class);
        startActivity(intent);
        finish();
    }
}