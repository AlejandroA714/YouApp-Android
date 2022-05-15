package sv.com.udb.youapp.dto;

import java.io.File;

public class Song {

    private File file;
    private String title;
    private String genreId;

    public Song() {
    }

    public Song(File file, String title, String genreId) {
        this.file = file;
        this.title = title;
        this.genreId = genreId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return "Song{" +
                "file=" + file +
                ", title='" + title + '\'' +
                ", genreId='" + genreId + '\'' +
                '}';
    }
}
