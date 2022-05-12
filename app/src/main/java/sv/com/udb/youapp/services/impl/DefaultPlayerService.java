package sv.com.udb.youapp.services.impl;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import androidx.annotation.AnyThread;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.services.PlayerService;

public class DefaultPlayerService implements PlayerService {

    private static final AtomicReference<WeakReference<DefaultPlayerService>> INSTANCE_REF =
            new AtomicReference<>(new WeakReference<>(null));
   private final AtomicReference<MediaPlayer> PLAYER_REF = new AtomicReference<>(null);
   private final AtomicReference<List<Music>> MUSIC_REF = new AtomicReference<>(new ArrayList<>());

    @AnyThread
    public static PlayerService getInstance(){
        DefaultPlayerService instance = INSTANCE_REF.get().get();
        if(instance == null){
            instance = new DefaultPlayerService(new MediaPlayer());
            INSTANCE_REF.set(new WeakReference<>(instance));
        }
        return instance;
    }

    private DefaultPlayerService(MediaPlayer player){
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.PLAYER_REF.set(player);
    }

    @Override
    public Music get() {
        return null;
    }

    @Override
    public void init(@NonNull List<Music> music) {
        try {
            MUSIC_REF.set(music);
            MediaPlayer player = PLAYER_REF.get();
            player.setDataSource(music.get(0).getUri());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Music music){
        this.MUSIC_REF.get().add(music);
    }


    @Override
    public void play() {
        PLAYER_REF.get().start();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }


}
