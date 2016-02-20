package ca.danielherrmann.NeuronProject;

/**
 * A main method, used for testing the functionality of the rest of my code.
 * I specify that my NeuralNet have 10 input Neurons and 10 output Neurons, as well as 0 hidden layers. 
 * Creates a NeuralNet with those specifications, and trains it to multiply its input by 2.
 * Training set input values start at 3 and increase by 7 each time, always having the corresponding doputput value be the input multiplied by 2.
 * Once trained, it then prints out all the the results of inputing the integers from 1 to 200.
 * @author DHermz
 */

public class NeuralTest {

    public static void main(String[] args) {

	NeuralNet firsttest = new NeuralNet(10, 0, 0, 10);
	TrainingSet firstset = new TrainingSet();
	for (int i = 3 ; i < 200; i += 7) {
	    firstset.addPair(i, (i*2));
	}
	Trainer testtrainer = new Trainer(firsttest, firstset, 0.1, 0.1);
	testtrainer.trainAll();

	for (int k = 1; k < 201; ++k) {
	    firsttest.fireAndPrint(k);
	}
	
    }

}
