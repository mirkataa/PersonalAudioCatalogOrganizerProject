import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Playlist implements Serializable {
    private String name;
    private List<AudioItem> items;
    private String description;

    public Playlist(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        this.description = "";
    }

    public Playlist(String name, String description) {
        this.name = name;
        this.items = new ArrayList<>();
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * Добавя обект към плейлиста
     */
    public boolean addItem(AudioItem item) {
        if (item == null) {
            return false;
        }
        items.add(item);
        return true;
    }

    /**
     * Премахва обект от плейлиста по индекс
     */
    public boolean removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Изчислява общата продължителност на плейлиста
     */
    public int getTotalDuration() {
        return items.stream()
                .mapToInt(AudioItem::getDurationSeconds)
                .sum();
    }

    /**
     * Връща обща продължителност
     */
    public String getTotalDurationFormatted() {
        int total = getTotalDuration();
        int hours = total / 3600;
        int minutes = (total % 3600) / 60;
        int seconds = total % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    /**
     * Връща броя на обектите в плейлиста
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Проверява дали плейлистътът е празен
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Изчиства целия плейлист
     */
    public void clear() {
        items.clear();
    }

    /**
     * Връща всички обекти в плейлиста
     */
    public List<AudioItem> getItems() {
        return new ArrayList<>(items);
    }
    // сорт

    /**
     * Сортира плейлиста по заглавие (азбучен ред)
     */
    public void sortByTitle() {
        items = items.stream()
                .sorted(Comparator.comparing(AudioItem::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по автор/изпълнител
     */
    public void sortByAuthor() {
        items = items.stream()
                .sorted(Comparator.comparing(AudioItem::getAuthor))
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по година (най-нови първи)
     */
    public void sortByYearDesc() {
        items = items.stream()
                .sorted(Comparator.comparingInt(AudioItem::getYearPublished).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по година (най-стари първи)
     */
    public void sortByYearAsc() {
        items = items.stream()
                .sorted(Comparator.comparingInt(AudioItem::getYearPublished))
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по продължителност (най-къси първи)
     */
    public void sortByDuration() {
        items = items.stream()
                .sorted(Comparator.comparingInt(AudioItem::getDurationSeconds))
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по продължителност (най-дълги първи)
     */
    public void sortByDurationDesc() {
        items = items.stream()
                .sorted(Comparator.comparingInt(AudioItem::getDurationSeconds).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по жанр
     */
    public void sortByGenre() {
        items = items.stream()
                .sorted(Comparator.comparing(AudioItem::getGenre))
                .collect(Collectors.toList());
    }

    /**
     * Сортира плейлиста по категория
     */
    public void sortByCategory() {
        items = items.stream()
                .sorted(Comparator.comparing(AudioItem::getCategory))
                .collect(Collectors.toList());
    }

    /**
     * Разбърква реда на обектите в плейлиста
     */
    public void shuffle() {
        List<AudioItem> shuffled = new ArrayList<>(items);
        java.util.Collections.shuffle(shuffled);
        items = shuffled;
    }

    /**
     * Обръща реда на обектите в плейлиста
     */
    public void reverse() {
        List<AudioItem> reversed = new ArrayList<>(items);
        java.util.Collections.reverse(reversed);
        items = reversed;
    }

    /**
     * Връща инфо за плейлиста
     */
    public String getDetailedInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=======================================\n");
        sb.append("Плейлист: ").append(name).append("\n");
        if (!description.isEmpty()) {
            sb.append("Описание: ").append(description).append("\n");
        }
        sb.append("Брой обекти: ").append(items.size()).append("\n");
        sb.append("Обща продължителност: ").append(getTotalDurationFormatted()).append("\n");
        sb.append("=======================================\n");

        if (items.isEmpty()) {
            sb.append("(празен плейлист)\n");
        } else {
            for (int i = 0; i < items.size(); i++) {
                sb.append(String.format("%2d. %s\n", i + 1, items.get(i)));
            }
        }
        sb.append("=======================================");

        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("Плейлист: %s (%d обекта, %s)",
                name, items.size(), getTotalDurationFormatted());
    }
}