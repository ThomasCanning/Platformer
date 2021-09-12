import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LevelGeneration {

    public int[][][][] wallGrid;
    int setMax;
    int piecemax = 0;

    LevelGeneration() {

        //Code checks LevelGeneration res folder for text files with binary layouts of level pieces
        Boolean setExists = true;
        int set = 0;
        int piece = 0;
        Boolean check = false;
        while (setExists == true) {
            File checkFile = new File("res/LevelGeneration/" + set + piece + ".txt");
            if (checkFile.isFile()) {
                check = false;
                piece++;
            } else {
                if (piece > piecemax) piecemax = piece;
                if (check == true) setExists = false;
                else set++;
                check = true;
                piece = 0;
            }
        }

        //Creates a 4D array of the various sets of different level piece layouts and the x and y grid
        setMax = set;
        wallGrid = new int[setMax][piecemax][14][14];

        for (int s = 0; s < setMax; s++) {
            for (int p = 0; p < piecemax; p++) {
                try {
                    File file = new File("res/LevelGeneration/" + s + "" + p + ".txt");
                    if (file.createNewFile()) {
                        FileWriter writer = new FileWriter("res/LevelGeneration/" + s + "" + p + ".txt");
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

    //returns the size of the current set for which piece layouts contain 1s
    int setSize(int set) {
        boolean empty = true;
        int i=0;
        while (empty == false) {
            empty=false;
            i++;
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 14; x++) {
                    if (wallGrid[set][piecemax-i][x][y] == 1) {
                        empty=true;
                        x=14;
                        y=14;
                    }
                }
            }
        }
        return piecemax-i;
    }
}