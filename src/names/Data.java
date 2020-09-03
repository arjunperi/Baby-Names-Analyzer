package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//code smells saying this shouldn't be a class??
public class Data {

        //get the 2d array for a given year
    // Was originally going to make this a void method, but when working with multiple years at once, need this to return a different array each time so changed to String[][]
    public String[][] getArray(int year) {
        List<String> names = new ArrayList<>();
        int lines = 0;
        String[][] ret;
        String[][] null_array= new String[0][0];

        try {
            File myObj = new File("data/TestSets/yob" + year + "split.txt");
            Scanner myReader = new Scanner(myObj);

            //Read file and add each line to an array list called names
            //Read file and count the number of total lines
            while (myReader.hasNextLine()) {
                lines++;
                names.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            return null_array;
        }

        //Initialize the size of 2D array with the number of lines as the number of rows, and 3 columns
        ret = new String[lines][4];

        //Populate 2D array by splitting each entry of the array list and placing the three items of each entry into the three columns of the array
        int rank = 1;
        for (int row = 0; row < ret.length; row++) {
            for (int col = 0; col < 3; col++) {
                ret[row][col] = names.get(row).split(",")[col];
            }
            if (row == 0){
                ret[row][3] = Integer.toString(rank);
            }
            else if (row<lines){
                if (!ret[row][2].equals(ret[row-1][2])){
                    rank++;
                }
                ret[row][3] = Integer.toString(rank);
            }
        }
        return ret;
    }

}
