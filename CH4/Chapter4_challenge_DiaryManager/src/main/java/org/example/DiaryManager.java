package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DiaryManager {
    private static final String ENTRIES_DIR = "entries";
    private static final String CONFIG_FILE = "diary_config.ser";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");

    private DiaryConfig config;

    public DiaryManager() {
        try {
            Files.createDirectories(Paths.get(ENTRIES_DIR));
        } catch (IOException e) {
            System.err.println("Error creating entries directory: " + e.getMessage());
        }
        loadConfig();
    }

    private void loadConfig() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CONFIG_FILE))) {
            config = (DiaryConfig) ois.readObject();
            System.out.println("Configuration loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("No configuration file found. Creating a new one.");
            config = new DiaryConfig("Default Author", false);
            saveConfig();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            config = new DiaryConfig("Default Author", false);
        }
    }

    private void saveConfig() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONFIG_FILE))) {
            oos.writeObject(config);
            System.out.println("Configuration saved.");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    public void updateConfig(String author, boolean backupsEnabled) {
        config.setAuthor(author);
        config.setBackupsEnabled(backupsEnabled);
        saveConfig();
    }

    public DiaryConfig getConfig() {
        return config;
    }

    public void writeEntry(String text) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String filename = "diary_" + timestamp + ".txt";
        Path filePath = Paths.get(ENTRIES_DIR, filename);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("Author: " + config.getAuthor() + "\n\n");
            writer.write(text);
            System.out.println("Diary entry saved successfully as " + filename);
        } catch (IOException e) {
            System.err.println("Error writing entry: " + e.getMessage());
        }
    }

    public List<String> listEntries() {
        try (Stream<Path> paths = Files.list(Paths.get(ENTRIES_DIR))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error listing entries: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String readEntry(String filename) {
        Path filePath = Paths.get(ENTRIES_DIR, filename);
        if (!Files.exists(filePath)) {
            return "Entry not found.";
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            System.err.println("Error reading entry: " + e.getMessage());
            return "Error reading entry.";
        }
    }

    public List<String> searchEntries(String keyword) {
        List<String> matchingEntries = new ArrayList<>();
        for (String filename : listEntries()) {
            String content = readEntry(filename);
            if (content.contains(keyword)) {
                matchingEntries.add(filename);
            }
        }
        return matchingEntries;
    }

    public void backupEntries() {
        if (!config.areBackupsEnabled()) {
            System.out.println("Backups are disabled in the configuration.");
            return;
        }

        String backupFileName = "backup_" + LocalDateTime.now().format(FORMATTER) + ".zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(backupFileName))) {
            for (String filename : listEntries()) {
                Path filePath = Paths.get(ENTRIES_DIR, filename);
                try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
                    ZipEntry zipEntry = new ZipEntry(filename);
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                }
            }
            System.out.println("Backup created successfully: " + backupFileName);
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}
