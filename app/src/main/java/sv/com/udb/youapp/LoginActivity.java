package sv.com.udb.youapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    //Hooks
    ImageView logo;
    TextView title1;
    Button btnAccder, btnReg, btnOlvi;

    //Animation
    Animation topAnimation, middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        logo = findViewById(R.id.logo);
        title1 = findViewById(R.id.title1);
        btnAccder = findViewById(R.id.btnAccder);
        btnOlvi = findViewById(R.id.btnOlvide);
        btnReg = findViewById(R.id.btnRegister);

        logo.setAnimation(topAnimation);
        title1.setAnimation(middleAnimation);
        btnAccder.setAnimation(middleAnimation);
        btnOlvi.setAnimation(middleAnimation);
        btnReg.setAnimation(middleAnimation);

    }
}