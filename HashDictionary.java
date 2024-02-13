import java.util.*;
public class HashDictionary implements DictionaryADT {
    private int sizeArray;
    LinearNode<Data>[] record; //Might have to increase this by a LOT
    private static final int HASH_VALUE_MULTIPLE = 33; //Experimentally 33,37,39,41 work best
    private int recordsInDict = 0; //Storing the number of records in the array

    public HashDictionary(int size) {
        sizeArray = size;
        record = new LinearNode[sizeArray];
    }
    public int put(Data pair) throws DictionaryException {
        //To put the information into the hash map I will use Horner's Algorithm to accurately map it to its own special part of the dictionary
        //The position in the dictionary to map the function to
        int pos = getPosition(pair.getConfiguration());

        //Checking if the table is empty at the spot found by the algorithm
        if (record[pos] == null) {
            //If its null, create a linked list to store the value
            LinearNode<Data> linkedPair = new LinearNode<Data>(pair);
            record[pos] = linkedPair; //Add the linked list to the dictionary
            recordsInDict += 1; //Adding one to the number of records
            return 0; //Return 0 to symbolzie no collision
        } else {
            //If the array is NOT empty, then we have to search through the linked list to try and find if the configuration has already been stored
            //Setting a variable to loop through the linear node
            LinearNode<Data> counter = record[pos];
            do {
                if (counter.getElement().getConfiguration().equals(pair.getConfiguration())) {
                    throw new DictionaryException();//Throw exception if pair is already stored in the dictionary
                }
                counter = counter.getNext(); //Going to next node if error not thrown
            } while (counter.getNext() != null);

            //If we get to the end of the loop with no errors
            LinearNode<Data> linkedPair = new LinearNode<Data>(pair); //Make the next node
            counter.setNext(linkedPair); //Add it to the linkedlist
            recordsInDict += 1; //Adding one to the number of records
            return 1; //Return 1 to symbolize a collision
        }
    }


    public void remove(String config) throws DictionaryException {
        //Getting the position of the string
        int pos = getPosition(config);
        //Checking if the value was removed
        boolean removed = false;
        //Setting counter to the linked list stored at pos
        LinearNode<Data> counter = record[pos];
        //Setting a node to lag behind counter to properly remove the node
        LinearNode<Data> previous = record[pos];
        if (counter == null) { //If the position is already empty
            throw new DictionaryException();
        } else {
            //Checking if counter is already the spot to be removed
            if (counter.getElement().getConfiguration().equals(config)) { //If the configurations are the same
                //If the front node needs to be removed
                record[pos] = counter.getNext();
                recordsInDict -= 1; //Subtracting one to the number of records
            } else {
                if (counter.getNext() == null) { //Making sure the next value isnt null
                    throw new DictionaryException();
                }
                counter = counter.getNext(); //Increasing counter by 1 so its ahead of previous

                do {
                    if (counter.getElement().getConfiguration().equals(config)) { //If the configurations are the same
                        previous.setNext(counter.getNext());
                        removed = true;
                        recordsInDict -= 1; //Subtracting one to the number of records
                        break;
                    }
                    //Going to the next node
                    counter = counter.getNext();
                    previous = previous.getNext();
                } while(counter.getNext() != null);

                //If it gets to the end of the while loop you need to throw an exception because the value is not there
                if (!removed) {
                    throw new DictionaryException();
                }
            }
        }
    }


    public int get(String config) {
        //Getting the position of the string
        int pos = getPosition(config);
        //Setting counter to the linked list stored at pos
        LinearNode<Data> counter = record[pos];
        if (counter == null) { //If no linkedList stored at that position
            return -1;
        } else { //Otherwise if there is a linked list
            //Looping through the linked list with a do-while loop to make sure it looks at the first node
            do {
                if (counter.getElement().getConfiguration().equals(config)) { //If the configurations are the same
                    return counter.getElement().getScore(); //Return the score of the move
                }
                counter = counter.getNext(); //Going to the next node
            } while(counter.getNext() != null);
            return -1; //If it goes through the whole loop without finding a match, return -1
        }
    }


    private int getPosition(String config) {
        //The position in the dictionary to map the function to
        int pos = (int)config.charAt(0);
        //Looping through the value to hopefully get a unique hash
        for (int i = 1; i < config.length(); i++) {
            pos = ((int)config.charAt(i) + pos * HASH_VALUE_MULTIPLE) % sizeArray; //Horner's Rule
        }

        return pos;
    }

    public int numRecords() {
        return recordsInDict;
    }
}
