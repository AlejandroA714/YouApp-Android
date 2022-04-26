package sv.com.udb.youapp.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import sv.com.udb.youapp.dto.Music;

public interface MusicApiService {

    @GET("v1/storage/music/")
    Call<List<Music>> getSongs(@Header("Authorization") String authHeader);

}
