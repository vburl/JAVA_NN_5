package ca.danielherrmann.NeuronProject;
import java.util.Vector;
import java.util.ListIterator;

/**
 * An object of the class NeuralNet brings together multiple elements to make a functional neural net. 
 * A NeuralNet stores a Vector, net, of Layers.
 * The different Layers in the Vector net makes up the NeuralNet.
 * @author DHermz
 */
public class NeuralNet {

    Vector<Layer> net = new Vector<Layer>();

    /*
     * Constructor:NeuralNet(int inputneurons, int neuronsper, int hiddenlayers, int outputneurons)
     * Creates a NeuralNet with the specified amount of Neurons in the input, output, and hidden layers, as well as the specified amount of hidden layers. 
     * Creates all the connections between Neurons in adjacent layers, with random weights. 
     */

    NeuralNet(int inputneurons, int neuronsper, int hiddenlayers, int outputneurons) {

	net.add(new Layer(inputneurons));
	for (int num = 0; num < hiddenlayers; ++num){
	    net.add(new Layer(neuronsper));
	}
	net.add(new Layer(outputneurons));
	ListIterator<Layer> gothroughlayers = net.listIterator();
	while (gothroughlayers.hasNext()){
	    ListIterator<Neuron> gothroughneurons = gothroughlayers.next().giveNeurons().listIterator();
	    while (gothroughneurons.hasNext()){
		Neuron lower = gothroughneurons.next();
		if (gothroughlayers.hasNext()){
		    Layer upperlayer = net.elementAt(gothroughlayers.nextIndex());
		    ListIterator<Neuron> gothroughupperneurons = upperlayer.giveNeurons().listIterator();
		    while (gothroughupperneurons.hasNext()){
			Neuron upper = gothroughupperneurons.next();
			Connection current = new Connection(lower, upper);
			upper.makeInputConnection(current);
			lower.makeOutputConnection(current);
		    }
		}
	    }
	}
    }

    /*
     * Method: cheesyInput(double in)
     * Gives each Neuron in the input layer an input of the specified double.
     * Useful for trouble shooting and testing how connections and Neurons are working.
     */

    public void cheesyInput(double in){
	ListIterator<Neuron> initializing = net.elementAt(0).giveNeurons().listIterator();
	while (initializing.hasNext()){
	    initializing.next().makeFirstOutput(in);
	}
    }

    /*
     * Method: inputInt(int inpt)
     * Takes the specified integer, converts it into binary, and assigns a digit to each Neuron in the input Layer. 
     * If the digit it 1, the Neuron will have an input of 10.
     * If the digit is 0, the Neuron will have an input of -10.
     */

    public void inputInt(int inpt) {
	ListIterator<Neuron> initializing = net.elementAt(0).giveNeurons().listIterator();
	while (initializing.hasNext()){
	    int onoroff = inpt & (1 << initializing.nextIndex());
	    Neuron current = initializing.next();
	    if (onoroff == 0) {
		current.makeFirstOutput(-10);
	    }
	    else {
		current.makeFirstOutput(10);
	    }

	}
    }

    /*
     * Method: fireNet()
     * Fires the whole net forward, starting with the input Layer and ending with the output Layer.
     */

    public void fireNet(){
	ListIterator<Layer> firelayer = net.listIterator();
	// skip the first layer because its the input layer, and the output is already set
	if (firelayer.hasNext()){
	    firelayer.next();
	}
	while (firelayer.hasNext()){
	    ListIterator<Neuron> fireneuron = firelayer.next().giveNeurons().listIterator();
	    while (fireneuron.hasNext()){
		fireneuron.next().makeOutput();
	    }
	}
    }

    /*
     * Method: backFireNet()
     * Fires the net backwards, using the calcNeuronError value from the ErrorCalculator object giveErrors with the TrainingPair teacher. 
     * Fires this backwards, instead of using a sigmoid function multiplies it by the derivative dEdW stores in each Neuron.
     * Updates the Partial Derivatives in all of the Connections at the location specified by indexor.
     * Useful for backpropogation.
     */

    public void backFireNet(TrainingPair teacher, int indexor){
	ListIterator<Neuron> backinitializing = net.lastElement().giveNeurons().listIterator();
	ErrorCalculator giveErrors = new ErrorCalculator(net.lastElement(), teacher);
	while (backinitializing.hasNext()){
	    int index = backinitializing.nextIndex();
	    backinitializing.next().makeFirstBackOutput(giveErrors.calcNeuronError().elementAt(index));
	}
	ListIterator<Layer> backfirelayer = net.listIterator(net.size());
	if (backfirelayer.hasPrevious()){
	    backfirelayer.previous();
	}
	while (backfirelayer.hasPrevious()) { 
	    ListIterator<Neuron> backfireneuron = backfirelayer.previous().giveNeurons().listIterator();
	    while (backfireneuron.hasNext()) {
		backfireneuron.next().makeBackOutput();
	    }
	    this.updateConPartDer(indexor);
	}
    }

    /*
     * Method: updateConnections(double learningrate)
     * Iterates through all of the Connections in the net, and updates their weights according to their stores values and the specified learningrate 
     * (see Connection methods updateDerivative() and updateWeight(double learningrate))
     * Useful for backpropogation.
     */

    public void updateConnections(double learningrate) {
	ListIterator<Layer> gothroughlayers = net.listIterator();
	while (gothroughlayers.hasNext()){
	    ListIterator<Neuron> gothroughneurons = gothroughlayers.next().giveNeurons().listIterator();
	    while (gothroughneurons.hasNext()){
		ListIterator<Connection> updater = gothroughneurons.next().giveOutputConnections().listIterator();
		while (updater.hasNext()) {
		    Connection currentcon = updater.next();
		    currentcon.updateDerivative();
		    currentcon.updateWeight(learningrate);
		}
	    }
	}
    }

    /*
     * Method: updateConPartDer(int index)
     * Goes through the NeuralNet and updates all of the Connections' PartDeriv Vectors at the location specified by the int index
     * (see Connection method updatePartDeriv(int index))
     * Useful for backpropogation.
     */

    public void updateConPartDer(int index) {
	ListIterator<Layer> gothroughlayers = net.listIterator();
	while (gothroughlayers.hasNext()){
	    ListIterator<Neuron> gothroughneurons = gothroughlayers.next().giveNeurons().listIterator();
	    while (gothroughneurons.hasNext()){
		ListIterator<Connection> updater = gothroughneurons.next().giveOutputConnections().listIterator();
		while (updater.hasNext()) {
		    Connection currentcon = updater.next();
		    currentcon.updatePartDeriv(index);
		}
	    }
	}
    }

    /*
     * Method: initializeSizeOfPartDeriv(int amount)
     * Iterates through all of the connections in the NeuralNet and sets the size of their Vector PartDeriv to the specified amount.
     * Useful for ensuring that other methods will have stuff to use, no "element missing" error message.  
     */

    public void initializeSizeOfPartDeriv(int amount) {
	ListIterator<Layer> gothroughlayers = net.listIterator();
	while (gothroughlayers.hasNext()){
	    ListIterator<Neuron> gothroughneurons = gothroughlayers.next().giveNeurons().listIterator();
	    while (gothroughneurons.hasNext()){
		ListIterator<Connection> updater = gothroughneurons.next().giveOutputConnections().listIterator();
		while (updater.hasNext()) {
		    Connection currentcon = updater.next();
		    currentcon.initialPartDeriv(amount);
		}
	    }
	}
    }

    /*
     * Method: printLayerOutput(int ind)
     * Prints the output of the specified Layer.
     * Useful for checking NeuralNets and debugging.
     */

    public void printLayerOutput(int ind){
	net.elementAt(ind).printOutput();
    }

    /*
     * Method: printNetOutput()
     * Prints the output of the output Layer.
     * Useful for checking the end result of a NeuralNet and debugging.
     */

    public void printNetOutput() {
	net.lastElement().printOutput();
    }

    /*
     * Method: FireAndPrint(int input)
     * Fires the net using the input specified by the int input (using the method inputInt()).
     * Prints out both a message detailing the input and the output, as well as a binary representation of the output.
     * Useful for seeing the results of a NeuralNet, and useful for seeing the relationship between the binary output and the base 10 output.
     */

    public void fireAndPrint(int input) {
	this.inputInt(input);
	this.fireNet();
	String binary = "";
	ListIterator<Neuron> lister = net.lastElement().giveNeurons().listIterator(net.lastElement().giveNeurons().size());
	if (lister.hasPrevious()) {
	    while (lister.hasPrevious()) {
		if (lister.previous().getOutput() >= 0.5) {
		    binary = binary + "1";
		    System.out.print("1");
		}
		else {
		    binary = binary + "0";
		    System.out.print("0");
		}
	    }
	    int printme = Integer.parseInt(binary, 2);
	    System.out.format("\nAfter you gave me %d, I think the number you want back is %s\n", input, printme);
	}
    }

    /*
     * Method: getNet()
     * Returns the Vector net.
     */

    public Vector<Layer> getNet() {
	return net;
    }

    /*
     * Method: dumptNet()
     * 'Dumps' a bunch of information about the NeuralNet onto the console, using the method printOutput().
     * Useful for debugging and seeing how the NeuralNet is functioning.
     */

    public void dumpNet() {
	System.out.println("\nNEURAL NET DUMP:\n======================================");
	ListIterator<Layer> layers = net.listIterator();
	int i = 0;
	while (layers.hasNext())
	{
	    System.out.format("Layer %2d\n-------------------\n", i++);
	    layers.next().printOutput();
	}
    }

    public int GiveNumberofLayers() {
	ListIterator<Layer> layers = net.listIterator();
	int i = 0;
	while (layers.hasNext())
	{
	    i++;
	}
	
	return i;
    }
    
//    public int GiveNumberofNeuronsinaLayer(Layer countme) {
//	ListIterator<Neuron> neurons = net.listIterator();
//	int i = 0;
//	while (neurons.hasNext())
//	{
//	    i++;
//	}
//	
//	return i;
//    }

}
