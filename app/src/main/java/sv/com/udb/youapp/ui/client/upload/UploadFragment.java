package sv.com.udb.youapp.ui.client.upload;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.FragmentUploadBinding;
import sv.com.udb.youapp.dto.Song;
import sv.com.udb.youapp.dto.request.Register;
import sv.com.udb.youapp.enums.HttpFactory;
import sv.com.udb.youapp.services.api.RegisterApiService;
import sv.com.udb.youapp.services.api.RetrofitFactory;
import sv.com.udb.youapp.services.api.UploadSong;


public class UploadFragment extends AppCompatDialogFragment {

    private AuthStateManager authManager;
    private FragmentUploadBinding binding;
    private UploadSong service;
    Song request = new Song();

    public UploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = RetrofitFactory.getInstance(HttpFactory.STORAGE,UploadSong.class);
        inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        authManager = AuthStateManager.getInstance(getContext());
        authManager = AuthStateManager.getInstance(getContext());
        binding.song.setOnClickListener((View v) -> {
            Intent intent = new Intent();
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Seleccionar cancion"), 0);
        });

        binding.btnSubir.setOnClickListener(this::uploadSong);

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            File song = new File(data.getDataString());
            request.setFile(song);
            System.err.println(data.getDataString());
        }
    }

    private void uploadSong(View view) {
        if (binding.name.getText().toString().isEmpty() ||
                binding.genre.getText().toString().isEmpty()
        ) {
            Toast.makeText(getContext().getApplicationContext(), "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        } else {

            request.setTitle("Titulo");
            request.setGenreId("1");

            final Call<Void> call = service.uploadSong(request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("Music Service", "Sucess?");
                    Toast.makeText(getContext().getApplicationContext(), "Registro finalizado correctamente", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}