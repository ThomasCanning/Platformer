import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener {

    Player player;
    ArrayList<Wall> walls = new ArrayList<>();

    int offset;
    int cameraX;

    Timer gameTimer;;

    public GamePanel(){
        this.setLocation(0,0);
        this.setBackground(Color.LIGHT_GRAY);

        player = new Player(200, 300, this);

        reset();

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(walls.get(walls.size()-1).x<700) {//checks if wall is almost onscreen
                    makeWalls(offset, 0,false);
                    offset +=700;
                    System.out.println(walls.size());
                }
                player.set();
                for(Wall wall: walls) wall.set(cameraX);
                for(int i =0; i<walls.size();i++) {
                    if(walls.get(i).x<-800) walls.remove(i);
                }
                repaint();
            }
        },0,17);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        player.draw(g2D);
        for(Wall wall: walls) wall.draw(g2D);
    }

    private void makeWalls(int offset, int set, Boolean overrideIndex) {
        LevelGeneration generator = new LevelGeneration();
        int s = 50; // size of walls
        Random rand = new Random();
        int sizeOfSet = generator.setSize(set);
        System.out.println((generator.setSize(set)-1)+1);
        int index = rand.nextInt(generator.setSize(set)-1)+1;
        if(overrideIndex==true){
            index=0;
        }
        System.out.println("index = "+index);
        for(int y = 0; y<12; y++){
            for(int x=0;x<14;x++){
                if(generator.wallGrid[set][index][x][y]==1){
                    walls.add(new Wall(offset+x*50, y*50+100, s, s));
                    System.out.println("test "+(offset+x*50));
                }
            }
        }
    }

    public void reset(){
        System.out.println("reset");
        player.x = 200;
        player.y = 150;
        cameraX = 150;
        player.xspeed = 0;
        player.yspeed = 0;
        walls.clear();
        offset = -150;
        makeWalls(offset, 0, true);
        offset+=700;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'a':
                player.keyLeft=true;
                break;
            case 'd':
                player.keyRight=true;
                break;
            case 'w':
                player.keyUp=true;
                break;
            case 's':
                player.keyDown=true;
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'a':
                player.keyLeft=false;
                break;
            case 'd':
                player.keyRight=false;
                break;
            case 'w':
                player.keyUp=false;
                if (player.yspeed<-2) player.yspeed+=2;
                break;
            case 's':
                player.keyDown=false;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
