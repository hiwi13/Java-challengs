package org.example;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final DiaryManager diaryManager;
    private final Scanner scanner;

    public Menu() {
        this.diaryManager = new DiaryManager();
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        while (true) {
            System.out.println("\n--- Diary Menu ---");
            System.out.println("1. Write a new entry");
            System.out.println("2. Read an entry");
            System.out.println("3. List all entries");
            System.out.println("4. Search entries");
            System.out.println("5. Update Configuration");
            System.out.println("6. View Configuration");
            System.out.println("7. Backup Entries");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    writeEntry();
                    break;
                case 2:
                    readEntry();
                    break;
                case 3:
                    listEntries();
                    break;
                case 4:
                    searchEntries();
                    break;
                case 5:
                    updateConfiguration();
                    break;
                case 6:
                    viewConfiguration();
                    break;
                case 7:
                    diaryManager.backupEntries();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void writeEntry() {
        System.out.print("Enter your diary entry: ");
        String text = scanner.nextLine();
        diaryManager.writeEntry(text);
    }

    private void listEntries() {
        List<String> entries = diaryManager.listEntries();
        if (entries.isEmpty()) {
            System.out.println("No entries found.");
        } else {
            System.out.println("\n--- Diary Entries ---");
            entries.forEach(System.out::println);
        }
    }

    private void readEntry() {
        listEntries();
        System.out.print("\nEnter the filename of the entry to read: ");
        String filename = scanner.nextLine();
        String content = diaryManager.readEntry(filename);
        System.out.println("\n--- Entry Content ---");
        System.out.println(content);
    }

    private void searchEntries() {
        System.out.print("Enter a keyword to search for: ");
        String keyword = scanner.nextLine();
        List<String> matchingEntries = diaryManager.searchEntries(keyword);

        if (matchingEntries.isEmpty()) {
            System.out.println("No entries found with that keyword.");
        } else {
            System.out.println("\n--- Matching Entries ---");
            matchingEntries.forEach(System.out::println);
        }
    }

    private void updateConfiguration() {
        System.out.print("Enter new author name: ");
        String author = scanner.nextLine();
        System.out.print("Enable backups? (true/false): ");
        boolean backupsEnabled = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        diaryManager.updateConfig(author, backupsEnabled);
    }

    private void viewConfiguration() {
        System.out.println("\n--- Current Configuration ---");
        System.out.println(diaryManager.getConfig());
    }
}
