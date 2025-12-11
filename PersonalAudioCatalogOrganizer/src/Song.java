class Song extends AudioItem {
    private String album;

    public Song(String title, String author, String genre,
                int durationSeconds, int yearPublished, String album) {
        super(title, author, genre, durationSeconds, yearPublished, AudioCategory.SONG);
        this.album = album;
    }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    @Override
    public String getDetailedInfo() {
        return String.format(
                "=======================================\n" +
                        "Тип: Песен\n" +
                        "Заглавие: %s\n" +
                        "Изпълнител: %s\n" +
                        "Албум: %s\n" +
                        "Жанр: %s\n" +
                        "Продължителност: %s\n" +
                        "Година: %d\n" +
                        "=======================================",
                getTitle(), getAuthor(), getAlbum(), getGenre(), getDurationFormatted(), getYearPublished()
        );
    }
}
