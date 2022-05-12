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
import java.util.function.Consumer;
import java.util.function.Function;

import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.exceptions.UnableToPlayException;
import sv.com.udb.youapp.services.PlayerService;

public class DefaultPlayerService implements PlayerService {

    private static final AtomicReference<WeakReference<DefaultPlayerService>> INSTANCE_REF =
            new AtomicReference<>(new WeakReference<>(null));
   private final AtomicReference<MediaPlayer> PLAYER_REF = new AtomicReference<>(null);
   private final AtomicReference<List<Music>> PLAYLIST_REF = new AtomicReference<>(new ArrayList<>());
   private final AtomicReference<Integer> INDEX_REF = new AtomicReference<>(0);

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
        return PLAYLIST_REF.get().get(INDEX_REF.get());
    }

    @Override
    public Music init(@NonNull List<Music> music) throws IOException {
        try {
            PLAYLIST_REF.set(music);
            return _prepare(music.get(0));
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void add(Music music){
        this.PLAYLIST_REF.get().add(music);
    }

    @Override
    public void play() {
        PLAYER_REF.get().start();
    }

    @Override
    public void pause() {
        try{
            PLAYER_REF.get().pause();
        }catch (IllegalStateException e){

        }
    }

    @Override
    public void stop() {
        PLAYER_REF.get().release();
        PLAYER_REF.set(null);
    }

    @Override
    public boolean isPlaying() {
        try{
            return PLAYER_REF.get().isPlaying();
        }catch (IllegalStateException e){
            return false;
        }
    }

    @Override
    public int getDuration() {
        return PLAYER_REF.get().getDuration() / 1000;
    }

    @Override
    public int getCurrentPostion() {
        return PLAYER_REF.get().getCurrentPosition();
    }

    @Override
    public void seekTo(int duration) {
        PLAYER_REF.get().seekTo(duration);
    }

    @Override
    public Music backward() throws UnableToPlayException{
        return _navigate(INDEX_REF.get() - 1);
    }

    @Override
    public Music forward() throws UnableToPlayException{
        return _navigate(INDEX_REF.get() + 1);
    }

    private Music _navigate(int index) throws UnableToPlayException {
        try{
            Music next = PLAYLIST_REF.get().get(index);
            _prepare(next);
            INDEX_REF.set(index);
            return next;
        }catch (IndexOutOfBoundsException | IllegalStateException | IOException ex){
            ex.printStackTrace();
            throw new UnableToPlayException("There is no songs in this direction",ex);
        }
    }

    private Music _prepare(Music music) throws IOException {
        MediaPlayer player = PLAYER_REF.get();
        player.reset();
        player.setDataSource(music.getUri());
        player.prepare();
        player.setOnCompletionListener(this::onCompleteListener);
        player.setOnPreparedListener(this::onPreparedListener);
        return music;
    }

    private void onPreparedListener(MediaPlayer player){
        player.start();
    }

    private void onCompleteListener(MediaPlayer player){
        forward();
    }





}
