package sv.com.udb.youapp.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.dto.Marca;

public class MarcaViewHolder extends AbstractViewHolder<Marca> {

    private final TextView textView;

    public MarcaViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
    }

    @Override
    public void bind(Marca payload) {
        textView.setText(payload.getNombre());
    }
}

