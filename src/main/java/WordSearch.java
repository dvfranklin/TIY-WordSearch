import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Spark;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class WordSearch {
    public static void main(String[] args) throws FileNotFoundException {

        // if running on heroku, use the configured port
        // otherwise, run on 4567
        int port = System.getenv("PORT") != null ? Integer.valueOf(System.getenv("PORT")) : 4567;
        Spark.port(port);

        // create a service object for the model, and a gson object to serialize/parse data
        WordSearchService service = new WordSearchService();
        Gson gson = new GsonBuilder().create();





        // returns JSON representing the capabilities we support in the puzzle
        Spark.get(
                "/capabilities",
                (request, response) -> {
                    ArrayList<Capability> capabilities = service.createAllCapabilities();
                    return gson.toJson(capabilities);
                }
        );


        Spark.post(
                "/puzzle",
                (request, response) -> {

                    // takes parameters from request body and converts to PuzzleProperties object
                    String puzzleJson = request.body();
                    PuzzleProperties pp = gson.fromJson(puzzleJson, PuzzleProperties.class);

                    // creates new puzzle with the requested properties
                    Puzzle puzzle = new Puzzle(pp);
                    puzzle.setPuzzle(service.createPuzzle(puzzle));
                    puzzle.getPp().setPuzzleCapabilities(service.createSelectedCapabilities(puzzle));

                    // places the proper amount of words of the correct size into the matrix
                    service.placeWords(puzzle);

                    // fills the remaining empty spaces with random letters
                    service.randomLetters(puzzle);
                    

                    // returns a JSON representation of the puzzle
                    return gson.toJson(puzzle);

                }
        );
    }
}
