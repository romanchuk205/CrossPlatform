import java.util.List;
import java.util.Scanner;

public class NotebookApp {
    private NoteManager noteManager;
    private Scanner scanner;
    private boolean running;

    public NotebookApp() {
        noteManager = new NoteManager();
        scanner = new Scanner(System.in);
        running = true;
    }

    public void start() {
        System.out.println("Ласкаво просимо до додатку Нотатник!");

        while (running) {
            displayMenu();
            int choice = getUserChoice();
            processChoice(choice);
        }

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n===== МЕНЮ =====");
        System.out.println("1. Створити нотатку");
        System.out.println("2. Видалити нотатку");
        System.out.println("3. Переглянути всі нотатки");
        System.out.println("4. Знайти нотатку за ID");
        System.out.println("5. Оновити нотатку");
        System.out.println("6. Пошук нотаток за назвою");
        System.out.println("7. Пошук нотаток за змістом");
        System.out.println("8. Сортувати нотатки за назвою");
        System.out.println("9. Сортувати нотатки за часом створення");
        System.out.println("10. Сортувати нотатки за часом останньої модифікації");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                createNote();
                break;
            case 2:
                deleteNote();
                break;
            case 3:
                showAllNotes();
                break;
            case 4:
                findNoteById();
                break;
            case 5:
                updateNote();
                break;
            case 6:
                searchNotesByTitle();
                break;
            case 7:
                searchNotesByContent();
                break;
            case 8:
                sortNotesByTitle();
                break;
            case 9:
                sortNotesByCreationTime();
                break;
            case 10:
                sortNotesByLastModifiedTime();
                break;
            case 0:
                running = false;
                System.out.println("Дякуємо за використання додатку Нотатник!");
                break;
            default:
                System.out.println("Невірний вибір. Спробуйте ще раз.");
                break;
        }
    }

    private void createNote() {
        System.out.print("Введіть назву нотатки: ");
        String title = scanner.nextLine();

        System.out.print("Введіть зміст нотатки: ");
        String content = scanner.nextLine();

        Note note = new Note(title, content);
        noteManager.addNote(note);

        System.out.println("Нотатку успішно створено з ID: " + note.getId());
    }

    private void deleteNote() {
        System.out.print("Введіть ID нотатки для видалення: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean deleted = noteManager.deleteNote(id);

            if (deleted) {
                System.out.println("Нотатку з ID " + id + " успішно видалено.");
            } else {
                System.out.println("Нотатку з ID " + id + " не знайдено.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат ID.");
        }
    }

    private void showAllNotes() {
        List<Note> notes = noteManager.getAllNotes();

        if (notes.isEmpty()) {
            System.out.println("У вас немає нотаток.");
            return;
        }

        System.out.println("\n===== ВСІ НОТАТКИ =====");
        for (Note note : notes) {
            System.out.println("\n" + note);
            System.out.println("--------------------");
        }
    }

    private void findNoteById() {
        System.out.print("Введіть ID нотатки: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Note note = noteManager.getNoteById(id);

            if (note != null) {
                System.out.println("\n===== ЗНАЙДЕНА НОТАТКА =====");
                System.out.println(note);
            } else {
                System.out.println("Нотатку з ID " + id + " не знайдено.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат ID.");
        }
    }

    private void updateNote() {
        System.out.print("Введіть ID нотатки для оновлення: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Note note = noteManager.getNoteById(id);

            if (note != null) {
                System.out.println("Поточна назва: " + note.getTitle());
                System.out.print("Введіть нову назву (залиште порожнім, щоб залишити без змін): ");
                String title = scanner.nextLine();

                System.out.println("Поточний зміст: " + note.getContent());
                System.out.print("Введіть новий зміст (залиште порожнім, щоб залишити без змін): ");
                String content = scanner.nextLine();

                if (title.isEmpty()) {
                    title = note.getTitle();
                }

                if (content.isEmpty()) {
                    content = note.getContent();
                }

                noteManager.updateNote(id, title, content);
                System.out.println("Нотатку успішно оновлено.");
            } else {
                System.out.println("Нотатку з ID " + id + " не знайдено.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Невірний формат ID.");
        }
    }

    private void searchNotesByTitle() {
        System.out.print("Введіть ключове слово для пошуку в назвах: ");
        String keyword = scanner.nextLine();

        List<Note> foundNotes = noteManager.searchNotesByTitle(keyword);
        displaySearchResults(foundNotes, "за назвою");
    }

    private void searchNotesByContent() {
        System.out.print("Введіть ключове слово для пошуку в змісті: ");
        String keyword = scanner.nextLine();

        List<Note> foundNotes = noteManager.searchNotesByContent(keyword);
        displaySearchResults(foundNotes, "за змістом");
    }

    private void displaySearchResults(List<Note> notes, String criteria) {
        if (notes.isEmpty()) {
            System.out.println("Нотатки " + criteria + " не знайдено.");
            return;
        }

        System.out.println("\n===== РЕЗУЛЬТАТИ ПОШУКУ " + criteria.toUpperCase() + " =====");
        for (Note note : notes) {
            System.out.println("\n" + note);
            System.out.println("--------------------");
        }
    }

    private void sortNotesByTitle() {
        List<Note> sortedNotes = noteManager.sortNotesByTitle();
        displaySortedNotes(sortedNotes, "за назвою");
    }

    private void sortNotesByCreationTime() {
        List<Note> sortedNotes = noteManager.sortNotesByCreationTime();
        displaySortedNotes(sortedNotes, "за часом створення");
    }

    private void sortNotesByLastModifiedTime() {
        List<Note> sortedNotes = noteManager.sortNotesByLastModifiedTime();
        displaySortedNotes(sortedNotes, "за часом останньої модифікації");
    }

    private void displaySortedNotes(List<Note> notes, String criteria) {
        if (notes.isEmpty()) {
            System.out.println("У вас немає нотаток для сортування.");
            return;
        }

        System.out.println("\n===== НОТАТКИ ВІДСОРТОВАНІ " + criteria.toUpperCase() + " =====");
        for (Note note : notes) {
            System.out.println("\n" + note);
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        NotebookApp app = new NotebookApp();
        app.start();
    }
}
