import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Optional;

public class DataSort {

    public static void main (String [] args){
        //SCRIPTS DATABASE************************************************************************
        //initialize an arrayList to hold every line of dialogue
        ArrayList<Line> allDialogue = new ArrayList<Line>();

        try{
            //find and open the data file
            FileInputStream scriptFis = new FileInputStream(new File("script.xlsx"));

            //make a workbook for the file, then a sheet object
            XSSFWorkbook scriptWb = new XSSFWorkbook(scriptFis);
            XSSFSheet scriptSheet = scriptWb.getSheetAt(0);

            String name = "";
            String dialogue = "";
            int number;
            int episode;

            //begin sorting through the data
            //for every row in the sheet
            for (Row row : scriptSheet){
                //and for every cell in the rows
                for (Cell cell : row){
                    //if this comes from Column 0, it is the name of the speaking character
                    if (cell.getColumnIndex() == 0){
                        //grab the name of the character
                        name = cell.getStringCellValue();
                    //otherwise, it comes from Column 1 and is a line of dialogue
                    } else {
                        //grab the dialogue line
                        dialogue = cell.getStringCellValue();
                    }
                }
                //get the number of the line (+1 due to starting at 0, +1 due to heading row)
                number = row.getRowNum();

                //and figure out which episode the line is from
                //Episode 1 ends at Line 305
                if (number < 305)
                    episode = 1;
                    //Episode 2 ends at Line 556
                else if (number < 556)
                    episode = 2;
                    //Episode 3 ends at Line 825
                else if (number < 825)
                    episode = 3;
                    //All other lines belong to episode 4
                else
                    episode = 4;

                //use all the data to create a new Line and store it in allDialogue
                allDialogue.add(new Line(dialogue, name, number, episode));
            }
            //sort the data by episode
            //Episode 4 ends at Line 1099

        } catch (IOException e){
            System.out.println("IOException encountered: ");
            e.printStackTrace();
            System.exit(0);
        }

        String line;
        String allLines = "";

        //variables for tracking data stats
        ArrayList<String> characters = new ArrayList<String>();
        ArrayList<Integer> characterLines = new ArrayList<Integer>();
        ArrayList<Term> termList = new ArrayList<Term>();
        int[] episodeLines = new int[4];
        int temp;
        int index;
        String[] lineTemp;
        Double avgLength = 0.0;
        Term tempTerm;

        //Go through all collected dialogue lines
        for (Line l : allDialogue){
            System.out.println(l);

            line = l.getDialogue();
            allLines += l.getDialogue();

            //track number of terms per line
            //split on punctuation, space, etc
            lineTemp = line.split("[ ?.,:()&%$#!+*'/\"-]+");
            //add number of terms per line to the avgLength var (to finish calculating later)
            avgLength += lineTemp.length * 1.0;

            //track number of lines per episode
            episodeLines[l.getEpisode()-1] += 1;

            //track number of lines per speaking character
            //if the name is new
            if (!characters.contains(l.getName())){
                //add to the character list. they have 1 line
                characters.add(l.getName());
                characterLines.add(1);
            } else {
                //otherwise, find the speaking character's index
                index = characters.indexOf(l.getName());
                //and increment their number of lines
                temp = characterLines.get(index);
                characterLines.set(index, temp+1);
            }

            //take the split dialogue and add the terms to an ArrayList as Term objects
            for (String t : lineTemp){
                //if the term isn't in the ArrayList yet
                if (termList.stream().noneMatch(p -> p.getTerm().equals(t))){
                    //make a new term using info from the current dialogue line
                    tempTerm = new Term(t);
                    tempTerm.addCharacter(l.getName());
                    tempTerm.addEpisode(l.getEpisode());
                    //and add the term to the termList
                    termList.add(tempTerm);

                //if the term is already in the ArrayList
                } else {
                    //check to see if the term's character posting needs updating
                    if (termList.stream().noneMatch(p -> p.getCharacters().contains(l.getName()))){
                        //locate the Term object containing the current term
                        Optional<Term> optTerm = termList.stream().filter(p -> p.getTerm().equals(t)).findFirst();
                        tempTerm = optTerm.get();
                        //add the current character to the character posting
                        tempTerm.addCharacter(l.getName());
                    }
                    //do the same with the term's episode posting
                    if (termList.stream().noneMatch(p -> p.getEpisodes().contains(l.getEpisode()))){
                        //locate the Term object containing the current term
                        Optional<Term> optTerm = termList.stream().filter(p -> p.getTerm().equals(t)).findFirst();
                        tempTerm = optTerm.get();
                        //add the current character to the character posting
                        tempTerm.addEpisode(l.getEpisode());
                    }
                }
            }


        }

        //WORK IN PROGRESS:
        //TRY TO IMPLEMENT FINDING MOST COMMON TERM BY CHARACTER
        /*
        String[] mostCommon = new String [4];
        for (int i = 0; i < 4; i++){
            //go through each of the
        }
        */

        //TWEETS DATABASE***************************************************************************
        //read in the .xlsx file first
        //text-query-tweets.xlsx

        try {
            FileInputStream tweetFis = new FileInputStream(new File("script.xlsx"));

            //make a workbook for the file, then a sheet object
            XSSFWorkbook tweetWb = new XSSFWorkbook(tweetFis);
            XSSFSheet scriptSheet = tweetWb.getSheetAt(0);

            String content = "";
            String user = "";
            String date = "";
            String time = "";

            //begin sorting through the data
            //for every row in the sheet
            for (Row row : scriptSheet){
                //and for every cell in the rows
                for (Cell cell : row) {

                }
            }
        } catch (IOException e) {
            System.out.println("The system encountered and IOException:");
            e.printStackTrace();
            System.exit(0);
        }

        //finish calculating the average length of dialogue lines by dividing by total number of lines
        avgLength /= allDialogue.size();

        //print out stats
        System.out.println("Speaking lines per character:");
        for (int i = 0; i < characters.size(); i++)
            System.out.println(characters.get(i) + " speaking lines: " + characterLines.get(i));

        System.out.println("Average number of terms per line of dialogue:" + avgLength);

        for (int i = 0; i < episodeLines.length; i++)
            System.out.println("Number of lines in episode " + (i+1) + ": " + episodeLines[i]);

        //total number of terms
        System.out.println("Total number of terms in all episodes: " + termList.size());


    }
}
