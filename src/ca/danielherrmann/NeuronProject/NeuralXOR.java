package ca.danielherrmann.NeuronProject;

/**
 * A main method, used for testing the functionality of the rest of my code. I
 * specify that my NeuralNet have 2 input Neurons and 1 output Neurons, as well
 * as 2 hidden layer of 2 neurons each. Creates a NeuralNet with those
 * specifications, and trains it to approximate the XOR function. Training set
 * input values are the integers 0-3, with integers corresponding to inputs
 * based on their binary value. For example, inputting 1 into the net inputs the
 * values 0 and 1 (01), and inputting 3 inputs the values 1 and 1 (11). Once
 * trained, it then prints out its input and its output for all cases of two
 * inputs. Training time differs dramatically each time the example is run
 * depending on the initial random weights.
 * 
 * @author DHermz
 */

public class NeuralXOR {

	public static void main(String[] args) {

		NeuralNet firsttest = new NeuralNet(2, 2, 2, 1);
		TrainingSet firstset = new TrainingSet();

		firstset.addPair(0, 0);
		firstset.addPair(1, 1);
		firstset.addPair(2, 1);
		firstset.addPair(3, 0);

		Trainer testtrainer = new Trainer(firsttest, firstset, 0.1, 0.1);
		testtrainer.trainAll();

		for (int k = 0; k < 4; ++k) {
			firsttest.fireAndPrint(k);
		}

	}

}
