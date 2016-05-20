# WordSearch Documentation

## GET capabilities

We have decided to allow words to be hidden horizontally, vertically, diagonally and backward.

To facilitate this, we will create a Capability class with "name", "description" and "keyword" properties. Each of the four capabilities will be represented by a Capability object - when the /capabilities path receives a GET request, it will return a JSON-formatted String representing these capabilities.

## POST puzzle

In order to create a puzzle, the user-specified height and width should be used to create a grid, with each cell representing a space for one letter.  Randomly create a X,Y coordinate that exists (i.e. is less than height and width) and use that as the word's starting point. Also randomly select a word length between the user-specified minimum and maximum length.

Randomly choose between one of the four capabilities, and test if a word  of this size will fit in the grid if going in that direction. If the word has too many letters to fit between its starting point and the edge of the grid, try a different capability among the three remaining. Do this until one of the capabilities will allow enough space for the word. If none of the capabilities will work, choose a new random X,Y coordinate that exists in the grid.

Using the user-specific minimum length of word and maximum length of word, pick a word at random from the dictionary. If the coordinates will intersect with an already existing word, make sure that the new word shares a letter with the already existing word.

Repeat this process until the user-specified number of words has been placed in the puzzle. Randomly generate letters to place in the empty spots on the grid where no words have been placed.


--

There was a lot of technical stuff in here but it was turning into pseudocode so I got rid of it and kept the explanation short.