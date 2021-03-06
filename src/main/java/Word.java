import java.util.ArrayList;
import java.util.HashMap;

/**
 * The words that are hidden in the puzzle, representing by a String and the coordinates where the word can be found.
 */
public class Word {
    private String word;
    private HashMap<String, Integer> location;

    public Word(String word) {
        this.word = word;
    }

    public Word(String word, HashMap<String, Integer> location) {
        this.word = word;
        this.location = location;
    }


    // returns starting x coord of a word
    public int getX(){
        return location.get("x1");
    }

    // returns starting y coord of a word
    public int getY(){
        return location.get("y1");
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public HashMap<String, Integer> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, Integer> location) {
        this.location = location;
    }

    public String toString(){
        return word + ", located at " + location.get("x1") + "," + location.get("y1") + " to " + location.get("x2") + "," + location.get("y2") + "\n";
    }
}
