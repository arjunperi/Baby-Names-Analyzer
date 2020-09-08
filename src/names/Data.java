package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Data {

    public String[][] getArray(int year) {

        List<String> names = new ArrayList<>();
        int number_of_lines = 0;
        String[][] names_array;
        String[][] null_array = new String[0][0];

        try {
            File myObj = new File("data/TestSets/yob" + year + "split.txt");
            Scanner myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {
                number_of_lines++;
                names.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            return null_array;
        }

        names_array = new String[number_of_lines][4];

        Map<String, Integer> gender_count = new HashMap<>();
        int rank = 1;
        for (int row = 0; row < names_array.length; row++) {
            for (int col = 0; col < 3; col++) {
                names_array[row][col] = names.get(row).split(",")[col];
            }
            int first_line = 0;
            if (row == first_line) {
                names_array[row][3] = Integer.toString(rank);
            }

            else if (row < number_of_lines) {
                if (!names_array[row][2].equals(names_array[row - 1][2])) {
                    rank++;
                }
                if((!names_array[row][1].equals(names_array[row-1][1])) && (names_array[row-1][1].equals("F"))){
                    rank = 1;
                }
                names_array[row][3] = Integer.toString(rank);
            }
        }
        return names_array;
    }
}





