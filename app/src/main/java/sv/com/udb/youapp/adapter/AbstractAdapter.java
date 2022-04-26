package sv.com.udb.youapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import sv.com.udb.youapp.interfaces.OnItemClickListener;
import sv.com.udb.youapp.interfaces.OnLongItemClickListener;

abstract class AbstractAdapter<T, K extends AbstractViewHolder> extends RecyclerView.Adapter<K> {

    @LayoutRes
    private int layoutResource;
    private List<T> values;
    private Class<K> genericType;
    private final OnItemClickListener<T> onItemClickListener;
    private final OnLongItemClickListener<T> onLongItemClickListener;

    public AbstractAdapter(@LayoutRes int resource, List<T> payload){
        this(resource, payload,null,null);
    }

    public AbstractAdapter(@LayoutRes int resource,List<T> payload, OnItemClickListener<T> onItemClickListener){
        this(resource, payload,onItemClickListener,null);
    }

    public AbstractAdapter(@LayoutRes int resource, List<T> payload, OnItemClickListener<T> onItemClickListener,
                           OnLongItemClickListener<T> onLongItemClickListener){
        this.genericType = (Class<K>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        this.layoutResource = resource;
        this.values = payload;
        this.onItemClickListener = onItemClickListener;
        this.onLongItemClickListener = onLongItemClickListener;
    }

    @NonNull
    @Override
    public K onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutResource,parent,false);
        try {
            K holder = genericType.getDeclaredConstructor(View.class).newInstance(itemView);
            return holder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create ViewHolder");
        }
    }

    public void update(List<T> payload){
        this.values = payload;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull K holder, int position) {
        holder.bindListener(values.get(position),onItemClickListener,
                onLongItemClickListener);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
