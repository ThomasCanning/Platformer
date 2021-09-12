import javax.swing.*;
import java.awt.*;

public class Player {

    GamePanel panel;
    int x;
    int y;
    int width;
    int height;

    double xspeed;
    double yspeed;

    Rectangle hitBox;

    public boolean keyLeft = false;
    public boolean keyRight = false;
    public boolean keyUp = false;
    public boolean keyDown = false;

    public Player(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        width = 50;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);
    }
    public void set() {
        //System.out.println(x + " " + y + " " + width + " " + height);
        if(keyLeft&&keyRight||!keyLeft&&!keyRight){
            xspeed *=0.8;
        }
        else if(keyLeft&&!keyRight) {
            xspeed--;
        }
        else if(keyRight&&!keyLeft){
            xspeed++;
        }
        if (xspeed>0 && xspeed <0.75) xspeed=0;
        if (xspeed<0 && xspeed >-0.75) xspeed=0;

        if (xspeed>7) xspeed=7;
        if (xspeed<-7) xspeed=-7;

        if(keyUp) {
            hitBox.y++;
            for(Wall wall: panel.walls) {
                if(wall.hitBox.intersects(hitBox)){
                    yspeed=-8;
                }
            }
            hitBox.y--;
        }

        if (yspeed < 8) yspeed += 0.3;

        //Horizontal collisons
        hitBox.x += xspeed;
        for(Wall wall: panel.walls) {
            if(hitBox.intersects(wall.hitBox)){ //if on floor
                hitBox.x -= xspeed;//Moves hitbox back to last place
                while(!wall.hitBox.intersects(hitBox)){
                    hitBox.x += Math.signum(xspeed);
                }
                hitBox.x -= Math.signum(xspeed); //Moves hitbox to space next to the wall
                panel.cameraX += x - hitBox.x;
                xspeed = 0;
                hitBox.x = x;
            }
        }

        //Vertical collisions
        hitBox.y += yspeed;
        for(Wall wall: panel.walls) {
            if(hitBox.intersects(wall.hitBox)){
                hitBox.y -= yspeed;
                while(!wall.hitBox.intersects(hitBox)) hitBox.y += Math.signum(yspeed);
                hitBox.y -= Math.signum(yspeed);
                yspeed = 0;
                y = hitBox.y;
            }
        }

        panel.cameraX -= xspeed;
        y += yspeed;
        hitBox.x=x;
        hitBox.y=y;

        //Death
        if(y>MainFrame.FRAME_HEIGHT+100) panel.reset();
    }
    public void draw(Graphics2D g2D) {
        g2D.setColor(Color.BLACK);
        g2D.fillRect(x, y, width,height);
    }
}
