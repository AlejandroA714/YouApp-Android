package sv.com.udb.youapp.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.PlaylistItemAdapter;
import sv.com.udb.youapp.databinding.DialogSelectPlaylistBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.impl.DefaultMusicService;

public class PlaylistItemDialog extends DialogFragment {

    private PlaylistItemAdapter adapter;
    private DialogSelectPlaylistBinding binding;
    private MusicService musicService;
    private Music m;

    public PlaylistItemDialog(@NonNull Music music){
        this.m = music;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflater.inflate(R.layout.dialog_select_playlist,container,false);
        binding = DialogSelectPlaylistBinding .inflate(inflater,container,false);
        adapter = new PlaylistItemAdapter(new ArrayList<>(),this::addToPlaylist);
        binding.pysRv.setAdapter(adapter);
        musicService = new DefaultMusicService(getContext());
        init();
        return binding.getRoot();
    }

    private void addToPlaylist(Playlist p){
        musicService.addToPlaylist(m.getId(),p.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Added to playlist", Toast.LENGTH_SHORT).show();
                dismiss();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to add to playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        musicService.getPlaylist().enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                if(null != response.body()){
                    adapter.update(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to recover playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
