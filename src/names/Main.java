package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;



//toUppercase???
//file stuff - getting it from different directories
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Information info = new Information();

        //System.out.println(info.topRankedName(1900,"L"));
        //System.out.println(info.numberOfBabiesAndNamesWithStartingLetter(1901,"F","x"));
        //System.out.println(info.printNumberOfBabiesAndNamesWithStartingLetter(1901,"F","M"));
        //System.out.println(info.getRankFromNameAndGender("Henry","F"));
        //System.out.println(info.correspondingNameMostRecentYear(1901,"William", "L"));
        //System.out.println(info.mostPopularNameInRange(1900,19203, "M"));
        //System.out.println(info.printMostPopularNameInRange(1900,1901,"F"));
        System.out.println(info.mostPopularGirlsStartingLetter(1900,1903));
        //System.out.println(info.rankingsOfSpecifiedNameAndGenderInRange("James","M",1900,1903));
        //System.out.println(info.rankingDifferenceFirstLast(1900,1901,"Mariel", "Ff"));
        //info.rankingChangeFirstAndLast(1900,1901,"F");
        //info.averageOverYears(1900,1901,"Florence","L");
        //info.highestAverageOverYears(1900,1901,"L");
        //System.out.println(info.namesAtRankInRange(1900,1903,"M",1));
        //System.out.println(info.namesWithSpecifiedRankMostOften(1900,1903,"M",1));
    }
}


