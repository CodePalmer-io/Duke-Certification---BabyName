import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import edu.duke.StorageResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class BabyName {

    //rec.get(0) is baby name
    //rec.get(1) is baby gender
    //rec.get(2) number of birth

    /**
     *
     * Modify the method totalBirths (shown in the video for this project) to also print the number of girls names ,
     * the number of boys names and the total names in the file.
     */
    private void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int uniqueBoyNameCounter = 0;
        int uniqueGirlNameCounter = 0;
        Set<String> uniqueGirlNames = new HashSet<>();          //Created a set collection because it ignores duplicates
        Set<String> uniqueBoyNames = new HashSet<>();


        for(CSVRecord record : fr.getCSVParser()) {             //As we go through the file

            int numBirth = Integer.parseInt(record.get(2));     //Convert String values in file to type int
            String name = record.get(0);
            totalBirths += numBirth;                            //Adds up all the numBirth recorded named it totalBirths
            if(record.get(1).equals("M")) {                     //If the gender equals("M") then add +1 to total boys
                totalBoys += 1;
                if(!uniqueBoyNames.contains(name)) {
                    uniqueBoyNames.add(name);                   //For every unique non dup boy names -> +1 to counter
                    uniqueBoyNameCounter++;
                }
            }
            else  {
                    totalGirls += 1;                            //If gender if ("F") --> +1 tot totalGirls
                    if(!uniqueGirlNames.contains(name)) {
                        uniqueGirlNames.add(name);              ////For every unique non dup girl names -> +1 to counter
                        uniqueGirlNameCounter++;
                    }
            }
        }

        System.out.println("Total birth recorded is: " + totalBirths);
        System.out.println("Total baby boys recorded is: " + totalBoys);
        System.out.println("Total baby girls recorded is: " + totalGirls);
        System.out.println("Total unique boy name is: " + uniqueBoyNameCounter);
        System.out.println("Total unique girl name is: " + uniqueGirlNameCounter);  //The answer for Quiz is 2225 but my output is 2224 not sure why its off by 1
    }

    private void testTotalBirths() {
        //String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Data/us_babynames/us_babynames_by_year/yob1905.csv";
        String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Data/us_babynames/us_babynames_by_year/yob1900.csv";
        FileResource fr = new FileResource(fileName);
        totalBirths(fr);
    }

    /**
     *
     * Write the method named getRank that has three parameters: an integer named year,
     * a string named name, and a string named gender (F for female and M for male).
     * This method returns the rank of the name in the file for the given gender,
     * where rank 1 is the name with the largest number of births. If the name is not in the file,
     * then -1 is returned.
     */

    private int getRank(int year, String name, String gender) {
       // String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Test/us_babynames_by_decade/yob"+year+"s.csv";
        String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Data/us_babynames/us_babynames_by_year/yob"+year+".csv";
        //String fileName = "Data/us_babynames/us_babynames_test/yob"+year+"short.csv";
        FileResource f = new FileResource(fileName);

        int rank = 0;                                                   //set a counter
        for (CSVRecord record : f.getCSVParser(false)) {
            if (record.get(1).equals(gender)) {                         //Check if gender matches, if it does, then increase rank by 1
                rank++;
                if (record.get(0).equals(name)) {                       //Only when the name matches will the loop exit
                    break;
                }
            }
        }
        return rank;                                                    //return the rank count
    }

    private void testGetRank(){
        int rank = getRank(1960, "Emily", "F");
       // int rank = getRank(1971,"Frank","M");
        System.out.println("The rank for this individual is: " + rank);
    }




    /**
     * Write the method named getName that has three parameters: an integer named year, an integer named rank,
     * and a string named gender (F for female and M for male). This method returns the name of the person
     * in the file at this rank, for the given gender, where rank 1 is the name with the largest number of births.
     * If the rank does not exist in the file, then “NO NAME”  is returned.
     */

    private String getName(int year, int rank, String gender) {
        //String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Test/us_babynames_by_decade/yob"+year+"s.csv";
        String fileName = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Data/us_babynames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr = new FileResource(fileName);
        String name = "NO NAME";
        int currentRank = 1;                                            //Initialize current rank to 1 - indicating at least one person is at rank 1

        for(CSVRecord record : fr.getCSVParser(false)) {
            if(record.get(1).equals(gender)) {                          //As you traverse through the file if gender matches proceed to next if statement
                if(currentRank == rank) {                               //If the currentRank equals Rank - retrieve(return) the name of that person
                    name = record.get(0);
                    return name;
                }
                currentRank++;                                          //If the name does not match -> increase currentRank +1
            }
        }
        return name;                                                    //If the name doesn't exist in file -> then the default String name = "NO NAME" is return
    }

    private void testGetName(){
        String result1 = getName(1980, 350, "F");
        //String result1 = getName(1982, 450, "M");
        System.out.println("The retrieved name is: " + result1);
    }



    /**
     * What would your name be if you were born in a different year? Write the void method named whatIsNameInYear that
     * has four parameters: a string name, an integer named year representing the year that name was born,
     * an integer named newYear and a string named gender (F for female and M for male). This method determines what
     * name would have been named if they were born in a different year, based on the same popularity. That is,
     * you should determine the rank of name in the year they were born, and then print the name born in newYear that
     * is at the same rank and same gender.
     */
    private void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int retrieveRank = getRank(year, name, gender);                    //Calls getRank method with parameter (year, name, gender)
        String yourName = getName(newYear,retrieveRank ,gender);           //Calls getName method with parameter (newYear, retrieveRank, gender)
        System.out.println(name + " born in " + year + " would be " + yourName + " if she/he was born in " + newYear);
    }

    private void testWhatIsNameInYear() {
       whatIsNameInYear("Susan", 1972, 2014, "F");
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }




    /**
     *
     *Write the method yearOfHighestRank that has two parameters: a string name, and a string named gender
     * (F for female and M for male). This method selects a range of files to process and returns an integer,
     * the year with the highest rank for the name and gender. If the name and gender are not in any of the
     * selected files, it should return -1.
     */

    private int yearOfHighestRank(String name, String gender) {
        int currRankRecorded = -1;                              //Used as the main storage for rank variable
        int yearRecorded = -1;                                  //Used as main storage for year variable and will be printed
        int rank;
        int year;
        int index;
        int tempRank;                                           //Will be used to stored the incremented rank count


        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            String fname = f.getPath();                         //Gets the path name for your file from your computer
            FileResource fr = new FileResource(fname);
            index = fname.indexOf("yob") + 3;                           //looks for "yob" as it is part of the file name of every cvs file
            year = Integer.parseInt(fname.substring(index, index + 4)); //Parse the year of type string to type int
            rank = -1;                                                  //Having rank set ot -1 will help ignore the if conditions on the bottom
            tempRank = 0;
            for (CSVRecord record : fr.getCSVParser(false)) {
                if (record.get(1).equals(gender)) {                     //If the gender matches --> tempRank +1
                    tempRank += 1;
                    if (record.get(0).equals(name)) {                   //If the name matches
                        rank = tempRank;                                //Initialize the rank to tempRank value's
                        System.out.println("This year " + year + " and my rank is: " + rank);
                        break;
                    }
                }
            }                                                           //The following if statement will initialize its first name found
            if (currRankRecorded == -1 && rank != -1) {                 //If currRankRecorded is -1 and rank is not -1
                currRankRecorded = rank;                                //set rank to currRankRecorded
                yearRecorded = year;                                    //set year to yearRecorded
            }                                                           //We're looking for the highest rank -> since 1 is considered the highest; the lowest
            else if (rank < currRankRecorded && rank != -1) {           //possible rank found for the individual is the highest rank
                currRankRecorded= rank;                                 //If currRankRecorded is higher than the next rank and rank is not -1
                yearRecorded = year;                                    //reinitialize currRankRecorded and yearRecorded with the new values.
            }
        }
        return yearRecorded;                                            //return the year of the highest rank
    }


    private void testYearOfHighestRank() {
        String name = "Mich";
        String gender = "M";
        int year = yearOfHighestRank(name, gender); //691 // 1968
        System.out.println("The year with the highest rank for " + name + " (gender " + gender
                + ") is " + year);
    }



    /**
     * Write the method getAverageRank that has two parameters: a string name,
     * and a string named gender (F for female and M for male). This method selects a range of files to
     * process and returns a double representing the average rank of the name and gender over the selected
     * files. It should return -1.0 if the name is not ranked in any of the selected files.
     */

    private double getAverageRank(String name, String gender) {
        int rank = 0;
        int count = 0;
        double average;

        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            String fname = f.getPath();
            FileResource fr = new FileResource(fname);
            for (CSVRecord record : fr.getCSVParser(false)) {
                if(record.get(1).equals(gender)) {
                    rank += 1;                                                  //Increment rank if gender matches
                    if (record.get(0).equals(name)) {
                        count++;                                                //Increment count if name matches
                        break;
                    }
                }
            }
        }

        if(count == 0) {                                                         //If name was never found, then count is 0
            return -1;                                                           //return -1
        }
        average = rank/count;
        return average;                                                          //return the average
    }


    private void testGetAverageRank() {
        double average = getAverageRank("Robert", "M");
        System.out.println("Average rank is "+average);
    }

    /**
     * Write the method getTotalBirthsRankedHigher that has three parameters:
     * an integer named year, a string named name, and a string named gender
     * (F for female and M for male). This method returns an integer,
     * the total number of births of those names with the same gender and same year who are
     * ranked higher than name.
     */

    private int getTotalBirthsRankedHigher (int year, String name, String gender) {
        int totalBirths = 0;
        //String filename = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/testing/yob"+year+"short.csv";
        String filename = "/Users/main/IdeaProjects/WeeklyProject - BabyNamee/src/Data/us_babynames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr = new FileResource(filename);
        for (CSVRecord record : fr.getCSVParser(false)) {
            if(record.get(1).equals(gender)) {                                      //If gender matches -> check if name matches
                if (record.get(0).equals(name))
                    break;                                                          //if name matches -> break the loop
                totalBirths += Integer.parseInt(record.get(2));                     //if name does not match -> + totalBirths -> check for gender and name again
            }
        }
        return totalBirths;
    }

    private void testGetTotalBirthsRankedHigher() {
        //int totalBirth = getTotalBirthsRankedHigher(2012,"Ethan","M");
        //int totalBirth = getTotalBirthsRankedHigher(1990, "Emily", "F");
        int totalBirth = getTotalBirthsRankedHigher(1990, "Drew", "M");

        System.out.println("Total Birth is: " + totalBirth);
    }



    public static void main(String args[]) {
        BabyName test = new BabyName();
        //test.testGetName();
        //test.testGetRank();
        test.testTotalBirths();
        //test.testWhatIsNameInYear();
        //test.testYearOfHighestRank();
        //test.testGetAverageRank();
        //test.testGetTotalBirthsRankedHigher();
    }

}




