import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MapReader {
    private String fileName;
    MapComponent[][] mapLayout;
    Scanner fileReader;

    MapReader (String fileName) {
        this.fileName = fileName;

        // creates file reader to initiate map
        try {
            fileReader = new Scanner(new File("res/" + fileName));
            readMap();
        } catch (Exception e) {
        }
    }

    void readMap() throws Exception{
        String line;
        ArrayList<String> mapChar = new ArrayList<>();
        int length = 0;
        int width = 0;
        int index = 0;



        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            length++;
            width = line.length();
            mapChar.add(line);
        }

        System.out.println(length + " " + width);

        mapLayout = new MapComponent[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= width -  1; j++) {
                if (mapChar.get(index).substring(j, j + 1).equals("r")){
                    mapLayout[i][j] = new Road(150, i * 150, j * 150, true);
                    mapLayout[i][j].setyPosition(j* (int)mapLayout[i][j].getDimensions());
                    mapLayout[i][j].setxPosition(i* (int)mapLayout[i][j].getDimensions());
                } else if (mapChar.get(index).substring(j, j + 1).equals("w")) {
                    mapLayout[i][j] = new Wall(150, i * 150, j * 150, true);
                    mapLayout[i][j].setyPosition(j* (int)mapLayout[i][j].getDimensions());
                    mapLayout[i][j].setxPosition(i* (int)mapLayout[i][j].getDimensions());
                }
            }
            index++;
        }

    }

    public MapComponent[][] getMap () {
        return mapLayout;
    }

}
