package sv.com.udb.youapp.services;

import androidx.annotation.AnyThread;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static final AtomicReference<WeakReference<Retrofit>> INSTANCE_REF =
            new AtomicReference<>(new WeakReference<>(null));

    @AnyThread
    public static Retrofit getInstance(){
        Retrofit retrofit = INSTANCE_REF.get().get();
        if(retrofit == null){

           retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.24:8083/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
