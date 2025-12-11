import java.io.Serializable;

public abstract class AudioItem implements Serializable {

    protected String title;
    protected String author;
    protected String genre;
    protected int durationSeconds;
    protected int yearPublished;
    protected AudioCategory category;

    public enum AudioCategory {
        SONG, ALBUM, PODCAST, AUDIOBOOK
    }

    public AudioItem(String title, String author, String genre,
                     int durationSeconds, int yearPublished, AudioCategory category) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
        this.yearPublished = yearPublished;
        this.category = category;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getDurationSeconds() { return durationSeconds; }
    public int getYearPublished() { return yearPublished; }
    public AudioCategory getCategory() { return category; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDurationSeconds(int durationSeconds) { this.durationSeconds = durationSeconds; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }

    public String getDurationFormatted() {
        int hours = getDurationSeconds() / 3600;
        int minutes = (getDurationSeconds() % 3600) / 60;
        int seconds = getDurationSeconds() % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s, %d) [%s]",
                getCategory(), getTitle(), getAuthor(), getGenre(), getYearPublished(), getDurationFormatted());
    }

    public abstract String getDetailedInfo();
}