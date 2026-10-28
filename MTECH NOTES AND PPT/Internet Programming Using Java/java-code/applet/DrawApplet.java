import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

/** Basic graphics: line, rectangle, oval, filled shapes, colours. */
public class DrawApplet extends Applet {

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(10, 10, 200, 10);
        g.drawRect(10, 20, 100, 50);               // x, y, width, height
        g.setColor(Color.RED);
        g.fillRect(130, 20, 70, 50);
        g.setColor(Color.GREEN);
        g.drawOval(10, 90, 100, 60);
        g.setColor(Color.BLUE);
        g.fillOval(130, 90, 70, 60);
        g.setColor(Color.MAGENTA);
        g.drawString("Shapes drawn with Graphics", 10, 175);
    }
}
