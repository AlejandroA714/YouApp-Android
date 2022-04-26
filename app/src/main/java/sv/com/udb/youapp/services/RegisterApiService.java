package sv.com.udb.youapp.services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sv.com.udb.youapp.dto.Register;

public interface RegisterApiService {

    @Headers("Content-type: application/json")
    @POST("v1/auth/register")
    Call<String> savePost(@Body Register body);
//    Call<String> savePost(@Field("nombres") String nombres,
//                          @Field("apellidos") String apellidos,
//                          @Field("email") String email,
//                          @Field("username") String username,
//                          @Field("password") String password,
//                          @Field("birthday") String birthday,
//                          @Field("photo") String photo
//    );

}
