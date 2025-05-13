import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private static int nextId = 1;
    private int id;
    private String title;
    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime lastModifiedTime;

    public Note(String title, String content) {
        this.id = nextId++;
        this.title = title;
        this.content = content;
        this.creationTime = LocalDateTime.now();
        this.lastModifiedTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.lastModifiedTime = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.lastModifiedTime = LocalDateTime.now();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "ID: " + id +
                "\nНазва: " + title +
                "\nСтворено: " + creationTime.format(formatter) +
                "\nОстанні зміни: " + lastModifiedTime.format(formatter) +
                "\nЗміст: " + content;
    }
}
