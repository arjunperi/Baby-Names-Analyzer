package names;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Main instance = new Main();

    @Test
    void femaleTopRanked() throws FileNotFoundException {
        assertEquals("There are no females in this dataset", instance.femaleTopRanked(19001));
        assertEquals("The top ranked female name is: Mary", instance.femaleTopRanked(1900));
        assertEquals("The top ranked female name is: Emily", instance.femaleTopRanked(2000));
    }

    @Test
    void maleTopRanked() throws FileNotFoundException {
        assertEquals("There are no males in this dataset", instance.maleTopRanked(19002));
        assertEquals("The top ranked male name is: John", instance.maleTopRanked(1900));
        assertEquals("The top ranked male name is: Jacob", instance.maleTopRanked(2000));
    }

    @Test
    void letter() throws FileNotFoundException {
        assertEquals("Gender L and starting letter M combination does not exist for this dataset", instance.letter(19002, "L", "M"));
        assertEquals("For gender F and starting letter M, there are 2 different names and 17560 total instances", instance.letter(1901, "F", "M"));
        assertEquals("For gender M and starting letter J, there are 3 different names and 20788 total instances", instance.letter(19001, "M", "J"));
    }

    @Test
    void nameGenderRank() throws FileNotFoundException {
        assertEquals("The rankings for the name Emily and gender F are: [1, 1, 1, 1, 1, 1, 1, 1, 3, 6], with rank 0 indicating the name/gender combination does not exist for a year.", instance.nameGenderRank("Emily","F"));
        assertEquals("The rankings for the name xyz and gender F are: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0], with rank 0 indicating the name/gender combination does not exist for a year.", instance.nameGenderRank("xyz", "F"));
        //assertEquals("");
    }

    @Test
    void nameGenderPair() throws FileNotFoundException {
        //invalid entry - doesn't exist in either
        assertEquals("Either the combination of name xyz and gender L in 1901 does not exist for the specified year / the most recent year in this dataset, " +
                "or the ranking of this combination does not correspond to an existing rank in the most recent year.", instance.nameGenderPair(1901, "xyz", "L"));
        //invalid entry - ranking of specified does not correspond to a valid rank in the most recent year
        assertEquals("Either the combination of name Arjun and gender M in 20001 does not exist for the specified year / the most recent year in this dataset, " +
                "or the ranking of this combination does not correspond to an existing rank in the most recent year.", instance.nameGenderPair(20001, "Arjun", "M"));
        assertEquals("The combination of name Michael and gender M in 2000 corresponds to the same popularity as the name Ethan in the most recent year in the dataset." , instance.nameGenderPair(2000,"Michael", "M"));

    }

    //possibly need to do a test over more years to test for ties
    @Test
    void rangeOfYears() throws FileNotFoundException {
        assertEquals("From 1900 to 1901, gender L does not exist in the dataset", instance.rangeOfYears(1900,1901,"L"));
        assertEquals("From 1900 to 1900 for gender M, the most popular name was John and it had the top rank 1 times", instance.rangeOfYears(1900,1900,"M"));
        assertEquals("From 1900 to 1901 for gender F, the most popular name was Mary and it had the top rank 2 times", instance.rangeOfYears(1900,1901,"F"));
    }

    //test exceptions of year - unresolved
    //test exception of gender
    //test tie
    @Test
    void popularGirls() throws FileNotFoundException {
        assertEquals("From 1900 to 1901, the most popular letter that girls' names started with was M, and the female names in the dataset starting with M are: [Margaret, Marie, Mary]" , instance.popularGirls(1900,1901));
        assertEquals("There are no females in this dataset", instance.popularGirls(19001,19001));
        assertEquals("From 19003 to 19004, the most popular letter that girls' names started with was E, and the female names in the dataset starting with E are: [Elizabeth, Ethel]", instance.popularGirls(19003,19004));
    }
}