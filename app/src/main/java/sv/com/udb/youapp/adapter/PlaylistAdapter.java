package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.AdapterPlaylistBinding;
import sv.com.udb.youapp.dto.Playlist;

public class PlaylistAdapter extends AbstractAdapter<Playlist, PlaylistAdapter.PlaylistViewHolder> {
    public PlaylistAdapter(List<Playlist> payload) {
        super(R.layout.adapter_playlist, payload);
    }

    public static class PlaylistViewHolder extends AbstractViewHolder<Playlist> {

        private AdapterPlaylistBinding binding;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterPlaylistBinding.bind(itemView);
        }

        @Override
        public void bind(Playlist payload) {
            binding.txtTitleAlt.setText(payload.getTitle());
            binding.txtAuthorAlt.setText(payload.getUser().getNombres());
            Picasso.get().load(R.drawable.ellipse).into(binding.imageViewAlt);
        }
    }

}
