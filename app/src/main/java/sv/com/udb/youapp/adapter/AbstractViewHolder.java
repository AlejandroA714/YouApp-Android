package sv.com.udb.youapp.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.com.udb.youapp.interfaces.OnItemClickListener;
import sv.com.udb.youapp.interfaces.OnLongItemClickListener;

abstract class AbstractViewHolder<T> extends RecyclerView.ViewHolder {

    public AbstractViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T payload);

    public void bindListener(T payload,OnItemClickListener<T> onItemClickListener,
                              OnLongItemClickListener<T> onLongItemClickListener){
        bind(payload);
        if(null != onItemClickListener){
            itemView.setOnClickListener((View v) -> onItemClickListener.onClick(payload));
        }
        if(null != onLongItemClickListener) {
            itemView.setOnLongClickListener((View v) -> {
                onLongItemClickListener.onLongClick(payload);
                return false;
            });
        }
    }

}
