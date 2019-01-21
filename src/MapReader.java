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

    /**
     * Constructor
     * @param fileName
     */
    MapReader (String fileName) {
        this.fileName = fileName;

        // creates file reader to initiate map
        try {
            //fileReader = new Scanner(new File("res/" + fileName));
            fileReader = new Scanner(new File(fileName));

            readMap();
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("error finding map file");
        }
    }

    /**
     * readMap
     * Constructs a 2D array out of a text file
     * @throws Exception
     */
    void readMap() throws Exception{
        String line;
        ArrayList<String> mapChar = new ArrayList<>();
        int length = 0;
        int width = 0;
        int index = 0;
        int markerNum = 0;
        boolean marker = false;
        int rightIndex = 0;


        //calculates the length of the text file for the array
        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            length++;
            width = line.length();
            mapChar.add(line);
        }

        //creates array
        mapLayout = new MapComponent[length][width];

        //initializes all objects in the array
        for (int i = 0; i < length; i++) {
            marker = false;
            for (int j = 0; j <= width -  1; j++) {

                //road
                if (mapChar.get(index).substring(j, j + 1).equals("r")){
                    mapLayout[i][j] = new Road(150, j * 150, i * 150, true);

                    //wall
                } else if (mapChar.get(index).substring(j, j + 1).equals("w")) {
                    mapLayout[i][j] = new Wall(150, j * 150, i * 150, false);

                    //road marker
                } else if (mapChar.get(index).substring(j, j + 1).equals("m")) {
                    mapLayout[i][j] = new Marker(150, j * 150, i * 150, true, markerNum);
                    if (!marker) {
                        markerList.add(mapLayout[i][j]);
                    }

                    //finish marker
                } else if (mapChar.get(index).substring(j, j + 1).equals("f")) {
                    mapLayout[i][j] = new FinishMarker (150, j * 150, i * 150, true);
                }
            }
            if (marker) {
                markerNum++;
            }
            index++;
        }

        //adjusts hit boxes for markers to make it easier to detect intersections in the game
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
