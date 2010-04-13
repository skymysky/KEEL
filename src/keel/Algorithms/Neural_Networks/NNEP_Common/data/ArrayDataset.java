package keel.Algorithms.Neural_Networks.NNEP_Common.data;

import java.util.ArrayList;

import org.apache.commons.configuration.Configuration;

/**
 * <p>
 * @author Written by Amelia Zafra, Sebastian Ventura (University of Cordoba) 17/07/2007
 * @version 0.1
 * @since JDK1.5
 * </p>
 */

public class ArrayDataset extends AbstractDataset 
{
	/**
	 * <p>
	 * ArrayDataset implementation (dataset populated with an array) TODO
	 * </p>
	 */
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------- Serialization constant
	/////////////////////////////////////////////////////////////////

	/** Generated by Eclipse */
	
	private static final long serialVersionUID = 1L;
	
	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------------- Properties
	/////////////////////////////////////////////////////////////////

	/** Instances list */
	
	protected ArrayList<Instance> instances = new ArrayList<Instance> ();
	
	
	/////////////////////////////////////////////////////////////////
	// ------------------------------------------------- Constructors
	/////////////////////////////////////////////////////////////////

	/**
	 * <p>
     * Empty constructor
	 * </p>
     */
	public ArrayDataset() 
	{
		super();
	}

	
	/////////////////////////////////////////////////////////////////
	// --------------------------------------------- Override methods
	/////////////////////////////////////////////////////////////////

	/**  
	 * <p>
	 * Open dataset 	 
	 * </p>
	 * @throws DatasetException If dataset can't be opened
	 */
	@Override
	public void open() throws DatasetException 
	{
		// TODO Auto-generated method stub		
	}
	
	/**  
	 * <p>
	 * Close dataset
	 * </p>
	 * @throws DatasetException If dataset can't be closed
	 */
	@Override
	public void close() throws DatasetException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * <p>
	 * Move cursor to index position
	 * </p>
	 * @param index New cursor position
	 * @return true|false 
	 * @throws DatasetException if a source access error occurs
	 */
	@Override
	public boolean move(int index) throws DatasetException 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * <p>
	 * Return the next instance
	 * </p>
	 * @return The next instance
	 * @throws DatasetException if a source access error occurs
	 */
	@Override
	public boolean next() throws DatasetException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** 
	 * <p>
	 * Reset dataset
	 * </p>
	 * @throws DatasetException if a source access error occurs
	 */	
	@Override
	public void reset() throws DatasetException 
	{
		cursorPosition = -1;
	}
	
	/**
	 * <p>
	 * Returns cursor instance
	 * </p>
	 * @return Actual instance (if exists)
	 * @throws DatasetException if a source access error occurs
	 */
	@Override
	public IInstance read() throws DatasetException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/////////////////////////////////////////////////////////////////
	// ---------------------------- Implementing IConfigure interface
	/////////////////////////////////////////////////////////////////
	
	/**
	 * <p>
	 * Configuration method
	 * Configuration parameters for ArrayDataset are:
	 * </p> 
	 * @param settings Configuration object to read the parameters 
	 */
	public void configure(Configuration settings) {
		// TODO Auto-generated method stub
		
	}
}
