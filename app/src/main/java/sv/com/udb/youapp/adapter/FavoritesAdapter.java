package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.AdapterFavoritesBinding;
import sv.com.udb.youapp.databinding.AdapterMusicBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.interfaces.OnItemClickListener;

public class FavoritesAdapter extends AbstractAdapter<Music, FavoritesAdapter.MusicViewHolder> {
    public FavoritesAdapter(List<Music> payload) {
        super(R.layout.adapter_favorites, payload);
    }

    public FavoritesAdapter(List<Music> payload, OnItemClickListener<Music> onItemClickListener){
        super(R.layout.adapter_favorites,payload,onItemClickListener);
    }

    public static class MusicViewHolder extends AbstractViewHolder<Music> {

        private AdapterFavoritesBinding binding;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AdapterFavoritesBinding.bind(itemView);
        }

        @Override
        public void bind(Music payload) {
            binding.favTxtTitle.setText(payload.getTitle());
            binding.favTxtAuthor.setText(payload.getUser().getNombres());
            Picasso.get().load(payload.getPhoto()).into(binding.favImageView);
        }
    }

}
