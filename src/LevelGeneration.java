import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class LevelGeneration {

    public static int[][][][] wallGrid;
    int setMax;
    static int pieceMax;
    static Random rand = new Random();

    LevelGeneration() {

        //Code checks LevelGeneration res folder for text files with binary layouts of level pieces
        Boolean setExists = true;
        int set = 0;
        int piece = 0;
        Boolean check = false;
        while (setExists == true) {

            File checkFile = new File("Platformer/res/LevelGeneration/" + set + "_" + piece + ".txt");
            if (checkFile.isFile()) {
                check = false;
                piece++;
            } else {
                if (piece > pieceMax) pieceMax = piece;
                if (check == true) setExists = false;
                else set++;
                check = true;
                piece = 0;
            }
        }

        //Creates a 4D array of the various sets of different level piece layouts and the x and y grid
        setMax = set;
        wallGrid = new int[setMax][pieceMax][14][12];

        for (int s = 0; s < setMax; s++) {
            for (int p = 0; p < pieceMax; p++) {
                try {
                    File file = new File("Platformer/res/LevelGeneration/" + s + "_" + p + ".txt");
                    if (file.createNewFile()) {
                        FileWriter writer = new FileWriter("Platformer/res/LevelGeneration/" + s + "_" + p + ".txt");
                        for(int l=1;l<=14;l++){
                            writer.write("00000000000000");
                            writer.write("\r\n");

                        }
                        writer.close();
                    }

                    Scanner reader = new Scanner(file);
                    int y = 0;
                    while (reader.hasNextLine()) {
                        String data = reader.nextLine();
                        for (int x = 0; x < data.length(); x++) {
                            wallGrid[s][p][x][y] = Integer.parseInt(String.valueOf(data.charAt(x)));
                        }
                        y++;
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Level layout doesn't exist");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int generateSet(int currentSet) {
        System.out.println("test");
        int generatedSet=0;
        switch(currentSet){
            case 0:
                System.out.println("0");
                generatedSet = rand.nextInt(2);
                System.out.println(generatedSet);
                break;
            case 1:
                System.out.println("1");
                generatedSet = currentSet+(rand.nextInt(2)+1);
                break;
            case 2:
                generatedSet = currentSet+(rand.nextInt(2));
                break;
            case 3:
                if(rand.nextInt(2)==0) generatedSet=0;
                else generatedSet=1;
                break;
        }
        System.out.println("set " + generatedSet);
        return generatedSet;
    }

    public static int generatePiece(int currentSet) {
        int generatedPiece = rand.nextInt(setSize(currentSet)-1)+1;
        return generatedPiece;
    }

    //returns the size of the current set for which piece layouts contain 1s
    static int setSize(int set) {
        boolean empty = true;
        int i=0;
        while (empty == false) {
            empty=false;
            i++;
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 14; x++) {
                    if (wallGrid[set][pieceMax -i][x][y] == 1) {
                        empty=true;
                        x=14;
                        y=14;
                    }
                }
            }
        }
        return pieceMax -i;
    }
}

/*
int index = rand.nextInt(generator.setSize(set)-1)+1;
if (nextSet<0)nextSet=0;
 if(nextSet>generator.setMax-1)nextSet=generator.setMax-1;
 */