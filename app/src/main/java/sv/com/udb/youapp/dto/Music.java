package sv.com.udb.youapp.dto;

import java.util.Objects;

public class Music {
    private Integer              id;
    private String               title;
    private int                  duration;
    private String               uri;
    private String               photo;
    private Status               status;
    private Genre                genre;
    private YouAppPrincipal      user;
    private boolean              likes;

    public Music(Integer id, String title, int duration, String uri, String photo, Status status, Genre genre, YouAppPrincipal user, boolean likes) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.uri = uri;
        this.photo = photo;
        this.status = status;
        this.genre = genre;
        this.user = user;
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public YouAppPrincipal getUser() {
        return user;
    }

    public void setUser(YouAppPrincipal user) {
        this.user = user;
    }

    public boolean likes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Music music = (Music) o;
        return duration == music.duration && id.equals(music.id) && title.equals(music.title) && Objects.equals(uri, music.uri) && Objects.equals(status, music.status) && Objects.equals(genre, music.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, uri, status, genre);
    }
}
