package sv.com.udb.youapp.ui.client.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sv.com.udb.youapp.R;


public class PlayListFragment extends DialogFragment {

    public PlayListFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return creatDialogPlayList();
    }

    private AlertDialog creatDialogPlayList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.create();
    }


}