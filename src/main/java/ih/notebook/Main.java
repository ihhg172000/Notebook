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
            scanner.useDelimiter("\n\n");

            System.out.println("Enter number of your choice\n*Press enter twice to confirm your choice\n");
            System.out.println("1- Add note\n2- Read note\n3- Delete note\n0- Save & Exit\n");

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
                case 3 -> deleteNote();
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
        Note note = new Note();

        System.out.println("Enter note title\n*Press enter twice to confirm title\n");

        note.setTitle(scanner.next());

        System.out.println("Enter note description\n*Press enter twice to confirm description\n");

        String[] tokens;
        tokens = scanner.next().split("\\n");

        note.setDescription(tokens);

        notes.add(note);

        printBox("Note added");
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

            System.out.println("\nEnter number of note to select it\n*Press enter twice to confirm your choice\n");

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
