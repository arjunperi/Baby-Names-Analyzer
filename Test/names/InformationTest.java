package names;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InformationTest {

    private Information info;
    @BeforeEach
    void setup () {
        info = new Information();
    }

    @Test
    void topRankedNameFemale() {
        assertEquals("[Mary]", info.topRankedName(1900,"F"));
    }

    @Test
    void topRankedNameMale() {
        assertEquals("[John]", info.topRankedName(1900,"M"));
    }

    @Test
    void topRankedNameGenderNonExistent(){
        assertEquals("[The gender/rank was not found for the given range of years for this dataset]", info.topRankedName(1900,"L"));
    }


    @Test
    void numberOfBabiesAndNamesWithStartingLetterFemale() {
        List<Integer> correct_list = new ArrayList<>();
        correct_list.add(20717);
        correct_list.add(3);
        assertEquals(correct_list,info.numberOfBabiesAndNamesWithStartingLetter(1901,"F","M"));
    }

    @Test
    void numberOfBabiesAndNamesWithStartingLetterMale() {
        List<Integer> correct_list = new ArrayList<>();
        correct_list.add(14401);
        correct_list.add(3);
        assertEquals(correct_list,info.numberOfBabiesAndNamesWithStartingLetter(1901,"M","J"));
    }

    @Test
    void numberOfBabiesAndNamesWithStartingLetter_LetterDoesNotExist() {
        List<Integer> correct_list = new ArrayList<>();
        correct_list.add(0);
        correct_list.add(0);
        assertEquals(correct_list,info.numberOfBabiesAndNamesWithStartingLetter(1901,"F","x"));
    }

    @Test
    void getRankFromNameAndGenderFemale() {
        assertEquals("The rankings for the name Emily and " +
                "gender F are: [-1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 6], with rank -1 indicating " +
                "the name/gender combination does not exist for a year.",info.getRankFromNameAndGender("Emily","F"));
    }

    @Test
    void getRankFromNameAndGenderMale() {
        assertEquals("The rankings for the name Henry and gender M are: [10, 10, 10, 10, 10, 10, 10, -1, -1, -1, -1, -1], " +
                "with rank -1 indicating the name/gender combination does not exist for a year.",info.getRankFromNameAndGender("Henry","M"));
    }

    @Test
    void getRankFromNameAndGender_InvalidCombo() {
        assertEquals("The rankings for the name Henry and gender F are: [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1], " +
                "with rank -1 indicating the name/gender combination does not exist for a year.",info.getRankFromNameAndGender("Henry","F"));
    }


    @Test
    void correspondingNameMostRecentYearFemale() {
        assertEquals("[[Isabella]]",info.correspondingNameMostRecentYear(1901,"Mary","F"));
    }

    @Test
    void correspondingNameMostRecentYear_GenderExistsInFirstButNotSecond() {
        assertEquals("The gender/rank was not found for the given range of years for this dataset",info.correspondingNameMostRecentYear(1903,"William","L"));
    }

    @Test
    void correspondingNameMostRecentYear_RankExistsInFirstButNotSecond() {
        assertEquals("The gender/rank was not found for the given range of years for this dataset",info.correspondingNameMostRecentYear(1901,"Frank","F"));
    }

    @Test
    void mostPopularNameInRangeFemale() {
        assertEquals("Most popular name and number of years at top spot: [Mary 4]", info.mostPopularNameInRange(1900,1903,"F"));
    }

    @Test
    void mostPopularNameInRangeMale() {
        assertEquals("Most popular name and number of years at top spot: [John 4]", info.mostPopularNameInRange(1900,1903,"M"));
    }

    @Test
    void mostPopularNameInRangeFileDoesNotExist() {
        assertEquals("There is an error with the specified file(s)", info.mostPopularNameInRange(1900,19013,"M"));
    }

    @Test
    void mostPopularGirlsStartingLetterExpectedCase() {
        assertEquals("The most popular letter is: M and the corresponding names are: [Mary, Margaret, Marie]", info.mostPopularGirlsStartingLetter(1900,1902));
    }

    @Test
    void mostPopularGirlsStartingLetterNoGirlsInSet() {
        assertEquals("There are no females in this dataset", info.mostPopularGirlsStartingLetter(19001,19001));
    }

    @Test
    void mostPopularGirlsStartingLetterTie() {
        assertEquals("The most popular letter is: E and the corresponding names are: [Elizabeth, Ethel]", info.mostPopularGirlsStartingLetter(1900,1903));
    }


    @Test
    void nameGenderRankingsInRange() {
    }

    @Test
    void rankingDifferenceFirstLast() {
    }

    @Test
    void rankingChangeFirstAndLast() {
    }

    @Test
    void averageOverYears() {
    }

    @Test
    void highestAverageOverYears() {
    }

    @Test
    void namesAtRankInRange() {
    }

    @Test
    void rankMostOften() {
    }
}