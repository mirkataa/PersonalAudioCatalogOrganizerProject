class Album extends AudioItem {
    private int numberOfTracks;

    public Album(String title, String author, String genre,
                 int durationSeconds, int yearPublished, int numberOfTracks) {
        super(title, author, genre, durationSeconds, yearPublished, AudioCategory.ALBUM);
        this.numberOfTracks = numberOfTracks;
    }

    public int getNumberOfTracks() { return numberOfTracks; }
    public void setNumberOfTracks(int numberOfTracks) { this.numberOfTracks = numberOfTracks; }

    @Override
    public String getDetailedInfo() {
        return String.format(
                "=======================================\n" +
                        "Тип: Албум\n" +
                        "Заглавие: %s\n" +
                        "Изпълнител: %s\n" +
                        "Брой песни: %d\n" +
                        "Жанр: %s\n" +
                        "Обща продължителност: %s\n" +
                        "Година: %d\n" +
                        "=======================================",
                getTitle(), getAuthor(), getNumberOfTracks(), getGenre(), getDurationFormatted(), getYearPublished()
        );
    }
}