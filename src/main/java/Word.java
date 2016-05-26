import java.util.ArrayList;
import java.util.HashMap;

/**
 * The words that are hidden in the puzzle, representing by a String and the coordinates where the word can be found.
 */
public class Word {
    private String word;
    private ArrayList<Integer> location;

    public Word(String word, ArrayList<Integer> location) {
        this.word = word;
        this.location = location;
    }
}
