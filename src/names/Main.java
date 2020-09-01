package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;



//toUppercase???
//file stuff - getting it from different directories
public class Main {

    public static void main (String[] args) throws FileNotFoundException {
        Main instance = new Main();


        //Call methods for female top name, male top name, and letter/gender algorithm
        //System.out.println(instance.femaleTopRanked(2010));
        //System.out.println(instance.maleTopRanked());
        //System.out.println(instance.letter("F","Z"));
        //System.out.println(instance.nameGenderRank("xyz","F"));
        //System.out.println(instance.nameGenderPair(2000,"Michael", "M"));
        //instance.rangeOfYears(2000,2010, "L");
        //System.out.println(instance.popularGirls(19003,19004));
    }


    //get the 2d array for a given year
    // Was originally going to make this a void method, but when working with multiple years at once, need this to return a different array each time so changed to String[][]
    public String[][] getArray(int year) throws FileNotFoundException {
        boolean check = true;
        List<String> names = new ArrayList<String>();
        int lines = 0;
        String[][] ret;

        //try {
            //File myObj = new File("data/yob" + year + ".txt");
            //File myObj = new File("data/TestSets/yob" + year + "split.txt");
            File myObj = new File("data/ssa_2000s/yob" + year + ".txt");
            Scanner myReader = new Scanner(myObj);

            //Read file and add each line to an array list called names
            //Read file and count the number of total lines
            while (myReader.hasNextLine()) {
                lines ++;
                //String data = myReader.nextLine();
                names.add(myReader.nextLine());
            }
            myReader.close();
        //}

        /*
        catch (FileNotFoundException e) {
            check = true;
            System.out.println("An error occurred.");
        }

         */


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
    public String femaleTopRanked(int year){
        try {
            String[][] name_arr = getArray(year);
            //Initialize return string to indicate no females found


        }
        catch (FileNotFoundException e){
            System.out.println("an error occured");
        }
        return "";
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
    //Edge cases -
    //name doesn't exist in file - resolved
    //gender doesn't exist in the file - resolved

    public String nameGenderRank(String name, String gender) throws FileNotFoundException {
        //test on ssa_2000s
        List<Integer> ret  = new ArrayList<>();
        File dir = new File("data/ssa_2000s");
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            boolean check = false;
            String[][] name_arr = getArray(Integer.parseInt(child.getName().substring(3,7)));
            for(int row=0;row<name_arr.length;row++){
                if (name_arr[row][0].equals(name) && name_arr[row][1].equals(gender)) {
                    ret.add(Integer.parseInt(name_arr[row][3]));
                    check = true;
                    break;
                }
            }
            if (check == false){
                ret.add(0);
            }

        }

        //over the span....?
        return "The rankings for the name " + name + " and gender " + gender + " are: " + ret.toString() + ", with rank 0 indicating the name/gender combination does" +
                " not exist for a year.";
    }

    //Works - file name assumption?
    //Year doesn't exist - unresolved
    //What if a rank exists in one file but not another - resolved
    //What if a name/gender doesn't exist in the specified year OR recent year- good
    public String nameGenderPair(int year, String name, String gender) throws FileNotFoundException {
        //get the most recent year
        boolean check_year = false;
        boolean check_recent = false;
        String ret = "Either the combination of name " + name + " and gender " + gender + " in " + year + " does not exist for the specified year / the most recent year in this dataset" +
                ", or the ranking of this combination does not correspond to an existing rank in the most recent year.";
        int max = 0;
        int year_rank = 0;
        //List<Integer> checker = new ArrayList<>();
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
                check_year = true;
                year_rank = Integer.parseInt(year_arr[row][3]);
                break;
            }
        }

        //go to the most recent year and get the name/gender at that rank
        String[][] recent_arr = getArray(max);
        for(int row=0;row<recent_arr.length;row++){
            if (Integer.parseInt(recent_arr[row][3]) == year_rank && recent_arr[row][1].equals(gender)) {
                check_recent= true;
                ret = recent_arr[row][0];
                break;
            }
        }

        if (check_recent && check_year){
            return "The combination of name " + name + " and gender " + gender + " in " + year + " corresponds to the same popularity as the name " + ret + " in the most recent year in the dataset.";
        }
        else{
            return ret;
        }
    }

    //works, need to design the return part better
    //edge cases
    //year not in dataset - unresolved
    //gender not in dataset - resolved
    public String rangeOfYears(int start, int end, String gender) throws FileNotFoundException {
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
                return "From " + start + " to " + end + " for gender " + gender + ", the most popular name was " + entry.getKey() + " and it had the top rank " + entry.getValue() + " times";
            }
        }
        else {
            return "From " + start + " to " + end + ", gender " + gender + " does not exist in the dataset";
        }
        return "";
    }

        //edge cases
        //year not in data set - unresolved
        //no girls in the dataset
        public String popularGirls(int start, int end) throws FileNotFoundException {
        boolean check = false;
            Map<String, Integer> letter_map = new TreeMap<String, Integer>();
            for (int year = start; year <= end; year++) {
                String[][] curr_arr = getArray(year);
                for (int row = 0; row < curr_arr.length; row++) {
                    if (curr_arr[row][1].equals("F")) {
                        check = true;
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

            if (check == true) {
                return "From " + start + " to " + end + ", the most popular letter that girls' names started with was " + letter_max + ", and the female names in the dataset starting with " + letter_max + " are: " + ret.toString();
            }
            else{
                return "There are no females in this dataset";
            }
        }


    }






