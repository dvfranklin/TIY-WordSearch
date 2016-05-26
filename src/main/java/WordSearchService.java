import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class WordSearchService {

    public ArrayList<Capability> createCapabilities(){
        ArrayList<Capability> capabilities = new ArrayList<>();

        capabilities.add(new Capability("Horizontal", "Adds words horizontally in the puzzle", "horizontal"));
        capabilities.add(new Capability("Vertical", "Adds words vertically in the puzzle", "vertical"));
        //capabilities.add(new Capability("Diagonal", "Adds words diagonally in the puzzle", "diagonal"));
        //capabilities.add(new Capability("Backward", "Adds words backwards in the puzzle", "backward"));

        return capabilities;
    }

    public ArrayList<ArrayList<String>> createPuzzle(int width, int height){
        Puzzle p = new Puzzle();
        p.setWidth(width);
        p.setHeight(height);
        ArrayList<ArrayList<String>> puzzle = new ArrayList<>();

        // adds rows to the vertical matrix
        for(int i = 0; i < height; i++){
            ArrayList<String> row = new ArrayList<>(width);

            // fills in a row with blank spaces
            for(int j = 0; j < width; j++){
                row.add("  ");
            }
            puzzle.add(row);
        }

        return puzzle;
    }

    public String getWord(int minLength, int maxLength) throws FileNotFoundException {
        File dict = new File("/usr/share/dict/words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");


        // reads the entire dictionary file and splits into separate words
        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        // filters to words between minLength & maxLength
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() >= minLength && word.length() <= maxLength)
                .collect(Collectors.toList());

        // picks a random word from the filtered list
        String word = filteredWords.get(new Random().nextInt(filteredWords.size()-1));

        return word;
    }

    public String getIntersectWord(int minLength, int maxLength, char intersectLetter, int intersectPoint) throws FileNotFoundException {
        File dict = new File("/usr/share/dict/words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");


        // reads the entire dictionary file and splits into separate words
        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        // filters to words between minLength & maxLength
        List<String> filteredWords = words.stream()
                .filter(word -> word.length() >= minLength && word.length() <= maxLength)
                .filter(word -> word.toLowerCase().charAt(intersectPoint) == intersectLetter)
                .collect(Collectors.toList());

        // picks a random word from the filtered list
        String word = filteredWords.get(new Random().nextInt(filteredWords.size()-1));

        return word;
    }

    public void placeWord(Puzzle puzzle, int numWords) throws FileNotFoundException {
        Random r = new Random();

        for(int i = 0; i < numWords; i++) {
            // picks a random coordinate that exists in the matrix
            int x0 = r.nextInt(puzzle.getWidth());
            int y0 = r.nextInt(puzzle.getHeight());


            String word = getWord(8, 10);

            int direction = r.nextInt(2);

            switch (direction) {
                case 0:
                    System.out.println("horizontal");
                    horizontalWord(word, puzzle, x0, y0);
                    break;
                case 1:
                    System.out.println("vertical");
                    verticalWord(word, puzzle, x0, y0);
                    break;
                /*case 2:
                    diagonalWord(word, puzzle, x0, y0);
                    break;*/
            }
        }
    }

    public void ghostWriter(int x0, int y0, String word, Puzzle puzzle) throws FileNotFoundException {
        Random r = new Random();
        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;


        while (true) {
            if (y0 + word.length() < puzzle.getHeight()) {
                for (int i = y0, count = 0; i < y0 + word.length(); i++, count++) {
                    if (!puzzle.getPuzzle().get(i).get(x0).equals("  ")) {
                        // check path for letters
                        checkLetters.add(puzzle.getPuzzle().get(i).get(x0));
                        intersectPoint = count;
                    }
                }
                if (checkLetters.size() > 1) {
                    //get new coord
                    x0 = r.nextInt(puzzle.getWidth());
                    y0 = r.nextInt(puzzle.getHeight());
                    checkLetters.removeAll(checkLetters);
                } else if (checkLetters.size() == 1) {
                    //get word that matches
                    char letter = checkLetters.get(0).charAt(0);
                    word = getIntersectWord(word.length(), word.length(), letter, intersectPoint);
                    checkLetters.removeAll(checkLetters);
                }
            }
        }
    }

    public void horizontalWord(String word, Puzzle puzzle, int x0, int y0) throws FileNotFoundException {
        Random r = new Random();
        ArrayList<Integer> coords = new ArrayList<>();
        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;


        while (true) {

            // make sure it fits inside array
            if (x0 + word.length() < puzzle.getWidth()) {

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
                        x0 = r.nextInt(puzzle.getWidth());
                        y0 = r.nextInt(puzzle.getHeight());
                        checkLetters.removeAll(checkLetters);

                    // if it's exactly 1
                    } else if(checkLetters.size() == 1){
                        System.out.println("***INTERSECTION***");
                        //get word that matches
                        char letter = checkLetters.get(0).charAt(0);
                            word = getIntersectWord(word.length(), word.length(), letter, intersectPoint);
                            checkLetters.removeAll(checkLetters);
                        for (int i = x0, count = 0; i < x0 + word.length(); i++, count++) {
                            puzzle.getPuzzle().get(y0).set(i, (word.charAt(count) + " ").toLowerCase());
                        }
                        coords.add(x0);
                        coords.add(y0);
                        coords.add(x0 + word.length());
                        coords.add(y0);
                        Word w = new Word(word, coords);
                        puzzle.words.add(w);
                        System.out.println(word);
                        System.out.println(coords);
                        break;
                    } else {

                        for (int i = x0, count = 0; i < x0 + word.length(); i++, count++) {
                            puzzle.getPuzzle().get(y0).set(i, (word.charAt(count) + " ").toLowerCase());
                        }
                        coords.add(x0);
                        coords.add(y0);
                        coords.add(x0 + word.length());
                        coords.add(y0);
                        Word w = new Word(word, coords);
                        puzzle.words.add(w);
                        System.out.println(word);
                        System.out.println(coords);
                        break;
                    }
            } else {
                x0 = r.nextInt(puzzle.getWidth());
                y0 = r.nextInt(puzzle.getHeight());
            }
        }
    }

    public void verticalWord(String word, Puzzle puzzle, int x0, int y0) throws FileNotFoundException {
        Random r = new Random();
        ArrayList<Integer> coords = new ArrayList<>();
        ArrayList<String> checkLetters = new ArrayList<>();
        int intersectPoint = 0;


        while (true) {
            if (y0 + word.length() < puzzle.getHeight()) {
                for(int i = y0, count = 0; i < y0 + word.length(); i++, count++) {
                    if (!puzzle.getPuzzle().get(i).get(x0).equals("  ")) {
                        // check path for letters
                        checkLetters.add(puzzle.getPuzzle().get(i).get(x0));
                        intersectPoint = count;
                    }
                }
                if(checkLetters.size() > 1){
                    //get new coord
                    x0 = r.nextInt(puzzle.getWidth());
                    y0 = r.nextInt(puzzle.getHeight());
                    checkLetters.removeAll(checkLetters);
                } else if(checkLetters.size() == 1){
                    //get word that matches
                    char letter = checkLetters.get(0).charAt(0);
                    word = getIntersectWord(word.length(), word.length(), letter, intersectPoint);
                    checkLetters.removeAll(checkLetters);

                    for (int i = y0, count = 0; i < y0 + word.length(); i++, count++) {
                        puzzle.getPuzzle().get(i).set(x0, (word.charAt(count) + " ").toLowerCase());
                    }
                    coords.add(x0);
                    coords.add(y0);
                    coords.add(x0);
                    coords.add(y0 + word.length());
                    Word w = new Word(word, coords);
                    puzzle.words.add(w);
                    System.out.println(word);
                    System.out.println(coords);
                    break;

                } else {


                    for (int i = y0, count = 0; i < y0 + word.length(); i++, count++) {
                        puzzle.getPuzzle().get(i).set(x0, (word.charAt(count) + " ").toLowerCase());
                    }
                    coords.add(x0);
                    coords.add(y0);
                    coords.add(x0);
                    coords.add(y0 + word.length());
                    Word w = new Word(word, coords);
                    puzzle.words.add(w);
                    System.out.println(word);
                    System.out.println(coords);
                    break;
                }
            } else {
                x0 = r.nextInt(puzzle.getWidth());
                y0 = r.nextInt(puzzle.getHeight());
            }
        }

    }


    public Puzzle randomLetters(Puzzle p){
        //loops through the existing matrix
        for(int i = 0; i < p.getHeight(); i++){
            for(int j = 0; j < p.getWidth(); j++){

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

}
