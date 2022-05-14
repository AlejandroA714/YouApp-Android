package sv.com.udb.youapp.services.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sv.com.udb.youapp.dto.request.Register;

public interface RegisterApiService {

    @POST("v1/auth/register")
    @Headers("Content-type: application/json")
    Call<Void> savePost(@Body Register body);
}
