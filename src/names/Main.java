package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files


/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    private static List<String> names =new ArrayList<String>();
    private static int lines;
    private static String[][] name_arr;

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        Main instance = new Main();

        try {
            File myObj = new File("C:\\Users\\16095\\Desktop\\CS307\\data_team07\\data\\yob1900split.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                lines ++;
                //String data = myReader.nextLine();
                names.add(myReader.nextLine());
            }
            name_arr = new String[lines][3];

            for(int row=0;row<name_arr.length;row++){
                for(int col=0;col<name_arr[row].length;col++){
                    name_arr[row][col] = names.get(row).split(",")[col];
                }
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(instance.femaleTopRanked());
        System.out.println(instance.maleTopRanked());
        System.out.println(Arrays.toString(instance.letter("M","J")));

    }

    public String femaleTopRanked(){
        //get the fem top name
        String femTop = name_arr[0][0];
        return femTop;
    }

    public String maleTopRanked(){
        //get male top name
        String maleTop = "";
        boolean check = true;

        for(int row=0;row<name_arr.length;row++){
            for(int col=0;col<name_arr[row].length;col++){
                if (name_arr[row][1].equals("M")) {
                    check = false;
                    maleTop = name_arr[row][0];
                    break;
                }
            }
            if (check == false) {
                break;
            }
        }
        return maleTop;
    }

    public int[] letter(String gender,String letter){
        int[] val = new int[2];
        int nameCounter = 0;
        int totalCounter = 0;

        for(int row=0;row<name_arr.length;row++){
            for(int col=0;col<name_arr[row].length;col++){
                if (name_arr[row][1].equals(gender) && name_arr[row][0].substring(0,1).equals(letter)){
                    nameCounter++;
                    totalCounter += Integer.parseInt(name_arr[row][2]);
                    break;
                }
            }
        }
        val[0] = nameCounter;
        val[1] = totalCounter;
        return val;
    }


    }

