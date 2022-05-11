package sv.com.udb.youapp.services;

import java.util.List;

import sv.com.udb.youapp.dto.Music;

public interface PlayerService {

    Music get();

    void init(List<Music> music);

    void add(Music m);

    void play();

    void pause();

    void stop();

}