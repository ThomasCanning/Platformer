import java.awt.*;

public class Wall {
    int x;
    int y;
    int width;
    int height;
    int startX;

    Rectangle hitBox;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        startX = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitBox = new Rectangle(x, y, width, height);
    }
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.WHITE);
        g2D.fillRect(x, y, width, height);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(x, y, width, height);
    }
    public int set(int cameraX) {
        x = startX + cameraX;
        hitBox.x = x;
        return x;
    }
}
