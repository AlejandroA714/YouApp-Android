 package sv.com.udb.youapp.ui.client;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.openid.appauth.AuthState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.com.udb.youapp.R;
import sv.com.udb.youapp.adapter.MusicAdapter;
import sv.com.udb.youapp.adapter.PlaylistAdapter;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.databinding.ActivityHomeBinding;
import sv.com.udb.youapp.databinding.FragmentHomeBinding;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.enums.HttpFactory;
import sv.com.udb.youapp.services.api.MusicApi;
import sv.com.udb.youapp.services.api.RetrofitFactory;
import sv.com.udb.youapp.ui.SplashActivity;
import sv.com.udb.youapp.ui.client.home.HomeActivity;

 /**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

     private FragmentHomeBinding binding;
     private AuthStateManager authManager;
     private MusicApi musicApiService;
     private MusicAdapter musicAdapter;
     private PlaylistAdapter playlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        authManager = AuthStateManager.getInstance(getContext());
        binding.btnLogout.setOnClickListener(this::onLogout);
        authManager = AuthStateManager.getInstance(getContext());
        musicApiService = RetrofitFactory.getInstance(HttpFactory.STORAGE,MusicApi.class);
        init();
        playlistAdapter = new PlaylistAdapter(new ArrayList<>());
        musicAdapter = new MusicAdapter(new ArrayList<>());
        binding.rv1.setAdapter(playlistAdapter);
        binding.rv2.setAdapter(musicAdapter);

        return binding.getRoot();
    }

     private void init(){
         final Call<List<Music>> call = musicApiService.getSongs("Bearer " + authManager.getCurrent().getAccessToken());
         call.enqueue(new Callback<List<Music>>() {
             @Override
             public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                 if(null != response.body()){
                     List<Music> music = response.body();
                     musicAdapter.update(music);
                     playlistAdapter.update(music);
                     Log.d("Music Service","Sucess?");
                 }
             }
             @Override
             public void onFailure(Call<List<Music>> call, Throwable t) {
                 t.printStackTrace();
                 Toast.makeText(getContext(),"Failed",Toast.LENGTH_LONG).show();
             }
         });
     }

     private void onLogout(View view){
         authManager.replace(new AuthState());
         Intent intent = new Intent(getActivity(), SplashActivity.class);
         startActivity(intent);
     }
}