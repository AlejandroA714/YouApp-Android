package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.AdapterMusicBinding;
import sv.com.udb.youapp.dto.Music;

public class PlaylistAdapter extends AbstractAdapter<Music, PlaylistAdapter.MusicViewHolder> {
    public PlaylistAdapter(List<Music> payload) {
        super(R.layout.adapter_music, payload);
    }

    public static class MusicViewHolder extends AbstractViewHolder<Music> {

        private AdapterMusicBinding binding;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterMusicBinding.bind(itemView);
        }

        @Override
        public void bind(Music payload) {
            binding.txtTitle.setText(payload.getTitle());
            binding.txtAuthor.setText(payload.getUser().getNombres());
            //Picasso.get().load(payload.getPhoto()).into(binding.imageViewAlt);
        }
    }

}
