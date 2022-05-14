package sv.com.udb.youapp.ui.client.player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityPlayerBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.exceptions.UnableToPlayException;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.PlayerService;
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
                    binding.txtInfo.setText(playerService.secondsToMMSS(mCurrentPosition));
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
        binding.btnLove.setOnClickListener(this::onLoveListener);
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
        binding.txtDuracion.setText(playerService.secondsToMMSS(m.getDuration()));
        binding.seekBar.setMax(playerService.getDuration());
        if(m.likes()){
            binding.btnLove.setImageResource(R.drawable.ic_heart_fill);
        }else{
            binding.btnLove.setImageResource(R.drawable.ic_heart);
        }
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

    private void onLoveListener(View view){
        Music m = playerService.get();
        if(m.likes()){
            musicService.dislike(m.getId()).enqueue(this.onLikeResult(m));
        }else{
            musicService.like(m.getId()).enqueue(this.onLikeResult(m));
        }
    }


    private Callback<Void> onLikeResult(Music m){
        return new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                boolean like = !m.likes();
                makeToast("Now you " + (like ? "like" : "dislike") + " this song");
                m.setLikes(like);
                playerService.update(m);
                updateStatus(m);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                makeToast("Failed to like/dislike");
            }
        };
    }

    private void init(){
        musicService.getSongs().enqueue(new Callback<List<Music>>() {
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