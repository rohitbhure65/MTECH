import java.applet.Applet;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Applet life cycle + button event handling + repaint(). */
public class ClickApplet extends Applet implements ActionListener {
    private Button button;
    private Label label;
    private int clicks = 0;
    private final StringBuilder log = new StringBuilder();

    @Override
    public void init() {                       // 1. once, when the applet is loaded
        log.append("init ");
        button = new Button("Click me");
        label = new Label("Clicks: 0     ");
        button.addActionListener(this);        // register the listener
        add(button);
        add(label);
    }

    @Override
    public void start() { log.append("start "); }     // 2. every time the page becomes visible

    @Override
    public void stop() { log.append("stop "); }       // 3. when the page is hidden

    @Override
    public void destroy() { log.append("destroy "); } // 4. once, when the applet is removed

    @Override
    public void actionPerformed(ActionEvent e) {
        clicks++;
        label.setText("Clicks: " + clicks);
        repaint();                                    // asks AWT to call paint() again
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Life cycle so far: " + log, 10, 80);
    }
}
