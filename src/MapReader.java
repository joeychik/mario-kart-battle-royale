import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Tester {
    public static void main (String[] args) {
        MapReader map = new MapReader("MapOne");
    }
}

public class MapReader {
    private String fileName;
    String[][] mapLayout;
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

        mapLayout = new String[length][width];
        for (int i = 0; i < length; i++) {

            for (int j = 0; j <= width - 1; j++) {
                mapLayout[i][j] = mapChar.get(index).substring(j, j + 1);
            }
            index++;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= width - 1; j++) {
               System.out.print(mapLayout[i][j]);
            }
            System.out.println(" ");
        }
    }

}
