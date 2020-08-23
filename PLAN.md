# Data Plan
## NAME

This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/01_data/):


### What is the answer to the two questions below for the data file yob1900.txt (pick a letter that makes it easy too answer)? 
1. The top ranked female name is Mary, and the top ranked male name is John.
2. In 1900 for males, 1 name starts with the letter Z (Zeno), and 5 total babies have names starting with the letter Z.

### Describe the algorithm you would use to answer each one.
1. First, I would take the text file, add each line into an array list, then use that to create a 2D array containing all the data, split into three columns - name, gender, and count (do this using the split() function). For the top ranked female name, I know it is going to be at the top so I will just take array[0][0]. For the top ranked male name, I would loop through the array until the first instance of gender = male, then I would take the namme at that position. 
2. I would loop through my 2D array and check if each row has the matching gender and the first letter of name (using substring() function). If so, I would add 1 to a name counter, and I would add the number of names to a total counter, and then break. If not, I would continue looping. 

### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?
1

### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?

### What kinds of things make the second question harder to test?

### What kind of errors might you expect to encounter based on the questions or in the dataset?

### How would you detect those errors and what would a reasonable "answer" be in such cases?

### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?

