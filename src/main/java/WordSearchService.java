import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class WordSearchService {

    public ArrayList<Capability> createCapabilities(){
        ArrayList<Capability> capabilities = new ArrayList<>();

        capabilities.add(new Capability("Horizontal", "Adds words horizontally in the puzzle", "horizontal"));
        capabilities.add(new Capability("Vertical", "Adds words vertically in the puzzle", "vertical"));
        capabilities.add(new Capability("Diagonal", "Adds words diagonally in the puzzle", "diagonal"));
        capabilities.add(new Capability("Backward", "Adds words backwards in the puzzle", "backward"));

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
                row.add(" ");
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

    public void placeWord(Puzzle puzzle) throws FileNotFoundException {
        Random r = new Random();

        // picks a random coordinate that exists in the matrix
        int x0 = r.nextInt(puzzle.getWidth());
        int y0 = r.nextInt(puzzle.getHeight());
        System.out.println(x0 + ", " + y0);


        String word = getWord(4,8);

        int direction = r.nextInt(2);
        System.out.println(direction);
        switch(direction) {
            case 0:
                horizontalWord(word, puzzle, x0, y0);
                break;
            case 1:
                verticalWord(word, puzzle, x0, y0);
                break;
            /*case 3:
                diagonalWord(word, puzzle, x0, y0);
                break;*/
        }

    }

    public void horizontalWord(String word, Puzzle puzzle, int x0, int y0) {
        Random r = new Random();

        while (true) {
            if (x0 + word.length() < puzzle.getWidth()) {
                int count = 0;
                for (int i = x0; i < x0 + word.length(); i++) {
                    puzzle.getPuzzle().get(y0).set(i, (word.charAt(count) + " ").toLowerCase());
                    count++;
                }
                System.out.println(word);
                break;
            } else {
                x0 = r.nextInt(puzzle.getWidth());
                y0 = r.nextInt(puzzle.getHeight());
            }
        }
    }

    public void verticalWord(String word, Puzzle puzzle, int x0, int y0){
        Random r = new Random();

        while (true) {
            if (y0 + word.length() < puzzle.getHeight()) {
                int count = 0;
                for (int i = y0; i < y0 + word.length(); i++) {
                    puzzle.getPuzzle().get(i).set(x0, (word.charAt(count) + " ").toLowerCase());
                    count++;
                }
                System.out.println(word);
                break;
            } else {
                x0 = r.nextInt(puzzle.getWidth());
                y0 = r.nextInt(puzzle.getHeight());
            }
        }

    }


    public Puzzle randomLetters(Puzzle p){
        //loops through the existing matrix
        for(int i = 0; i < p.getHeight(); i++){
            //sets the 6th character on each row to Z
            //p.get(i).set(5, "Z ");
            for(int j = 0; j < p.getWidth(); j++){

                // creates a random letter
                p.getPuzzle().get(i).get(j);
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'A');

                // if there is already a letter there, leave it alone. if a blank space, enter random char
                if(p.getPuzzle().get(i).get(j).equals(" ")){
                    p.getPuzzle().get(i).set(j, c + " ");
                }
            }
        }

        return p;
    }

}
