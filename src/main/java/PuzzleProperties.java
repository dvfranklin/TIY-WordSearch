import java.util.ArrayList;

/**
 * These are the properties that are passed to us via JSON on the POST /puzzle endpoint.
 * They determine how the puzzle will be structured. The properties & getters are static so they
 * can be accessed without instantiating a PuzzleProperties object.
 */
public class PuzzleProperties {

    private int height;
    private int width;
    private int numberOfWords;
    private int minLength;
    private int maxLength;
    private ArrayList<Capability> puzzleCapabilities;
    private ArrayList<String> capabilities;

    public PuzzleProperties() {
    }

    public PuzzleProperties(int height, int width, int numberOfWords, int minLength, int maxLength, ArrayList<String> capabilities) {
        this.height = height;
        this.width = width;
        this.numberOfWords = numberOfWords;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.capabilities = capabilities;
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

    public ArrayList<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<String> capabilities) {
        this.capabilities = capabilities;
    }

    public ArrayList<Capability> getPuzzleCapabilities() {
        return puzzleCapabilities;
    }

    public void setPuzzleCapabilities(ArrayList<Capability> puzzleCapabilities) {
        this.puzzleCapabilities = puzzleCapabilities;
    }
}
