import java.awt.*;
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
    ArrayList<MapComponent> markerList = new ArrayList<>();
    ArrayList<MapComponent> markerListRight = new ArrayList<>();

    MapReader (String fileName) {
        this.fileName = fileName;

        // creates file reader to initiate map
        try {
            fileReader = new Scanner(new File("res/" + fileName));
            readMap();
        } catch (Exception e) {
            System.err.println("error finding map file");
        }
    }

    void readMap() throws Exception{
        String line;
        ArrayList<String> mapChar = new ArrayList<>();
        int length = 0;
        int width = 0;
        int index = 0;
        int markerNum = 0;
        boolean marker = false;
        int rightIndex = 0;



        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            length++;
            width = line.length();
            mapChar.add(line);
        }

        System.out.println(length + " " + width);

        mapLayout = new MapComponent[length][width];
        for (int i = 0; i < length; i++) {
            marker = false;
            for (int j = 0; j <= width -  1; j++) {
                if (mapChar.get(index).substring(j, j + 1).equals("r")){
                    mapLayout[i][j] = new Road(150, j * 150, i * 150, true);

                } else if (mapChar.get(index).substring(j, j + 1).equals("w")) {
                    mapLayout[i][j] = new Wall(150, j * 150, i * 150, false);

                } else if (mapChar.get(index).substring(j, j + 1).equals("m")) {
                    mapLayout[i][j] = new Marker(150, j * 150, i * 150, true, markerNum);
                    if (!marker) {
                        markerList.add(mapLayout[i][j]);
                    }

                } else if (mapChar.get(index).substring(j, j + 1).equals("f")) {
                    mapLayout[i][j] = new FinishMarker (150, j * 150, i * 150, true);
                }
            }
            if (marker) {
                markerNum++;
            }
            index++;
        }

        for (MapComponent m : markerList) {
            rightIndex = m.getxPosition();
            while (!(mapLayout[(m.getyPosition()/150) - 1][rightIndex] instanceof Wall)) {
                rightIndex++;
            }
            m.getHitBox().setSize(rightIndex * 150, 150);
        }

    }

    public MapComponent[][] getMap () {
        return mapLayout;
    }

    public ArrayList<MapComponent> getMarkerList () {
        return markerList;
    }

}
