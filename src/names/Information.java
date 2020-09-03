package names;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Information {

    Data data = new Data();

    //helper method that checks if file is valid
    private boolean isFileValid(String[][] file){
        boolean ret = true;
        if (file.length ==0){
            ret = false;
        }
        return ret;
    }

    //We should have a helper method to see if equals F or M
    private boolean checkGenderEquality(String gender_unknown, String gender_check_against){
        boolean genders_equal= false;
        if (gender_check_against.equals("F")){
            if (gender_unknown.equals("F")){
                genders_equal = true;
            }
        }
        else{
            if (gender_unknown.equals("M")){
                genders_equal = true;
            }
        }
        return genders_equal;
    }

//    //helper method to check name equality
//    private boolean checkNameEquality(String name_unknown, String name_check_against){}
//    boolean names_equal = false;
//    if ()

    //helper method to check letter equality

    //helper method to check rank equality

    //Helper method for doing file loops
    private File[] rangeOfFilesToRangeOfYears() {
        File directory = new File("data/TestSets");
        return directory.listFiles();
    }

    //helper method to get array from file name
    private String[][] getArrayFromFileName(File file){
        return data.getArray(Integer.parseInt(file.getName().substring(3,7)));
    }

    public String topRankedName(int year, String gender){
        String[][] name_arr = data.getArray(year);
        if (!isFileValid(name_arr)){
            return "There is an error with the specified file(s)";
        }
        String ret = "";
        if (checkGenderEquality(gender, "F")){
            ret = "There are no females in this dataset";
            if (checkGenderEquality(name_arr[0][1], "F")){
                ret = "The top ranked female name is: " + name_arr[0][0];
            }
        }
        else{
            ret = "There are no males in this dataset";
            for (String[] rows : name_arr) {
                if (checkGenderEquality(rows[1],"M")){
                    ret = "The top ranked male name is: " + rows[0];
                    break;
                }
            }
        }
        return ret;
    }


    //Method to perform the gender/starting letter algorithm
    //Edge cases - gender/letter combo doesn't exist
    //Year isn't in dataset!
    public String letter(int year, String gender,String letter) throws FileNotFoundException {
        String[][] name_arr = data.getArray(year);
        if (!isFileValid(name_arr)){
            return "There is an error with the specified file(s)";
        }

        //Intialize return string to indicate that the given combination does not exist
        String ret = "Gender " + gender + " and starting letter " + letter +  " combination does not exist for this dataset";

        //Initialize counters and checking field to 0/false
        int nameCounter = 0;
        int totalCounter = 0;
        boolean does_combination_exist = false;

        //Go through the rows of 2D array, and for a row if the gender and starting letter of the name match the inputs, add 1 to the name counter and the count field
        // (last column entry) to the total counter
        for (String[] rows : name_arr) {
            //check starting letter method?
            if (checkGenderEquality(rows[1],gender) && rows[0].substring(0, 1).equals(letter)) {
                nameCounter++;
                totalCounter += Integer.parseInt(rows[2]);
                does_combination_exist = true;
            }
        }

        //If the combination exists, format string as follows
        if (does_combination_exist){
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
        //have to define ret here cuz if I do it in the loop I can't call it in the print statement 
        List<Integer> ret  = new ArrayList<>();
        File[] list_of_files = rangeOfFilesToRangeOfYears();
        for (File current_file : list_of_files) {
            String[][] name_arr = getArrayFromFileName(current_file);
            if (!isFileValid(name_arr)){
                return "There is an error with the specified file(s)";
            }
            //loop through the array and get the rank associated with a name and gender
            //HAVE A NAME EQUALS METHOD?
            boolean does_name_gender_combo_exist = false;
            for (String[] rows: name_arr) {
                if (rows[0].equals(name) && checkGenderEquality(rows[1],gender)) {
                    ret.add(Integer.parseInt(rows[3]));
                    does_name_gender_combo_exist= true;
                    break;
                }
            }
            if (!does_name_gender_combo_exist){
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
        File dir = new File("data/ssa_2000s");
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            int curr = Integer.parseInt(child.getName().substring(3, 7));
            if (curr > max) {
                max = curr;
            }
        }

        //get the rank of the name within the year in question
        String[][] year_arr = data.getArray(year);
        for (String[] rows : year_arr) {
            if (rows[0].equals(name) && rows[1].equals(gender)) {
                check_year = true;
                year_rank = Integer.parseInt(rows[3]);
                break;
            }
        }

        //go to the most recent year and get the name/gender at that rank
        String[][] recent_arr = data.getArray(max);
        for (String[] rows : recent_arr) {
            if (Integer.parseInt(rows[3]) == year_rank && rows[1].equals(gender)) {
                check_recent = true;
                ret = rows[0];
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
        Map<String, Integer> rank_map = new HashMap<>();
        Map<String, Integer> ret = new HashMap<>();
        boolean check = false;
        //File dir = new File("data/ssa_2000s");
        //File[] directoryListing = dir.listFiles();
        for (int year = start; year <= end; year++) {
            //String[][] curr_arr = getArray(Integer.parseInt(child.getName().substring(3, 7)));
            String[][] curr_arr = data.getArray(year);
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
                for (String[] rows : curr_arr) {
                    if (rows[1].equals("M")) {
                        String top = rows[0];
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

        if (check) {
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
        Map<String, Integer> letter_map = new TreeMap<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            for (String[] rows : curr_arr) {
                if (rows[1].equals("F")) {
                    check = true;
                    //just check if length of return is >0
                    String letter = rows[0].substring(0, 1);
                    if (letter_map.containsKey(letter)) {
                        letter_map.put(letter, letter_map.get(letter) + Integer.parseInt(rows[2]));
                    } else {
                        letter_map.put(letter, Integer.parseInt(rows[2]));
                    }
                }
            }
        }

        //now that we have a map of all the letters, we want to go through that map, and find the highest value
        // once we get that value, return all names associated with the value
        // which means we once again need to loop through all of the years?
        // seems innefficent - should we have stored the name somewhere?
        //now, if we have ties -> thus will always give us alphabetically first because of the treeset

        //lambda
        int max =0;
        String letter_max = "";
        for (String key: letter_map.keySet()){
            if (letter_map.get(key) > max){
                max = letter_map.get(key);
                letter_max = key;
            }
        }

        //use get or lambda
        //see other methods that use this
        Set<String> ret = new TreeSet<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            for (String[] rows : curr_arr) {
                if (rows[1].equals("F") && rows[0].substring(0, 1).equals(letter_max)) {
                    ret.add(rows[0]);
                }
            }
        }

        for (Map.Entry entry : letter_map.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        if (check) {
            return "From " + start + " to " + end + ", the most popular letter that girls' names started with was " + letter_max + ", and the female names in the dataset starting with " + letter_max + " are: " + ret.toString();
        }
        else{
            return "There are no females in this dataset";
        }
    }


}



