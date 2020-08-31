package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.nio.file.FileAlreadyExistsException;
import java.util.*;



//toUppercase???
//file stuff - getting it from different directiories
public class Names {

    public static void main (String[] args) throws FileNotFoundException {
        Names instance = new Names();


        //Call methods for female top name, male top name, and letter/gender algorithm
        //System.out.println(instance.femaleTopRanked(2000));
        //System.out.println(instance.maleTopRanked());
        //System.out.println(instance.letter("F","Z"));
        //System.out.println(instance.nameGenderRank("Emily","F"));
        //System.out.println(instance.nameGenderPair(2000,"Zyshaun", "M"));
        //instance.rangeOfYears(2000,2010, "L");
        instance.popularGirls(2000,2010);
    }


    //get the 2d array for a given year
    // Was originally going to make this a void method, but when working with multiple years at once, need this to return a different array each time so changed to String[][]
    public String[][] getArray(int year) throws FileNotFoundException {
        boolean check = true;
        List<String> names = new ArrayList<String>();
        int lines = 0;
        String[][] ret;

        try {
            //File myObj = new File("data/yob" + year + ".txt");
            File myObj = new File("data/ssa_complete/yob" + year + ".txt");
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
            check = true;
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
    //Edge cases -
    // there are no females in the file,
    // the year isn't in the data set!
    public String femaleTopRanked(int year) throws FileNotFoundException {
        String[][] name_arr = getArray(year);

        //Initialize return string to indicate no females found
        String femTop = "There are no females in this dataset";
        //If the first row entry of the 2D array has middle column = "F", then take the first column of that row's entry as the top name
        if (name_arr[0][1].equals("F")){
            femTop = "The top ranked female name is: " + name_arr[0][0];
        }
        return femTop;
    }

    //Method to find the top ranked male name in the file
    //Edge cases -
    // no males in the file
    // input year isn't in the data set!
    public String maleTopRanked(int year ) throws FileNotFoundException {
        String[][] name_arr = getArray(year);
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
    //Edge cases - gender/letter combo doesn't exist
    //Year isn't in dataset!
    public String letter(int year, String gender,String letter) throws FileNotFoundException {
        String[][] name_arr = getArray(year);
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
    //How are you supposed to input the decade?
    //What about if the name isn't found?
    //Edge cases -
    //name doesn't exist in file!
    //gender doesn't exist in the file!

    public List<Integer> nameGenderRank(String name, String gender) throws FileNotFoundException {
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
    //What if a rank exists in one file but not another - unresolved
    //What if a name/gender doesn't exist in the specified year - good
    public String nameGenderPair(int year, String name, String gender) throws FileNotFoundException {
        //get the most recent year
        String ret = "The name " + name + " and gender " + gender + " does not exist for this dataset";
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
        return "The name " + name + " and gender " + gender + " corresponds to the same popularity as the name " + ret + " in the most recent year in the dataset.";
    }

    //works, need to design the return part better
    //edge cases
    //year not in dataset - unresolved
    //gender not in dataset - resolved
    public void rangeOfYears(int start, int end, String gender) throws FileNotFoundException {
        Map<String, Integer> rank_map = new HashMap<String, Integer>();
        Map<String, Integer> ret = new HashMap<String, Integer>();
        boolean check = false;
        //File dir = new File("data/ssa_2000s");
        //File[] directoryListing = dir.listFiles();
        for (int year = start; year <= end; year++) {
            //String[][] curr_arr = getArray(Integer.parseInt(child.getName().substring(3, 7)));
            String[][] curr_arr = getArray(year);
            if (gender.equals("F")) {
                check = true;
                String top = curr_arr[0][0];
                if (rank_map.containsKey(top)) {
                    rank_map.put(top, rank_map.get(top) + 1);
                } else {
                    rank_map.put(top, 1);
                }
            } else if (gender.equals("M")){
                check = true;
                for (int row = 0; row < curr_arr.length; row++) {
                    if (curr_arr[row][1].equals("M")) {
                        String top = curr_arr[row][0];
                        if (rank_map.containsKey(top)) {
                            rank_map.put(top, rank_map.get(top) + 1);
                        } else {
                            rank_map.put(top, 1);
                        }
                        break;
                    }
                }
            }
        }

        /*
        for (Map.Entry entry : rank_map.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }
         */

        //need to design this better
        //we need to go into the the map, find the max value, and then get all keys associated with that value
        //for now, lets return in key:value format like above
        int max = 0;
        for (String key : rank_map.keySet()) {
            if (rank_map.get(key) > max) {
                max = rank_map.get(key);
            }
        }

        for (String key : rank_map.keySet()) {
            if (rank_map.get(key) == max) {
                ret.put(key, rank_map.get(key));
            }
        }

        if (check == true) {
            for (Map.Entry entry : ret.entrySet()) {
                //System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
                System.out.println("From " + start + " to " + end + " for gender " + gender + ", the most popular name was " + entry.getKey() + " and it had the top rank " + entry.getValue() + " times.");
            }
        }
        else {
            System.out.println("From " + start + " to " + end + ", gender " + gender + " does not exist in the dataset.");
        }
    }

        //edge cases
        //year not in data set - unresolved
        public void popularGirls(int start, int end) throws FileNotFoundException {
            Map<String, Integer> letter_map = new TreeMap<String, Integer>();
            for (int year = start; year <= end; year++) {
                String[][] curr_arr = getArray(year);
                for (int row = 0; row < curr_arr.length; row++) {
                    if (curr_arr[row][1].equals("F")) {
                        String letter = curr_arr[row][0].substring(0, 1);
                        if (letter_map.containsKey(letter)) {
                            letter_map.put(letter, letter_map.get(letter) + Integer.parseInt(curr_arr[row][2]));
                        } else {
                            letter_map.put(letter, Integer.parseInt(curr_arr[row][2]));
                        }
                    }
                }
            }

            //now that we have a map of all the letters, we want to go through that map, and find the highest value
            // once we get that value, return all names associated with the value
            // which means we once again need to loop through all of the years?
            // seems innefficent - should we have stored the name somewhere?
            //now, if we have ties -> thus will always give us alphabetically first because of the treeset

            int max =0;
            String letter_max = "";
            for (String key: letter_map.keySet()){
                if (letter_map.get(key) > max){
                    max = letter_map.get(key);
                    letter_max = key;
                }
            }

            Set<String> ret = new TreeSet<>();
            for (int year = start; year <= end; year++) {
                String[][] curr_arr = getArray(year);
                for (int row = 0; row < curr_arr.length; row++) {
                    if (curr_arr[row][1].equals("F") && curr_arr[row][0].substring(0,1).equals(letter_max)){
                        ret.add(curr_arr[row][0]);
                    }
                    }
                }


            for (Map.Entry entry : letter_map.entrySet())
            {
                System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
            }

            System.out.println(ret);

        }



    }






