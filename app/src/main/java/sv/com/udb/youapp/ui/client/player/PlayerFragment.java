package sv.com.udb.youapp.ui.client.player;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.PlaylistItemAdapter;
import sv.com.udb.youapp.databinding.DialogCreatePlaylistBinding;
import sv.com.udb.youapp.databinding.DialogSelectPlaylistBinding;
import sv.com.udb.youapp.databinding.FragmentPlayerBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.exceptions.UnableToPlayException;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.PlayerService;
import sv.com.udb.youapp.services.impl.DefaultMusicService;
import sv.com.udb.youapp.services.impl.DefaultPlayerService;
import sv.com.udb.youapp.ui.dialog.PlaylistItemDialog;

public class PlayerFragment extends Fragment {

    private MusicService musicService;
    private FragmentPlayerBinding binding;
    private PlayerService playerService;
    private Handler mHandler = new Handler();

    public PlayerFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.fragment_player,container,false);
        binding = FragmentPlayerBinding.inflate(inflater,container,false);
        musicService = new DefaultMusicService(getContext());
        playerService = DefaultPlayerService.getInstance();
        if(playerService.isPlaying()) {
            updateStatus(playerService.get());
            if(playerService.isPlaying()){
                binding.btnPlay.setImageResource(R.drawable.ic_pause);
            }else{
                playerService.play();
                binding.btnPlay.setImageResource(R.drawable.ic_play);
            }
        }else {
            Toast.makeText(getContext(), "No song has been selected", Toast.LENGTH_SHORT).show();
        }
        getActivity().runOnUiThread(new Runnable(){
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
        binding.btnPlaylist.setOnClickListener(this::showPlaylistDialog);
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
        return binding.getRoot();
    }

    private void makeToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private void updateStatus(Music m){
        if(null == m){
            Toast.makeText(getActivity(), "No curreting playing", Toast.LENGTH_SHORT).show();
            return;
        }
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

    private void showPlaylistDialog(View view){
        if(playerService.isPlaying()){
            new PlaylistItemDialog(playerService.get()).show(
                    getChildFragmentManager(),"TAG?");
        }else {
            Toast.makeText(getContext(), "Not playing anything", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToPlaylist(Playlist p){

    }


}