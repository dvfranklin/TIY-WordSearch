import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class WordSearchServiceTests {

    // create a service to use throughout
    private WordSearchService service = new WordSearchService();
    private Random r = new Random();


    /**
     * Given a puzzle object
     * When the puzzle is created
     * Then any space is equal to an empty string
     */
    @Test
    public void whenPuzzleCreatedThenEveryElementIsEmpty() {
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);

        //act
        puzzle.setPuzzle(service.createPuzzle(puzzle));

        //assert
        assertThat(puzzle.getPuzzle().get(r.nextInt(20)).get(r.nextInt(20)), is(" "));
    }

    /**
     * Given two puzzles
     * When random letters are added to each
     * Then the puzzle contents are different
     */
    @Test
    public void whenRandomLettersAddedThenPuzzlesAreDifferent(){
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);
        Puzzle testPuzzle = new Puzzle(pp);
        puzzle.setPuzzle(service.createPuzzle(puzzle));
        testPuzzle.setPuzzle(service.createPuzzle(testPuzzle));


        //act
        service.randomLetters(puzzle);
        service.randomLetters(testPuzzle);


        //assert
        assertNotEquals(puzzle.getPuzzle(), (testPuzzle.getPuzzle()));
    }


    /**
     * Given a puzzle
     * When a word is placed in the puzzle
     * Then that word is in the puzzle
     */
    @Test
    public void whenWordEnteredThenWordIsInPuzzle(){
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);
        puzzle.setPuzzle(service.createPuzzle(puzzle));
        HashMap<String, Integer> location = new HashMap<>();
        location.put("x1", 2);
        location.put("y1", 2);
        Word word = new Word("testing", location);

        //act
        service.printWord(puzzle, word, puzzle.getProperties().getPuzzleCapabilities().get(0));

        //assert
        assertThat(puzzle.getPuzzle().get(2).get(2), is("T"));
        assertThat(puzzle.getPuzzle().get(2).get(3), is("E"));
        assertThat(puzzle.getPuzzle().get(2).get(8), is("G"));
        assertThat(puzzle.getPuzzle().get(0).get(0), is(" "));
    }

    /**
     * Given a puzzle and two words
     * When the words will intersect
     * Then a word is chosen with the correct letter in the correct place
     */
    @Test
    public void whenWordsIntersectThenGoodWordIsChosen() throws FileNotFoundException {
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);
        puzzle.setPuzzle(service.createPuzzle(puzzle));
        HashMap<String, Integer> location = new HashMap<>();
        HashMap<String, Integer> intersectLocation = new HashMap<>();
        location.put("x1", 6);
        location.put("y1", 2);
        Word word = new Word("testing", location);
        intersectLocation.put("x1", 5);
        intersectLocation.put("y1", 4);
        Word intersectWord = new Word("abadaba", intersectLocation);

        //act
        service.printWord(puzzle, word, puzzle.getProperties().getPuzzleCapabilities().get(1));
        service.ghostWriter(puzzle, intersectWord, puzzle.getProperties().getPuzzleCapabilities().get(0));

        //assert
        assertThat(puzzle.getPuzzle().get(4).get(6), is("S"));
    }

    /**
     * Given a puzzle and word
     * When the word is printed
     * The coordinates are correctly entered into the hashmap
     */
    @Test
    public void whenWordEnteredThenCoordsAreCorrect(){
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);
        puzzle.setPuzzle(service.createPuzzle(puzzle));
        HashMap<String, Integer> location = new HashMap<>();
        location.put("x1", 2);
        location.put("y1", 2);
        Word word = new Word("testing", location);

        //act
        service.printWord(puzzle, word, puzzle.getProperties().getPuzzleCapabilities().get(0));
        int x1 = word.getLocation().get("x1");
        int y1 = word.getLocation().get("y1");
        int x2 = word.getLocation().get("x2");
        int y2 = word.getLocation().get("y2");

        //assert
        assertThat(puzzle.getPuzzle().get(y1).get(x1), is("T"));
        assertThat(puzzle.getPuzzle().get(y2).get(x2), is("G"));
    }

    /**
     * Given a puzzle and word
     * When the word is entered backward diagonally up
     * Then the coordinates are correctly entered into the Hashmap
     */
    @Test
    public void whenWordEnteredBackDiagUpThenCoordsAreCorrect(){
        //arrange
        ArrayList<Capability> capabilities = service.createAllCapabilities();
        PuzzleProperties pp = new PuzzleProperties(20, 20, 8, 5, 8);
        pp.setPuzzleCapabilities(capabilities);
        Puzzle puzzle = new Puzzle(pp);
        puzzle.setPuzzle(service.createPuzzle(puzzle));
        HashMap<String, Integer> location = new HashMap<>();
        location.put("x1", 10);
        location.put("y1", 10);
        Word word = new Word("testing", location);

        //act
        service.printWord(puzzle, word, puzzle.getProperties().getPuzzleCapabilities().get(6));
        int x1 = word.getLocation().get("x1");
        int y1 = word.getLocation().get("y1");
        int x2 = word.getLocation().get("x2");
        int y2 = word.getLocation().get("y2");

        //assert
        assertThat(puzzle.getPuzzle().get(y1).get(x1), is("T"));
        assertThat(puzzle.getPuzzle().get(y2).get(x2), is("G"));
    }
}