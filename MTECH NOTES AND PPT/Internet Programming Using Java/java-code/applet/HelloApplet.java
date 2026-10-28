import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** The smallest applet. paint() draws on the applet's screen area. */
public class HelloApplet extends Applet {

    @Override
    public void paint(Graphics g) {              // NOTE: the method is paint, not pain!
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Hello, Applet!", 50, 60);   // text, x, y (y = baseline)
    }
}
