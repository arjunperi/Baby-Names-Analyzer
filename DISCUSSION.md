## Lab Discussion
### Arjun Peri, Partner - Christian Welch 


### Issues in Current Code

 * What pieces of code help versus obscure your understanding?
 Long methods, bad naming conventions, convoluted for loops and logic. 

 * What names in the code are helpful and what makes other names less useful?
Method names like getArray() and topRankedName() are clear and helpful, but others like nameGenderPain() and rangeOfYears() are not. 

 * What additional methods might be helpful to make the code more readable or usable?
Need methods that combine the repeated for loop that goes through the 2D array. 

 * What assumptions does this code have?
This code assumes the file name will be in the form "yob____.txt", and females will come first and then males. No duplicate names as well. s

 * What comments might be helpful within the code?
 I need javadoc comments that explain high level what the methods do rather than the inline comments I have. 

 * WhatÂ Code SmellsÂ did you find?
Duplicate code, shotgun surgery, not following the single responsibility principle. 

### Refactoring Plan

 * What are the code's biggest issues?
Duplicated code- specifically with the repeated for loop that traverses the data structure. 

 * Which issues are easy to fix and which are hard?
 The easy issues are changing small repeated lines into methods (like gender.equals"F)), but the bigger logic is harder to combine. 

 * What are good ways to implement the changes "in place"?
One good way is to make a method that can take parameters to determine how to traverse an array and check equality.

### Refactoring Work

 * Issue chosen: Fix and Alternatives
See above -> making the helper method that traverses array. 
