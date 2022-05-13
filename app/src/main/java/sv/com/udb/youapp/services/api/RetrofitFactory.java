package sv.com.udb.youapp.services.api;

import androidx.annotation.AnyThread;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.com.udb.youapp.enums.HttpFactory;

public class RetrofitFactory {

    @AnyThread
    public static <T> T getInstance(HttpFactory httpFactory,Class<T> api){
        return new Retrofit.Builder()
                    .baseUrl(httpFactory.getHost())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(api);
    }
}
