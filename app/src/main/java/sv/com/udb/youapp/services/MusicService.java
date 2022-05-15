package sv.com.udb.youapp.services;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.dto.Playlist;

public interface MusicService {

    Call<List<Music>> getSongs();

    Call<List<Playlist>> getPlaylist();

    Call<Void> like(int id);

    Call<Void> dislike(int id);

    Call<Playlist> createPlaylist(String title);

    Call<List<Music>> getFavorities();

    Call<Void> addToPlaylist(int musicId,int playlistId);
}
