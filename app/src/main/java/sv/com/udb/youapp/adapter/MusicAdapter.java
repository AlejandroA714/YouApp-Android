package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.AdapteMusicAltBinding;
import sv.com.udb.youapp.dto.Music;

public class MusicAdapter extends AbstractAdapter<Music, MusicAdapter.MusicViewHolder> {
    public MusicAdapter(List<Music> payload) {
        super(R.layout.adapte_music_alt, payload);
    }

    public static class MusicViewHolder extends AbstractViewHolder<Music> {

        private AdapteMusicAltBinding binding;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapteMusicAltBinding.bind(itemView);
        }

        @Override
        public void bind(Music payload) {
            binding.txtTitleAlt.setText(payload.getTitle());
            binding.txtAuthorAlt.setText(payload.getUser().getNombres());
            Picasso.get().load(payload.getPhoto()).into(binding.imageViewAlt);
        }
    }

}
