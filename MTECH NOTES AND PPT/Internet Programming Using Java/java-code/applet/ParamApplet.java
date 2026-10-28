import java.applet.Applet;
import java.awt.Graphics;

/** Reads values given in the HTML file with <param name=... value=...>. */
public class ParamApplet extends Applet {
    private String user;
    private int times;

    @Override
    public void init() {
        user = getParameter("user");                    // returns null if not given
        if (user == null) user = "Guest";
        String t = getParameter("times");
        times = (t == null) ? 1 : Integer.parseInt(t);
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < times; i++) {
            g.drawString("Welcome, " + user + "  (" + (i + 1) + ")", 20, 30 + i * 20);
        }
    }
}
