package sv.com.udb.youapp.services.impl;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import sv.com.udb.youapp.auth.AuthStateManager;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;
import sv.com.udb.youapp.dto.request.CreatePlaylist;
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
    public Call<List<Music>> getSongs() {
        return api.getSongs(getAuthorizationHeader());
    }

    @Override
    public Call<List<Playlist>> getPlaylist() {
        return api.getPlaylist(getAuthorizationHeader());
    }

    @Override
    public Call<Void> like(int id) {
        return api.like(getAuthorizationHeader(),id);
    }

    @Override
    public Call<Void> dislike(int id) {
        return api.dislike(getAuthorizationHeader(),id);
    }

    @Override
    public Call<Playlist> createPlaylist(String title) {
        return api.createPlaylist(getAuthorizationHeader(),new CreatePlaylist(title));
    }

    private String getAuthorizationHeader(){
        return AUTH_TYPE + authManager.getAccessToken();
    }
}
