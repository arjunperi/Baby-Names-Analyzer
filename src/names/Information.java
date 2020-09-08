package names;

import java.io.File;
import java.util.*;

public class Information {

    Data data = new Data();


    public String topRankedName(int year, String gender) {
        List<String> top_ranked_name = namesAtSpecifiedRankInRange(year, year, gender, 1);
        return top_ranked_name.toString();
    }


    public List<Integer> numberOfBabiesAndNamesWithStartingLetter(int year, String gender, String letter) {
        String[][] name_arr = data.getArray(year);
        List<Integer> num_babies_and_total_count = new ArrayList<>();
        if (!isFileValid(name_arr)) {
            num_babies_and_total_count.add(-1);
            return num_babies_and_total_count;
        }

        int totalCounter = 0;
        List<String> checked_list = firstLetterTotalCountAndNames(name_arr, gender, letter, "instances");
        if (checked_list.size() > 0) {
            for (String instances : checked_list) {
                totalCounter += Integer.parseInt(instances);
            }
            num_babies_and_total_count.add(totalCounter);
        }
        else {
            num_babies_and_total_count.add(0);
        }
        num_babies_and_total_count.add(checked_list.size());
        return num_babies_and_total_count;
    }

    public String printNumberOfBabiesAndNamesWithStartingLetter(int year, String gender, String letter){
        List<Integer> methodResult = numberOfBabiesAndNamesWithStartingLetter(year, gender, letter);
        if (methodResult.contains(-1)){
            return "There is an error with the specified file(s)";
        }
        return "For gender " + gender + " and starting letter " + letter + ", there are " + methodResult.get(1) + " different names and " + methodResult.get(0) + " total instances";
    }


    //To input decade, update getArrayOfFiles()
    public String getRankFromNameAndGender(String name, String gender) {
        List<Integer> rankings = new ArrayList<>();
        File[] array_of_files = getArrayOfFiles();
        for (File current_file : array_of_files) {
            int year = getYearFromFileName(current_file);
            rankings.addAll(rankingsOfSpecifiedNameAndGenderInRange(name, gender, year, year));
        }
        return "The rankings for the name " + name + " and gender " + gender + " are: " + rankings.toString() + ", with rank -1 indicating the name/gender combination does" +
                " not exist for a year.";
    }


    public String correspondingNameMostRecentYear(int year, String name, String gender) {
        List<Integer> specified_year_rankings = rankingsOfSpecifiedNameAndGenderInRange(name, gender, year, year);
        if (specified_year_rankings.contains(-1)){
            return "The gender/rank was not found for the given range of years for this dataset";
        }

        int most_recent_year = getMostRecentYear();

        List<List<String>> recent_name = new ArrayList<>();
        for (int rank : specified_year_rankings) {
            recent_name.add(namesAtSpecifiedRankInRange(most_recent_year, most_recent_year, gender, rank));
        }

        return recent_name.toString();
    }


    public String mostPopularNameInRange(int start, int end, String gender) {
        if (namesWithSpecifiedRankMostOften(start,end,gender,1).toString().contains("There is an error with the specified file(s)")){
            return "There is an error with the specified file(s)";
        }
        return "Most popular name and number of years at top spot: " + namesWithSpecifiedRankMostOften(start, end, gender, 1).toString();
    }



    public String mostPopularGirlsStartingLetter(int start, int end) {
        Map<String, Integer> letter_map = new TreeMap<>();
        for (int year = start; year <= end; year++) {
            for (char letter_char = 'A'; letter_char < 'Z'; letter_char++) {
                String letter_as_string = String.valueOf(letter_char);
                letter_map.put(letter_as_string, letter_map.getOrDefault(letter_as_string, 0) + numberOfBabiesAndNamesWithStartingLetter(year, "F", String.valueOf(letter_char)).get(0));
            }
        }
        List<String> top_letters = keysWithMaxValues("int", letter_map, null);

        List<String> names_with_top_letters = new ArrayList<>();
        for (String letters : top_letters) {
            for (int year = start; year <= end; year++) {
                String[][] name_array = data.getArray(year);
                if (!isFileValid(name_array)) {
                    names_with_top_letters.add("There is an error with the specified file(s)");
                    return names_with_top_letters.toString();
                }
                names_with_top_letters = firstLetterTotalCountAndNames(name_array, "F", letters, "names");
            }
            break;
        }

        if (names_with_top_letters.size() == 0){
            return "There are no females in this dataset";
        }

        return "The most popular letter is: " + top_letters.get(0) + " and the corresponding names are: " + names_with_top_letters;
    }



    public List<Integer> rankingsOfSpecifiedNameAndGenderInRange(String name, String gender, int start, int end) {
        List<Integer> rankings = new ArrayList<>();
        for (int year = start; year <= end; year++) {
            String[][] current_array = data.getArray(year);
            if (!isFileValid(current_array)) {
                rankings.add(-2);
                return rankings;
            }
            List<String[]> recent_checked_list = checkSpecifiedEqualitiesInArray(current_array, "name & gender", name, gender);
            if (recent_checked_list.size() > 0) {
                rankings.add(Integer.parseInt(recent_checked_list.get(0)[3]));
            }
            else {
                rankings.add(-1);
            }
        }
        if (rankings.size() == 0) {
            rankings.add(-3);
        }
        return rankings;

    }


    public String  rankingDifferenceFirstAndLastYears(int start, int end, String name, String gender) {
        List<Integer> rankings = rankingsOfSpecifiedNameAndGenderInRange(name, gender, start, end);

        if (rankings.contains(-2)) {
            return("There is an error with the specified file(s)");
        }

        int first_year_index = 0;
        int last_year_index = rankings.size() - 1;

        if (rankings.get(first_year_index).equals(-1) || rankings.get(last_year_index).equals(-1)) {
            return("The name and gender combination could not be found");

        }

        int first_year_rank = rankings.get(first_year_index);
        int last_year_rank = rankings.get(last_year_index);
        return "" + (first_year_rank - last_year_rank);
    }


    public String largestRankingChangeFirstAndLastYears(int start, int end, String gender) {
        Map<String, Integer> names_and_rankings = new HashMap<>();
        Set<String> names_in_range = listToSet(getListOfNamesInRange(start, end, gender));

        if (names_in_range.contains("-2")) {
            return "There is an error with the specified file(s)";

        }
        if (names_in_range.contains("-1")) {
            return "The gender was not found for the given range of years in this dataset";

        }

        for (String names : names_in_range) {
            List<Integer> rankings = rankingsOfSpecifiedNameAndGenderInRange(names, gender, start, end);
            int first_year_index = 0;
            int last__year_index = rankings.size() - 1;
            if (!(rankings.get(first_year_index) == -1 && rankings.get(last__year_index) == -1)) {
                names_and_rankings.put(names, Math.abs(rankings.get(first_year_index) - rankings.get(last__year_index)));
            }
        }

        List<String> max_difference_names = keysWithMaxValues("int", names_and_rankings, null);
        return (max_difference_names.toString());
    }


    public String averageRankOverYears(int start, int end, String name, String gender) {
        List<Integer> rankings = rankingsOfSpecifiedNameAndGenderInRange(name, gender, start, end);
        if (rankings.contains(-2)) {
            return "There is an error with the specified file(s)";

        }

        if (rankings.contains(-3)) {
            return "The name and gender combination could not be found";

        }
        return ("Average: " + average(rankings));
    }


    public String highestAverageRankOverYears(int start, int end, String gender) {
        Set<String> names_in_range = listToSet(getListOfNamesInRange(start, end, gender));

        if (names_in_range.contains("-2")) {
            return "There is an error with the specified file(s)";

        }
        if (names_in_range.contains("-1")) {
            return "The gender was not found for the given range of years for this dataset";
        }

        Map<String, Double> names_and_averages = new HashMap<>();
        for (String names : names_in_range) {
            List<Integer> rankings = rankingsOfSpecifiedNameAndGenderInRange(names, gender, start, end);
            double average = average(rankings);
            names_and_averages.put(names, average);
        }

        List<String> max_difference_names = keysWithMaxValues("double", null, names_and_averages);
        return (max_difference_names.toString());
    }

    public String averageRankMostRecentYears(String name, String gender, int number_of_years){
        int most_recent_year = getMostRecentYear();
        int start_year = most_recent_year - number_of_years + 1;
        return averageRankOverYears(start_year,most_recent_year,name,gender);
    }


    public List<String> namesAtSpecifiedRankInRange(int start, int end, String gender, int rank) {
        List<String> names_in_range = getListOfNamesInRange(start, end, gender);

        List<String> names_with_matching_rank = new ArrayList<>();
        for (String name : names_in_range) {
            List<Integer> rankings = rankingsOfSpecifiedNameAndGenderInRange(name, gender, start, end);
            if (rankings.contains(-3)) {
                names_with_matching_rank.add("The gender/rank was not found for the given range of years for this dataset");
                return names_with_matching_rank;
            }
            if (rankings.contains(-2)){
                names_with_matching_rank.clear();
                names_with_matching_rank.add("There is an error with the specified file(s)");
                return names_with_matching_rank;
            }
            if (rankings.contains(rank)) {
                names_with_matching_rank.add(name);
            }
        }
        if (names_with_matching_rank.size() ==0){
            names_with_matching_rank.add("The gender/rank was not found for the given range of years for this dataset");
        }
        return names_with_matching_rank;
    }


    public List<String> namesWithSpecifiedRankMostOften(int start, int end, String gender, int rank) {
        List<String> names_with_rank = namesAtSpecifiedRankInRange(start, end, gender, rank);
        Map<String, Integer> names_with_count = new HashMap<>();
        for (String names : names_with_rank) {
            names_with_count.put(names, names_with_count.getOrDefault(names, 0) + 1);
        }
        List<String> most_popular_names = keysWithMaxValues("int", names_with_count, null);
        List<String> ret = new ArrayList<>();
        for (String names : most_popular_names) {
            ret.add(names + " " + names_with_count.get(names));
        }
        return ret;
    }


    //Warning says file is always inverted - kept this way because I always want to check for the false condition, not true.
    private boolean isFileValid(String[][] file) {
        boolean file_valid = true;
        if (file.length == 0) {
            file_valid = false;
        }
        return file_valid;
    }

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


    private boolean checkFirstLetterEquality(String name_unknown_starting_letter, String letter_check_against) {
        boolean letters_equal = false;
        String first_letter = name_unknown_starting_letter.substring(0, 1);
        if (first_letter.equals(letter_check_against)) {
            letters_equal = true;
        }
        return letters_equal;
    }

    private List<String> firstLetterTotalCountAndNames(String[][] name_array, String gender, String letter, String type) {
        List<String> checked_rows = new ArrayList<>();
        for (String[] rows : name_array) {
            if ((checkGenderEquality(rows[1], gender) && checkFirstLetterEquality(rows[0], letter))) {
                //need the total instances, but also need all the names
                if (type.equals("instances")) {
                    checked_rows.add(rows[2]);
                } else {
                    checked_rows.add(rows[0]);
                }
            }
        }
        return checked_rows;
    }


    private List<String[]> checkSpecifiedEqualitiesInArray(String[][] name_array, String specified_equality, String equality_check_one, String equality_check_two) {
        List<String[]> checked_rows = new ArrayList<>();
        for (String[] rows : name_array) {

            if (specified_equality.equals("name & gender")) {
                if (rows[0].equals(equality_check_one) && checkGenderEquality(rows[1], equality_check_two)) {
                    checked_rows.add(rows);
                }
            }

            if (specified_equality.equals("rank & gender")) {
                if (rows[3].equals(equality_check_one) && rows[1].equals(equality_check_two)) {
                    checked_rows.add(rows);
                }
            }

            if (specified_equality.equals("just gender")) {
                if (checkGenderEquality(rows[1], equality_check_one)) {
                    checked_rows.add(rows);
                }
            }
        }
        return checked_rows;
    }


    private List<String> keysWithMaxValues(String indicator, Map<String, Integer> map_unknown_max_int, Map<String, Double> map_unknown_max_double) {
        if (indicator.equals("int")) {
            int max_value = Collections.max(map_unknown_max_int.values());
            List<String> keys_with_max_difference = new ArrayList<>();
            for (String keys : map_unknown_max_int.keySet()) {
                if (map_unknown_max_int.get(keys) == max_value) {
                    keys_with_max_difference.add(keys);
                }
            }
            return keys_with_max_difference;

        } else {
            double max_value = Collections.min(map_unknown_max_double.values());
            List<String> keys_with_max_difference = new ArrayList<>();
            for (String keys : map_unknown_max_double.keySet()) {
                if (map_unknown_max_double.get(keys) == max_value) {
                    keys_with_max_difference.add(keys);
                }
            }
            return keys_with_max_difference;
        }
    }


    private File[] getArrayOfFiles() {
        File directory = new File("data/TestSets");
        return directory.listFiles();
    }


    private int getYearFromFileName(File file) {
        return Integer.parseInt(file.getName().substring(3, 7));
    }

    private List<String> getListOfNamesInRange(int start, int end, String gender) {
        List<String> names_in_range = new ArrayList<>();
        for (int year = start; year <= end; year++) {
            String[][] current_arrray = data.getArray(year);
            if (!isFileValid(current_arrray)) {
                names_in_range.add("-2");
                return names_in_range;
            }
            List<String[]> recent_checked_list = checkSpecifiedEqualitiesInArray(current_arrray, "just gender", gender, null);
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
        return new HashSet<>(list);
    }

    private double average(List<Integer> rankings) {
        int sum = 0;
        int count = 0;
        for (int rank : rankings) {
            sum += rank;
            count++;
        }
        return (double) sum / count;
    }

    private int getMostRecentYear(){
        File[] array_of_files = getArrayOfFiles();
        List<Integer> years = new ArrayList<>();
        int most_recent_year;
        for (File current_file : array_of_files) {
            years.add(getYearFromFileName(current_file));
        }
        return Collections.max(years);
    }

}

