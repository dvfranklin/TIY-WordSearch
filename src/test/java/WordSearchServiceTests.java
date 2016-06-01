import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Taylor on 5/26/16.
 */
//@RunWith(value = Parameterized.class)
public class WordSearchServiceTests {



/*

    @Before
    public void setUpTestPuzzle() {
        //run WordSearch

    }
*//*
    @Test
    public void printBlankPuzzleTest() {

        //TODO: Arrange
        //instantiate WordSearchService
        WordSearchService testService = new WordSearchService();
        //instantiate new Puzzle
        Puzzle testPuzzle = new Puzzle();
        //instantiate a pre-defined set of PuzzleProperties
        new PuzzleProperties(20, 20, 10, 8, 10, testService.createCapabilities());
        //create a blank Puzzle object
        testPuzzle.setPuzzle(testService.createPuzzle());

        //set origin coordinates to 2,2
        int x0 = 2;
        int y0 = 2;
        //create a new HashMap to store origin coordinates
        HashMap<String, Integer> testLocation = new HashMap<>();
        testLocation.put("x0", x0);
        testLocation.put("y0", y0);
        //create a new Word object spelled "testing" and set the testLocation hashmap into it
        //Word testingWord = new Word("testing", testLocation);
        //set direction to horizontal
        int testDirection = 0;


        for (int i = 0; i < testPuzzle.getPuzzle().size(); i++) {
            for (int j = 0; j < testPuzzle.getPuzzle().get(i).size(); j++) {
                System.out.print(testPuzzle.getPuzzle().get(i).get(j));
            }
            System.out.println();

            assertThat(testPuzzle.puzzle.contains("  "), is(true));
        }
    }*/
    /**
     * Given puzzle set up correctly, word selected
     * When GhostWriter() is run
     * Then it does the following:
     *      -Doesn't find intersection, okays word placement
     *      -Finds valid intersection, okays word placement
     *      -Finds invalid intersection(s), it chooses a new word
     */
/*
    @Test
    public void GhostWriterTest(WordSearchService service, Puzzle p, Word w, PuzzleProperties properties){
        //Arrange
        ArrayList<ArrayList<String>> testPuzzleArrayList = p.getPuzzle();
        ArrayList<Word> testWordsArrayList = p.getWords();
        WordSearchService wordSearchService;
        PuzzleProperties puzzleProperties;

        //Act


        //Assert
        assertThat(testPuzzleArrayList, contains())
    }
*/


    /**
     * Given GhostWriter successfully ran
     * When PrintWord() is run
     * Then word is placed into puzzle
     */
    @Test
    public void PrintWordTest() throws FileNotFoundException{
        //TODO: Arrange
        //instantiate WordSearchService
        WordSearchService testService = new WordSearchService();
        //instantiate new Puzzle
        Puzzle testPuzzle = new Puzzle();
        //instantiate a pre-defined set of PuzzleProperties
        //new PuzzleProperties(20, 20, 10, 8, 10, testService.createCapabilities(testPuzzle));
        //create a blank Puzzle object
        testPuzzle.setPuzzle(testService.createPuzzle(testPuzzle));

        //determine starting coordinates
        int x1 = 2;
        int y1 = 2;

        //determine ending coordinates
        int x2 = 8;
        int y2 = 2;

        //create a new HashMap to store origin coordinates
        HashMap<String, Integer> testLocation = new HashMap<>();
        testLocation.put("x1", x1);
        testLocation.put("y1", y1);
        //create a new Word object spelled "testing" and set the testLocation hashmap into it
        Word testingWord = new Word("testing", testLocation);
        //set direction to horizontal
        Capability testDirection = new Capability("horizontal", "horizontal", "horizontal");

        //run ghostWriter
        testService.ghostWriter(testPuzzle, testingWord, testDirection);

        //print testPuzzle
        for (int i = 0; i < testPuzzle.getPuzzle().size(); i++) {
            for (int j = 0; j < testPuzzle.getPuzzle().get(i).size(); j++) {
                System.out.print(testPuzzle.getPuzzle().get(i).get(j));
            }
            System.out.println();


            //Assert
            //assert that determined starting and ending coordinates have the same characters
            // as printed starting and ending coordinates

            assertThat(testPuzzle.getPuzzle().get(y1).get(x1), is("T "));
            assertThat(testPuzzle.getPuzzle().get(y2).get(x2), is("G "));


        }

    }
}