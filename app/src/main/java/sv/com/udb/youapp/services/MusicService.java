package sv.com.udb.youapp.services;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sv.com.udb.youapp.dto.Music;

public interface MusicService {

    Call<List<Music>> getSongsAsync();

    List<Music> getSongs() throws IOException;

}
