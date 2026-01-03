package ecolife;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public class ImageGenerator {
    public static void main(String[] args) {
        // The image URL from Getty Images.
        String imageUrl = "https://media.gettyimages.com/id/1339751014/photo/rain-in-the-forest-vancouver-canada.jpg?s=612x612&w=0&k=20&c=9OWQKb3u7p2DpSDrxDTS7uBqawrjtuKycrcthLVNpZA=";
        String destinationFile = "src/main/resources/forest.jpg";

        try {
            downloadImage(imageUrl, destinationFile);
            System.out.println("Image from Getty Images downloaded successfully to: " + destinationFile);
        } catch (IOException e) {
            System.err.println("Error downloading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void downloadImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        // Getty Images may require a user-agent to allow the download.
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");

        File file = new File(destinationFile);
        
        // Ensure directory exists
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedInputStream in = new BufferedInputStream(httpcon.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }
}
