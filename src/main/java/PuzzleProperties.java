import java.util.ArrayList;

/**
 * These are the properties that are passed to us via JSON on the POST /puzzle endpoint.
 * They determine how the puzzle will be structured. The properties & getters are static so they
 * can be accessed without instantiating a PuzzleProperties object.
 */
public class PuzzleProperties {

    private static int height;
    private static int width;
    private static int numberOfWords;
    private static int minLength;
    private static int maxLength;
    private static ArrayList<Capability> capabilities;

    public PuzzleProperties(int height, int width, int numberOfWords, int minLength, int maxLength, ArrayList<Capability> capabilities) {
        this.height = height;
        this.width = width;
        this.numberOfWords = numberOfWords;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.capabilities = capabilities;
    }

    public static int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public static int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public static int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public static ArrayList<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<Capability> capabilities) {
        this.capabilities = capabilities;
    }
}
