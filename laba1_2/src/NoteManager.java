// Файл: NoteManager.java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NoteManager {
    private List<Note> notes;

    public NoteManager() {
        notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public boolean deleteNote(int id) {
        return notes.removeIf(note -> note.getId() == id);
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
            return true;
        }
        return false;
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    public List<Note> searchNotesByTitle(String keyword) {
        return notes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Note> searchNotesByContent(String keyword) {
        return notes.stream()
                .filter(note -> note.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Note> sortNotesByTitle() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getTitle))
                .collect(Collectors.toList());
    }

    public List<Note> sortNotesByCreationTime() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getCreationTime))
                .collect(Collectors.toList());
    }

    public List<Note> sortNotesByLastModifiedTime() {
        return notes.stream()
                .sorted(Comparator.comparing(Note::getLastModifiedTime))
                .collect(Collectors.toList());
    }
}
