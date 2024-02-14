import java.util.*;
/**
 * This is a class to store the game board and all related methods
 *
 * Date: February 16th 2024
 * @author Jonathan Peters
 */
public class Configurations {
    private int boardSize; //Specifies size of the board
    private int lengthToWin; //Length of the sequence needed to win the game
    private int maxLevels; //Maximum level of the game tree that will be explored
    private char[][] gameBoard; //Storing the game board

    public Configurations(int board, int length, int max) {
        this.boardSize = board;
        this.lengthToWin = length;
        this.maxLevels = max;
        gameBoard = new char[boardSize][boardSize];
        //Setting every value in the game
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }

    /**
     * Name: createDictionary
     * Create a dictionary of a given size 9883
     *
     * @return Hashdictionary - Empty dictionary
     */
    public HashDictionary createDictionary() {
        HashDictionary myDictionary = new HashDictionary(9883);
        return myDictionary;
    }

    /**
     * Name: repeatedConfiguration
     * Getting a configuration and checking if its repeated
     * @param hashDictionary - Given dictionary
     * @return int - -1 if unique or the score if not repeated
     */
    public int repeatedConfiguration(HashDictionary hashDictionary) {
        //Getting and storing the board in a string
        String boardString = boardToString();
        //Returning the score of the boardString
        return hashDictionary.get(boardString);
    }

    /**
     * Name: addConfiguration
     * Adding the configuration to the dictionary
     * @param hashDictionary - The given dictionary
     * @param score - The score of the move to be stored in the dictionary
     */
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        //Getting and storing the board in a string
        String boardString = boardToString();
        //Adding the configuration to the hash dictionary
        Data data = new Data(boardString, score);
        hashDictionary.put(data);
    }

    /**
     * Name: savePlay
     * Saving the play
     * @param row - Row of the play
     * @param col - Column of the play
     * @param symbol - The symbol of the move
     */
    public void savePlay(int row, int col, char symbol) {
        gameBoard[row][col] = symbol;
    }

    /**
     * Name: squareIsEmpty
     * Checking if the given square is empty
     * @param row - Row of the play
     * @param col - Column of the play
     * @return boolean - Returning if the square is empty
     */
    public boolean squareIsEmpty(int row, int col) {
        if (gameBoard[row][col] == ' ') {
            return true;
        }
        return false;
    }

    /**
     * Name: wins
     * Checks if given symbol won the game
     * @param symbol - Check if this symbol won the game
     * @return boolean - If they won
     */
    public boolean wins (char symbol) {
        String boardString = boardToString(); //Incase I need it
        int run = 0; //Holding how many values in a row they got
        //Find if its vertical
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[j][i] == symbol) {
                    run++;
                } else {
                    run = 0;
                }
                if (run == lengthToWin) {
                    return true;
                }
            }
            run = 0; //Setting run back to 0 because they didnt win in this row
        }

        //Find if horizontal
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == symbol) {
                    run++;
                } else {
                    run = 0;
                }
                if (run == lengthToWin) {
                    return true;
                }
            }
            run = 0; //Setting run back to 0 because they didnt win in this column
        }
        //Find if diagonal
        //A diagonal only has a run when the spaces between one symbol and another is boardSize+1 or boardSize-1, nothing else works
        //Need to check both diagonals, easiest way is to use the boardString and step forward by a given amount based on which way to look for the diagonal
        //Since the for loops in the for loop go until the end of the loop, only need to look at the start of the diagonal or boardString.length - lengthtowin-1 * boardsize
        for (int i = 0; i < (boardString.length() - (lengthToWin-1) * boardSize); i++) {
            if (boardString.charAt(i) == symbol) {
                //If you want to look to the diagonal to the left
                if (Math.floorDiv(((i % boardSize) + boardSize - 1), boardSize) > Math.floorDiv(i, boardSize)) {
                    //Looping through boardString stepping by boardSize-1 at a time to find if there is a run, if no run, stop
                    for (int j = i; j < boardString.length(); j = j + boardSize - 1) {
                        if (boardString.charAt(j) == symbol) {
                            run += 1;
                        } else {
                            run = 0;
                            break;
                        }
                        if (run == lengthToWin) { //If the run == length to win, return true
                            return true;
                        }
                    }
                }
                //If you want to look at the diagonal to the right
                if (Math.floorDiv(((i % boardSize) + boardSize + 1), boardSize) < Math.floorDiv(i + 2 * boardSize, boardSize)) {
                    //Looping through boardString stepping by boardSize+1 at a time to find if there is a run, if no run, stop
                    for (int j = i; j < boardString.length(); j = j + boardSize + 1) {
                        if (boardString.charAt(j) == symbol) {
                            run += 1;
                        } else {
                            run = 0;
                            break;
                        }
                        if (run == lengthToWin) { //If the run == length to win, return true
                            return true;
                        }
                    }
                }
            }
        }
        //If no other win conditions are true
        return false;
    }

    /**
     * Name: isDraw
     * Determines if the board is a draw
     * @return boolean - If its a draw
     */
    public boolean isDraw() {
        String boardString = boardToString();
        if (boardString.contains(" ")) { //If there is a space left to place, return false
            return false;
        }
        if (wins('X') || wins('O')) { //If someone won, return false
            return false;
        }
        return true; //Otherwise, return true as its a draw
    }

    /**
     * Name: evalBoard
     * Evaluate how good a move is
     * @return int - Score of the move
     */
    public int evalBoard() {
        if (wins('O')) { //If the computer won
            return 3;
        }
        else if (wins('X')) { //If the user won
            return 0;
        }
        else if (isDraw()) { //If its a draw
            return 2;
        } else { //If undecided return 1
            return 1;
        }
    }

    /**
     * Name: boardtoString
     * Convert the game board to a string
     * @return String - The configuration of the board
     */
    private String boardToString() {
        //Storing the board
        String s = "";

        //Looping through the game board to get it in a string
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s += gameBoard[i][j];
            }
        }

        return s;
    }

}
