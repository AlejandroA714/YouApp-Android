package sv.com.udb.youapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.List;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.dto.Music;

public class MusicAdapter extends AbstractAdapter<Music, MusicAdapter.MusicViewHolder> {
    public MusicAdapter(List<Music> payload) {
        super(R.layout.item_list, payload);
    }

    public static class MusicViewHolder extends AbstractViewHolder<Music> {

        private final TextView txtTitle;
        private final TextView txtAuthor;
        private final ImageView imageView;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            imageView = itemView.findViewById(R.id.imageView);;
        }

        @Override
        public void bind(Music payload) {
            txtTitle.setText(payload.getTitle());
            txtAuthor.setText(payload.getUser().getNombres());
            Picasso.get().load(payload.getPhoto()).into(imageView);
        }
    }

}
