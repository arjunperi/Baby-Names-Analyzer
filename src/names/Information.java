package names;

import java.io.File;
import java.util.*;

public class Information {

    Data data = new Data();

    //helper method that checks if file is valid
    private boolean isFileValid(String[][] file) {
        boolean ret = true;
        if (file.length == 0) {
            ret = false;
        }
        return ret;
    }

    //We should have a helper method to see if equals F or M
    private boolean checkGenderEquality(String gender_unknown, String gender_check_against) {
        boolean genders_equal = false;
        if (gender_check_against.equals("F")) {
            if (gender_unknown.equals("F")) {
                genders_equal = true;
            }
        } else if (gender_check_against.equals("M")) {
            if (gender_unknown.equals("M")) {
                genders_equal = true;
            }
        }
        return genders_equal;
    }

    //helper method to check letter equality
    private boolean checkFirstLetterEquality(String name_unknown_starting_letter, String letter_check_against) {
        boolean letters_equal = false;
        String first_letter = name_unknown_starting_letter.substring(0, 1);
        if (first_letter.equals(letter_check_against)) {
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
    private List<String[]> getRowAtSpecifiedEqualityCheck(String[][] name_array, String check, String check_one, String check_two) {
        List<String[]> checked_rows = new ArrayList();
        for (String[] rows : name_array) {

            if (check.equals("name & gender")) {
                if (rows[0].equals(check_one) && checkGenderEquality(rows[1], check_two)) {
                    checked_rows.add(rows);
                    // checked_row = rows;
                }
            }

            if (check.equals("rank & gender")) {
                if (rows[3].equals(check_one) && rows[1].equals(check_two)) {
                    checked_rows.add(rows);
                    //checked_row=rows;
                }
            }

            if (check.equals("just gender")) {
                if (checkGenderEquality(rows[1], check_one)) {
                    checked_rows.add(rows);
                }
            }
        }
        return checked_rows;
    }

    private int getRankFromCheckedList(List<String[]> year_checked_list) {
        return Integer.parseInt(year_checked_list.get(0)[3]);
    }

    private Map<String, Integer> getMapWithMaxValues(Map<String, Integer> map_unknown_max) {
        Map<String, Integer> map_max_keys = new HashMap<>();
        int max = Collections.max(map_unknown_max.values());
        for (String key : map_unknown_max.keySet()) {
            if (map_unknown_max.get(key) == max) {
                map_max_keys.put(key, map_unknown_max.get(key));
            }
        }
        return map_max_keys;
    }

    private List<String> keysWithMaxValues(String indicator, Map<String, Integer> map_unknown_max_int, Map<String, Double> map_unknown_max_double) {
        if (indicator.equals("int")) {
            List<String> keys_max = new ArrayList<>();
            int max_value = Collections.max(map_unknown_max_int.values());
            List names_with_max_difference = new ArrayList();
            for (String keys : map_unknown_max_int.keySet()) {
                if (map_unknown_max_int.get(keys) == max_value) {
                    names_with_max_difference.add(keys);
                }
            }
            return names_with_max_difference;
        } else {
            List<String> keys_max = new ArrayList<>();
            double max_value = Collections.max(map_unknown_max_double.values());
            List names_with_max_difference = new ArrayList();
            for (String keys : map_unknown_max_double.keySet()) {
                if (map_unknown_max_double.get(keys) == max_value) {
                    names_with_max_difference.add(keys);
                }
            }
            return names_with_max_difference;
        }

    }


    //Helper method for doing file loops
    private File[] getArrayOfFiles() {
        File directory = new File("data/TestSets");
        return directory.listFiles();
    }

    //helper method to get array from file name
    private int getYearFromFileName(File file) {
        return Integer.parseInt(file.getName().substring(3, 7));
    }

    private List<String> getListOfNamesInRange(int start, int end, String gender) {
        List<String> names_in_range = new ArrayList<>();
        //loop through years, if gender equal then add name to set
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)) {
                names_in_range.add("-2");
                return names_in_range;
            }
            List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(curr_arr, "just gender", gender, "");
            if (recent_checked_list.size() > 0) {
                for (String[] rows : recent_checked_list) {
                    String name = rows[0];
                    names_in_range.add(name);
                }
            }
        }
        if (names_in_range.size() == 0) {
            names_in_range.add("-1");
        }
        return names_in_range;
    }

    private Set<String> listToSet(List<String> list) {
        Set<String> set = new HashSet<>();
        for (String item : list) {
            set.add(item);
        }
        return set;
    }
    
    private double average(List<Integer> rankings) {
        int sum = 0;
        int count = 0;
        for (int rank : rankings) {
            sum += rank;
            count++;
        }
        double average = (double) sum / count;
        return average;
    }

    public String topRankedName(int year, String gender) {
        String[][] name_arr = data.getArray(year);
        if (!isFileValid(name_arr)) {
            return "There is an error with the specified file(s)";
        }

        String top_ranked_name = "";
        if (checkGenderEquality(gender, "F")) {
            top_ranked_name = "There are no females in this dataset";
            if (checkGenderEquality(name_arr[0][1], "F")) {
                top_ranked_name = "The top ranked female name is: " + name_arr[0][0];
            }

        } else {
            top_ranked_name = "There are no males in this dataset";
            List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(name_arr, "just gender", gender, null);
            if (recent_checked_list.size() > 0) {
                top_ranked_name = "The top ranked male name is: " + recent_checked_list.get(0)[0];
            }
            for (String[] rows : name_arr) {
                if (checkGenderEquality(rows[1], "M")) {
                    top_ranked_name = "The top ranked male name is: " + rows[0];
                    break;
                }
            }
        }
        return top_ranked_name;
    }


    //Method to perform the gender/starting letter algorithm
    //Edge cases - gender/letter combo doesn't exist
    //Year isn't in dataset!
    public String letter(int year, String gender, String letter) {
        String[][] name_arr = data.getArray(year);
        if (!isFileValid(name_arr)) {
            return "There is an error with the specified file(s)";
        }

        //Intialize return string to indicate that the given combination does not exist
        String ret = "Gender " + gender + " and starting letter " + letter + " combination does not exist for this dataset";

        //Initialize counters and checking field to 0/false
        int nameCounter = 0;
        int totalCounter = 0;
        boolean does_combination_exist = false;

        //Go through the rows of 2D array, and for a row if the gender and starting letter of the name match the inputs, add 1 to the name counter and the count field
        // (last column entry) to the total counter
        List<String> checked_list = firstLetterHelper(name_arr, gender, letter, "instances");
        if (checked_list.size() > 0) {
            //maybe duplicate this elsewhere ?
            //now I have an array list with all the nam
            for (String instances : checked_list) {
                totalCounter += Integer.parseInt(instances);
            }
            does_combination_exist = true;
        }

        //If the combination exists, format string as follows
        if (does_combination_exist) {
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
    public String nameGenderRank(String name, String gender) {
        //test on ssa_2000s
        //have to define ret here cuz if I do it in the loop I can't call it in the print statement
        List<Integer> ret = new ArrayList<>();
        File[] array_of_files = getArrayOfFiles();
        for (File current_file : array_of_files) {
            String[][] name_arr = data.getArray(getYearFromFileName(current_file));
            if (!isFileValid(name_arr)) {
                return "There is an error with the specified file(s)";
            }
            //loop through the array and get the rank associated with a name and gender
            //HAVE A NAME EQUALS METHOD?
            boolean does_name_gender_combo_exist = false;

            List<String[]> checked_list = getRowAtSpecifiedEqualityCheck(name_arr, "name & gender", name, gender);
            if (checked_list.size() > 0) {
                ret.add(getRankFromCheckedList(checked_list));
                does_name_gender_combo_exist = true;
            }

            if (!does_name_gender_combo_exist) {
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
        if (!isFileValid(year_arr)) {
            return "There is an error with the specified file(s)";
        }
        List<String[]> year_checked_list = getRowAtSpecifiedEqualityCheck(year_arr, "name & gender", name, gender);
        if (year_checked_list.size() > 0) {
            check_year = true;
            year_rank = getRankFromCheckedList(year_checked_list);
        }

        //HAVE A HELPER METHOD TO RETRIEVE THE NAME/GENDER/(and possible instacnes) FOR A GIVEN RANK AND GIVEN YEAR?
        //go to the most recent year and get the name/gender at that rank
        String[][] recent_arr = data.getArray(max);
        if (!isFileValid(recent_arr)) {
            return "There is an error with the specified file(s)";
        }

        List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(recent_arr, "name & gender", name, gender);
        if (recent_checked_list.size() > 0) {
            check_year = true;
            year_rank = getRankFromCheckedList(recent_checked_list);
        }

        if (check_recent && check_year) {
            return "The combination of name " + name + " and gender " + gender + " in " + year + " corresponds to the same popularity as the name " + ret + " in the most recent year in the dataset.";
        } else {
            return ret;
        }
    }

    //works, need to design the return part better
    //edge cases
    //year not in dataset - resolved
    //gender not in dataset - resolved
    public void rangeOfYears(int start, int end, String gender) {
        System.out.println(rankMostOften(start, end, gender, 1));
    }


    //edge cases
    //year not in data set - resolved
    //no girls in the dataset
    public String popularGirls(int start, int end) {
        boolean check_if_females_exist = false;
        Map<String, Integer> letter_map = new TreeMap<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)) {
                return "There is an error with the specified file(s)";
            }

            List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(curr_arr, "just gender", "F", "");
            if (recent_checked_list.size() > 0) {
                check_if_females_exist = true;
                for (String[] rows : recent_checked_list) {
                    String letter = rows[0].substring(0, 1);
                    letter_map.put(letter, letter_map.getOrDefault(letter, 0) + Integer.parseInt(rows[2]));
                }
            } else {
                return "There are no females in this dataset";
            }
        }

        String letter_max = keysWithMaxValues("int", letter_map, null).get(0);

        //use get or lambda
        //see other methods that use this
        Set<String> ret = new TreeSet<>();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            List<String> checked_list = firstLetterHelper(curr_arr, "F", letter_max, "names");
            if (checked_list.size() > 0) {
                for (String names : checked_list) {
                    ret.add(names);
                }

            }
        }

        for (Map.Entry entry : letter_map.entrySet()) {
            System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
        }

        if (check_if_females_exist) {
            return "From " + start + " to " + end + ", the most popular letter that girls' names started with was " + letter_max + ", and the female names in the dataset starting with " + letter_max + " are: " + ret.toString();
        }
        return "";
    }

    //EdgeCases - name & gender combo doesn't exist
    public List<Integer> nameGenderRankingsInRange(String name, String gender, int start, int end) {
        List<Integer> rankings = new ArrayList();
        for (int year = start; year <= end; year++) {
            String[][] curr_arr = data.getArray(year);
            if (!isFileValid(curr_arr)) {
                rankings.add(-2);
                return rankings;
            }
            List<String[]> recent_checked_list = getRowAtSpecifiedEqualityCheck(curr_arr, "name & gender", name, gender);
            if (recent_checked_list.size() > 0) {
                rankings.add(Integer.parseInt(recent_checked_list.get(0)[3]));
            }
        }
        if (rankings.size() == 0) {
            rankings.add(-1);
        }
        return rankings;


//        return "From " + start + " to " + end + " the name " + name + " and gender " + gender + " had the following rankings, with 0 indicating the combination did" +
//                " not exist for that year: " + rankings.toString();
    }

    //difference in ranking between first and last
    //all files in the range should exist
    //name and gender should exist for first and last files
    public void rankingDifferenceFirstLast(int start, int end, String name, String gender) {
        List<Integer> rankings = nameGenderRankingsInRange(name, gender, start, end);

        if (rankings.contains(-2)) {
            System.out.println("There is an error with the specified file(s)");
            return;
        }

        int first_year_index = 0;
        int last_year_index = rankings.size() - 1;

        if (rankings.get(first_year_index).equals(-1) || rankings.get(last_year_index).equals(-1)) {
            System.out.println("The name and gender combination could not be found");
            return;
        }

        int first_year_rank = rankings.get(first_year_index);
        int last_year_rank = rankings.get(last_year_index);
        System.out.println(first_year_rank - last_year_rank);
    }


    //name is in one year but not the other -> -1 value could cause an issue
    // all files in range should exist
    //gender needs to exist in first and last file

    public void rankingChangeFirstAndLast(int start, int end, String gender) {
        Map<String, Integer> names_and_rankings = new HashMap<>();
        Set<String> names_in_range = listToSet(getListOfNamesInRange(start, end, gender));

        if (names_in_range.contains("-2")) {
            System.out.println("There is an error with the specified file(s)");
            return;
        }
        if (names_in_range.contains("-1")) {
            System.out.println("The gender was not found for the given range of years in this dataset");
            return;
        }

        for (String names : names_in_range) {
            List<Integer> rankings = nameGenderRankingsInRange(names, gender, start, end);
            int first_year_index = 0;
            int last__year_index = rankings.size() - 1;
            if (!(rankings.get(first_year_index) == -1 && rankings.get(last__year_index) == -1)) {
                names_and_rankings.put(names, Math.abs(rankings.get(first_year_index) - rankings.get(last__year_index)));
            }
        }

        List<String> max_difference_names = keysWithMaxValues("int", names_and_rankings, null);
        System.out.println(max_difference_names.toString());
    }


    //error with files
    //don't throw an error message unless the combo isn't found for ALL years
    public void averageOverYears(int start, int end, String name, String gender) {
        List<Integer> rankings = nameGenderRankingsInRange(name, gender, start, end);
        if (rankings.contains(-2)) {
            System.out.println("There is an error with the specified file(s)");
            return;
        }

        //FOR ALL YEARS
        if (rankings.contains(-1)) {
            System.out.println("The name and gender combination could not be found");
            return;
        }
        System.out.println("Average: " + average(rankings));
    }

    //file error
    //gender can't  be found for ALL years
    public void highestAverageOverYears(int start, int end, String gender) {
        Set<String> names_in_range = listToSet(getListOfNamesInRange(start, end, gender));

        if (names_in_range.contains("-2")) {
            System.out.println("There is an error with the specified file(s)");
            return;
        }
        if (names_in_range.contains("-1")) {
            System.out.println("The gender was not found for the given range of years for this dataset");
            return;
        }

        Map<String, Double> names_and_averages = new HashMap<>();
        for (String names : names_in_range) {
            List<Integer> rankings = nameGenderRankingsInRange(names, gender, start, end);
            double average = average(rankings);
            names_and_averages.put(names, average);
        }

        List<String> max_difference_names = keysWithMaxValues("double", null, names_and_averages);
        System.out.println(max_difference_names.toString());
    }

    //File error
    //rank doesn't exist for a given year -> omit it from the set
    //gender doesn't exist for a given year -> omit from the set
    //rank doesn't exist for ALL years -> error
    //gender doesn't exist for ALL years -> error

    public List<String> namesAtRankInRange(int start, int end, String gender, int rank) {
        List<String> names_in_range = getListOfNamesInRange(start, end, gender);


//        if (names_in_range.contains("-2")){
//            names_in_range.clear();
//            names_in_range.add("There is an error with the specified file(s)");
//            return names_in_range;
//        }
//
//        if (names_in_range.contains("-1")){
//            names_in_range.clear();
//            names_in_range.add("The gender was not found for the given range of years for this dataset");
//            return names_in_range;
//        }

        //Set<String> names_with_matching_rank = new HashSet<>();
        List<String> names_with_matching_rank = new ArrayList<>();
        for (String names : names_in_range) {
            List<Integer> rankings = nameGenderRankingsInRange(names, gender, start, end);
            if (rankings.contains(-1)) {
                names_with_matching_rank.add("The rank was not found for the given range of years for this dataset");
                return names_with_matching_rank;
            }
            if (rankings.contains(rank)) {
                names_with_matching_rank.add(names);
            }
        }
        if (names_with_matching_rank.size() == 0) {
            names_with_matching_rank.add("The rank was not found for the given range of years for this dataset");
        }
        return names_with_matching_rank;
    }

    //file error
    //gender doesn't exist for ALL years
    //rank doesn't exist for ALL years
    public List<String> rankMostOften(int start, int end, String gender, int rank) {
        List<String> names_with_rank = namesAtRankInRange(start, end, gender, rank);
        Map<String, Integer> names_with_count = new HashMap();
        for (String names : names_with_rank) {
            names_with_count.put(names, names_with_count.getOrDefault(names, 0) + 1);
        }
        List<String> most_popular_names = keysWithMaxValues("int", names_with_count, null);


//        int names_max = Collections.max(names_with_count.values());
//        Set highest_rank_most_often = new HashSet();
//        for (String names : names_with_count.keySet()) {
//            if (names_with_count.get(names) == names_max) {
//                highest_rank_most_often.add(names);
//            }
//        }

        List<String> ret  = new ArrayList<>();
        for (String names : most_popular_names) {
            ret.add(names + " " + names_with_count.get(names));
        }
        return ret;
    }
}



