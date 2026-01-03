package org.example.brandedweatherwidget;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Fill with a forest green color
        g2d.setColor(new Color(34, 139, 34)); // ForestGreen
        g2d.fillRect(0, 0, width, height);

        // Add some "trees" (simple darker rectangles)
        g2d.setColor(new Color(0, 100, 0)); // DarkGreen
        for (int i = 0; i < 20; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (height / 2 + Math.random() * (height / 2));
            int w = 50 + (int) (Math.random() * 100);
            int h = height - y;
            g2d.fillRect(x, y, w, h);
        }

        g2d.dispose();

        File file = new File("src/main/resources/forest.jpg");
        try {
            // Ensure directory exists
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            ImageIO.write(bufferedImage, "jpg", file);
            System.out.println("forest.jpg created successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error creating image: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
