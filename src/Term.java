import java.util.ArrayList;

public class Term {
    //each term tracks the term itself, as well as the characters that speak it and the episodes it's spoken in
    String term;
    ArrayList<String> characterPosting;
    ArrayList<Integer> episodePosting;

    public Term(String term){
        this.term = term;
        characterPosting = new ArrayList<String>();
        episodePosting = new ArrayList<Integer>();
    }

    //getters and setters
    public String getTerm(){return term;}
    public ArrayList<String> getCharacters(){return characterPosting;}
    public ArrayList<Integer> getEpisodes(){return episodePosting;}

    public void addCharacter(String character){characterPosting.add(character);}
    public void addEpisode(int episode){episodePosting.add(episode);}

    //for searching the term's postings
    public int findCharacter(String character){
        //go through the posting and return the index of the given name
        for (String c : characterPosting)
            if (c.equals(character))
                return characterPosting.indexOf(c);
        //otherwise return -1
        return -1;
    }

    public int findEpisode(int episode){
        for (int i : episodePosting)
            if (i == episode)
                return episodePosting.indexOf(i);
        return -1;
    }

    public String toString(){return "\"" + term +"\": Spoken in episodes " + getEpisodes() + " by " + getCharacters();}
}
