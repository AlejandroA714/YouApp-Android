package sv.com.udb.youapp.ui.client.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.openid.appauth.AuthState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.PlaylistAdapter;
import sv.com.udb.youapp.adapter.MusicAdapter;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivityHomeBinding;
import sv.com.udb.youapp.databinding.DialogCreatePlaylistBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.impl.DefaultMusicService;
import sv.com.udb.youapp.ui.SplashActivity;
import sv.com.udb.youapp.ui.client.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private AuthStateManager authManager;
    private MusicService musicService;
    private PlaylistAdapter playlistAdapter;
    private MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        binding.bottomNavigation.setSelectedItemId(R.id.nav_home);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
        authManager = AuthStateManager.getInstance(this);
        binding.btnLogout.setOnClickListener(this::onLogout);
        authManager = AuthStateManager.getInstance(getApplicationContext());
        musicService = new DefaultMusicService(getApplicationContext());
        init();
        playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        musicAdapter = new MusicAdapter(new ArrayList<>());
        binding.rv1.setAdapter(musicAdapter);
        binding.rv2.setAdapter(playlistAdapter);
        binding.btnCreatePy.setOnClickListener(this::showPlaylistDialog);
    }


    private void showPlaylistDialog(View view){
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_create_playlist);
        DialogCreatePlaylistBinding binding = DialogCreatePlaylistBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        binding.btnAceptar.setOnClickListener(v -> {
            String title = binding.txtTitleModal.getText().toString();
            if(null == title || title.length() < 3){
                Toast.makeText(this, "Must be more than 3 characters", Toast.LENGTH_SHORT).show();
            }else{
                dialog.cancel();
                musicService.createPlaylist(title).enqueue(new Callback<Playlist>() {
                    @Override
                    public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                        if(null != response.body()){
                            updatePlaylist();
                        }
                    }
                    @Override
                    public void onFailure(Call<Playlist> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Failed to create playlist", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }

    private void init(){
        updatePlaylist();
       musicService.getSongs().enqueue(new Callback<List<Music>>() {
           @Override
           public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(null != response.body()){
                    List<Music> music = response.body();
                    musicAdapter.update(music);
                }
           }
           @Override
           public void onFailure(Call<List<Music>> call, Throwable t) {
               t.printStackTrace();
               Toast.makeText(getApplicationContext(),"Failed to recover songs",Toast.LENGTH_LONG).show();
           }
       });
    }

    private void updatePlaylist(){
        musicService.getPlaylist().enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if(null != response.body()){
                    List<Playlist> playlists = response.body();
                    playlistAdapter.update(playlists);
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(HomeActivity.this, "Failed to recover playlist", Toast.LENGTH_SHORT).show();
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