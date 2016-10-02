package main_package;
import java.util.Vector;
import java.util.Random;
import java.util.ListIterator;

/**
 * An object of the class TrainingSet is used in order to train a neural net to perform a task. 
 * A TrainingSet specifies this task by storing a Vector of TrainingPair, which the class Trainer uses to train the neural net.
 * A TrainingSet also stores a Vector of Doubles that describe the error of each TrainingPair, which it can use to calculate a total error. 
 * @author DHermz
 */
public class TrainingSet {
    Vector<TrainingPair> set = new Vector<TrainingPair>();
    Vector<Double> errors = new Vector<Double>(set.size());
    double systemerror;

    /*
     * Constructor: TrainingSet()
     * Creates an object of the class TrainingSet with empty set and errors Vectors, as well as a systemerror initiated to 0.
     */

    /*
     * Method: addPair(int input, int output)
     * Creates a new TrainingPair setting the doutput and input values of the TrainingPair equal to the specified int. 
     * Adds this TraingPair to the TrainingSet Vector set.
     * Useful for adding a TrainingPair to a TrainingSet.
     */

    public void addPair(int input, int output) {
	set.add(new TrainingPair(input, output));
    }

    /*
     * Method: getRandomPair()
     * Returns a random TrainingPair from the Vector set.
     * Useful for certain styles and types of neural net training.
     */

    public TrainingPair getRandomPair(){
	Random chooser = new Random();
	int index = chooser.nextInt(set.size());
	return set.elementAt(index);
    }

    /*
     * Method: addError(double error, int index)
     * Sets the Double value at the specified position in the Vector errors to the specified value.
     * Useful for updating the Vector errors during training.  
     */

    public void addError(double error, int index) {
	errors.set(index, error);	
    }
    
    /*
     * Method: getErrors()
     * Returns the Vector errors.
     */

    public Vector<Double> getErrors() {
	return errors;
    }
    
    /*
     * Method: getSet()
     * Returns the Vector set.
     */

    public Vector<TrainingPair> getSet() {
	return set;
    }
    
    /*
     * Method: initializeErrors()
     * Sets all of the Double values in the Vector Errors to 10000.
     * Useful so that when starting to train the neural net, it will not have an error bellow the error margin. 
     */

    public void initializeErrors() {
	ListIterator<Double> initialize = errors.listIterator();
	while (initialize.hasNext()) {
	    initialize.next();
	    double init = 10000;
	    initialize.set(init);
	}
    }
    
    /*
     * Method: getHighestErrorPair()
     * Returns the TrainingPair with the highest error at the corresponding position in the Vector errors from the Vector set.
     * Useful for certain styles and types of neural net training.
     */

    public TrainingPair getHighestErrorPair() {
	double currenterror = 0;
	int index = 0;
	ListIterator<Double> findhighest = errors.listIterator();
	while (findhighest.hasNext()) {
	    int currindex = findhighest.nextIndex();
	    Double error = findhighest.next();
	    if (error > currenterror) {
		currenterror = error;
		index = currindex;
	    }
	}
	return set.elementAt(index);
    }

    /*
     * Method: updateSystemError()
     * Sets the systemerror to the sum of all the double values in the Vector errors.
     */

    public void updateSystemError() {
	systemerror = 0;
	for (int i = 0; i < errors.size(); ++i) {
	    systemerror += errors.elementAt(i);
	}
    }
    
    /*
     * Method: getSystemError()
     * Returns the double systemerror.
     */

    public double getSystemError() {
	return systemerror;
    }

}
