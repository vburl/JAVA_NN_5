package ca.danielherrmann.NeuronProject;

/**
 * An object of the class TraingingPair defines an input and an output pair,
 * used to create a TrainingSet to train the neural net. Stores two int values,
 * doutput (the desired output), and an input.
 * 
 * @author DHermz
 */

public class TrainingPair {
	int doutput, input;

	/*
	 * Constructor: TrainingPair(int in, int out) Creates an object of the class
	 * TrainingPair, setting doutput equal to the specified int, and input equal
	 * to the specified int.
	 */

	TrainingPair(int in, int out) {
		doutput = out;
		input = in;
	}

	/*
	 * Method: getInput() Returns the double input.
	 */

	public int getInput() {
		return input;
	}

	/*
	 * Method: getDoutput() Returns the double doutput.
	 */

	public int getDoutput() {
		return doutput;
	}

}
