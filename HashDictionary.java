import java.util.*;
/**
 * This is the class to create the hash dictionary used to store the possible moves for the computer
 *
 * Class: CS2210
 * Date: February 16th 2024
 * @author Jonathan Peters
 */
public class HashDictionary implements DictionaryADT {
    private int sizeArray; //Stores the size of the array
    private LinkedList<Data>[] record; //Might have to increase this by a LOT
    private static final int HASH_VALUE_MULTIPLE = 97; //Choosing constant hash multiple to store the keys in a pseudo random spot in the array
    private int recordsInDict; //Storing the number of records in the array

    /**
     * Name: HashDictionary
     * Returns an empty hashArray
     * @param size - size of the empty dictionary
     */
    @SuppressWarnings({"unchecked"})
    public HashDictionary(int size) {
        sizeArray = size;
        recordsInDict = 0;

        record = new LinkedList[sizeArray];
    }

    /**
     * Name: put
     * Adds a record to the dictionary
     * Must throw an exception if the pair is already in the record
     * @param pair - Data to put into the
     * @return 1 or 0 depending on if there is a collision or not
     * @throws DictionaryException
     */
    public int put(Data pair) throws DictionaryException {
        //To put the information into the hash map I will use Horner's Algorithm to accurately map it to its own special part of the dictionary
        //The position in the dictionary to map the function to
        int pos = getPosition(pair.getConfiguration());

        //Checking if the table is empty at the spot found by the algorithm
        if (record[pos] == null) {
            //If its null, create a linked list to store the value
            LinkedList<Data> linkedPair = new LinkedList<Data>();
            linkedPair.add(pair);
            record[pos] = linkedPair; //Add the linked list to the dictionary
            recordsInDict += 1; //Adding one to the number of records
            return 0; //Return 0 to symbolzie no collision
        } else {
            //If the array is NOT empty, then we have to search through the linked list to try and find if the configuration has already been stored
            //Setting a variable to loop through the linear node
            LinkedList<Data> counter = record[pos];

            //Using a for-each loop to loop through the linked list
            for (Data element: counter) {
                if (element.getConfiguration().equals(pair.getConfiguration())) {
                    throw new DictionaryException();//Throw exception if pair is already stored in the dictionary
                }
            }

            //If we get to the end of the loop with no errors
            counter.add(pair); //Add it to the linkedlist
            recordsInDict += 1; //Adding one to the number of records
            return 1; //Return 1 to symbolize a collision
        }
    }

    /**
     * Name: remove
     * Removing an element from the record
     * Throws an exception if the board configuration is not present in the record
     * @param config - The board stored in a string
     * @throws DictionaryException - Throws an exception if the value is not present
     */
    public void remove(String config) throws DictionaryException {
        //Getting the position of the string
        int pos = getPosition(config);
        //Checking if the value was removed
        boolean removed = false;
        //Setting counter to the linked list stored at pos
        LinkedList<Data> counter = record[pos];

        if (counter == null) { //If the position is already empty
            throw new DictionaryException();
        } else {
            //For-each loop to check every value stored at that
            for (Data element: counter) {
                if (element.getConfiguration().equals(config)) { //If the configurations are the same
                    //If the node needs to be removed
                    counter.remove(element);
                    removed = true;
                    recordsInDict -= 1; //Subtracting one to the number of records
                    //Setting record[pos] back to counter after removing the element
                    record[pos] = counter;
                    break;
                }
            }

            //If it gets to the end of the while loop you need to throw an exception because the value is not there
            if (!removed) {
                throw new DictionaryException();
            }
        }
    }

    /**
     * Name: get
     * Getting the score from the configuration stored in the record
     * If the configuration does not exist in the record, throw an exception
     * @param config - Board in a string
     * @return int - Return the score of the configuration
     */
    public int get(String config) {
        //Getting the position of the string
        int pos = getPosition(config);
        //Setting counter to the linked list stored at pos
        LinkedList<Data> counter = record[pos];
        if (counter == null) { //If no linkedList stored at that position
            return -1;
        } else { //Otherwise if there is a linked list
            //Looping through the linked list with a for-each loop to check every possible node
            for (Data element: counter) {
                if (element.getConfiguration().equals(config)) { //If the configurations are equal, return the score
                    return element.getScore();
                }
            }
            return -1; //If it goes through the whole loop without finding a match, return -1
        }
    }

    /**
     * Name: getPosition
     * Get the position the configuration was stored in the array
     * @param config - The game board in a string
     * @return int - The position of the data in the array
     */
    private int getPosition(String config) {
        //The position in the dictionary to map the function to
        int pos = (int)config.charAt(0);
        //Looping through the value to hopefully get a unique hash
        for (int i = 1; i < config.length(); i++) {
            pos = ((int)config.charAt(i) + pos * HASH_VALUE_MULTIPLE) % sizeArray; //Horner's Rule
        }

        return pos;
    }

    /**
     * Name: numRecords
     * Return the number of records in the array
     * @return int - number of records in the array
     */
    public int numRecords() {
        return recordsInDict;
    }
}