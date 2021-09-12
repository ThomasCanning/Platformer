import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyChecker implements KeyListener {

    GamePanel panel;

    public KeyChecker(GamePanel panel){
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
    }
}
