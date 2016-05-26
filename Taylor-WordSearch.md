# WORDSEARCH PUZZLE PROJECT

For this project we will be creating a wordsearch puzzle from scratch using Java.  The Java backend developer teams will work with IOS frontend developer teams to create a fully-functional wordsearch game app.  The Java developers will create a restful API that generates a wordsearch puzzle based on user specifications that is encoded in JSON format.  

A wordsearch puzzle consists of a grid comprised primarily of randomly written letters.  Hidden with the matrix of gibberish are words that are to be found by the player.  The words to be found are contained in a provided list or box; when the player recognizes one of the words, they then highlight, circle, or otherwise mark the letters that comprise the word, and then cross it out.   These words can be arranged horizontally, vertically, and diagonally in the grid.  They can also be spelled out forwards ('a r r a y') or backwards ('y a r r a').  This game is a test of pattern recognition and methodical examination.

## <a name="capabilities-title"></a> /capabilities Endpoint
The wordsearch puzzle generation program will support a number of options the user can select to create a desired word search puzzle. At minimum the program will allow users to choose between:

* Horizontal word orientation
* Vertical word orientation

Additional word orientation options will be added as time permits.  These include:

* Diagonal word orientation
* Words written out forwards
* Words written out backwards

In addition to how the words are hidden, the program will also allow the user to choose the parameters of the wordsearch puzzle.  The parameters are as follows:

Parameter        | Description
:---------------:|:---------------:
 Word length     | 4 to 10 letters
 Number of words | 5 to 10 words 
 Puzzle height   | 20 to 40 tiles
 Puzzle width    | 20 to 40 tiles

In order to be able to give the user the list of options to select, the code will have to have a 'capabilities' container that holds the various options.  This will also necessitate functions that allow the java program to retrieve those options and implement them directly in the main puzzle generation code.  The program will also have to encode the list of capabilities in JSON so that the app the IOS developers create can interact with the Java code.


## <a name="puzzle-title"></a> /puzzle Endpoint

The actual word search puzzle generation program will be able to generate a number of wordsearch puzzles with a variety of difficulties based upon what the user input when selecting the capabilities.  The generation code will be fairly robust, and will follow a similar process as when writing out a wordsearch puzzle by hand.  To illustrate the general idea that the code will follow, the method to create a wordsearch puzzle from scratch by hand will be written out.

###Wordsearch Puzzle Method
 1. Determine grid dimensions.
 2. Determine number of words to hide.
 3. Determine word length range.
 4. Draw wordsearch grid on graph paper with pen or pencil. The grid will be however tiles wide by tiles tall as chosen in step 1.
 5. Create a grid identification system to give individual tiles specific labels so as to easily keep track of which tiles contain which letters later.  This can be an (X,Y) coordinate system with X and Y being numbers, letters, or both in combination.  
 6. Choose words from a dictionary at random using ranges from steps 2 and 3.  
 7. Write out chosen words in a separate text box.
 8. Select a word from the text box.
 9. To begin placing the word, choose a pair of numbers  at random that are within the width and height ranges used to construct the grid.  These will form the origin coordinate (OC).
 10. Next determine which orientation the word will be written out in.  This can be horizontal, vertical, or diagonal (if selected).  
 11. From the OC using the orientation from step 10, count out how many tiles there are.  
   * A. If there are not enough tiles to write out the word, choose a different orientation from the OC and repeat step 10.
 	* B. If there are no available orientations from the OC, repeat steps 9-11 as needed.
 12. Once a valid orientation is found, determine whether or not the word will be written out forwards or backwards (if this capability is implemented).
 13. Determine if there are any characters associated with other previously-written words in the path of the current word. 
  	 * A. If there are none, proceed to step 14.
	 * B. If there are already words in the path, determine if the current word shares a single character with the previous word in the specific overlapping tile.
	 	* a. If the two words have two or more overlapping characters, repeat this process from step 9.
	 	* b. If the two words do share a single overlapping character, then proceed to step 14.
	 	* c. If they don't share the letter, repeat this process from step 10.  
	 	* d. If no other valid orientations exist, then choose a new word with the same length from the dictionary at random, but this time one that shares a character at the same location as the previously written word.  Erase the original word in the text box and replace it with this new one. Proceed to step 14.
14. Break up the word into its individual characters. 
15. Write each character in each tile, starting with the first letter in the OC, in sequence depending on the word orientation and spelling direction.
16. Repeat steps 8 - 15 until all the selected words are entered into the grid.
17. Populate whatever tiles are not already filled with random letters starting from the first empty tile in the upper left corner and working across each row.  
	* If there is already a character in the tile, skip over the tile until an empty tile is found.

Once all the tiles are filled, there's your wordsearch.  Here's an example of what one should look like when completed.

![Markdown crossword example](https://i.imgur.com/BBT9hea.png)

* 5 words
* 6 characters each
* 10 x 10
* Horizontal, vertical, and diagonal
* Forwards and backwards
 