package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

//adding rank field - start at 1, increment every time you go to the next line UNLESS the next line has count field equal to the prev --> add the rank in


public class Main {
    // Declare instance variables to be used throughout different methods
    private static List<String> names =new ArrayList<String>();
    private static int lines;
    private static String[][] name_arr;


    public static void main (String[] args) {
        Main instance = new Main();

        try {
            File myObj = new File("C:\\Users\\16095\\Desktop\\CS307\\data_team07\\data\\yob1900.txt");
            Scanner myReader = new Scanner(myObj);

            //Read file and add each line to an array list called names
            //Read file and count the number of total lines
            while (myReader.hasNextLine()) {
                lines ++;
                //String data = myReader.nextLine();
                names.add(myReader.nextLine());
            }

            //Initialize the size of 2D array with the number of lines as the number of rows, and 3 columns
            name_arr = new String[lines][4];


            //Populate 2D array by splitting each entry of the array list and placing the three items of each entry into the three columns of the array
            for(int row=0;row<name_arr.length;row++){
                for(int col=0;col<3;col++){
                    name_arr[row][col] = names.get(row).split(",")[col];
                }
            }

            //Add the rank field - is there any way to do this within one of the existing loops?

            int rank = 1;
            for(int row=0;row<name_arr.length;row++) {
                name_arr[row][3] = Integer.toString(rank);
                if (row < 3729) {
                    if (!name_arr[row][2].equals(name_arr[row + 1][2])) {
                        rank++;
                    }
                }
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Call methods for female top name, male top name, and letter/gender algorithm
        System.out.println(instance.femaleTopRanked());
        System.out.println(instance.maleTopRanked());
        System.out.println(instance.letter("F","Z"));

    }

    //Method to find the top ranked female name in the file
    public String femaleTopRanked(){
        //Initialize return string to indicate no females found
        String femTop = "There are no females in this dataset";
        //If the first row entry of the 2D array has middle column = "F", then take the first column of that row's entry as the top name
        if (name_arr[0][1].equals("F")){
            femTop = "The top ranked female name is: " + name_arr[0][0];
        }
        return femTop;
    }

    //Method to find the top ranked male name in the file
    public String maleTopRanked(){
        //Initialize return string to indicate no females found
        String maleTop = "There are no males in this dataset";

        //Initialize check to be false
        boolean check = false;

        //Go through 2D array, if you find a row with middle column = "M", take that row's first column as the top male name
//        for(int row=0;row<name_arr.length;row++){
//            for(int col=0;col<name_arr[row].length;col++){
//                if (name_arr[row][1].equals("M")) {
//                    check = true;
//                    maleTop = "The top ranked male name is: " + name_arr[row][0];
//                    break;
//                }
//            }
//            //If we found a male, leave the loop
//            if (check == true) {
//                break;
//            }
//        }

        for(int row=0;row<name_arr.length;row++){
            if (name_arr[row][1].equals("M")) {
                maleTop = "The top ranked male name is: " + name_arr[row][0];
                break;
            }
        }

        return maleTop;
    }

    //Method to perform the gender/starting letter algorithm
    public String letter(String gender,String letter){
        //Intialize return string to indicate that the given combination does not exist
        String ret = "Gender " + gender + " and starting letter " + letter +  " combination does not exist for this dataset";

        //Initialize counters and checking field to 0/false
        int nameCounter = 0;
        int totalCounter = 0;
        boolean check = false;

        //Go through the rows of 2D array, and for a row if the gender and starting letter of the name match the inputs, add 1 to the name counter and the count field
        // (last column entry) to the total counter
        for(int row=0;row<name_arr.length;row++){
            if (name_arr[row][1].equals(gender) && name_arr[row][0].substring(0,1).equals(letter)){
                nameCounter++;
                totalCounter += Integer.parseInt(name_arr[row][2]);
                check = true;
            }
        }

        //If the combination exists, format string as follows
        if (check == true){
            ret = "For gender " + gender + " and starting letter " + letter + ", there are " + nameCounter + " different names and " + totalCounter + " total instances";
        }

        return ret;
    }

    }

