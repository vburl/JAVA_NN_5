package ca.danielherrmann.NeuronProject;

import java.util.ListIterator;
import java.util.Vector;

/**
 * An object of the class ErrorCalculator is used during neural net training to calculate a few different types of errors.
 * Stores a Layer, a TrainingPair, and a double value error.
 * @author DHermz
 */
public class ErrorCalculator {

    Layer calculateme;
    TrainingPair icalc;
    double error;

    /*
     * Constructor: ErrorCalculator(Layer connectme, TrainingPair teachme)
     * Creates an object of class ErrorCalculator with Layer connectme, and TrainingPair teachme.
     */
    ErrorCalculator(Layer connectme, TrainingPair teachme) {
	calculateme = connectme;
	icalc = teachme;
    }

    /*
     * Method: calcError()
     * Calculates the error that the Layer calculatme has as compared to the doutput value of the TrainingPair, 
     * adding up all of the errors of the individual Neurons in that Layer. Sets error equal to this value.
     * Useful for calculating the error of a neural net regarding a specific TrainingPair, useful for training. 
     */

    public void calcError() {
	ListIterator<Neuron> calcme = calculateme.giveNeurons().listIterator();
	error = 0;
	while (calcme.hasNext()) {
	    int des;
	    int indexer = (icalc.getDoutput() & (1 << calcme.nextIndex()));
	    if ( indexer == 0) {
		des = 0;
	    }
	    else des = 1;
	    double act = calcme.next().getOutput();
	    error += (0.5)*(act - des)*(act - des);
	}
    }

    /*
     * Method: calcNeuronError()
     * Calculates the errors of the neurons in the calculateme Layer as compared to the value each Neurons needs for the doutput value of the TrainingPair icalc.
     * Returns a Vector of Double values of these errors. 
     * Useful for backpropogation when you need to fire the errors of individual Neurons backwards. 
     */

    public Vector<Double> calcNeuronError(){
	Vector<Double> neuronErrors = new Vector<Double>();
	ListIterator<Neuron> calcme = calculateme.giveNeurons().listIterator();
	while (calcme.hasNext()) {
	    int target;
	    int onoroff = (icalc.getDoutput() & (1 << calcme.nextIndex()));
	    Neuron current = calcme.next();
	    if (onoroff == 0) {
		target = 0;
	    }
	    else target = 1;	
	    neuronErrors.add((current.getOutput() - target));

	}
	return neuronErrors;
    }

    /*
     * Method: getError()
     * Returns the double error.
     */

    public double getError() {
	return error;
    }
}
