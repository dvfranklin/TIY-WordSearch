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

        // until we add the controller for the post endpoint, we will have to create our puzzle manually
        WordSearchService service = new WordSearchService();
        Puzzle puzzle = new Puzzle();
        new PuzzleProperties(20, 20, 5, 5, 7, service.createCapabilities());
        puzzle.setPuzzle(service.createPuzzle());
        service.placeWords(puzzle);
        service.randomLetters(puzzle);



        // prints the entire puzzle to the console so we can make sure it worked
        for(int i = 0; i < puzzle.getPuzzle().size(); i++){
            for(int j = 0; j < puzzle.getPuzzle().get(i).size(); j++){
                System.out.print(puzzle.getPuzzle().get(i).get(j) + " ");
            }
            System.out.println();
        }

        System.out.println(puzzle.getWords());

        Gson gson = new GsonBuilder().create();


        // returns JSON representing the capabilities we support in the puzzle
        Spark.get(
                "/capabilities",
                (request, response) -> {
                    ArrayList<Capability> capabilities = service.createCapabilities();
                    return gson.toJson(capabilities);
                }
        );


        // todo - write the controller for this POST
        Spark.post(
                "/puzzle",
                (request, response) -> {

                    //String puzzleJson = request.queryParams("planet");

                    return gson.toJson(puzzle);

                    /*return "{\n" +
                            "    \"puzzle\": [\n" +
                            "        [\"F\",\"Q\",\"P\",\"Y\",\"A\",\"M\",\"H\",\"W\",\"Q\",\"E\",\"Q\",\"A\",\"J\",\"K\",\"X\"],\n" +
                            "        [\"O\",\"W\",\"Z\",\"E\",\"W\",\"D\",\"S\",\"P\",\"C\",\"R\",\"J\",\"T\",\"R\",\"V\",\"S\"],\n" +
                            "        [\"O\",\"L\",\"S\",\"I\",\"L\",\"T\",\"R\",\"F\",\"W\",\"E\",\"N\",\"L\",\"A\",\"K\",\"J\"],\n" +
                            "        [\"D\",\"R\",\"D\",\"V\",\"T\",\"Q\",\"B\",\"Y\",\"E\",\"H\",\"M\",\"O\",\"I\",\"O\",\"T\"],\n" +
                            "        [\"M\",\"M\",\"J\",\"Y\",\"R\",\"D\",\"F\",\"M\",\"Y\",\"T\",\"S\",\"K\",\"C\",\"Q\",\"K\"],\n" +
                            "        [\"K\",\"B\",\"C\",\"B\",\"M\",\"B\",\"U\",\"N\",\"W\",\"Y\",\"F\",\"G\",\"Y\",\"E\",\"W\"],\n" +
                            "        [\"K\",\"E\",\"U\",\"C\",\"Q\",\"T\",\"W\",\"U\",\"B\",\"F\",\"E\",\"U\",\"G\",\"H\",\"H\"],\n" +
                            "        [\"M\",\"P\",\"D\",\"Y\",\"R\",\"V\",\"B\",\"K\",\"T\",\"F\",\"J\",\"R\",\"T\",\"F\",\"A\"],\n" +
                            "        [\"R\",\"W\",\"X\",\"O\",\"H\",\"H\",\"J\",\"N\",\"S\",\"G\",\"U\",\"P\",\"T\",\"Q\",\"T\"],\n" +
                            "        [\"L\",\"O\",\"L\",\"H\",\"M\",\"N\",\"O\",\"B\",\"J\",\"Q\",\"D\",\"Q\",\"T\",\"N\",\"Q\"],\n" +
                            "        [\"Z\",\"K\",\"K\",\"S\",\"B\",\"O\",\"D\",\"X\",\"I\",\"J\",\"Y\",\"C\",\"E\",\"R\",\"V\"],\n" +
                            "        [\"O\",\"A\",\"A\",\"Q\",\"X\",\"X\",\"X\",\"I\",\"P\",\"L\",\"L\",\"K\",\"Q\",\"V\",\"X\"],\n" +
                            "        [\"M\",\"J\",\"D\",\"W\",\"J\",\"Q\",\"X\",\"L\",\"H\",\"C\",\"Z\",\"V\",\"G\",\"B\",\"S\"],\n" +
                            "        [\"B\",\"V\",\"F\",\"B\",\"X\",\"Z\",\"V\",\"G\",\"J\",\"K\",\"C\",\"U\",\"A\",\"A\",\"W\"],\n" +
                            "        [\"F\",\"W\",\"K\",\"K\",\"S\",\"F\",\"B\",\"R\",\"O\",\"C\",\"K\",\"P\",\"X\",\"U\",\"J\"]\n" +
                            "    ],\n" +
                            "    \"words\": [\n" +
                            "        {\n" +
                            "            \"word\": \"food\",\n" +
                            "            \"location\": {\n" +
                            "                x1: 0,\n" +
                            "                y1: 0,\n" +
                            "                x2: 0,\n" +
                            "                y2: 3\n" +
                            "            }\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"word\": \"there\",\n" +
                            "            \"location\": {\n" +
                            "                x1: 9,\n" +
                            "                y1: 4,\n" +
                            "                x2: 9,\n" +
                            "                y2: 0\n" +
                            "            }\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"word\": \"hi\",\n" +
                            "            \"location\": {\n" +
                            "                x1: 8,\n" +
                            "                y1: 12,\n" +
                            "                x2: 7,\n" +
                            "                y2: 11\n" +
                            "            }\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}";*/


                }
        );
    }
}
