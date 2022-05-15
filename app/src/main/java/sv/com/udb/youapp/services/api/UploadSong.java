package sv.com.udb.youapp.services.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sv.com.udb.youapp.dto.Song;
import sv.com.udb.youapp.dto.request.Register;

public interface UploadSong {

    @POST("v1/storage/upload")
    @Headers("Content-type: application/json")
    Call<Void> uploadSong(@Body Song body);
}
