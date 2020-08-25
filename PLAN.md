# Data Plan
## NAME

This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/01_data/):


### What is the answer to the two questions below for the data file yob1900.txt (pick a letter that makes it easy too answer)? 
1. The top ranked female name is Mary, and the top ranked male name is John.
2. In 1900 for males, 0 names start with the letter X, so 0 total instances.

### Describe the algorithm you would use to answer each one.
1. First, I would take the text file, add each line into an array list, then use that to create a 2D array containing all the data, split into three columns - name, gender, and count (do this using the split() function). For the top ranked female name, I know it is going to be at the top so I will just take array[0][0]. For the top ranked male name, I would loop through the array until the first instance of gender = male, then I would take the namme at that position. 
2. I would loop through my 2D array and check if each row has the matching gender and the first letter of name (using substring() function). If so, I would add 1 to a name counter, and I would add the number of names to a total counter, and then break. If not, I would continue looping. 
- Note that for my program, I used yob1900.txt

### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?
1. To answer the questions in Part 2, I need to implement a consistent method of tracking rankings. To do this, I can add another column to my 
2D array that holds the ranking of each row. To find these rankings, I would have to go through the data and adjust for ties. 

### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?
1. I could make a smaller version of a yob file with only a few names, or only a few genders and test with that, instead of using the very large files. 

### What kinds of things make the second question harder to test?
1. The second question is harder to test because there are more input parameters, and it is also harder to manually verify that the code is 
working correctly by going through the file. 

### What kind of errors might you expect to encounter based on the questions or in the dataset?
1. The kind of errors I expect to encounter are 

### How would you detect those errors and what would a reasonable "answer" be in such cases?

### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?
1. My algorithm and testing would change because right now, I just manually read a specified file, so to handle multiple files I would 
likely have to add some sort of inputs that take in what files are to be read. 
