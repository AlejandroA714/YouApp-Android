package sv.com.udb.youapp.adapter;

import androidx.annotation.LayoutRes;

import java.util.List;

import sv.com.udb.youapp.dto.Marca;

public class DefaultAdapter extends AbstractAdapter<Marca,MarcaViewHolder> {
    public DefaultAdapter(@LayoutRes int resource, List<Marca> payload) {
        super(resource, payload);
    }
}
