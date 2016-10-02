package main_package;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Objects of this class, Neuron, act as the neurons in the neural net (as the name suggests), and as a result are the main processing units of the neural net, 
 * processing numerical data using a sigmoid function . A Neuron stores two Vectors of objects of the class Connection as well a double output, a double backoutput, 
 * and a double dfdx.
 * The two Vectors, inputconnections and outputconnections, are used to keep track of all the Connection objects connected to the Neuron.
 * The double output stores the output of the Neuron (for firing forward and Connection weight adjustment).
 * The double backouput stores the backoutput of the Neuron (for firing backwards and Connection weight adjustment).
 * The double dfdx stores the derivative of the system output wrt the sigmoid functions of the individual Neuron.
 * @author DHermz
 */
public class Neuron {

    Vector<Connection> inputconnections = new Vector<Connection>();
    Vector<Connection> outputconnections = new Vector<Connection>();
    double output;
    double backoutput;
    double dfdx;
    
    /*
     * Constructor: Neuron()
     * Creates a new instance of the class Neuron, with empty inputconnections and outputconnections Vectors.
     */

    /*
     * Method: makeInputConnection(Connection connector)
     * Adds the specified Connection to the inputconnections Vector.
     * Useful for connecting two Neurons via a Connection object
     */
    
    public void makeInputConnection(Connection connector){
	inputconnections.add(connector);
    }
    
    /*
     * Method: makeOutputConnection(Connection connector)
     * Adds the specified Connection to the outputconnections Vector.
     * Useful for connecting two Neurons via a Connection object
     */

    public void makeOutputConnection(Connection connector){
	outputconnections.add(connector);
    }
    
    /*
     * Method: makeOutput()
     * Sums the values returned by the Connections in the Vector inputconnections, puts the sum through the sigmoid function (1/(1+e^x)), 
     * and sets the output equal to the sigmoidal value. Also sets dfdx equal to the derivative of the sigmoid function. 
     * Useful both for firing forward and for backpropogation. Sigmoid function is used because it is continuous and differentiable, crucial for backpropogation. 
     */

    public void makeOutput(){
	double sum = 0;
	ListIterator<Connection> lister = inputconnections.listIterator();
	while (lister.hasNext()) {
	    Connection add = lister.next();
	    sum = sum + add.fireForward();
	}
	output = ((1 / (1+Math.exp(-sum))));
	dfdx = output - (output * output);
    }
    
    /*
     *Method: makeBackOutput()
     *Sets the backoutput of the Neuron to 0. in order to 'clear the memory', then sums the values returned by the Connections 
     *in the Vector outputconnections multiplied by the dfdx of the Neuron. Sets the backoutput equal to this value.
     * Useful for firing backwards and backpropogation. 
     */

    public void makeBackOutput() {
	backoutput = 0.;
	ListIterator<Connection> backlister = outputconnections.listIterator();
	while (backlister.hasNext()) {
	    Connection backadd = backlister.next();
	    double test = backadd.fireBackwards();
	    backoutput += (test * dfdx);
	}
    }
    
    /*
     * Method: getOutput()
     * Returns the double output.
     */

    public double getOutput(){
	return output;
    }

    /*
     * Method: getBackOutput()
     * Returns the double backoutput.
     */
    
    public double getBackOutput() {
	return backoutput;
    }
    
    /*
     * Method: makeFirstOutput(double input)
     * Sets the output to the specified double modified by a sigmoid function, as well as setting dfdx equal to the derivative of the sigmoid function
     * Useful for firing the first layer of Neurons
     */

    public void makeFirstOutput(double input) {
	output = (1 / (1+Math.exp(-input)));
	dfdx = output - (output * output);
    }
    
    /*
     * Method: makeFirstBackOutput(double backinput)
     * First sets the backoutput to 0., then sets the backoutput equal to the specified value multiplied by the dfdx value of the Neuron
     * Useful for firing the Neuorns in the last layer of the net during backwards firing and backpropogation
     */

    public void makeFirstBackOutput(double backinput) {
	backoutput = 0.;
	backoutput = backinput * dfdx;
    }
    
    /*
     * Method: giveOutputConnections()
     * Returns the Vector outputconnections.
     */

    public Vector<Connection> giveOutputConnections() {
	return outputconnections;
    }
}
