package sv.com.udb.youapp.ui.client.player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityPlayerBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.PlayerService;
import sv.com.udb.youapp.services.api.MusicApi;
import sv.com.udb.youapp.services.api.RetrofitFactory;
import sv.com.udb.youapp.services.impl.DefaultMusicService;
import sv.com.udb.youapp.services.impl.DefaultPlayerService;

public class PlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private MusicService musicService;
    private ActivityPlayerBinding binding;
    private List<Music> music;
    private PlayerService playerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //binding
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Services
        musicService = new DefaultMusicService(getApplicationContext());
        playerService = DefaultPlayerService.getInstance();
        init();

        binding.btnPlay.setOnClickListener(v -> {
            playerService.play();
                //mediaPlayer.setDataSource("http://192.168.101.17:9090/youapp/swae_lee.mp3");
                //mediaPlayer.prepare();
                //mediaPlayer.stop();
                //mediaPlayer.start();

        });

    }

    private void init(){
        musicService.getSongsAsync().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                List<Music> body = response.body();
                if(null != body){
                    playerService.init(body);
                    Toast.makeText(PlayerActivity.this, "Ready!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}