package sv.com.udb.youapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import sv.com.udb.youapp.interfaces.SpinnerAdapterItem;

public class SpinnerAdapter<T extends SpinnerAdapterItem> extends ArrayAdapter<T> {

    private T[] values;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, T[] values) {
        super(context, resource, values);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(values[position].getViewContent());
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(values[position].getDropDownView());
        return label;
    }

    @Override
    public T getItem(int position) {
        return values[position];
    }
}
