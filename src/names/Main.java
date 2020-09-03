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
        //System.out.println(instance.letter("F","Z"));
        System.out.println(info.nameGenderRank("xyz","F"));
        //System.out.println(instance.nameGenderPair(1901,"xyz", "L"));
        //instance.rangeOfYears(2000,2010, "L");
        //System.out.println(instance.popularGirls(19003,19004));
    }
}


