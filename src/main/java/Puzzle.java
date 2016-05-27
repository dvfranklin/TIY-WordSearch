import java.util.ArrayList;

/**
 * This is the puzzle to be returned from our POST /puzzle endpoint. It contains the Matrix of letters making up the word search,
 * and the words (along with the coordinates where they can be found).
 */
public class Puzzle {

    private ArrayList<ArrayList<String>> puzzle;
    private ArrayList<Word> words = new ArrayList<>();


    public ArrayList<ArrayList<String>> getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(ArrayList<ArrayList<String>> puzzle) {
        this.puzzle = puzzle;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }
}
