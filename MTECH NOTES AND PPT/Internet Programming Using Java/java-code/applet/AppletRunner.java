import java.applet.Applet;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Modern JDKs have no appletviewer and browsers cannot run applets.
 * This launcher puts an applet inside a normal window:   java AppletRunner ClickApplet
 */
public class AppletRunner {
    public static void main(String[] args) throws Exception {
        String className = args.length > 0 ? args[0] : "HelloApplet";
        Applet applet = (Applet) Class.forName(className).getDeclaredConstructor().newInstance();

        Frame frame = new Frame(className);
        frame.add(applet);
        frame.setSize(300, 220);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                applet.stop();
                applet.destroy();
                frame.dispose();
            }
        });
        applet.init();            // same order the browser would use
        applet.start();
        frame.setVisible(true);
    }
}
