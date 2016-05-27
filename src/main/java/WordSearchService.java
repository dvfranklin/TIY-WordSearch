import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class WordSearchService {

    /**
     * Adds capabilities to an Array List manually to be returned when requested by the front end
     * Uncommenting them one at a time as supported
     * @return An ArrayList of all the capabilities we are supporting
     */
    public ArrayList<Capability> createCapabilities(){
        ArrayList<Capability> capabilities = new ArrayList<>();

        capabilities.add(new Capability("Horizontal", "Adds words horizontally in the puzzle", "horizontal"));
        capabilities.add(new Capability("Vertical", "Adds words vertically in the puzzle", "vertical"));
        //capabilities.add(new Capability("Diagonal Up", "Adds words diagonally in the puzzle", "diagonUp"));
        //capabilities.add(new Capability("Diagonal Down", Adds words diagonally down in the puzzle", "diagonDown"));
        //capabilities.add(new Capability("Backward", "Adds words backwards in the puzzle", "backward"));

        return capabilities;
    }

    /**
     * This creates a matrix for us to use when generating the puzzle
     * Since items in an ArrayList can't be accessed until they exist, we populate the puzzle with blank spaces
     * @return An ArrayList of blank strings, to the height and width requested by front end
     */
    public ArrayList<ArrayList<String>> createPuzzle(){

        ArrayList<ArrayList<String>> puzzle = new ArrayList<>();

        // adds rows to the vertical matrix
        for(int i = 0; i < PuzzleProperties.getHeight(); i++){
            ArrayList<String> row = new ArrayList<>();

            // fills in a row with blank spaces
            for(int j = 0; j < PuzzleProperties.getWidth(); j++){
                row.add("  ");
            }
            puzzle.add(row);
        }

        return puzzle;
    }

    /**
     * Reads the dictionary file, filters by minLength & maxLength, and returns a random word from the filtered list
     * @return A word that fits the min/max length parameters
     * @throws FileNotFoundException If /usr/share/dict/words does not exist
     */
    public String getWord() throws FileNotFoundException {
        File dict = new File("/usr/share/dict/words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");


        // reads the entire dictionary file and splits into separate words
        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        // filters to words between minLength & maxLength
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() >= PuzzleProperties.getMinLength() && word.length() <= PuzzleProperties.getMaxLength())
                .collect(Collectors.toList());

        // picks a random word from the filtered list
        String word = filteredWords.get(new Random().nextInt(filteredWords.size()-1));

        return word;
    }


    /**
     * If the ghost writer determines that a word will intersect, this method will find a word that has the correct letter in the correct space
     * @param length How long the word must be
     * @param intersectLetter What the intersecting letter is
     * @param intersectPoint At what point in the word the intersecting letter appears
     * @return A word that fits in the correct place
     * @throws FileNotFoundException
     */
    public String getIntersectWord(int length, char intersectLetter, int intersectPoint) throws FileNotFoundException {
        File dict = new File("/usr/share/dict/words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");


        // reads the entire dictionary file and splits into separate words
        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        // filters to words between minLength & maxLength
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() == length)
                .filter(word -> word.toLowerCase().charAt(intersectPoint) == intersectLetter)
                .collect(Collectors.toList());

        // picks a random word from the filtered list
        String word = filteredWords.get(new Random().nextInt(filteredWords.size()-1));

        return word;
    }

    /**
     * Picks a random x & y coordinate, a random word and a random direction, then attempts to write it.
     * Loops for as many words as specified by front end.
     * @param puzzle The puzzle that the words will be placed in.
     * @throws FileNotFoundException In case the dictionary file does not exist
     */
    public void placeWords(Puzzle puzzle) throws FileNotFoundException {
        Random r = new Random();

        for(int i = 0; i < PuzzleProperties.getNumberOfWords(); i++) {
            // picks a random coordinate that exists in the matrix
            int x0 = randomX();
            int y0 = randomY();

            // gets a random word
            String word = getWord();
            HashMap<String, Integer> location = new HashMap<>();
            location.put("x0", x0);
            location.put("y0", y0);
            Word w = new Word(word, location);

            // picks a random direction based on the capabilities we are supporting
            int direction = r.nextInt(PuzzleProperties.getCapabilities().size());
            switch (direction) {
                case 0:
                    ghostWriter(puzzle, w, 0);
                    break;
                case 1:
                    ghostWriter(puzzle, w, 1);
                    break;
            }
        }
    }

    /**
     * Starting at the x1, y1 coords, checks the path the word will take to determine if it will intersect with another word
     * @param p The puzzle that we are inserting the word into
     * @param w The word that we are checking the path for
     * @param direction An integer representing the direction the word is going (horizontal, vertical, diagonal, etc.)
     * @throws FileNotFoundException Because it calls the getWord function that needs a dictionary file
     */
    public void ghostWriter(Puzzle p, Word w, int direction) throws FileNotFoundException {

        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;
        int xCoord = w.getX();
        int yCoord = w.getY();

        while(true) {
            if ((xCoord + w.word.length() < PuzzleProperties.getWidth()) && (yCoord + w.word.length() < PuzzleProperties.getHeight())) {
                for (int count = 0; count < w.word.length(); count++) {
                    if (!p.puzzle.get(yCoord).get(xCoord).equals("  ")) {
                        checkLetters.add(p.puzzle.get(yCoord).get(xCoord));
                        intersectPoint = count;
                    }
                    switch (direction) {
                        case (0):
                            xCoord++;
                            break;
                        case (1):
                            yCoord++;
                            break;
                    }
                }

                if (checkLetters.size() > 1) {
                    xCoord = randomX();
                    yCoord = randomY();
                    checkLetters.removeAll(checkLetters);
                } else if (checkLetters.size() == 1) {
                    char letter = checkLetters.get(0).charAt(0);
                    w.setWord(getIntersectWord(w.word.length(), letter, intersectPoint));
                    printWord(p, w, direction);
                    break;
                } else {
                    printWord(p, w, direction);
                    break;
                }
            } else {
                xCoord = randomX();
                yCoord = randomY();
            }
        }
    }

    /**
     *
     * @param p
     * @param w
     * @param direction
     */
    public void printWord(Puzzle p, Word w, int direction){
        // for (each letter in word)
        // add that letter to the correct spot in matrix
        int xCoord = w.getX();
        int yCoord = w.getY();

        while(true) {
            if ((xCoord + w.word.length() < PuzzleProperties.getWidth()) && (yCoord + w.word.length() < PuzzleProperties.getHeight())) {
                for (int count = 0; count < w.word.length(); count++) {
                    p.puzzle.get(yCoord).set(xCoord, (w.word.charAt(count) + " ").toUpperCase());
                    switch (direction) {
                        case (0):
                            xCoord++;
                            break;
                        case (1):
                            yCoord++;
                            break;
                    }
                }

                break;

            } else {
                xCoord = randomX();
                yCoord = randomY();
            }
        }
    }

    /**
     * Populates the puzzle with random letters in all spaces that are not already occupied
     * @param p The puzzle (after being populated with words)
     * @return A puzzle with every space occupied by a letter
     */
    public Puzzle randomLetters(Puzzle p){
        //loops through the existing matrix
        for(int i = 0; i < PuzzleProperties.getHeight(); i++){
            for(int j = 0; j < PuzzleProperties.getWidth(); j++){

                // creates a random letter
                p.getPuzzle().get(i).get(j);
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'A');

                // if there is already a letter there, leave it alone. if a blank space, enter random char
                if(p.getPuzzle().get(i).get(j).equals("  ")){
                    p.getPuzzle().get(i).set(j, c + " ");
                }
            }
        }

        return p;
    }

    /**
     * Creates a new random X coordinate inside the width of the puzzle
     * @return The new X coord
     */
    public int randomX(){
        Random r = new Random();
        return r.nextInt(PuzzleProperties.getWidth());
    }

    /**
     * Creates a new random Y coordinate inside the height of the puzzle
     * @return The new Y coord
     */
    public int randomY(){
        Random r = new Random();
        return r.nextInt(PuzzleProperties.getHeight());
    }


    /*

    public void horizontalWord(String word, Puzzle puzzle, int x0, int y0) throws FileNotFoundException {



        while (true) {

            // make sure it fits inside array
            if (x0 + word.length() < PuzzleProperties.getWidth()) {


                    } else {

                        for (int i = x0, count = 0; i < x0 + word.length(); i++, count++) {
                            puzzle.getPuzzle().get(y0).set(i, (word.charAt(count) + " ").toLowerCase());
                        }

                        System.out.println(word);
                        break;
                    }
            } else {
                x0 = r.nextInt(PuzzleProperties.getWidth());
                y0 = r.nextInt(PuzzleProperties.getHeight());
            }
        }
    }

    */
}
