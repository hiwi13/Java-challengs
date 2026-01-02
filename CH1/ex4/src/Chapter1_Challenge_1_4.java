

import java.io.*;

public class Chapter1_Challenge_1_4 {

    public static void main(String[] args) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("config.txt"));

            // Read first line (version)
            String versionLine = reader.readLine();
            int version = Integer.parseInt(versionLine);

            if (version < 2) {
                throw new Exception("Config version too old!");
            }

            // Read second line (file path)
            String filePath = reader.readLine();
            File file = new File(filePath);

            if (!file.exists()) {
                throw new IOException("File not found at path: " + filePath);
            }

            System.out.println("Config read successfully! Version: " + version);

        } catch (FileNotFoundException e) {
            System.out.println("Error: Config file not found!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Config version must be a number!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Config read attempt finished.");
            try {
                if (reader != null) reader.close();
            } catch (IOException ignored) {}
        }
    }
}