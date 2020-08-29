package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

//adding rank field - start at 1, increment every time you go to the next line UNLESS the next line has count field equal to the prev --> add the rank in
// need to get data structures for multiple files
// should have a getData strucutre method
// let's say I want to run gender/name rank for the 2000s :


//toUppercase???
public class Main {

    public static void main (String[] args) {
        Main instance = new Main();

        //Call methods for female top name, male top name, and letter/gender algorithm
        //System.out.println(instance.femaleTopRanked());
        //System.out.println(instance.maleTopRanked());
        //System.out.println(instance.letter("F","Z"));
        //System.out.println(instance.nameGenderRank("Emily","F"));
        //System.out.println(instance.nameGenderPair(2000,"Emily", "F"));
        instance.rangeOfYears(2000,2009, "F");
    }


    //get the 2d array for a given year
    // Was originally going to make this a void method, but when working with multiple years at once, need this to return a different array each time so changed to String[][]
    public String[][] getArray(int year){
        List<String> names = new ArrayList<String>();
        int lines = 0;
        String[][] ret;

        try {
            //File myObj = new File("data/yob" + year + ".txt");
            File myObj = new File("data/ssa_2000s/yob" + year + ".txt");
            //ssa_2000s/yob2000.txt
            Scanner myReader = new Scanner(myObj);

            //Read file and add each line to an array list called names
            //Read file and count the number of total lines
            while (myReader.hasNextLine()) {
                lines ++;
                //String data = myReader.nextLine();
                names.add(myReader.nextLine());
            }
            myReader.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


            //Initialize the size of 2D array with the number of lines as the number of rows, and 3 columns
            ret = new String[lines][4];


            //Populate 2D array by splitting each entry of the array list and placing the three items of each entry into the three columns of the array
            for(int row=0;row<ret.length;row++){
                for(int col=0;col<3;col++){
                    ret[row][col] = names.get(row).split(",")[col];
                }
            }

            //Add the rank field - is there any way to do this within one of the existing loops?
            int rank = 1;
            for(int row=0;row<ret.length;row++) {
                ret[row][3] = Integer.toString(rank);
                if (row < lines-1) {
                    if (!ret[row][2].equals(ret[row + 1][2])) {
                        rank++;
                    }
                }
            }
            return ret;
    }


    //Method to find the top ranked female name in the file
    public String femaleTopRanked(){
        String[][] name_arr = getArray(2000);

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
        String[][] name_arr = getArray(2000);
        //Initialize return string to indicate no females found
        String maleTop = "There are no males in this dataset";

        //Initialize check to be false
        boolean check = false;


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
        String[][] name_arr = getArray(2000);
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

    //Works - file name assumption?
    //What about if the name isn't found?
    public List<Integer> nameGenderRank(String name, String gender){
        //test on ssa_2000s
        List<Integer> ret  = new ArrayList<>();
        File dir = new File("data/ssa_2000s");
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            String[][] name_arr = getArray(Integer.parseInt(child.getName().substring(3,7)));
            for(int row=0;row<name_arr.length;row++){
                if (name_arr[row][0].equals(name) && name_arr[row][1].equals(gender)) {
                    ret.add(Integer.parseInt(name_arr[row][3]));
                    break;
                }
            }

        }

        return ret;
    }

    //Works - file name assumption?
    //What if a rank exists in one file but not another?
    //What if a name/gender doesn't exist in the specified year
    public String nameGenderPair(int year, String name, String gender) {
        //get the most recent year
       String ret = "";
        int max = 0;
        int year_rank = 0;
        List<Integer> check = new ArrayList<>();
        File dir = new File("data/ssa_2000s");
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            int curr = Integer.parseInt(child.getName().substring(3, 7));
            if (curr > max) {
                max = curr;
            }
        }

        //get the rank of year in question
        String[][] year_arr = getArray(year);
        for(int row=0;row<year_arr.length;row++){
            if (year_arr[row][0].equals(name) && year_arr[row][1].equals(gender)) {
                year_rank = Integer.parseInt(year_arr[row][3]);
                break;
            }
        }

        //go to the most recent year and get the name/gender at that rank
        String[][] recent_arr = getArray(max);
        for(int row=0;row<recent_arr.length;row++){
            if (Integer.parseInt(recent_arr[row][3]) == year_rank && recent_arr[row][1].equals(gender)) {
                ret = recent_arr[row][0];
                break;
            }
        }
        return ret;
    }

    //works, need to design the return part better
    //edge cases -
    public void rangeOfYears(int start, int end, String gender){
        Map<String, Integer> rank_map = new HashMap<String, Integer>();
        Map<String,Integer> ret = new HashMap<String, Integer>();
        //File dir = new File("data/ssa_2000s");
        //File[] directoryListing = dir.listFiles();
        for (int year = start; year <= end; year++) {
            //String[][] curr_arr = getArray(Integer.parseInt(child.getName().substring(3, 7)));
            String[][] curr_arr = getArray(year);
            if (gender.equals("F")){
                String top = curr_arr[0][0];
                if (rank_map.containsKey(top)){
                    rank_map.put(top, rank_map.get(top) + 1);
                }
                else{
                    rank_map.put(top, 1);
                }
            }
            else {
                for (int row = 0; row < curr_arr.length; row++) {
                    if (curr_arr[row][1].equals("M")) {
                        String top = curr_arr[row][0];
                        if (rank_map.containsKey(top)){
                            rank_map.put(top, rank_map.get(top) + 1);
                        }
                        else{
                            rank_map.put(top, 1);
                        }
                        break;
                    }
                }
            }
            }
        for (Map.Entry entry : rank_map.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        //need to design this better
        //we need to go into the the map, find the max value, and then get all keys associated with that value
        //for now, lets return in key:value format like above

        int max =0;
        for (String key : rank_map.keySet())
        {
            if (rank_map.get(key) >max){
                max = rank_map.get(key);
            }
        }

        for (String key : rank_map.keySet())
        {
            if (rank_map.get(key) == max){
                ret.put(key, rank_map.get(key));
            }
        }

        for (Map.Entry entry : ret.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        }

        public void 


    }





        // go to recent year, get the rank at that gender,




