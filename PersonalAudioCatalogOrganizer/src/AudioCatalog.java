import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AudioCatalog {
    private List<AudioItem> catalog;
    private List<Playlist> playlists;
    private static final String CATALOG_FILE = "audio_catalog.dat";
    private static final String PLAYLISTS_DIR = "playlists/";

    public AudioCatalog() {
        this.catalog = new ArrayList<>();
        this.playlists = new ArrayList<>();

        File dir = new File(PLAYLISTS_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    // управление на обк

    /**
     * Добавя нов аудио обект в каталога
     */
    public boolean addItem(AudioItem item) {
        if (item == null) {
            return false;
        }
        catalog.add(item);
        return true;
    }

    /**
     * Изтрива обект от каталога по индекс
     */
    public boolean removeItem(int index) {
        if (index >= 0 && index < catalog.size()) {
            catalog.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Изтрива обект от каталога
     */
    public boolean removeItem(AudioItem item) {
        return catalog.remove(item);
    }

    /**
     * Връща всички обекти в каталога
     */
    public List<AudioItem> getAllItems() {
        return new ArrayList<>(catalog);
    }

    /**
     * Връща броя обекти в каталога
     */
    public int getCatalogSize() {
        return catalog.size();
    }

    // търсене

    /**
     * Търси обекти по заглавие
     */
    public List<AudioItem> searchByTitle(String title) {
        String searchTerm = title.toLowerCase();
        return catalog.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Търси обекти по автор/изпълнител
     */
    public List<AudioItem> searchByAuthor(String author) {
        String searchTerm = author.toLowerCase();
        return catalog.stream()
                .filter(item -> item.getAuthor().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Търси обекти по жанр
     */
    public List<AudioItem> searchByGenre(String genre) {
        String searchTerm = genre.toLowerCase();
        return catalog.stream()
                .filter(item -> item.getGenre().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Търси обекти по категория
     */
    public List<AudioItem> searchByCategory(AudioItem.AudioCategory category) {
        return catalog.stream()
                .filter(item -> item.getCategory() == category)
                .collect(Collectors.toList());
    }

    /**
     * Универсално търсене (търси в заглавие, автор и жанр)
     */
    public List<AudioItem> universalSearch(String searchTerm) {
        String term = searchTerm.toLowerCase();
        return catalog.stream()
                .filter(item ->
                        item.getTitle().toLowerCase().contains(term) ||
                                item.getAuthor().toLowerCase().contains(term) ||
                                item.getGenre().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    // филтриране

    /**
     * Филтрира по жанр
     */
    public List<AudioItem> filterByGenre(String genre) {
        return catalog.stream()
                .filter(item -> item.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    /**
     * Филтрира по автор
     */
    public List<AudioItem> filterByAuthor(String author) {
        return catalog.stream()
                .filter(item -> item.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    /**
     * Филтрира по година
     */
    public List<AudioItem> filterByYear(int year) {
        return catalog.stream()
                .filter(item -> item.getYearPublished() == year)
                .collect(Collectors.toList());
    }

    /**
     * Филтрира по период от години
     */
    public List<AudioItem> filterByYearRange(int startYear, int endYear) {
        return catalog.stream()
                .filter(item -> item.getYearPublished() >= startYear &&
                        item.getYearPublished() <= endYear)
                .collect(Collectors.toList());
    }

    // сортирания

    /**
     * Сортира по заглавие (азбучен ред)
     */
    public List<AudioItem> sortByTitle() {
        return catalog.stream()
                .sorted(Comparator.comparing(AudioItem::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Сортира по автор
     */
    public List<AudioItem> sortByAuthor() {
        return catalog.stream()
                .sorted(Comparator.comparing(AudioItem::getAuthor))
                .collect(Collectors.toList());
    }

    /**
     * Сортира по година (най-нови първи)
     */
    public List<AudioItem> sortByYearDesc() {
        return catalog.stream()
                .sorted(Comparator.comparingInt(AudioItem::getYearPublished).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Сортира по продължителност
     */
    public List<AudioItem> sortByDuration() {
        return catalog.stream()
                .sorted(Comparator.comparingInt(AudioItem::getDurationSeconds))
                .collect(Collectors.toList());
    }

    /**
     * Сортира обектите групирани по плейлист (по азбучен ред на плейлистите)
     * и накрая показва обектите, които не са в нито един плейлист
     */
    public List<AudioItem> sortByPlaylistGroups() {
        List<AudioItem> result = new ArrayList<>();
        Set<AudioItem> addedItems = new HashSet<>();

        // сорт по име
        List<Playlist> sortedPlaylists = playlists.stream()
                .sorted(Comparator.comparing(Playlist::getName))
                .collect(Collectors.toList());

        for (Playlist playlist : sortedPlaylists) {
            for (AudioItem item : playlist.getItems()) {
                if (!addedItems.contains(item)) {
                    result.add(item);
                    addedItems.add(item);
                }
            }
        }

        for (AudioItem item : catalog) {
            if (!addedItems.contains(item)) {
                result.add(item);
            }
        }

        return result;
    }

    // круд на плейлист

    /**
     * Създава нов плейлист
     */
    public Playlist createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Създава нов плейлист с описание
     */
    public Playlist createPlaylist(String name, String description) {
        Playlist playlist = new Playlist(name, description);
        playlists.add(playlist);
        return playlist;
    }

    /**
     * Изтрива плейлист
     */
    public boolean removePlaylist(Playlist playlist) {
        return playlists.remove(playlist);
    }

    /**
     * Връща всички плейлисти
     */
    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlists);
    }

    /**
     * Намира плейлист по име
     */
    public Playlist findPlaylistByName(String name) {
        return playlists.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Връща броя на плейлистите
     */
    public int getPlaylistsCount() {
        return playlists.size();
    }

    // фнк за файловете

    /**
     * Записва целия каталог във файл
     */
    public boolean saveCatalog() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(CATALOG_FILE))) {
            oos.writeObject(catalog);
            oos.writeObject(playlists);
            return true;
        } catch (IOException e) {
            System.err.println("Грешка при записване на каталога: " + e.getMessage());
            return false;
        }
    }

    /**
     * Зарежда каталога от файл
     */
    @SuppressWarnings("unchecked")
    public boolean loadCatalog() {
        File file = new File(CATALOG_FILE);
        if (!file.exists()) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(CATALOG_FILE))) {
            catalog = (List<AudioItem>) ois.readObject();
            playlists = (List<Playlist>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при зареждане на каталога: " + e.getMessage());
            return false;
        }
    }

    /**
     * Записва отделен плейлист във файл
     */
    public boolean savePlaylist(Playlist playlist) {
        if (playlist == null) {
            return false;
        }

        String filename = PLAYLISTS_DIR + sanitizeFilename(playlist.getName()) + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(playlist);
            return true;
        } catch (IOException e) {
            System.err.println("Грешка при записване на плейлист: " + e.getMessage());
            return false;
        }
    }

    /**
     * Зарежда плейлист от файл
     */
    public Playlist loadPlaylist(String filename) {
        String fullPath = PLAYLISTS_DIR + filename;
        if (!fullPath.endsWith(".dat")) {
            fullPath += ".dat";
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fullPath))) {
            Playlist playlist = (Playlist) ois.readObject();

            if (!playlists.contains(playlist)) {
                playlists.add(playlist);
            }

            return playlist;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при зареждане на плейлист: " + e.getMessage());
            return null;
        }
    }

    /**
     * Почиства името на файл от недопустими символи
     */
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    // статс

    /**
     * Връща статистика за каталога
     */
    public String getCatalogStatistics() {
        if (catalog.isEmpty()) {
            return "Каталогът е празен.";
        }

        Map<AudioItem.AudioCategory, Long> categoryCount = catalog.stream()
                .collect(Collectors.groupingBy(AudioItem::getCategory, Collectors.counting()));

        int totalDuration = catalog.stream()
                .mapToInt(AudioItem::getDurationSeconds)
                .sum();

        int hours = totalDuration / 3600;
        int minutes = (totalDuration % 3600) / 60;

        StringBuilder sb = new StringBuilder();
        sb.append("=======================================\n");
        sb.append("СТАТИСТИКА НА КАТАЛОГА\n");
        sb.append("=======================================\n");
        sb.append(String.format("Общо обекти: %d\n", getCatalogSize()));
        sb.append(String.format("Песни: %d\n", categoryCount.getOrDefault(AudioItem.AudioCategory.SONG, 0L)));
        sb.append(String.format("Албуми: %d\n", categoryCount.getOrDefault(AudioItem.AudioCategory.ALBUM, 0L)));
        sb.append(String.format("Подкасти: %d\n", categoryCount.getOrDefault(AudioItem.AudioCategory.PODCAST, 0L)));
        sb.append(String.format("Аудиокниги: %d\n", categoryCount.getOrDefault(AudioItem.AudioCategory.AUDIOBOOK, 0L)));
        sb.append(String.format("Обща продължителност: %d ч. %d мин.\n", hours, minutes));
        sb.append(String.format("Плейлисти: %d\n", getPlaylistsCount()));
        sb.append("=======================================");

        return sb.toString();
    }
}