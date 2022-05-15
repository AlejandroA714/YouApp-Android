 package sv.com.udb.youapp.ui.client;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import net.openid.appauth.AuthState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.MusicAdapter;
import sv.com.udb.youapp.adapter.PlaylistAdapter;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.DialogCreatePlaylistBinding;
import sv.com.udb.youapp.databinding.FragmentHomeBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.exceptions.SongAlreadyOnQueueException;
import sv.com.udb.youapp.interfaces.OnItemClickListener;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.PlayerService;
import sv.com.udb.youapp.services.impl.DefaultMusicService;
import sv.com.udb.youapp.services.impl.DefaultPlayerService;
import sv.com.udb.youapp.ui.SplashActivity;
import sv.com.udb.youapp.ui.client.player.PlayerFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private AuthStateManager authManager;
    private MusicService musicService;
    private MusicAdapter musicAdapter;
    private PlaylistAdapter playlistAdapter;
    private PlayerService playerService;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        authManager = AuthStateManager.getInstance(getContext());
        binding.btnLogout.setOnClickListener(this::onLogout);
        authManager = AuthStateManager.getInstance(getContext());
        musicService = new DefaultMusicService(getContext());
        playerService = DefaultPlayerService.getInstance();
        init();
        playlistAdapter = new PlaylistAdapter(new ArrayList<>(),this::onPlaylistListener);
        musicAdapter = new MusicAdapter(new ArrayList<>(),this::onMusicLister);
        binding.rv1.setAdapter(musicAdapter);
        binding.rv2.setAdapter(playlistAdapter);
        binding.btnCreatePy.setOnClickListener(this::showPlaylistDialog);
        return binding.getRoot();
    }

    private void onPlaylistListener(Playlist p){
        try{
            playerService.add(p.getSongs());
            Toast.makeText(getContext(), p.getTitle() + " songs has been added", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to play this song", Toast.LENGTH_SHORT).show();
        }catch (RuntimeException e){
            Toast.makeText(getContext(), "Playlist is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void onMusicLister(Music m){
        try {
            playerService.add(m);
                Toast.makeText(getContext(), m.getTitle() + " is now on queue", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to play this song", Toast.LENGTH_SHORT).show();
        } catch (SongAlreadyOnQueueException e){
            Toast.makeText(getContext(), m.getTitle() + " is already on queue", Toast.LENGTH_SHORT).show();
        }
    }

     private void showPlaylistDialog(View view){
         final Dialog dialog = new Dialog(getActivity());
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setCancelable(true);
         dialog.setContentView(R.layout.dialog_create_playlist);
         DialogCreatePlaylistBinding binding = DialogCreatePlaylistBinding.inflate(getLayoutInflater());
         dialog.setContentView(binding.getRoot());
         binding.btnAceptar.setOnClickListener(v -> {
             String title = binding.txtTitleModal.getText().toString();
             if(null == title || title.length() < 3){
                 Toast.makeText(getActivity(), "Must be more than 3 characters", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(getActivity(), "Failed to create playlist", Toast.LENGTH_SHORT).show();
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
                 //Toast.makeText(getContext().getApplicationContext(),"Failed to recover songs",Toast.LENGTH_LONG).show();
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
                 //Toast.makeText(getActivity(), "Failed to recover playlist", Toast.LENGTH_SHORT).show();
             }
         });
     }

     private void onLogout(View view){
         authManager.replace(new AuthState());
         Intent intent = new Intent(getActivity(), SplashActivity.class);
         startActivity(intent);
     }
}