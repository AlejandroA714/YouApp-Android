package sv.com.udb.youapp.ui.client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityLoginBinding;
import sv.com.udb.youapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                String image = Base64.getEncoder().encodeToString(bb);
                System.out.println(image);
            }
        }
    }
}