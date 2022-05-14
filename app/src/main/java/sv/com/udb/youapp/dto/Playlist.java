package sv.com.udb.youapp.dto;

import java.util.List;

public class Playlist {
    private int id;
    private String title;
    private YouAppPrincipal user;
    private List<Music> songs;

    public Playlist() {
    }

    public Playlist(int id, String title, YouAppPrincipal user, List<Music> songs) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.songs = songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public YouAppPrincipal getUser() {
        return user;
    }

    public void setUser(YouAppPrincipal user) {
        this.user = user;
    }

    public List<Music> getSongs() {
        return songs;
    }

    public void setSongs(List<Music> songs) {
        this.songs = songs;
    }
}
