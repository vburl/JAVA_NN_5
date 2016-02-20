package ca.danielherrmann.NeuronProject;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * A main method, allows a user to build his own neural net with his specifications, train it with his training set and other parameters, and then test to see if it works.
 * Not an amazingly useful or powerful way to use the neural net, but valuable as an easy interface/educational tool about neural nets and how they work.
 * @author DHermz
 */
public class ConsoleUI {

    public static void main(String[] args) throws IOException, InputMismatchException {

	int inputn = 0;
	int outputn = 0;
	int layers = -1;
	int neuronsper = -1;
	int currentin;
	int currentout;
	double learningrate = -1;
	double errormargin = -1;
	boolean pairs = true;
	boolean ready = false;
	boolean done = false;

	System.out.println("Hello! I am the neural net constructor and trainer. You can tell me what properties you want your neural net to "
		+ "have, and what numerical  training sets to train it with.\n\nPlease answer the following questions with just a number:");
	while (inputn == 0) {
	    System.out.println("\nHow many input neurons do you want your neural net to have?");

	    try {
		Scanner scan = new Scanner (System.in);     
		inputn = scan.nextInt();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }
	}

	while (outputn == 0) {
	    System.out.println("\nHow many output neurons do you want your neural net to have?");

	    try {
		Scanner scan2 = new Scanner (System.in);     
		outputn = scan2.nextInt();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }

	}

	while (layers == -1) {
	    System.out.println("\nHow many hidden layers do you want your neural net to have?");

	    try {
		Scanner scan3 = new Scanner (System.in);     
		layers = scan3.nextInt();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }

	}

	while (neuronsper == -1) {
	    System.out.println("\nHow many neurons do you want in each hidden layer of your neural net?");

	    try {
		Scanner scan3 = new Scanner (System.in);     
		neuronsper = scan3.nextInt();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }

	}

	NeuralNet thenet = new NeuralNet(inputn, neuronsper, layers, outputn);

	TrainingSet theset = new TrainingSet();

	System.out.println("Thank you! Your neural net will be created according to specification."
		+ "\nNow please enter the training  pairs that you wish your neural net to learn from. "
		+ "\nEnter each number separated by a space, and after each pair press 'Enter'. Then, when done, type 'Done' on  new line.");

	while (pairs == true) {

	    try {
		Scanner scan4 = new Scanner (System.in);
		currentin =  scan4.nextInt();
		currentout = scan4.nextInt();
		theset.addPair(currentin, currentout);
		System.out.format("You have added the pair %d , %d\n", currentin, currentout);
	    } catch (InputMismatchException ex) {
		pairs = false;
	    }
	}

	while (learningrate == -1) {
	    System.out.println("\nWhat learningrate do you want your neural net to train with? Somewhere between 0.1 and 0.5 is usually good.");

	    try {
		Scanner scan5 = new Scanner (System.in);     
		learningrate = scan5.nextDouble();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }

	}

	while (errormargin == -1) {
	    System.out.println("\nWhat error margin do you want your neural net to train with? I would suggest 0.1");

	    try {
		Scanner scan6 = new Scanner (System.in);     
		errormargin = scan6.nextDouble();
	    } catch (InputMismatchException ex) {
		System.out.println("That was not a valid number.");

	    }

	}

	System.out.println("We are ready to train your neural net! Depending on your training set and neural net characteristics this could take anything from a few seconds to a few days. "
		+ "\nAlso, if you find that your neural net is memorizing the training data as opposed to learning from it, your neural net probably has too many neurons. "
		+ "\n\nWhen you are ready, type 'Train'.");

	while (ready == false) {
	    BufferedReader sentencereader = new BufferedReader(new InputStreamReader(System.in));
	    String inpt = sentencereader.readLine();
	    if (inpt.equalsIgnoreCase("Train")) {
		Trainer thetrainer = new Trainer(thenet, theset, learningrate, errormargin);
		thetrainer.trainAll();
		ready = true;
	    }
	    else if (inpt != "Train") System.out.println("\nAllright, you are not ready. I will wait.\n");
	}

	System.out.println("Please enter an input number to get the neural net's output number. When you are done with the program, please type 'Done'.");

	while (done == false) {
	    System.out.println("Please tell me the number you would like to input into the neural net.");
	    try {
		Scanner scan8 = new Scanner (System.in);
		thenet.fireAndPrint(scan8.nextInt());
	    } catch (InputMismatchException ex) {
		done = false;
	    }
	}
    }
}
