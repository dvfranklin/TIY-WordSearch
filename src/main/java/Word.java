import java.util.ArrayList;
import java.util.HashMap;

/**
 * The words that are hidden in the puzzle, representing by a String and the coordinates where the word can be found.
 */
public class Word {
    String word;
    private HashMap<String, Integer> location;

    public Word(String word, HashMap<String, Integer> location) {
        this.word = word;
        this.location = location;
    }


    public int getX(){
        return location.get("x0");
    }

    public int getY(){
        return location.get("y0");
    }
}
