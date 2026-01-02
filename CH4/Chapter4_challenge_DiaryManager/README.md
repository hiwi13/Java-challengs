# Command-Line Diary Manager

A simple command-line application for managing diary entries.

## Features

-   Write, read, list, and search diary entries.
-   Each entry is saved as a timestamped text file.
-   Configuration for author name and backups.
-   Backup feature to create a ZIP archive of all entries.

## How to Run

1.  **Compile:**
    ```bash
    mvn clean install
    ```
2.  **Run:**
    ```bash
    java -jar target/Chapter4_challenge_DiaryManager-1.0-SNAPSHOT.jar
    ```

## Menu Options

1.  **Write a new entry**
2.  **Read an entry**
3.  **List all entries**
4.  **Search entries**
5.  **Update Configuration**
6.  **View Configuration**
7.  **Backup Entries**
8.  **Exit**
