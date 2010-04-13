/**
* <p>
* @author Written by Alberto Fernandez (University of Granada)01/01/2008
* @version 1.0
* @since JDK1.5
* </p>
*/
package keel.Algorithms.Statistical_Tests.Regression.Friedman;

import keel.Algorithms.Statistical_Tests.Shared.*;
import keel.Algorithms.Shared.Parsing.*;
import org.core.*;

public class Main {
	/**
	* <p>
	* This class has only a main method that calls Friedman test for regression problems, defined in StatTest
	* </p>
	*/
	
	/**
	* <p>
	* This method reads a configuration file and calls statisticalTest with appropriate values to run the
	* Friedman test for regression problems, defined in StatTest class
	* @param args A string that contains the command line arguments
	* </p>
	*/
    public static void main(String args[]) {

        boolean tty = false;
        ProcessConfig pc = new ProcessConfig();
        System.out.println("Reading configuration file: " + args[0]);
        if (pc.fileProcess(args[0]) < 0) {
            return;
        }
        int algorithm = pc.parAlgorithmType;

        ParseFileList pl = new ParseFileList();

        pl.statisticalTest(StatTest.FriedmanR, tty, pc);

    }
}
