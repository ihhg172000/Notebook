package ih.notebook;

import java.util.*;
import java.io.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Note> notes = new ArrayList<>();

    public static void main(String[] args) {

        try {
            loadNotes();
        } catch (IOException | ClassNotFoundException exception) {
            printBox(exception.getMessage());
        }

        int choice;
        boolean continueProgram = true;

        do {

            scanner.reset();

            System.out.println("""
                    Enter number of your choice
                    *Press enter to confirm your choice
                    """);
            System.out.println("""
                    1- Add note
                    2- Read note
                    3- Update note
                    4- Delete note
                    0- Save & Exit
                    """);

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException exception) {

                printBox("You must enter a number");
                scanner.next();

                continue;

            }

            switch (choice) {
                case 1 -> addNote();
                case 2 -> readNote();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 0 -> {

                    continueProgram = false;
                    scanner.close();

                    try {
                        saveNotes();
                    } catch (IOException exception) {
                        printBox(exception.getMessage());
                    }

                }
                default -> printBox("Invalid choice number");
            }
        } while (continueProgram);
    }

    public static void addNote() {

        scanner.useDelimiter("\n\n");
        Note note = new Note();

        System.out.println("""
                
                Enter note title
                *Press enter twice to confirm title
                """);

        note.setTitle(scanner.next().trim());

        System.out.println("""
                Enter note description
                *Press enter twice to confirm description
                """);

        String[] tokens;
        tokens = scanner.next().split("\\n");

        note.setDescription(tokens);

        notes.add(note);

        printBox("Note added");

        scanner.useDelimiter("\n");

    }

    public static void readNote() {
        try {

            int index = selectNote();
            if (index != -1) {

                Note note = notes.get(index);
                printBox(note.getDescription());

            } else {
                printBox("Empty");
            }

        } catch (InputMismatchException exception) {

            printBox("You must enter a number");
            scanner.next();
            readNote();

        } catch (Exception exception) {

            printBox(exception.getMessage());
            readNote();

        }
    }

    public static void updateNote() {
        try {

            int index = selectNote();
            if (index != -1) {

                scanner.useDelimiter("\n\n");
                Note note = notes.get(index);

                System.out.println("""
                        
                        Enter new title
                        *Press enter twice with empty text field to keep no changes
                        *Type new title and press enter twice to update it
                        """);

                String newTitle = scanner.next();
                if (!newTitle.isEmpty()) {
                    note.setTitle(newTitle.trim());
                }

                System.out.println("""
                        Enter new description
                        **Press enter twice with empty text field to keep no changes
                        *Type new description and press enter twice to update it
                        """);

                String[] tokens;
                tokens = scanner.next().split("\\n");
                if (tokens.length != 1 && !tokens[0].isEmpty()) {
                    note.setDescription(tokens);
                }

                notes.remove(index);
                notes.add(index, note);

                printBox("Updated");

                scanner.useDelimiter("\n");
                scanner.next();

            } else {
                printBox("Empty");
            }

        } catch (InputMismatchException exception) {

            printBox("You must enter a number");
            scanner.next();
            updateNote();

        } catch (Exception exception) {

            printBox(exception.getMessage());
            updateNote();

        }
    }

    public static void deleteNote() {
        try {

            int index = selectNote();
            if (index != -1) {

                notes.remove(index);
                printBox("Deleted");

            } else {
                printBox("Empty");
            }

        } catch (InputMismatchException exception) {

            printBox("You must enter a number");
            scanner.next();
            deleteNote();

        } catch (Exception exception) {

            printBox(exception.getMessage());
            deleteNote();

        }
    }

    public static int selectNote() throws Exception {

        if (notes.isEmpty()) {
            return -1;
        } else {

            System.out.println("""

                    Enter number of note to select it
                    *Press enter to confirm your choice
                    """);

            int count = 0;
            for (Note note : notes) {

                count++;
                System.out.println(count + "- " + note.getTitle());

            }

            System.out.println();

            int choice = scanner.nextInt();

            if (choice > 0 && choice <= notes.size()) {
                return choice - 1;
            } else {
                throw new Exception("Invalid choice number");
            }
        }
    }

    // Custom print functions for print text in box

    public static void printBox(String text) {

        for (int i = 0; i < text.length(); i++) {
            System.out.print("_");
        }

        System.out.println("\n\n" + text);

        for (int i = 0; i < text.length(); i++) {
            System.out.print("_");
        }

        System.out.println("\n");

    }

    public static void printBox(String[] text) {

        int length = 0;

        for (String line : text) {

            if (length < line.length()) {
                length = line.length();
            }

        }

        for (int i = 0; i < length; i++) {
            System.out.print("_");
        }

        System.out.println("\n");

        for (String line : text) {
            System.out.println(line);
        }

        for (int i = 0; i < length; i++) {
            System.out.print("_");
        }

        System.out.println("\n");

    }

    // File handling for load & save notes.

    public static void loadNotes() throws IOException, ClassNotFoundException {

        File file = new File(FILE_PATH);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            notes = (ArrayList<Note>) objectInputStream.readObject();
        }

    }

    public static void saveNotes() throws IOException {

        File file = new File(FILE_PATH);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {

            objectOutputStream.writeObject(notes);
            objectOutputStream.flush();

        }

    }

    private static final String FILE_PATH = ".notes.text";

}
