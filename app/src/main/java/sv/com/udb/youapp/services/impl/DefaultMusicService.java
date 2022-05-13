package sv.com.udb.youapp.services.impl;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.enums.HttpFactory;
import sv.com.udb.youapp.services.MusicService;
import sv.com.udb.youapp.services.api.MusicApi;
import sv.com.udb.youapp.services.api.RetrofitFactory;

public class DefaultMusicService implements MusicService {

    public static final String AUTH_TYPE = "Bearer ";
    private final AuthStateManager authManager;

    private final MusicApi api;

    public DefaultMusicService(Context context){
        this.authManager = AuthStateManager.getInstance(context);
        this.api = RetrofitFactory.getInstance(HttpFactory.STORAGE,MusicApi.class);
    }

    @Override
    public Call<List<Music>> getSongsAsync() {
        String accessToken = authManager.getAccessToken();
        return api.getSongs(AUTH_TYPE + accessToken);
    }

    @Override
    public List<Music> getSongs() throws IOException {
       return null;
    }
}
