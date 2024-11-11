import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A program that allows users to draw multiple polygons on a panel using mouse clicks.
 * Each polygon is preserved until the spacebar is pressed to clear the canvas.  Arrow keys start a new polygon.
 */
public class PolygonDrawer extends JPanel implements MouseListener, KeyListener {

    private List<List<Point>> polygons;     // Stores all drawn polygons
    private List<Point> currentPolygon;     // Stores points for the polygon currently being drawn

    /**
     * Constructor for the PolygonDrawer class.
     * Initializes lists and adds listeners.
     */
    public PolygonDrawer() {
        polygons = new ArrayList<>();
        currentPolygon = new ArrayList<>();
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true); //For keyboard focus
    }

    /**
     * Paints the component, drawing all stored polygons and the current polygon.
     * @param g The Graphics context to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Draw all completed polygons
        for (List<Point> polygon : polygons) {
            drawPolygon(g, polygon);
        }

        // Draw the currently active polygon
        if (!currentPolygon.isEmpty()) {
            drawPolygon(g, currentPolygon);
        }
    }

    /**
     * Helper method to draw a polygon given a list of points.
     * @param g The Graphics context.
     * @param polygon The list of points defining the polygon.
     */
    private void drawPolygon(Graphics g, List<Point> polygon) {
        for (int i = 0; i < polygon.size() - 1; i++) {
            g.drawLine(polygon.get(i).x, polygon.get(i).y, polygon.get(i + 1).x, polygon.get(i + 1).y);
        }
        for (Point p : polygon) {
            g.drawString("(" + p.x + ", " + p.y + ")", p.x + 5, p.y + 15);
        }
    }


    /**
     * Handles mouse clicks; adds the click coordinates to the current polygon.
     * @param e The MouseEvent.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        currentPolygon.add(new Point(e.getX(), e.getY()));
        repaint();
    }

    // Unused MouseListener methods
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}


    /**
     * Handles key presses; clears the canvas or starts a new polygon.
     * @param e The KeyEvent.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            polygons.clear();
            currentPolygon.clear();
            repaint();
        } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            if (!currentPolygon.isEmpty()) {
                polygons.add(new ArrayList<>(currentPolygon));
            }
            currentPolygon.clear();
            repaint();
        }
    }

    // Unused KeyListener methods
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}


    /**
     * Main method to set up and display the GUI.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygon Drawer");
        PolygonDrawer panel = new PolygonDrawer();
        frame.add(panel);
        frame.setSize(500, 300);  // Set the frame size to 500x300 pixels
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
