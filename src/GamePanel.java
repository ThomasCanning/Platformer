import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener {

    Player player;
    ArrayList<Wall> walls = new ArrayList<>();

    int offset;
    int cameraX;
    int currentSet = 0;
    int nextSet = currentSet;
    int piecesComplete = 0;

    Timer gameTimer;;

    public GamePanel() {
        this.setLocation(0, 0);
        this.setBackground(Color.LIGHT_GRAY);

        player = new Player(200, 300, this);

        reset();

        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (walls.get(walls.size() - 1).x < 700) {//checks if wall is almost onscreen
                    makeWalls(offset, nextSet, false);
                    offset += 700;
                }
                player.set();
                for (Wall wall : walls) wall.set(cameraX);
                for (int i = 0; i < walls.size(); i++) {
                    if (walls.get(i).x < -800) walls.remove(i);
                }
                repaint();
            }
        }, 0, 17);
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        player.draw(g2D);
        for(Wall wall: walls) wall.draw(g2D);
    }

    private void makeWalls(int offset, int set, Boolean overrideIndex) {
        System.out.println(piecesComplete);
        LevelGeneration generator = new LevelGeneration();
        int s = 50; // size of walls
        nextSet = LevelGeneration.generateSet(currentSet);
        int index = LevelGeneration.generatePiece(currentSet);
        if(overrideIndex==true){
            index=0;
        }
        if(piecesComplete>=10){
            if(set==0||set==3){
                set=1;
            }
            else if(set==1||set==2){
                set=2;
            }
            index=0;
            nextSet=set;
        }
        for(int y = 0; y<12; y++){
            for(int x=0;x<14;x++){
                if(generator.wallGrid[set][index][x][y]==1){
                    walls.add(new Wall(offset+x*50, y*50+100, s, s));
                }
            }
        }
        currentSet=nextSet;
        piecesComplete++;
    }

    public void reset(){
        player.x = 200;
        player.y = 150;
        cameraX = 150;
        player.xspeed = 0;
        player.yspeed = 0;
        walls.clear();
        offset = -150;
        currentSet=0;
        piecesComplete=0;
        makeWalls(offset, currentSet, true);
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
