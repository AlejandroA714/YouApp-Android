 package sv.com.udb.youapp.ui.client.like;

 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Toast;

 import androidx.fragment.app.Fragment;

 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
 import sv.com.udb.youapp.R;
 import sv.com.udb.youapp.adapter.FavoritesAdapter;
 import sv.com.udb.youapp.databinding.FragmentLikeBinding;
 import sv.com.udb.youapp.dto.Music;
 import sv.com.udb.youapp.exceptions.SongAlreadyOnQueueException;
 import sv.com.udb.youapp.services.MusicService;
 import sv.com.udb.youapp.services.PlayerService;
 import sv.com.udb.youapp.services.impl.DefaultMusicService;
 import sv.com.udb.youapp.services.impl.DefaultPlayerService;

public class LikeFragment extends Fragment {

    private FragmentLikeBinding binding;
    private FavoritesAdapter musicAdapter;
    private PlayerService playerService;
    private MusicService musicService;

     public LikeFragment() {
     }

     @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         inflater.inflate(R.layout.fragment_like, container, false);
         binding = FragmentLikeBinding.inflate(inflater, container, false);
         playerService = DefaultPlayerService.getInstance();
         musicService = new DefaultMusicService(getContext());
         init();
         musicAdapter = new FavoritesAdapter(new ArrayList<>(),this::onMusicLister);
         binding.likeRv1.setAdapter(musicAdapter);
         return binding.getRoot();
     }
     
     private void onMusicLister(Music m){
         try {
             playerService.add(m);
                 Toast.makeText(getContext(), m.getTitle() + " is now on queue", Toast.LENGTH_SHORT).show();
         } catch (IOException e) {
             e.printStackTrace();
             Toast.makeText(getContext(), "Failed to play this song", Toast.LENGTH_SHORT).show();
         } catch (SongAlreadyOnQueueException e){
             Toast.makeText(getContext(), m.getTitle() + " is already on queue", Toast.LENGTH_SHORT).show();
         }
     }


      private void init(){
          musicService.getFavorities().enqueue(new Callback<List<Music>>() {
              @Override
              public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                  if(null != response.body()){
                      List<Music> music = response.body();
                      musicAdapter.update(music);
                  }
              }
              @Override
              public void onFailure(Call<List<Music>> call, Throwable t) {
                  t.printStackTrace();
                  Toast.makeText(getContext(),"Failed to recover songs",Toast.LENGTH_LONG).show();
              }
          });
      }
 }