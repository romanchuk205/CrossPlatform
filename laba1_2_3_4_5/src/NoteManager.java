import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NoteManager {
    private List<Note> notes;
    private static final String FILE_NAME = "notes.json";

    public NoteManager() {
        notes = new ArrayList<>();
        loadNotesFromFile();
    }

    public void addNote(Note note) {
        notes.add(note);
        saveNotesToFile();  // Зберігаємо нотатку після додавання
    }

    public boolean deleteNote(int id) {
        boolean removed = notes.removeIf(note -> note.getId() == id);
        if (removed) {
            saveNotesToFile();  // Зберігаємо після видалення
        }
        return removed;
    }

    public Note getNoteById(int id) {
        return notes.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean updateNote(int id, String title, String content) {
        Note note = getNoteById(id);
        if (note != null) {
            note.setTitle(title);
            note.setContent(content);
            saveNotesToFile();  // Зберігаємо після оновлення
            return true;
        }
        return false;
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    // Пошук нотаток за назвою
    public List<Note> searchNotesByTitle(String keyword) {
        return notes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Пошук нотаток за змістом
    public List<Note> searchNotesByContent(String keyword) {
        return notes.stream()
                .filter(note -> note.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Сортування нотаток за назвою
    public List<Note> sortNotesByTitle() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getTitle))
                .collect(Collectors.toList());
    }

    // Сортування нотаток за часом створення
    public List<Note> sortNotesByCreationTime() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getCreationTime))
                .collect(Collectors.toList());
    }

    // Сортування нотаток за часом останньої модифікації
    public List<Note> sortNotesByLastModifiedTime() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getLastModifiedTime))
                .collect(Collectors.toList());
    }

    // Метод для збереження нотаток у файл
    private void saveNotesToFile() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(notes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для завантаження нотаток із файлу
    private void loadNotesFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            notes = gson.fromJson(reader, new TypeToken<List<Note>>(){}.getType());
            if (notes == null) {
                notes = new ArrayList<>();  // Якщо в файлі немає нотаток, ініціалізуємо порожній список
            }
        } catch (FileNotFoundException e) {
            // Якщо файл не знайдений, то просто починаємо з порожнього списку
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
