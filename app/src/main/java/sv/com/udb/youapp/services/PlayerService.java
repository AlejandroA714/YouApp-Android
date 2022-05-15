package sv.com.udb.youapp.services;

import android.media.MediaPlayer;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import sv.com.udb.youapp.dto.Music;
import sv.com.udb.youapp.exceptions.SongAlreadyOnQueueException;
import sv.com.udb.youapp.exceptions.UnableToPlayException;

public interface PlayerService {

    Music get();

    /*
    * @param List<Music> A list with the music from
    * @return Music Song that is prepared
    * @throws IOException if fails to prepare song, url may be incorrect
     */
    Music init(List<Music> music) throws IOException;

    /*
    *  @param Music song that must be added
    */
    void add(Music m) throws IOException, SongAlreadyOnQueueException;

    void add(List<Music> m) throws IOException, SongAlreadyOnQueueException;

    void update(Music m);

    void play();

    void pause();

    void stop();

    /*
    * @return Music song that it actually plays
    * @throws UnableToPlayException if there is no left songs
    * @see UnableToPlayException
     */
    Music backward() throws UnableToPlayException;

    /*
     * @return Music song that it actually plays
     * @throws UnableToPlayException if there is no left songs
     * @see UnableToPlayException
     */
    Music forward() throws UnableToPlayException;

    boolean isPlaying();

    int getDuration();

    int getCurrentPostion();

    void seekTo(int duration);

    void setOnCompleteListener(MediaPlayer.OnCompletionListener listener);

    String secondsToMMSS(int duration);
}