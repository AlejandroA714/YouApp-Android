package sv.com.udb.youapp.services;

import android.content.Context;

import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.sql.Date;
import java.time.LocalDate;
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
                    .baseUrl("http://192.168.101.17:8085/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
