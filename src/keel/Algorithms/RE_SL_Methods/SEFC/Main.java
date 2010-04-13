package keel.Algorithms.RE_SL_Methods.SEFC;

/**
 * <p>
 * @author Written by Francisco Jos� Berlanga (University of Ja�n) 01/01/2007
 * @version 1.0
 * @since JDK 1.6
 * </p>
 */
 
public class Main {
/**	
 * <p>
 * Main Class of the Program
 * It reads the configuration file (data-set files and parameters) and launch the algorithm
 * </p>
 */	
 
    private parseParameters parameters;

    /**
     * <p>
     * Default Constructor
     * </p>
     */
    public Main() {
    }

    /**
     * <p>
     * It launches the algorithm
     * </p>
     * @param confFile String it is the filename of the configuration file.
     */
    private void execute(String confFile) {
        parameters = new parseParameters();
        parameters.parseConfigurationFile(confFile);
        Algorithm method = new Algorithm(parameters);
        method.execute();
    }

    /**
     * <p>
     * Main Program
     * </p>
     * @param args It contains the name of the configuration file<br/>
     * Format:<br/>
     * <em>algorith = &lt;algorithm name></em><br/>
     * <em>inputData = "&lt;training file&gt;" "&lt;validation file&gt;" "&lt;test file&gt;"</em> ...<br/>
     * <em>outputData = "&lt;training file&gt;" "&lt;test file&gt;"</em> ...<br/>
     * <br/>
     * <em>seed = value</em> (if used)<br/>
     * <em>&lt;Parameter1&gt; = &lt;value1&gt;</em><br/>
     * <em>&lt;Parameter2&gt; = &lt;value2&gt;</em> ... <br/>
     */
    public static void main(String args[]) {
        Main program = new Main();
        System.out.println("Executing Algorithm.");
        program.execute(args[0]);
    }
}
