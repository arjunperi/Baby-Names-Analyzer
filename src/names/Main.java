package names;
import java.io.File; // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;



//toUppercase???
//file stuff - getting it from different directories
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Information info = new Information();


        //Call methods for female top name, male top name, and letter/gender algorithm
        //System.out.println(info.femaleTopRanked(2009));
        //System.out.println(info.topRankedName(19001,"M"));
        //System.out.println(instance.maleTopRanked());
        //System.out.println(info.letter(1901,"F","M"));
        //System.out.println(info.nameGenderRank("Emily","F"));
        //System.out.println(info.nameGenderPair(1901,"xyz", "L"));
        //System.out.println(info.rangeOfYears(1900,1901, "F"));
        //System.out.println(info.popularGirls(1900,1901));
        //System.out.println(info.nameGenderRankingsInRange("Mary","F",1900,1901));
        System.out.println(info.rankingDifferenceFirstLast(1900,1901,"Mariel", "F"));
        //System.out.println(info.rankingChangeFirstAndLast(1900,1901,"F"));
        //System.out.println(info.averageOverYears(1900,1901,"Florence","F"));
        //System.out.println(info.highestAverageOverYears(1900,1901,"F"));
        //System.out.println(info.namesAtRankInRange(1900,1901,"F",8));
        //System.out.println(info.rankMostOften(1900,1903,"F",7));
    }
}


