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
     *
     * @param length
     * @param intersectLetter
     * @param intersectPoint
     * @return
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

//        printWord(w);

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
     *
     * @param P
     * @param w
     * @param direction
     * @throws FileNotFoundException
     */
    public void ghostWriter(Puzzle P, Word w, int direction) throws FileNotFoundException {

        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;
        Random random = new Random();
        int xCoord = w.getX();
        int yCoord = w.getY();

        while(true) {
            if ((xCoord + w.word.length() < PuzzleProperties.getWidth()) && (yCoord + w.word.length() < PuzzleProperties.getHeight())) {
                for (int count = 0; count < w.word.length(); count++) {
                    if (!P.puzzle.get(yCoord).get(xCoord).equals("  ")) {
                        checkLetters.add(P.puzzle.get(yCoord).get(xCoord));
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
                } else if (checkLetters.size() == 1) {
                    char letter = checkLetters.get(0).charAt(0);
                    getIntersectWord(w.word.length(), letter, intersectPoint);
                } else {
                    printWord(w);
                }
            } else {
                xCoord = randomX();
                yCoord = randomY();
            }
        }
    }

    public void printWord(Word w){


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

    public int randomX(){
        Random r = new Random();
        return r.nextInt(PuzzleProperties.getWidth());
    }

    public int randomY(){
        Random r = new Random();
        return r.nextInt(PuzzleProperties.getHeight());
    }

/*
    */
/**
     *
     * @param word
     * @param puzzle
     * @param x0
     * @param y0
     * @throws FileNotFoundException
     *//*

    public void horizontalWord(String word, Puzzle puzzle, int x0, int y0) throws FileNotFoundException {
        Random r = new Random();
        ArrayList<Integer> coords = new ArrayList<>();
        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;


        while (true) {

            // make sure it fits inside array
            if (x0 + word.length() < PuzzleProperties.getWidth()) {

                // loops through the path the word will take
                for(int i = x0, count = 0; i < x0 + word.length(); i++, count++) {

                    // if there's something there that's not a blank space
                    if (!puzzle.getPuzzle().get(y0).get(i).equals("  ")) {
                        // check path for letters
                        checkLetters.add(puzzle.getPuzzle().get(y0).get(i));
                        intersectPoint = count;
                    }
                }

                    // if it intersects in 2+ points
                    if(checkLetters.size() > 1){
                        //get new coord
                        x0 = r.nextInt(PuzzleProperties.getWidth());
                        y0 = r.nextInt(PuzzleProperties.getHeight());
                        checkLetters.removeAll(checkLetters);

                    // if it's exactly 1
                    } else if(checkLetters.size() == 1){
                        System.out.println("***INTERSECTION***");
                        //get word that matches
                        char letter = checkLetters.get(0).charAt(0);
                            word = getIntersectWord(word.length(), letter, intersectPoint);
                            checkLetters.removeAll(checkLetters);
                        for (int i = x0, count = 0; i < x0 + word.length(); i++, count++) {
                            puzzle.getPuzzle().get(y0).set(i, (word.charAt(count) + " ").toLowerCase());
                        }

                        System.out.println(word);

                        break;
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
/**
     *
     * @param word
     * @param puzzle
     * @param x0
     * @param y0
     * @throws FileNotFoundException
     *//*

    public void verticalWord(String word, Puzzle puzzle, int x0, int y0) throws FileNotFoundException {
        Random r = new Random();
        ArrayList<Integer> coords = new ArrayList<>();
        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;


        while (true) {
            for (int i = y0, count = 0; i < y0 + word.length(); i++, count++) {
                puzzle.getPuzzle().get(i).set(x0, (word.charAt(count) + " ").toLowerCase());
            }

            System.out.println(word);
            break;
        }
    }
*/





}
