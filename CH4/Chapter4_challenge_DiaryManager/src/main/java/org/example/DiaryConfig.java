package org.example;

import java.io.Serializable;

public class DiaryConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String author;
    private boolean backupsEnabled;

    public DiaryConfig(String author, boolean backupsEnabled) {
        this.author = author;
        this.backupsEnabled = backupsEnabled;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean areBackupsEnabled() {
        return backupsEnabled;
    }

    public void setBackupsEnabled(boolean backupsEnabled) {
        this.backupsEnabled = backupsEnabled;
    }

    @Override
    public String toString() {
        return "DiaryConfig{" +
                "author='" + author + '\'' +
                ", backupsEnabled=" + backupsEnabled +
                '}';
    }
}
