class Podcast extends AudioItem {
    private int episodeNumber;
    private String host;

    public Podcast(String title, String author, String genre,
                   int durationSeconds, int yearPublished, int episodeNumber, String host) {
        super(title, author, genre, durationSeconds, yearPublished, AudioCategory.PODCAST);
        this.episodeNumber = episodeNumber;
        this.host = host;
    }

    public int getEpisodeNumber() { return episodeNumber; }
    public String getHost() { return host; }

    @Override
    public String getDetailedInfo() {
        return String.format(
                "=======================================\n" +
                        "Тип: Podcast\n" +
                        "Заглавие: %s\n" +
                        "Епизод: %d\n" +
                        "Водещ: %s\n" +
                        "Автор: %s\n" +
                        "Жанр: %s\n" +
                        "Продължителност: %s\n" +
                        "Година: %d\n" +
                        "=======================================",
                getTitle(), getEpisodeNumber(), getHost(), getAuthor(), getGenre(), getDurationFormatted(), getYearPublished()
        );
    }
}