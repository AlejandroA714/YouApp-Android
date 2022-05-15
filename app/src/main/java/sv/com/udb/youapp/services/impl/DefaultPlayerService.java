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
   private MediaPlayer.OnCompletionListener onCompletionListener;

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
       if(isPlaying()){
           return PLAYLIST_REF.get().get(INDEX_REF.get());
       }else {
           return null;
       }
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
    public void update(Music m) {
       List<Music> music = PLAYLIST_REF.get();
       int index = music.indexOf(m); // works because equals and hashcode no includes likes field
       music.set(index,m);
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
    public void setOnCompleteListener(MediaPlayer.OnCompletionListener listener) {
        this.onCompletionListener = listener;
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
        if(null != onCompletionListener){
            player.setOnCompletionListener(this.onCompletionListener);
        }
        player.setOnPreparedListener(this::onPreparedListener);
        return music;
    }

    public String secondsToMMSS(int duration){
        final int m = (int) Math.floor((duration % 3600) / 60);
        final int s = (int) Math.floor((duration % 3600) % 60);
        final String mins = m > 0 ? (m < 10 ? "0" + m + ":" : m + ":") : "00:";
        final String secnds = s > 0 ? (s < 10 ? "0" + s : String.valueOf(s)) : "00";
        return mins+secnds;
    }


    private void onPreparedListener(MediaPlayer player){
        player.start();
    }

}
