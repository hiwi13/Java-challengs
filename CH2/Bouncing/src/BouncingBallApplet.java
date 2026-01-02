import java.applet.Applet;
import java.awt.*;

@SuppressWarnings("removal")
public class BouncingBallApplet extends Applet implements Runnable {
    private Thread thread;
    private int x = 50;      // Starting x position
    private int y = 50;      // Starting y position
    private int dx = 5;      // Horizontal speed
    private int dy = 5;      // Vertical speed
    private int diameter = 40; // Ball size
    private Color ballColor = Color.BLACK;

    public void init() {
        setSize(400, 300);           // Applet size
        setBackground(Color.CYAN);   // Background color
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start(); // Start animation thread
        }
    }

    public void stop() {
        thread = null; // Stop the animation
    }

    public void run() {
        while (thread != null) {
            // Update ball position
            x += dx;
            y += dy;

            // Bounce off the edges
            if (x < 0 || x + diameter > getWidth()) {
                dx = -dx; // Reverse horizontal direction
            }
            if (y < 0 || y + diameter > getHeight()) {
                dy = -dy; // Reverse vertical direction
            }

            repaint(); // Redraw applet

            try {
                Thread.sleep(30); // Pause for smooth animation
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(ballColor);
        g.fillOval(x, y, diameter, diameter); // Draw the ball
    }
}