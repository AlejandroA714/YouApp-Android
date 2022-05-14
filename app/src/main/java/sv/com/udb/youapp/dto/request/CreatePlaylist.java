package sv.com.udb.youapp.dto.request;

public class CreatePlaylist {

    private String title;

    public CreatePlaylist() {
    }

    public CreatePlaylist(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
