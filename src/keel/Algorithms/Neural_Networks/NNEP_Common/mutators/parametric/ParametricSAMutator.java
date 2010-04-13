package keel.Algorithms.Neural_Networks.NNEP_Common.mutators.parametric;



import keel.Algorithms.Neural_Networks.NNEP_Common.NeuralNetIndividual;

/**  
 * <p>
 * @author Written by Pedro Antonio Gutierrez Penia (University of Cordoba) 16/7/2007
 * @author Written by Aaron Ruiz Mora (University of Cordoba) 16/7/2007
 * @param <I> Type of individuals to mutate
 * @version 0.1
 * @since JDK1.5
 * </p>
 */

public class ParametricSAMutator<I extends NeuralNetIndividual> extends ParametricMutator<I>
{
	
	/**
	 * <p>
     * Parametric mutator for neural nets, mutate the weights of the neural nets
     * mutated. This implementation uses a Simulated Annealing method to update
     * alpha values.
     * 
     * IMPORTANT NOTE: Parametric mutator works directly with  he individuals instead
     *	                 of returning a mutated copy of them. This is for performance 
     *                 reasons. If you want to use another mutator you have to consider 
     *                 that individuals will be changed when you use parametric mutation
     * </p>
	 */
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////
	
	/** Generated by Eclipse */

	private static final long serialVersionUID = -4723976893815126429L;
		
	/////////////////////////////////////////////////////////////////
	// --------------------- Private variables for the alpha control
	/////////////////////////////////////////////////////////////////
	
	/** Number of generations the fitness has worsen */
	
	private int contR = 0;
	
	/** Number of generations the fitness has improved */
	
	private int contS = 0;
	
	/** Best fitness of the previous generation */
	
	protected double bestPreviousFitness = 0;
	
	/////////////////////////////////////////////////////////////////
	// -------------------------------------------------- Constructor
	/////////////////////////////////////////////////////////////////
	
    /**
     * <p>
     * Empty Constructor
     * </p>
     */
    
    public ParametricSAMutator() {
        super();
    }

	/////////////////////////////////////////////////////////////////
	// ------------------------ Overwriting ParametricMutator methods
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Init the values of alpha parameters used in the mutations
	 * </p>
	 *
	 */
    
    public void alphaInit() {
        alphaInput = initialAlphaInput;
        alphaOutput = initialAlphaOutput;
        contR = 0;
        contS = 0;
        bestPreviousFitness = 0;
    }
    
	/**
	 * <p>
	 * Updates the values of alpha parameters used in the mutations
	 * </p>
	 * @param bestFitness Best fitness of this generation
	 */
    
    protected void alphaUpdate(double bestFitness) {

        if(bestPreviousFitness+fitDif < bestFitness) {
            contS++;
            contR=0;
        }
        else {
            contR++;
            contS=0;
        }
        
    	if (contS>=10) {
    		contS=0;
    		alphaInput*=1.1;
    		alphaOutput*=1.1;
    	}
    	
    	if (contR>=10) {
    		contR=0;
    		alphaInput*=0.9;
    		alphaOutput*=0.9;
    	}

    	//System.out.println(contS + " "+ contR + " " + bestFitness +" " + (bestPreviousFitness + fitDif));
    	bestPreviousFitness = bestFitness;
    }
    
	/**
	 * <p>
	 * Updates alpha control parameters at the end of each
	 * neuron mutation, if neccesary
	 * </p>
	 * @param newFitness Result fitness of the mutation
	 * @param fitness Previous fitness befor making the mutation
	 */
    
    protected void alphaControlParametersUpdate(double newFitness, double fitness){
    	// => Nothing to update
    }
}
