package sv.com.udb.youapp.ui.client.player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityPlayerBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.exceptions.UnableToPlayException;
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
    private Handler mHandler = new Handler();

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
        PlayerActivity.this.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                    int mCurrentPosition = playerService.getCurrentPostion() / 1000;
                    binding.seekBar.setProgress(mCurrentPosition);
                    binding.txtInfo.setText(secondsToMMSS(mCurrentPosition));
                mHandler.postDelayed(this, 1000);
            }
        });
        playerService.setOnCompleteListener(mp -> {
            try{
                updateStatus(playerService.forward());
            }catch (UnableToPlayException e){
                makeToast(e.getMessage());
            }
        });
        binding.btnPlay.setOnClickListener(this::onPlayListener);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    playerService.seekTo(progress*1000);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        binding.btnBackward.setOnClickListener((View v) -> {
            try{
               updateStatus(playerService.backward());
            }catch (UnableToPlayException e){
                makeToast(e.getMessage());
            }
        });
        binding.btnForward.setOnClickListener(v -> {
           try{
               updateStatus(playerService.forward());
           }catch (UnableToPlayException e){
               makeToast(e.getMessage());
           }
        });
    }

    private void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void updateStatus(Music m){
        Picasso.get().load(m.getPhoto()).into(binding.imageViewArt);
        binding.txtPlayerTitle.setText(m.getTitle());
        binding.txtPlayerAuthor.setText(m.getUser().getUsername());
        binding.txtDuracion.setText(secondsToMMSS(m.getDuration()));
        binding.seekBar.setMax(playerService.getDuration());
    }

    public static String secondsToMMSS(int duration){
        final int m = (int) Math.floor((duration % 3600) / 60);
        final int s = (int) Math.floor((duration % 3600) % 60);
        final String mins = m > 0 ? (m < 10 ? "0" + m + ":" : m + ":") : "00:";
        final String secnds = s > 0 ? (s < 10 ? "0" + s : String.valueOf(s)) : "00";
        return mins+secnds;
    }


    private void onPlayListener(View view){
        if(playerService.isPlaying()){
            playerService.pause();
            binding.btnPlay.setImageResource(R.drawable.ic_play);
        }else{
            playerService.play();
            binding.btnPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    private void init(){
        musicService.getSongsAsync().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                List<Music> body = response.body();
                if(null != body){
                    try {
                        playerService.init(body);
                        updateStatus(body.get(0));
                        binding.btnPlay.setImageResource(R.drawable.ic_pause);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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