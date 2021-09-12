import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int FRAME_WIDTH = 700;
    public static final int FRAME_HEIGHT = 700;
    MainFrame(){
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(screenSize.getWidth()/2-this.getWidth()/2),(int)(screenSize.getHeight()/2-this.getHeight()/2));
        this.setResizable(false);
        this.setTitle("Platformer");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        GamePanel panel = new GamePanel();
        panel.setSize(this.getSize());
        this.add(panel);

        addKeyListener(new KeyChecker(panel));

    }

}
