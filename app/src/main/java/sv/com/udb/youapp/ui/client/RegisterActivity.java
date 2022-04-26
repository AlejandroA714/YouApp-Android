package sv.com.udb.youapp.ui.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivityRegisterBinding;
import sv.com.udb.youapp.services.RegisterApiService;
import sv.com.udb.youapp.services.RetrofitFactory;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    String ppBase64 = "";
    private RegisterApiService service;
    private AuthStateManager authManager;

    JSONObject request = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = RetrofitFactory.getInstance().create(RegisterApiService.class);
        authManager = AuthStateManager.getInstance(getApplicationContext());
    }

    public void oncick(View view) {
        onLoadImage();
    }

    private void onLoadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            System.err.println(uri);
            binding.imgPP.setImageURI(uri);
            binding.imgPP.buildDrawingCache();
            BitmapDrawable drawable = (BitmapDrawable) binding.imgPP.getDrawable();
            Bitmap bmap = drawable.getBitmap();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            byte[] bb = bos.toByteArray();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                ppBase64 = Base64.getEncoder().encodeToString(bb);
            }
        }
    }

    public void registerUser(View view) throws Exception{

        if (binding.name.getText().toString().isEmpty() ||
                binding.lastname.getText().toString().isEmpty() ||
                binding.email.getText().toString().isEmpty() ||
                binding.username.getText().toString().isEmpty() ||
                binding.pass.getText().toString().isEmpty() ||
                binding.passConfirm.getText().toString().isEmpty() ||
                ppBase64.isEmpty()
        ) {
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
        } else if (binding.pass.getText().toString().equals(binding.passConfirm.getText().toString())){
            request.put("nombres", binding.name.getText().toString());
            request.put("apellidos", binding.lastname.getText().toString());
            request.put("email", binding.email.getText().toString());
            request.put("username", binding.username.getText().toString());
            request.put("password", binding.pass.getText().toString());
            request.put("birthday", binding.birthday.getText().toString());
            request.put("photo", ppBase64);
            System.out.println(request);

//            final Call<String> call = service.savePost(
//                    binding.name.getText().toString(),
//                    binding.lastname.getText().toString(),
//                    binding.email.getText().toString(),
//                    binding.username.getText().toString(),
//                    binding.pass.getText().toString(),
//                    binding.birthday.getText().toString(),
//                    ppBase64
//                    );

            final Call<String> call = service.savePost(request.toString());

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(null != response.body()){
                        String music = response.body();
                        Log.d("Music Service","Sucess?");
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

    }

}