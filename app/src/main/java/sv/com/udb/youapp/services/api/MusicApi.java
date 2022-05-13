package sv.com.udb.youapp.services.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.dto.request.CreatePlaylist;

public interface MusicApi {

    String AUTHORIZATION = "Authorization";
    String ID = "id";

    @GET("v1/storage/music/")
    Call<List<Music>> getSongs(@Header(AUTHORIZATION) String authHeader);

    @GET("v1/storage/music/playlist")
    Call<List<Playlist>> getPlaylist(@Header(AUTHORIZATION) String authHeader);

    @GET("v1/storage/music/like/{id}")
    Call<Void> like(@Header(AUTHORIZATION) String authHeader, @Path(ID) int id);

    @GET("v1/storage/music/dislike/{id}")
    Call<Void> dislike(@Header(AUTHORIZATION) String authHeader, @Path(ID) int id);

    @POST("v1/storage/music/playlist/add")
    @Headers("Content-type: application/json")
    Call<Playlist> createPlaylist(@Header(AUTHORIZATION) String authHeader,@Body CreatePlaylist body);

}
