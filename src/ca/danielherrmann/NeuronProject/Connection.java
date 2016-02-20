package ca.danielherrmann.NeuronProject;

import java.util.Vector;
import java.util.Random;

/**
 * This class, Connection, is the class that forms the connection between neurons in the neural net. 
 * Connection stores a weight, 2 Neurons, a derivative (dEdW), and a Vector of partial derivatives (Part Deriv).
 * The weight is initialized to 1, and is the modifier to the information the Connection passes between Neurons.
 * The two Neurons specify the Neurons that the specific Connection object connects, 
 * and the direction (which one gets information from which while firing forwards or backwards).
 * The derivative (dEdW) is used to store the derivative of the error of the total system with respect to the weight of the specific Connection
 * The Vector PartDeriv stores the derivatives of the Error of a specific training set with respect to the weight of the specific Connection.
 * @author DHermz
 */
public class Connection {



    double weight = 1;
    Neuron sendforward;
    Neuron sendbackward;
    double dEdW;
    Vector<Double> PartDeriv = new Vector<Double>(0);

    /*
     * Constructor: Connection(Neuron from, Neuron to))
     * As well as creating a new instance of the class Connection, this constructor also gives the Connection a random weight according to the method randomWeight(), 
     * and specifies both a sendforward Neuron and a sendbackward Neuron for the Connection
     */

    Connection(Neuron from, Neuron to){
	sendforward = from;
	sendbackward = to;
	this.randomWeight();
    }

    /*
     * Method: randomWeight()
     * Sets the weight of the Connection to a random double value between 1 and -1 
     */

    public void randomWeight(){
	Random rando = new Random();
	weight = (rando.nextDouble() * 2) - 1;
    }

    /*
     * Method: specifyWeight(double specify)
     * Sets the weight to the specified double value
     */

    public void specifyWeight(double specify){
	weight = specify;
    }

    /*
     * Method: fireForward()
     * Returns the output of the sendforward Neuron multiplied by the weight of the Connection
     * Useful for firing the net forward
     */

    public double fireForward() {
	return (sendforward.getOutput() * weight);
    }

    /*
     * Method: fireBackwards()
     * Returns the backoutput of the sendbackward Neuron multiplied by the weight of the Connection
     * Using for firing the net backwards during backpropogation
     */

    public double fireBackwards() {
	return (sendbackward.getBackOutput() * weight);
    }

    /*
     * Method: initialPartDeriv(int amount)
     * Sets the size of the PartDeriv Vector to the specified amount
     * Useful for setting the PartDeriv Vector to the right size for the TrainingSet during training
     */

    public void initialPartDeriv(int amount) {
	PartDeriv.setSize(amount);
    }

    /*
     * Method: updatePartDeriv(int index)
     * Sets the Double at the specified index in the PartDeriv Vector to the sendbackward backoutput multiplied by the sendforward output
     * Useful for storing partial derivatives of the error of a specific TraingingPair wrt the Connecion, used during training
     */

    public void updatePartDeriv(int index) {
	PartDeriv.set(index, (sendbackward.getBackOutput() * sendforward.getOutput()));
    }

    /*
     * Method: updateDerivative()
     * Sums all of the double value in the PartDeriv Vector and sets dEdW to that value
     * Useful in order to get the error of the whole system (the entire TrainingSet) wrt this Neuron, 
     * in order to adjust the weight in the method updateWeight(double learningrate)
     */

    public void updateDerivative() {

	dEdW = 0;
	for (int i = 0; i < PartDeriv.size(); ++i) {
	    dEdW += PartDeriv.elementAt(i);
	}
    }
    
    /*
     * Method: updateWeight
     * Adds the negative value of the learningrate multiplied by dEdW to the current weight of the Connection
     * Useful in backpropogation to modify the weights of the neural net in order to reduce the error of the system
     */

    public void updateWeight(double learningrate) {
	this.specifyWeight(-(learningrate)*(dEdW) + weight);
    }

}
