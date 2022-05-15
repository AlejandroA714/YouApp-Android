package sv.com.udb.youapp.ui.client.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import sv.com.udb.youapp.R;
import sv.com.udb.youapp.databinding.ActivityHomeBinding;
import sv.com.udb.youapp.ui.client.HomeFragment;
import sv.com.udb.youapp.ui.client.upload.UploadFragment;
import sv.com.udb.youapp.ui.client.like.LikeFragment;
import sv.com.udb.youapp.ui.client.player.PlayerFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new HomeFragment()).commit();
        binding.bottomNavigation.setSelectedItemId(R.id.nav_home);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_player:
                    fragment = new PlayerFragment();
                    break;
                case R.id.nav_library:
                    fragment = new LikeFragment();
                    break;
                default:
                    fragment = new UploadFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            return true;
        });
    }
}