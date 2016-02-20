package ca.danielherrmann.NeuronProject;
import java.util.Vector;
import java.util.ListIterator;

/**
 * An object of the class Layer acts as a layer in a neural net, keeping track of all the Neurons in the layer in a Vector. 
 * The Vector layer stores all of the Neurons in the layer.
 * @author DHermz
 */
public class Layer {

    Vector<Neuron> layer = new Vector<Neuron>();

    /*
     * Constructor: Layer(int numberofneurons)
     * Creates an object of the class Layer with the specified amount of Neurons in the Vector layer
     */

    Layer(int numberofneurons){
	for (int x = 0; x < numberofneurons; ++x) {
	    layer.add(new Neuron());
	}
    }

    /*
     * Method: giveNeurons()
     * Returns the Vector layer.
     */

    public Vector<Neuron> giveNeurons(){
	return layer;
    }
    
    /*
     * Method: printOutput()
     * Prints out a series of lines describing a Neuron and its output. Goes through all of the Neurons in the Vector layer.
     * Useful for checking the output of individual Neurons in specific Layers. 
     */
    
    public void printOutput(){
	int i = 0;
	ListIterator<Neuron> printing = layer.listIterator();
	while (printing.hasNext()){
	    System.out.format(" Neuron %2d: Output %.3f\n", i++, printing.next().getOutput());
	}
    }
}
