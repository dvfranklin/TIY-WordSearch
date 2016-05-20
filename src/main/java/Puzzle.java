import java.util.ArrayList;

public class Puzzle {

    WordSearchService service = new WordSearchService();

    private int height;
    private int width;
    private int numberOfWords;
    private int minLength;
    private int maxLength;
    private ArrayList<Capability> capabilities;
    private ArrayList<ArrayList<String>> puzzle;

    //service.createPuzzle(width, height);

    public WordSearchService getService() {
        return service;
    }

    public void setService(WordSearchService service) {
        this.service = service;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public ArrayList<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public ArrayList<ArrayList<String>> getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(ArrayList<ArrayList<String>> puzzle) {
        this.puzzle = puzzle;
    }
}
