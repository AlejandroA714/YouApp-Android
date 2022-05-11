package sv.com.udb.youapp.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sv.com.udb.youapp.dto.Register;

public interface RegisterApiService {

    @Headers("Content-type: application/json")
    @POST("v1/auth/register")
    Call<Void> savePost(@Body Register body);
}
