package ca.danielherrmann.NeuronProject;
import java.util.ListIterator;

/**
 * An object of class Trainer is the object that can utilize TrainingSets to train a neural net. Its one method, trainAll() trains the neural net.
 * A Trainer object stores a NeuralNet, TrainingSet, a double learningrate, and a double errormargin.
 * The NeuralNet trainme specifies the neural net that the trainer trains.
 * The TrainingSet theset is the specific TrainingSet that the Trainer uses to train the net. 
 * The double learningrate specifies how large steps the backpropogation algorithm takes. 
 * The double errormargin defines the error level that the Trainer will stop training the neural net at.
 * @author DHermz
 */
public class Trainer {
    /*
     * Load a premade neural net, training set, and error margin
     * Randomly choose a training pair from the training set, and feed it forward through the net
     * Back propagate the errors and update the weights, 
     * Repeat until all training pairs give error less error margin
     * Once this is the case, training is done.
     */

    NeuralNet trainme;
    TrainingSet theset;
    double learningrate;
    double errormargin;
    
    /*
     * Constructor: Trainer(NeuralNet thetrainme, TrainingSet dothis, double thelearningrate, double theerrormargin)
     * Creates an object of the Trainer class, setting all the Trainer fields to the specified paramaters. 
     */

    Trainer(NeuralNet thetrainme, TrainingSet dothis, double thelearningrate, double theerrormargin) {
	trainme = thetrainme;
	theset = dothis;
	learningrate = thelearningrate;
	errormargin = theerrormargin;
    }
    
    /*
     * Method: trainAll()
     * This method will cause the Trainer to fire the neural netforward, backprogpogate the errors, and adjust the weights of all the connections in the neural net 
     * until the system error, as defined in the ErrorCalculator class, is below the errormargin.
     * Will also print out the total system error, and a message telling the used that the neural net is trained. 
     * Crucial for training the neural net, brings everything together and trains the net. 
     */

    public void trainAll() {
	double systemerror = 1e32;
	theset.getErrors().setSize(theset.getSet().size());
	theset.initializeErrors();
	trainme.initializeSizeOfPartDeriv(theset.getSet().size());
	while (systemerror > errormargin) {
	    for (int counter = 0; counter < theset.getSet().size(); ++counter) {
		ListIterator<TrainingPair> tester = theset.getSet().listIterator();
		while (tester.hasNext()) {
		    int index = tester.nextIndex();
		    TrainingPair current = tester.next();
		    trainme.inputInt(current.getInput());
		    trainme.fireNet();
		    ErrorCalculator errorcalculator = new ErrorCalculator(trainme.getNet().lastElement(), current);
		    errorcalculator.calcError();
		    theset.addError(errorcalculator.getError(), index);
		    trainme.backFireNet(current, index);
		}
		theset.updateSystemError();
		System.out.format("\nThe total system error is %f", theset.getSystemError());
		systemerror = theset.getSystemError();
	    }
	    trainme.updateConnections(learningrate);
	}
	System.out.println();
	System.out.println("\nYour neural net is trained!");

    }
}

