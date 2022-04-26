package sv.com.udb.youapp.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForgotPasswordService {
    @GET("/v1/auth/reset-password/{mail}")
    Call<Void> resetPassword(@Path("mail") String mail);
}
