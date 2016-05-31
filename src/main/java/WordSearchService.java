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
    public ArrayList<Capability> createAllCapabilities(){
        ArrayList<Capability> capabilities = new ArrayList<>();

        capabilities.add(new Capability("Horizontal", "Adds words horizontally in the puzzle", "horizontal"));
        capabilities.add(new Capability("Vertical", "Adds words vertically in the puzzle", "vertical"));
        capabilities.add(new Capability("Diagonal Down", "Adds words diagonally down in the puzzle", "diagDown"));
        capabilities.add(new Capability("Diagonal Up", "Adds words diagonally in the puzzle", "diagUp"));
        capabilities.add(new Capability("Backward", "Adds words horizontally backwards in the puzzle", "backHoriz"));
        capabilities.add(new Capability("Backward Vertical", "Adds words vertically backwards in the puzzle", "backVert"));
        capabilities.add(new Capability("Backward Diagonal Up", "Adds words diagonally up and backwards in the puzzle", "backDiagUp"));
        capabilities.add(new Capability("Backward Diagonal Down", "Adds words diagonally down and backwards in the puzzle", "backDiagDown"));

        return capabilities;
    }

    public ArrayList<Capability> createSelectedCapabilities(Puzzle p){
        ArrayList<Capability> capabilities = new ArrayList<>();
        ArrayList<Capability> allCapabilities = createAllCapabilities();


        for (String s: p.getPp().getCapabilities()) {
            for (int i = 0; i < allCapabilities.size(); i++) {
                if (allCapabilities.get(i).getKeyword().equals(s)){
                    capabilities.add(allCapabilities.get(i));
                }
            }
        }

        return capabilities;
    }

    /**
     * This creates a matrix for us to use when generating the puzzle
     * Since items in an ArrayList can't be accessed until they exist, we populate the puzzle with blank spaces
     * @return An ArrayList of blank strings, to the height and width requested by front end
     */
    public ArrayList<ArrayList<String>> createPuzzle(Puzzle p){

        ArrayList<ArrayList<String>> puzzle = new ArrayList<>();

        // adds rows to the vertical matrix
        for(int i = 0; i < p.getPp().getHeight(); i++){
            ArrayList<String> row = new ArrayList<>();

            // fills in a row with blank spaces
            for(int j = 0; j < p.getPp().getWidth(); j++){
                row.add(" ");
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
    private String getWord(Puzzle p) throws FileNotFoundException {
        File dict = new File("words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");


        // reads the entire dictionary file and splits into separate words
        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        // filters to words between minLength & maxLength
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() >= p.getPp().getMinLength() && word.length() <= p.getPp().getMaxLength())
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
    private String getIntersectWord(int length, char intersectLetter, int intersectPoint) throws FileNotFoundException {
        File dict = new File("words");
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

        for(int i = 0; i < puzzle.getPp().getNumberOfWords(); i++) {
            // picks a random coordinate that exists in the matrix
            int x1 = randomX(puzzle);
            int y1 = randomY(puzzle);

            // gets a random word
            String word = getWord(puzzle);
            HashMap<String, Integer> location = new HashMap<>();
            location.put("x1", x1);
            location.put("y1", y1);
            Word w = new Word(word, location);

            // picks a random direction based on the capabilities we are supporting
            int direction = r.nextInt(puzzle.getPp().getPuzzleCapabilities().size());

            ghostWriter(puzzle, w, direction);

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
        int xCoord = w.getLocation().get("x1");
        int yCoord = w.getLocation().get("y1");

        while(true) {
            if (fitsInPuzzle(xCoord, yCoord, w, direction, p)){
                for (int count = 0; count < w.getWord().length(); count++) {
                    if (!p.getPuzzle().get(yCoord).get(xCoord).equals(" ")) {
                        checkLetters.add(p.getPuzzle().get(yCoord).get(xCoord));
                        intersectPoint = count;
                    }
                    switch (direction) {
                        case (0):
                            xCoord++;
                            break;
                        case (1):
                            yCoord++;
                            break;
                        case (2):
                            xCoord++;
                            yCoord++;
                            break;
                        case (3):
                            xCoord++;
                            yCoord--;
                            break;
                        case (4):
                            xCoord--;
                            break;
                        case (5):
                            yCoord--;
                            break;
                        case (6):
                            xCoord--;
                            yCoord--;
                            break;
                        case (7):
                            xCoord--;
                            yCoord++;
                            break;
                    }
                }

                if (checkLetters.size() > 1) {
                    xCoord = randomX(p);
                    w.getLocation().put("x1", xCoord);
                    yCoord = randomY(p);
                    w.getLocation().put("y1", yCoord);
                    checkLetters.removeAll(checkLetters);
                } else if (checkLetters.size() == 1) {
                    Character letter = checkLetters.get(0).charAt(0);
                    letter = letter.toLowerCase(letter);
                    w.setWord(getIntersectWord(w.getWord().length(), letter, intersectPoint));
                    printWord(p, w, direction);
                    break;
                } else {
                    printWord(p, w, direction);
                    break;
                }
            } else {
                xCoord = randomX(p);
                w.getLocation().put("x1", xCoord);
                yCoord = randomY(p);
                w.getLocation().put("y1", yCoord);
            }
        }
    }

    /**
     *
     * @param p
     * @param w
     * @param direction
     */
    private void printWord(Puzzle p, Word w, int direction){
        // for (each letter in word)
        // add that letter to the correct spot in matrix
        int xCoord = w.getLocation().get("x1");
        int yCoord = w.getLocation().get("y1");

        while(true) {
            if (fitsInPuzzle(xCoord, yCoord, w, direction, p)){
                for (int count = 0; count < w.getWord().length(); count++) {
                    p.getPuzzle().get(yCoord).set(xCoord, (w.getWord().charAt(count) + "").toUpperCase());
                    switch (direction) {
                        case (0):
                            xCoord++;
                            break;
                        case (1):
                            yCoord++;
                            break;
                        case (2):
                            xCoord++;
                            yCoord++;
                            break;
                        case (3):
                            xCoord++;
                            yCoord--;
                            break;
                        case (4):
                            xCoord--;
                            break;
                        case (5):
                            yCoord--;
                            break;
                        case (6):
                            xCoord--;
                            yCoord--;
                            break;
                        case (7):
                            xCoord--;
                            yCoord++;
                            break;
                    }
                }
                w.getLocation().put("x2", xCoord);
                w.getLocation().put("y2", yCoord);
                p.getWords().add(w);
                break;

            } else {
                xCoord = randomX(p);
                w.getLocation().put("x1", xCoord);
                yCoord = randomY(p);
                w.getLocation().put("y1", yCoord);
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
        for(int i = 0; i < p.getPp().getHeight(); i++){
            for(int j = 0; j < p.getPp().getWidth(); j++){

                // creates a random letter
                p.getPuzzle().get(i).get(j);
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'A');

                // if there is already a letter there, leave it alone. if a blank space, enter random char
                if(p.getPuzzle().get(i).get(j).equals(" ")){
                   p.getPuzzle().get(i).set(j, c + "");
                }
            }
        }

        return p;
    }

    public boolean fitsInPuzzle(int xCoord, int yCoord, Word w, int direction, Puzzle p){
        //if word fits in height & width of puzzle
        if ((xCoord + w.getWord().length() < p.getPp().getWidth()) && (yCoord + w.getWord().length() < p.getPp().getHeight())
                // and direction is horizontal, vertical, diagonal down
                && (direction >= 0 && direction <= 2)){ return true; }

        //if word fits in width of puzzle & zero vertical bound
        else if((xCoord + w.getWord().length() < p.getPp().getWidth()) && (yCoord - w.getWord().length() > 0)
                // and direction is diagonal up
                && (direction == 3))  { return true; }

        // if backwards horizontal, check against zero
        else if((xCoord - w.getWord().length() > 0) && (direction == 4)){ return true; }

        // if backwards vertical, check against zero
        else if ((yCoord - w.getWord().length() > 0) && (direction == 5)) { return true; }

        // if backward diagonal up, check both x & y against zero
        else if((xCoord - w.getWord().length() > 0) && (yCoord - w.getWord().length() > 0)
                && (direction == 6)) { return true; }

        // if backward diagonal down, check x against zero and y against height
        else if((xCoord - w.getWord().length() > 0) && (yCoord + w.getWord().length() < p.getPp().getHeight())
                && (direction == 7)){ return true;}

        else{
            return false;
        }

    }


    /**
     * Creates a new random X coordinate inside the width of the puzzle
     * @return The new X coord
     */
    private int randomX(Puzzle p){
        Random r = new Random();
        return r.nextInt(p.getPp().getWidth());
    }

    /**
     * Creates a new random Y coordinate inside the height of the puzzle
     * @return The new Y coord
     */
    private int randomY(Puzzle p){
        Random r = new Random();
        return r.nextInt(p.getPp().getHeight());
    }
}
