data
====

This project uses data about [baby names from the US Social Security Administration](https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 


Name: 
Arjun Peri

### Timeline

Start Date: 
8/22/2020

Finish Date: 
9/8/2020

Hours Spent:
~40Hrs

### Resources Used
Piazza, Course Webpage, Readings, Google (Stack Overflow etc.)

### Running the Program

Main class:
Main.java

Data files needed: 
Everything in my folder called TestSets is what is needed for the tests I have created. I mainly
use yob1900split.txt - yob1903split.txt to run tests, which are files with 10 girls and 10 boys so 
it is easy to manually check things. The other files in the folder were used to test certain edge cases. 

Key/Mouse inputs:
?

Cheat keys:
?

Known Bugs:

Extra credit:


### Notes/Assumptions
NOTES:

- There are three classes
    - Data: This class is the data processing class. It is called by other classes whenever the
    2D array is needed for a given year. This 2D has rows that each hold a name, gender, count, and rank.
    - Information: This class is the method performing class. It holds all the methods needed to answer
    the questions. 
    - Main: this class calls the methods in the other two classes in order to print them.  
    
- Some methods are called by other methods and thus are hard to format print statements. For these,
print helpers are created in the Information class and will be called in main. 

ASSUMPTIONS: 

- In the file: 
   - females will always come before males
   - When giving a range of years, the second input should be larger than the first input
   - All genders are uppercase
   - Files are formatted as "yob" + year + "(anything).txt" 

THINGS IN CODE THAT GRADER MAY HAVE TO MANUALLY CHANGE:

- Right now, the code is formatted in a way that takes in files from my testing set, thus in the form
"yob" + year + "split.txt" 

- This can easily be changed by going into the method getArray() in the Data class and changing the 
pathname there from "data/TestSets/yob" + year + "split.txt" to "data/ssa_complete/yob" + year + ".txt"

- Additionally, in order to work with questions that take in a different dataset, like a decade
 rather than a year (e.g Basic #1), the code is optimized to handle an entire folder. To edit this,
 all that needs to be done is to go into the method getArrayOfFiles() in the Information class 
 and changing the folder pathname from "data/TestSets" to "data/ssa_complete"
    - This should work independently from any pathname change made in the getArray() method based on how the 
    code is designed. 

HANDLING ERRORS:

- Invalid/empty data sources are handled with the try/catch statements made when the method getArray() 
in the Data class is called on a certain year. The method returns a null array, and then in the Information
class, there is a method called isFileValid() that will return any method call if a null array is received. 

- Since the range of years feeds directly into the path string, erroneous ranges will result in 
the file error message being displayed, as long as they are according to the assumptions stated. For example,
inputting a year like 40000 as the start year of a range will trigger the try/catch logic stated above
and will return a corresponding print statement.  

- Other errors that are handled include: specified gender/name/rank combination not existing in the 
given dataset, rank existing in one year but not another year where it is being searched for, erroneous
names, genders, or ranks not existing,etc. 
    - These errors are handled in different ways throughout the code depending on which methods call
    which methods, for example there are certain return values from helper methods that trigger a certain
    print statement or response. The code shouldn't crash as long as all the assumptions hold. 


### Impressions
I didn't think the algorithms themselves were hard to code, the difficult part was understanding the scope of the 
project specifications and connecting all the methods together. 