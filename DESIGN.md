- names of all people who worked on the project
    - Arjun  Peri
    
- each person's role in developing the project
    - Arjun Peri - Completed the entirety of the project : all code, refactoring, documentation, analysis.

- what are the project's design goals, specifically what kinds of new features did you want to make easy to add?
    - The project design goals were  to eliminate as much duplication as possible and find all the avenues
    for all the separate methods to work together. I wanted to make implementing new methods that accomplish
    similar tasks (going through data files, getting information about name, gender, rank, etc.) as easy as
    possible to implement and have them be able to run off existing code with minimal changes. 
    
- describe the high-level design of your project, focusing on the purpose and interaction of the core classes
    - At a high level, the code works with three classes - one to run methods, one to process the algorithms,
    and one to get a 2D array given a year that holds all the information from that year. The Information class
    is the one that processes the algorithms, and it calls the Data class to get the array any time 
    information for a given year is needed. Within the information class, there are helper methods that are 
    called by the public methods (the ones that actually run the algorithms to answer the questions) which 
    work to eliminate duplicate code - for example running a loop through a 2D array for a year and finding 
    where a certain name and gender exist. Additionally, there are some "key" public methods that are called 
    repeatedly by other public methods throughout the information class, such as rankingsOfSpecifiedNameAndGenderInRange(),
    due to their versatility of parameter inputs and the functionality of what they return in the context of 
    the greater code specifications. 
    
- what assumptions or decisions were made to simplify your project's design, especially those that affected adding required features
    - The assumptions I made were mostly related to the formatting of the files that would hold the information needed, but they 
     also dealt with the nature of how the code is run by a user. These include:
 
       - females will always come before males
       - When giving a range of years, the second input should be larger than the first input
       - All genders are uppercase
       - Files are formatted as "yob" + year + "(anything).txt" 
    
    
- describe, in detail, how to add new features to your project, especially ones you were not able to complete by the deadline.
    
    - To add the URL functionality, I would need to go to the URL and view the source code, and then find a way to 
    isolate the "yob____.txt" portion. Then I would need to read in the URL and for each line, find where it says
    "href" and is followed by a string that contains 4 numerical digits. Then, I would go into that string and take the substring
    that gets me the year, and process it into my 2D array within the Data class as normal. 
    - For the prefix optional question, I would maintain a map that contains all the prefixes as keys and the number of derivative names
    as values. I would call my getListOfNamesInRange() method, make it into a set, then for each name in the set
    loop through the years to get the map values. I would then use my keysWithMaxValues() method to get the top name(s).
    - For the name meaning optional question I would read in the meanings data file and store in an array list, then I would
    add a method that takes in a name and gender and returns the meaning by going through that file and using .contains()
    with the name and gender substring. 
   


