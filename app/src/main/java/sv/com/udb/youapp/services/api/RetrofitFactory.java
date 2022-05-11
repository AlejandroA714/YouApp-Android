package sv.com.udb.youapp.services.api;

import androidx.annotation.AnyThread;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    @AnyThread
    public static Retrofit getInstance(String baseUrl){
        return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }
}
