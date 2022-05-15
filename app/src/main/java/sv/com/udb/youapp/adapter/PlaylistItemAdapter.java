package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.AdapterPlaylistItemBinding;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.interfaces.OnItemClickListener;

public class PlaylistItemAdapter extends AbstractAdapter<Playlist, PlaylistItemAdapter.PlaylistViewHolder> {
    public PlaylistItemAdapter(List<Playlist> payload) {
        super(R.layout.adapter_playlist, payload);
    }

    public PlaylistItemAdapter(List<Playlist> payload, OnItemClickListener<Playlist> onItemClickListener){
        super(R.layout.adapter_playlist_item,payload,onItemClickListener);
    }

    public static class PlaylistViewHolder extends AbstractViewHolder<Playlist> {

        private AdapterPlaylistItemBinding binding;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterPlaylistItemBinding.bind(itemView);
        }

        @Override
        public void bind(Playlist payload) {
            binding.pyTitle.setText(payload.getTitle());
            binding.pyAuthor.setText(payload.getUser().getNombres());
        }
    }

}
