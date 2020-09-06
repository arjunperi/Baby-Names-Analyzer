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

    //helper method to check letter equality
    private boolean checkFirstLetterEquality(String name_unknown_starting_letter, String letter_check_against){
        boolean letters_equal = false;
        String first_letter = name_unknown_starting_letter.substring(0,1);
        if (first_letter.equals(letter_check_against)){
            letters_equal = true;
        }
        return letters_equal;
    }

    private List firstLetterHelper(String[][] name_array, String gender, String letter, String type) {
        List checked_rows = new ArrayList();
        for (String[] rows : name_array) {
            if ((checkGenderEquality(rows[1], gender) && checkFirstLetterEquality(rows[0], letter))) {
                //need the total instances, but also need all the names
                if (type.equals("instances")) {
                    checked_rows.add(rows[2]);
                    //checked_row=rows
                } else {
                    checked_rows.add(rows[0]);
                }
            }
        }
        return checked_rows;
    }

    //helper method that does a lot of loop/comparison work for me:
    // loops through a name_array and checks for any type of equality
    private List getRowAtSpecifiedEqualityCheck(String[][] name_array, String check, String check_one, String check_two){
        List checked_rows = new ArrayList();
        for (String[] rows: name_array){

            if (check.equals("name & gender")) {
                if (rows[0].equals(check_one) && checkGenderEquality(rows[1], check_two)) {
                    checked_rows.add(rows);
                    // checked_row = rows;
                }
            }

            if (check.equals("rank & gender")){
                if (rows[3].equals(check_one) && rows[1].equals(check_two)){
                    checked_rows.add(rows);
                    //checked_row=rows;
                }
            }
        }
        return checked_rows;
    }

//    //helper method to check name equality
//    private boolean checkNameEquality(String name_unknown, String name_check_against){}
//    boolean names_equal = false;
//    if ()

    //helper method to check letter equality

    //helper method to check rank equality

    //helper method to get rank
    //helper method to get

    //Helper method for doing file loops
    private File[] getArrayOfFiles() {
        File directory = new File("data/TestSets");
        return directory.listFiles();
    }

    //helper method to get array from file name
    private int getYearFromFileName(File file){
        return Integer.parseInt(file.getName().substring(3,7));
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
    public String letter(int year, String gender,String letter){
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
        List <String> checked_list = firstLetterHelper(name_arr,gender, letter, "instances");
        if (checked_list.size()>0){
            //maybe duplicate this elsewhere ?
            //now I have an array list with all the nam
            for (String instances: checked_list){
                totalCounter += Integer.parseInt(instances);
            }
            does_combination_exist = true;
        }

        //If the combination exists, format string as follows
        if (does_combination_exist){
            //ret = "For gender " + gender + " and starting letter " + letter + ", there are " + nameCounter + " different names and " + totalCounter + " total instances";
            ret = "For gender " + gender + " and starting letter " + letter + ", there are " + checked_list.size() + " different names and " + totalCounter + " total instances";
        }

        return ret;
    }


    //Works - file name assumption?
    //How are you supposed to input the decade?
    //Edge cases -
    //name doesn't exist in file - resolved
    //gender doesn't exist in the file - resolved

    public String nameGenderRank(String name, String gender){
        //test on ssa_2000s
        //have to define ret here cuz if I do it in the loop I can't call it in the print statement
        List<Integer> ret  = new ArrayList<>();
        File[] array_of_files = getArrayOfFiles();
        for (File current_file : array_of_files) {
            String[][] name_arr = data.getArray(getYearFromFileName(current_file));
            if (!isFileValid(name_arr)){
                return "There is an error with the specified file(s)";
            }
            //loop through the array and get the rank associated with a name and gender
            //HAVE A NAME EQUALS METHOD?
            boolean does_name_gender_combo_exist = false;

            List<String[]> checked_list = getRowAtSpecifiedEqualityCheck(name_arr, "name & gender", name, gender);
            if (checked_list.size()>0){
                ret.add(Integer.parseInt(checked_list.get(0)[3]));
                does_name_gender_combo_exist = true;
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
    public String nameGenderPair(int year, String name, String gender) {
        //get the most recent year
        boolean check_year = false;
        boolean check_recent = false;
        String ret = "Either the combination of name " + name + " and gender " + gender + " in " + year + " does not exist for the specified year / the most recent year in this dataset" +
                ", or the ranking of this combination does not correspond to an existing rank in the most recent year.";
        int max = 0;
        int year_rank = 0;

        //HAVE A HELPER METHOD TO RUN MAX ALGO FOR A SET OF FILES?
        File[] array_of_files = getArrayOfFiles();
        List<Integer> years = new ArrayList<>();
        for (File current_file : array_of_files) {
            years.add(getYearFromFileName(current_file));
        }
        max = Collections.max(years);

        //HAVE A HELPER METHOD TO GET THE RANK GIVEN A NAME AND A YEAR?
        //get the rank of the name within the year in question
        String[][] year_arr = data.getArray(year);
        if (!isFileValid(year_arr)){
            return "There is an error with the specified file(s)";
        }
        List<String[]> year_checked_list = getRowAtSpecifiedEqualityCheck(year_arr, "name & gender", name, gender);
        if (year_checked_list.size()>0){
            check_year = true;
            year_rank = (Integer.parseInt(year_checked_list.get(0)[3]));
        }

        //HAVE A HELPER METHOD TO RETRIEVE THE NAME/GENDER/(and possible instacnes) FOR A GIVEN RANK AND GIVEN YEAR?
        //go to the most recent year and get the name/gender at that rank
        String[][] recent_arr = data.getArray(max);
        if (!isFileValid(recent_arr)){
            return "There is an error with the specified file(s)";
        }

        List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(recent_arr, "name & gender", name, gender);
        if (recent_checked_list.size()>0){
            check_year = true;
            year_rank = (Integer.parseInt(recent_checked_list.get(0)[3]));
        }

//
        if (check_recent && check_year){
            return "The combination of name " + name + " and gender " + gender + " in " + year + " corresponds to the same popularity as the name " + ret + " in the most recent year in the dataset.";
        }
        else{
            return ret;
        }
    }

    //works, need to design the return part better
    //edge cases
    //year not in dataset - resolved
    //gender not in dataset - resolved
    public String rangeOfYears(int start, int end, String gender) {
        Map<String, Integer> rank_map = new HashMap<>();
        boolean does_gender_exist = false;
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)){
                return "There is an error with the specified file(s)";
            }
            if (checkGenderEquality(gender, "F")){
                does_gender_exist = true;
                //get the top ranked female name
                String top = topRankedName(year,gender);
                rank_map.put(top,rank_map.getOrDefault(top,0)+1);
            }
            else if (checkGenderEquality(gender,"M")){
                does_gender_exist=  true;
                String top = topRankedName(year, gender);
                rank_map.put(top, rank_map.getOrDefault(top,0)+1);
            }
        }

        /*
        for (Map.Entry entry : rank_map.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }
         */

        Map<String, Integer> ret = new HashMap<>();
        int max = Collections.max(rank_map.values());
        for (String key : rank_map.keySet()) {
            if (rank_map.get(key) == max) {
                ret.put(key, rank_map.get(key));
            }
        }

        if (does_gender_exist) {
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
    //year not in data set - resolved
    //no girls in the dataset
    public String popularGirls(int start, int end) {
        boolean check = false;
        Map<String, Integer> letter_map = new TreeMap<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)){
                return "There is an error with the specified file(s)";
            }

            for (String[] rows : curr_arr) {
                if (checkGenderEquality(rows[1],"F")) {
                    check = true;
                    //just check if length of return is >0
                    String letter = rows[0].substring(0, 1);
                    letter_map.put(letter, letter_map.getOrDefault(letter,0)+Integer.parseInt(rows[2]));
                }
                else{
                    return "There are no females in this dataset";
                }
            }
        }

        //now that we have a map of all the letters, we want to go through that map, and find the highest value
        // once we get that value, return all names associated with the value
        // which means we once again need to loop through all of the years?
        // seems innefficent - should we have stored the name somewhere?
        //now, if we have ties -> thus will always give us alphabetically first because of the treeset


        //lambda
        String letter_max = "";
        int max = Collections.max(letter_map.values());
        for (String key: letter_map.keySet()){
            if (letter_map.get(key) == max){
                letter_max = key;
                break;
            }
        }

        //use get or lambda
        //see other methods that use this
        Set<String> ret = new TreeSet<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            List<String> checked_list = firstLetterHelper(curr_arr,"F",letter_max,"names");
            if (checked_list.size()>0){
                for (String names: checked_list){
                    ret.add(names);
                }

            }

//            for (String[] rows : curr_arr) {
//                if ((checkGenderEquality(rows[1],"F") && checkFirstLetterEquality(rows[0],letter_max))) {
//                    ret.add(rows[0]);
//                }
//            }
        }

        for (Map.Entry entry : letter_map.entrySet())
        {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        if (check) {
            return "From " + start + " to " + end + ", the most popular letter that girls' names started with was " + letter_max + ", and the female names in the dataset starting with " + letter_max + " are: " + ret.toString();
        }
        return "";
//        else{
//            return "There are no females in this dataset";
//        }
    }

    public String nameGenderRankingsInRange(String name, String gender, int start, int end){
        List rankings = new ArrayList();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)){
                return "There is an error with the specified file(s)";
            }
            for (String[] rows: curr_arr){
                if (rows[0].equals(name) && checkGenderEquality(rows[1],"F")){
                    rankings.add(rows[3]);
                }
            }
        }

        return "From " + start + " to " + end + " the name " + name + " and gender " + gender + " had the following rankings, with 0 indicating the combination did" +
                " not exist for that year: " + rankings.toString();
    }
}

//check name and gender equality
//check gender equality and first letter
//check gender eqaulity
//check rank and gender equality

//helper method could do the loop and retunr the


