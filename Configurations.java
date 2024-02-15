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

    /**
     * Name: Configurations
     * Constructor for the class
     * @param board - size of the board
     * @param length - length needed to win
     * @param max - Maximum levels the tree will explore
     */
    public Configurations(int board, int length, int max) {
        this.boardSize = board;
        this.lengthToWin = length;
        this.maxLevels = max;
        gameBoard = new char[boardSize][boardSize]; //Initializing the game board
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
        return gameBoard[row][col] == ' ';
    }

    /**
     * Name: wins
     * Checks if given symbol won the game
     * @param symbol - Check if this symbol won the game
     * @return boolean - If they won
     */
    public boolean wins (char symbol) {
        return (horizontalWin(symbol) || verticalWin(symbol) || diagonalWin(symbol) || reverseDiagonalWin(symbol));
    }

    /**
     * Name: diagonalWin
     * Checking if the user won with a diagonal
     * @param symbol - Checking if this symbol won
     * @return boolean - If the symbol won
     */
    private boolean diagonalWin(char symbol) {
        int run = 0; //Holding how many values in a row are the given symbol
        //Looping through the first couple rows only because diagonal must start in this range to be a win
        for (int i = 0; i < (boardSize - lengthToWin + 1); i++) {
            run = 0; //Resetting run
            //Looping through the first couple columns for the same reason
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == symbol) { //If the gameBoard matches the given symbol
                    try { //Try catch in case the diagonal goes outside of the board
                        //Loop lengthToWin times to check if each value in the diagonal is the given symbol
                        for (int k = 0; k < lengthToWin; k++) {
                            //If it is add to run
                            if (gameBoard[i+k][j+k] == symbol) {
                                run += 1;
                            } //If it isn't, break because there isnt enough for a run
                            else {
                                break;
                            }
                        }
                        //If run is the right length, the symbol wins
                        if (run == lengthToWin) {
                            return true;
                        }
                    //Catch out of index exceptions
                    } catch (Exception e) {
                        run = 0; //Resetting run
                    }
                }
            }
        }
        //If the program gets there the symbol did not win with a diagonal
        return false;
    }

    /**
     * Name: reverseDiagonalWin
     * Checking if the user won with a reverse diagonal
     * @param symbol - Checking if this symbol won
     * @return boolean - If the symbol won
     */
    private boolean reverseDiagonalWin(char symbol) {
        int run = 0; //Holding how many values in a row are the given symbol
        //Looping through the first couple rows only because diagonal must start in this range to be a win
        for (int i = 0; i < (boardSize - lengthToWin + 1); i++) {
            run = 0; //Resetting run
            //Looping through the first couple columns for the same reason
            for (int j = 0; j < boardSize; j++) {
                if (gameBoard[i][j] == symbol) { //If the gameBoard matches the given symbol
                    try { //Try catch in case the diagonal goes outside of the board
                        //Loop lengthToWin times to check if each value in the diagonal is the given symbol
                        for (int k = 0; k < lengthToWin; k++) {
                            //If it is add to run
                            if (gameBoard[i+k][j-k] == symbol) {
                                run += 1;
                            } //If it isn't, break because there isnt enough for a run
                            else {
                                break;
                            }
                        }
                        //If run is the right length, the symbol wins
                        if (run == lengthToWin) {
                            return true;
                        }
                        //Catch out of index exceptions
                    } catch (Exception e) {
                        run = 0; //Resetting run
                    }
                }
            }
        }
        //If the program gets there the symbol did not win with a diagonal
        return false;
    }

    /**
     * Name: verticalWin
     * Checking if the symbol won vertically on the board
     * @param symbol - The symbol to check for
     * @return boolean - Did the symbol win
     */
    private boolean verticalWin(char symbol) {
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
        //If it got here there was no vertical win
        return false;
    }

    /**
     * Name: hortizontalWin
     * Checking if the symbol won horizontally on the board
     * @param symbol - The symbol to check for
     * @return boolean - Did the symbol win
     */
    private boolean horizontalWin(char symbol) {
        int run = 0; //Holding how many values in a row they got
        //Find if its vertical
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
            run = 0; //Setting run back to 0 because they didnt win in this row
        }
        //If it got here there was no vertical win
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
