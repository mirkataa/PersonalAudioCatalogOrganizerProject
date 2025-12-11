class Audiobook extends AudioItem {
    private String narrator;
    private int numberOfChapters;

    public Audiobook(String title, String author, String genre,
                     int durationSeconds, int yearPublished, String narrator, int numberOfChapters) {
        super(title, author, genre, durationSeconds, yearPublished, AudioCategory.AUDIOBOOK);
        this.narrator = narrator;
        this.numberOfChapters = numberOfChapters;
    }

    public String getNarrator() { return narrator; }
    public int getNumberOfChapters() { return numberOfChapters; }

    @Override
    public String getDetailedInfo() {
        return String.format(
                "=======================================\n" +
                        "Тип: Аудиокнига\n" +
                        "Заглавие: %s\n" +
                        "Автор: %s\n" +
                        "Разказвач: %s\n" +
                        "Брой глави: %d\n" +
                        "Жанр: %s\n" +
                        "Обща продължителност: %s\n" +
                        "Година: %d\n" +
                        "=======================================",
                getTitle(), getAuthor(), getNarrator(), getNumberOfChapters(), getGenre(), getDurationFormatted(), getYearPublished()
        );
    }
}