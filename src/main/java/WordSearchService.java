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

    public Puzzle createPuzzle(int width, int height){
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

        for(int i = 0; i < puzzle.size(); i++){
            puzzle.get(i).set(5, "Z ");
            for(int j = 0; j < width; j++){
                puzzle.get(i).get(j);
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'A');
                if(puzzle.get(i).get(j).equals(" ")){
                    puzzle.get(i).set(j, c + " ");
                }
            }
        }

        p.setPuzzle(puzzle);
        return p;
    }

    public String getWord(int minLength, int maxLength) throws FileNotFoundException {
        File dict = new File("/usr/share/dict/words");
        Scanner scanner = new Scanner(dict);
        scanner.useDelimiter("\\Z");

        String[] allWords = scanner.next().split("\n");
        List<String> words = Arrays.asList(allWords);

        List<String> filteredWords = words.stream()
                .filter(word -> word.length() >= minLength && word.length() <= maxLength)
                .collect(Collectors.toList());


        String word = filteredWords.get(new Random().nextInt(filteredWords.size()-1));

        return word;
    }

    public void placeWord(String word, Puzzle puzzle){
        Random r = new Random();

        int x0 = r.nextInt(puzzle.getWidth());
        int y0 = r.nextInt(puzzle.getHeight());

        System.out.println(x0 + y0);
    }
}
