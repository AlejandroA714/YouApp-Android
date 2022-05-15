package sv.com.udb.youapp.dto.request;

public class AddToPlaylist {

    private int musicId;
    private int playlistId;

    public AddToPlaylist() {
    }

    public AddToPlaylist(int musicId, int playlistId) {
        this.musicId = musicId;
        this.playlistId = playlistId;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }
}
