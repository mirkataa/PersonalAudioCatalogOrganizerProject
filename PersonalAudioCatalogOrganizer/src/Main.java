import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static AudioCatalog catalog = new AudioCatalog();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("|  ОРГАНИЗАТОР НА ЛИЧЕН АУДИО КАТАЛОГ   |");
        System.out.println("=========================================");

        if (catalog.loadCatalog()) {
            System.out.println("Каталогът е зареден успешно!");
        } else {
            System.out.println("Създаване на нов каталог...");
        }

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readInt("Избор");

            switch (choice) {
                case 1: addNewItem(); break;
                case 2: removeItem(); break;
                case 3: editItem(); break;
                case 4: searchItems(); break;
                case 5: filterItems(); break;
                case 6: sortItems(); break;
                case 7: displayAllItems(); break;
                case 8: managePlaylists(); break;
                case 9: displayStatistics(); break;
                case 10: saveAndExit(); running = false; break;
                case 0: running = false; break;
                default: System.out.println("Невалиден избор!");
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "═".repeat(40));
        System.out.println("ГЛАВНО МЕНЮ");
        System.out.println("═".repeat(40));
        System.out.println("1. Добавяне на нов обект");
        System.out.println("2. Изтриване на обект");
        System.out.println("3. Редактиране на обект");
        System.out.println("4. Търсене");
        System.out.println("5. Филтриране");
        System.out.println("6. Сортиране");
        System.out.println("7. Преглед на всички обекти");
        System.out.println("8. Управление на плейлисти");
        System.out.println("9. Статистика");
        System.out.println("10. Запазване и изход");
        System.out.println("0. Изход без запазване");
        System.out.println("═".repeat(40));
    }

    // дабавяне на обекти

    private static void addNewItem() {
        System.out.println("\n--- ДОБАВЯНЕ НА НОВ ОБЕКТ ---");
        System.out.println("1. Песен");
        System.out.println("2. Албум");
        System.out.println("3. Подкаст");
        System.out.println("4. Аудиокнига");

        int type = readInt("Тип");

        switch (type) {
            case 1: addSong(); break;
            case 2: addAlbum(); break;
            case 3: addPodcast(); break;
            case 4: addAudiobook(); break;
            default: System.out.println("Невалиден избор!");
        }
    }

    private static void addSong() {
        System.out.println("\n--- Нова песен ---");
        String title = readString("Заглавие");
        String artist = readString("Изпълнител");
        String album = readString("Албум");
        String genre = readString("Жанр");
        int duration = readInt("Продължителност (секунди)");
        int year = readInt("Година");

        Song song = new Song(title, artist, genre, duration, year, album);
        catalog.addItem(song);
        System.out.println("Песента е добавена успешно!");
    }

    private static void addAlbum() {
        System.out.println("\n--- Нов албум ---");
        String title = readString("Заглавие");
        String artist = readString("Изпълнител");
        String genre = readString("Жанр");
        int duration = readInt("Обща продължителност (секунди)");
        int year = readInt("Година");
        int tracks = readInt("Брой песни");

        Album album = new Album(title, artist, genre, duration, year, tracks);
        catalog.addItem(album);
        System.out.println("Албумът е добавен успешно!");
    }

    private static void addPodcast() {
        System.out.println("\n--- Нов подкаст ---");
        String title = readString("Заглавие");
        String author = readString("Автор");
        String host = readString("Водещ");
        int episode = readInt("Номер на епизода");
        String genre = readString("Жанр");
        int duration = readInt("Продължителност (секунди)");
        int year = readInt("Година");

        Podcast podcast = new Podcast(title, author, genre, duration, year, episode, host);
        catalog.addItem(podcast);
        System.out.println("Подкастът е добавен успешно!");
    }

    private static void addAudiobook() {
        System.out.println("\n--- Нова аудиокнига ---");
        String title = readString("Заглавие");
        String author = readString("Автор");
        String narrator = readString("Разказвач");
        int chapters = readInt("Брой глави");
        String genre = readString("Жанр");
        int duration = readInt("Обща продължителност (секунди)");
        int year = readInt("Година");

        Audiobook audiobook = new Audiobook(title, author, genre, duration, year, narrator, chapters);
        catalog.addItem(audiobook);
        System.out.println("Аудиокнигата е добавена успешно!");
    }

    // изтриване на обекти

    private static void removeItem() {
        List<AudioItem> items = catalog.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Каталогът е празен!");
            return;
        }

        System.out.println("\n--- ИЗТРИВАНЕ НА ОБЕКТ ---");
        displayItemsList(items);

        int index = readInt("Номер на обект за изтриване (0 за отказ)");
        if (index == 0) return;

        if (catalog.removeItem(index - 1)) {
            System.out.println("Обектът е изтрит!");
            System.out.println("В каталога остават " + catalog.getCatalogSize() + " обекта");
        } else {
            System.out.println("Невалиден номер!");
        }
    }

    // редактиране на обект

    private static void editItem() {
        List<AudioItem> items = catalog.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Каталогът е празен!");
            return;
        }

        System.out.println("\n--- РЕДАКТИРАНЕ НА ОБЕКТ ---");
        displayItemsList(items);

        int index = readInt("Номер на обект за редактиране (0 за отказ)");
        if (index == 0) return;

        if (index > 0 && index <= items.size()) {
            AudioItem item = items.get(index - 1);
            editAudioItem(item);
        } else {
            System.out.println("Невалиден номер!");
        }
    }

    private static void editAudioItem(AudioItem item) {
        System.out.println("\n" + item.getDetailedInfo());
        System.out.println("\n--- Какво искате да промените? ---");
        System.out.println("1. Заглавие");
        System.out.println("2. Автор/Изпълнител");
        System.out.println("3. Жанр");
        System.out.println("4. Продължителност");
        System.out.println("5. Година");

        if (item instanceof Song) {
            System.out.println("6. Албум");
        } else if (item instanceof Album) {
            System.out.println("6. Брой песни");
        }

        System.out.println("0. Отказ");

        int choice = readInt("Избор");

        switch (choice) {
            case 1:
                String newTitle = readString("Ново заглавие");
                item.setTitle(newTitle);
                System.out.println("Заглавието е променено!");
                break;
            case 2:
                String newAuthor = readString("Нов автор/изпълнител");
                item.setAuthor(newAuthor);
                System.out.println("Авторът е променен!");
                break;
            case 3:
                String newGenre = readString("Нов жанр");
                item.setGenre(newGenre);
                System.out.println("Жанрът е променен!");
                break;
            case 4:
                int newDuration = readInt("Нова продължителност (секунди)");
                item.setDurationSeconds(newDuration);
                System.out.println("Продължителността е променена!");
                break;
            case 5:
                int newYear = readInt("Нова година");
                item.setYearPublished(newYear);
                System.out.println("Годината е променена!");
                break;
            case 6:
                if (item instanceof Song) {
                    String newAlbum = readString("Нов албум");
                    ((Song) item).setAlbum(newAlbum);
                    System.out.println("Албумът е променен!");
                } else if (item instanceof Album) {
                    int newTracks = readInt("Нов брой песни");
                    ((Album) item).setNumberOfTracks(newTracks);
                    System.out.println("Броят песни е променен!");
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Невалиден избор!");
        }
    }

    // търсене

    private static void searchItems() {
        System.out.println("\n--- ТЪРСЕНЕ ---");
        System.out.println("1. По заглавие");
        System.out.println("2. По автор/изпълнител");
        System.out.println("3. По жанр");
        System.out.println("4. По категория");
        System.out.println("5. Универсално търсене");

        int choice = readInt("Избор");
        List<AudioItem> results = null;

        switch (choice) {
            case 1:
                String title = readString("Заглавие");
                results = catalog.searchByTitle(title);
                break;
            case 2:
                String author = readString("Автор");
                results = catalog.searchByAuthor(author);
                break;
            case 3:
                String genre = readString("Жанр");
                results = catalog.searchByGenre(genre);
                break;
            case 4:
                results = searchByCategory();
                break;
            case 5:
                String term = readString("Търсене");
                results = catalog.universalSearch(term);
                break;
            default:
                System.out.println("Невалиден избор!");
                return;
        }

        displaySearchResults(results);
    }

    private static List<AudioItem> searchByCategory() {
        System.out.println("1. Песни");
        System.out.println("2. Албуми");
        System.out.println("3. Подкасти");
        System.out.println("4. Аудиокниги");

        int choice = readInt("Категория");
        AudioItem.AudioCategory category = null;

        switch (choice) {
            case 1: category = AudioItem.AudioCategory.SONG; break;
            case 2: category = AudioItem.AudioCategory.ALBUM; break;
            case 3: category = AudioItem.AudioCategory.PODCAST; break;
            case 4: category = AudioItem.AudioCategory.AUDIOBOOK; break;
            default: System.out.println("Невалиден избор!"); return null;
        }

        return catalog.searchByCategory(category);
    }

    // филтрация

    private static void filterItems() {
        System.out.println("\n--- ФИЛТРИРАНЕ ---");
        System.out.println("1. По жанр");
        System.out.println("2. По автор");
        System.out.println("3. По година");
        System.out.println("4. По период от години");

        int choice = readInt("Избор");
        List<AudioItem> results = null;

        switch (choice) {
            case 1:
                String genre = readString("Жанр");
                results = catalog.filterByGenre(genre);
                break;
            case 2:
                String author = readString("Автор");
                results = catalog.filterByAuthor(author);
                break;
            case 3:
                int year = readInt("Година");
                results = catalog.filterByYear(year);
                break;
            case 4:
                int startYear = readInt("От година");
                int endYear = readInt("До година");
                results = catalog.filterByYearRange(startYear, endYear);
                break;
            default:
                System.out.println("Невалиден избор!");
                return;
        }

        displaySearchResults(results);
    }

    // сорт

    private static void sortItems() {
        System.out.println("\n--- СОРТИРАНЕ ---");
        System.out.println("1. По заглавие (азбучен ред)");
        System.out.println("2. По автор");
        System.out.println("3. По година (най-нови първи)");
        System.out.println("4. По продължителност");
        System.out.println("5. По плейлист (азбучен ред на името на плейлиста)");

        int choice = readInt("Избор");
        List<AudioItem> results = null;

        switch (choice) {
            case 1: results = catalog.sortByTitle(); break;
            case 2: results = catalog.sortByAuthor(); break;
            case 3: results = catalog.sortByYearDesc(); break;
            case 4: results = catalog.sortByDuration(); break;
            case 5: sortAndDisplayByPlaylistGroups(); return;
            default:
                System.out.println("Невалиден избор!");
                return;
        }

        displayItemsList(results);
    }

    private static void sortAndDisplayByPlaylistGroups() {
        if (catalog.getPlaylistsCount() == 0) {
            System.out.println("Няма създадени плейлисти!");
            System.out.println("Показване на всички обекти:");
            displayItemsList(catalog.getAllItems());
            return;
        }

        List<AudioItem> sortedItems = catalog.sortByPlaylistGroups();

        // вс плейлисти сортирани по име
        List<Playlist> sortedPlaylists = catalog.getAllPlaylists().stream()
                .sorted(Comparator.comparing(Playlist::getName))
                .collect(Collectors.toList());

        System.out.println("\n=======================================");
        System.out.println("СОРТИРАНЕ ПО ПЛЕЙЛИСТ");
        System.out.println("=======================================");

        Set<AudioItem> displayedItems = new HashSet<>();
        int totalNumber = 1;

        // Показва обектите групирани по плейлист
        for (Playlist playlist : sortedPlaylists) {
            List<AudioItem> playlistItems = playlist.getItems();

            if (!playlistItems.isEmpty()) {
                System.out.println("\n Плейлист: " + playlist.getName());
                System.out.println(" Обекти: " + playlistItems.size() +
                        " Обща продължителност: " + playlist.getTotalDurationFormatted());
                System.out.println("--------------------------------------");

                for (AudioItem item : playlistItems) {
                    if (!displayedItems.contains(item)) {
                        System.out.println(String.format("%3d. %s", totalNumber++, item));
                        displayedItems.add(item);
                    }
                }
            }
        }

        // Показва обектите които не са в нито един плейлист
        List<AudioItem> notInAnyPlaylist = new ArrayList<>();
        for (AudioItem item : catalog.getAllItems()) {
            if (!displayedItems.contains(item)) {
                notInAnyPlaylist.add(item);
            }
        }

        if (!notInAnyPlaylist.isEmpty()) {
            System.out.println("\nНе са в плейлист");
            System.out.println("Обекти: " + notInAnyPlaylist.size());
            System.out.println("-------------------------------------");

            for (AudioItem item : notInAnyPlaylist) {
                System.out.println(String.format("%3d. %s", totalNumber++, item));
            }
        }

        System.out.println("Общо показани обекти: " + (totalNumber - 1));
    }

    // плейлист меню

    private static void managePlaylists() {
        System.out.println("\n--- УПРАВЛЕНИЕ НА ПЛЕЙЛИСТ ---");
        System.out.println("1. Създаване на нов плейлист");
        System.out.println("2. Преглед на плейлист");
        System.out.println("3. Добавяне на обект в плейлист");
        System.out.println("4. Премахване на обект от плейлист");
        System.out.println("5. Преглед на плейлист");
        System.out.println("6. Редактиране на плейлист (име/описание)");
        System.out.println("7. Изчистване на плейлист");
        System.out.println("8. Изтриване на плейлист");
        System.out.println("9. Запазване на плейлист във файл");
        System.out.println("10. Зареждане на плейлист от файл");
        System.out.println("11. Сортиране на плейлист");

        int choice = readInt("Избор");

        switch (choice) {
            case 1: createPlaylist(); break;
            case 2: viewAllPlaylists(); break;
            case 3: addToPlaylist(); break;
            case 4: removeFromPlaylist(); break;
            case 5: viewPlaylist(); break;
            case 6: editPlaylist(); break;
            case 7: clearPlaylist(); break;
            case 8: deletePlaylist(); break;
            case 9: savePlaylist(); break;
            case 10: loadPlaylist(); break;
            case 11: sortPlaylist(); break;
            default: System.out.println("Невалиден избор!");
        }
    }

    private static void createPlaylist() {
        String name = readString("Име на плейлист");
        String desc = readString("Описание (Enter за празно)");

        Playlist playlist;
        if (desc.trim().isEmpty()) {
            playlist = catalog.createPlaylist(name);
        } else {
            playlist = catalog.createPlaylist(name, desc);
        }

        System.out.println("Плейлистът \"" + playlist.getName() + "\" е успешно създаден!");
    }

    private static void viewAllPlaylists() {
        List<Playlist> playlists = catalog.getAllPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("Няма създадени плейлисти!");
            return;
        }

        System.out.println("\n--- ПЛЕЙЛИСТ (" + catalog.getPlaylistsCount() + ") ---");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println((i + 1) + ". " + playlists.get(i));
        }
    }

    private static void findPlaylistByName() {
        String name = readString("Име на плейлист");
        Playlist playlist = catalog.findPlaylistByName(name);

        if (playlist != null) {
            System.out.println("Намерен плейлист:");
            System.out.println(playlist.getDetailedInfo());
        } else {
            System.out.println("Плейлист с име \"" + name + "\" не е намерен!");
        }
    }

    private static void addToPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist == null) return;

        List<AudioItem> items = catalog.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Няма обекти в каталога!");
            return;
        }

        displayItemsList(items);
        int index = readInt("Номер на обект за добавяне (0 за отказ)");

        if (index > 0 && index <= items.size()) {
            playlist.addItem(items.get(index - 1));
            System.out.println("Обектът е добавен към плейлист" + playlist.getName());
            System.out.println(playlist.getName() + " сега има " + playlist.getSize() + " обекта");
        }
    }

    private static void removeFromPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist == null) return;

        if (playlist.isEmpty()) {
            System.out.println("Плейлистът е празен!");
            return;
        }

        System.out.println(playlist.getDetailedInfo());
        int index = readInt("Номер на обект за премахване (0 за отказ)");

        if (index > 0 && playlist.removeItem(index - 1)) {
            System.out.println("Обектът е премахнат от " + playlist.getName());
            System.out.println(playlist.getName() + " сега има " + playlist.getSize() + " обекта");
        }
    }

    private static void viewPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist != null) {
            System.out.println("\n" + playlist.getDetailedInfo());
        }
    }

    private static void editPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist == null) return;

        System.out.println("\n--- РЕДАКТИРАНЕ НА ПЛЕЙЛИСТ ---");
        System.out.println("Текущо име: " + playlist.getName());
        System.out.println("Текущо описание: " + playlist.getDescription());
        System.out.println();
        System.out.println("1. Промяна на име");
        System.out.println("2. Промяна на описание");
        System.out.println("3. Промяна на и двете");

        int choice = readInt("Избор");

        switch (choice) {
            case 1:
                String newName = readString("Ново име");
                playlist.setName(newName);
                System.out.println("Името е променено!");
                break;
            case 2:
                String newDesc = readString("Ново описание");
                playlist.setDescription(newDesc);
                System.out.println("Описанието е променено!");
                break;
            case 3:
                String name = readString("Ново име");
                String desc = readString("Ново описание");
                playlist.setName(name);
                playlist.setDescription(desc);
                System.out.println("Плейлистът е обновен!");
                break;
            default:
                System.out.println("Невалиден избор!");
        }
    }

    private static void clearPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist == null) return;

        if (playlist.isEmpty()) {
            System.out.println("Плейлистът вече е празен!");
            return;
        }

        String confirm = readString("Сигурни ли сте? Това ще изтрие всички " + playlist.getSize() + " обекта! (yes/no)");
        if (confirm.equalsIgnoreCase("yes")) {
            playlist.clear();
            System.out.println("Плейлистът е изчистен!");
        }
    }

    private static void deletePlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist != null) {
            String confirm = readString("Сигурни ли сте? (yes/no)");
            if (confirm.equalsIgnoreCase("yes")) {
                if (catalog.removePlaylist(playlist)) {
                    System.out.println("Плейлистът е изтрит!");
                } else {
                    System.out.println("Грешка при изтриване!");
                }
            }
        }
    }

    private static void savePlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist != null) {
            if (catalog.savePlaylist(playlist)) {
                System.out.println("Плейлистът е запазен във файл!");
            } else {
                System.out.println("Грешка при записване!");
            }
        }
    }

    private static void loadPlaylist() {
        String filename = readString("Име на файл");
        Playlist playlist = catalog.loadPlaylist(filename);
        if (playlist != null) {
            System.out.println("Плейлист \"" + playlist.getName() + "\" е зареден!");
        } else {
            System.out.println("Грешка при зареждане!");
        }
    }

    private static Playlist selectPlaylist() {
        List<Playlist> playlists = catalog.getAllPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("Няма създадени плейлист!");
            return null;
        }

        viewAllPlaylists();
        int index = readInt("Номер на плейлист (0 за отказ)");

        if (index > 0 && index <= playlists.size()) {
            return playlists.get(index - 1);
        }
        return null;
    }

    private static void sortPlaylist() {
        Playlist playlist = selectPlaylist();
        if (playlist == null) return;

        if (playlist.isEmpty()) {
            System.out.println("Плейлистът е празен!");
            return;
        }

        System.out.println("\n--- СОРТИРАНЕ НА ПЛЕЙЛИСТ ---");
        System.out.println("1. По заглавие (азбучен ред)");
        System.out.println("2. По автор");
        System.out.println("3. По година (най-нови първи)");
        System.out.println("4. По година (най-стари първи)");
        System.out.println("5. По продължителност (най-къси първи)");
        System.out.println("6. По продължителност (най-дълги първи)");
        System.out.println("7. По жанр");
        System.out.println("8. По категория");
        System.out.println("9. Разбъркване (shuffle)");
        System.out.println("10. Обръщане на реда");
        System.out.println("0. Отказ");

        int choice = readInt("Избор");

        switch (choice) {
            case 1:
                playlist.sortByTitle();
                System.out.println("Плейлистът е сортиран по заглавие!");
                break;
            case 2:
                playlist.sortByAuthor();
                System.out.println("Плейлистът е сортиран по автор!");
                break;
            case 3:
                playlist.sortByYearDesc();
                System.out.println("Плейлистът е сортиран по година (най-нови първи)!");
                break;
            case 4:
                playlist.sortByYearAsc();
                System.out.println("Плейлистът е сортиран по година (най-стари първи)!");
                break;
            case 5:
                playlist.sortByDuration();
                System.out.println("Плейлистът е сортиран по продължителност (най-къси първи)!");
                break;
            case 6:
                playlist.sortByDurationDesc();
                System.out.println("Плейлистът е сортиран по продължителност (най-дълги първи)!");
                break;
            case 7:
                playlist.sortByGenre();
                System.out.println("Плейлистът е сортиран по жанр!");
                break;
            case 8:
                playlist.sortByCategory();
                System.out.println("Плейлистът е сортиран по категория!");
                break;
            case 9:
                playlist.shuffle();
                System.out.println("Плейлистът е разбъркан!");
                break;
            case 10:
                playlist.reverse();
                System.out.println("Редът е обърнат!");
                break;
            case 0:
                return;
            default:
                System.out.println("Невалиден избор!");
        }

        System.out.println("\n--- Нов ред на обектите ---");
        System.out.println(playlist.getDetailedInfo());
    }

    // хелпър функции

    private static void displayAllItems() {
        List<AudioItem> items = catalog.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Каталогът е празен!");
            return;
        }

        System.out.println("\n--- ВСИЧКИ ОБЕКТИ В КАТАЛОГА ---");
        displayItemsList(items);
    }

    private static void displayItemsList(List<AudioItem> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    private static void displaySearchResults(List<AudioItem> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("Няма намерени резултати!");
            return;
        }

        System.out.println("\n--- РЕЗУЛТАТИ (" + results.size() + ") ---");
        displayItemsList(results);

        System.out.println("\nПоказване на детайли? (номер на обект или 0 за отказ)");
        int index = readInt("Номер");

        if (index > 0 && index <= results.size()) {
            System.out.println("\n" + results.get(index - 1).getDetailedInfo());
        }
    }

    private static void displayStatistics() {
        System.out.println("\n" + catalog.getCatalogStatistics());

        // Допълнителна статове
        List<AudioItem> allItems = catalog.getAllItems();
        if (!allItems.isEmpty()) {
            System.out.println("\nДопълнителна информация:");

            System.out.println("Средна продължителност: " +
                    String.format(Locale.US, "%.2f",
                            allItems.stream()
                                    .mapToInt(AudioItem::getDurationSeconds)
                                    .average()
                                    .orElse(0) / 60.0
                    ) + " мин.");
        }
    }

    private static void saveAndExit() {
        System.out.print("Запазване на каталога... ");
        if (catalog.saveCatalog()) {
            System.out.println("Готово!");
        } else {
            System.out.println("Грешка!");
        }
        System.out.println("Довиждане!");
    }

    private static String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Моля, въведете число!");
            }
        }
    }
}