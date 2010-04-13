package keel.GraphInterKeel.experiments;


/**
 * <p>
 * @author Written by Juan Carlos Fernández and Pedro Antonio Gutiérrez (University of Córdoba) 23/08/2009
 * @version 1.0
 * @since JDK1.5
 * </p>
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jdom.Element;


public class EducationalISReport extends EducationalReport
{
    /**
     * <p>
     * This class creates a report in the experiment directory.
     * A file "report.txt" is creates in the same result directory
     * The report is for Instance Selection
     * </p>
     */


	protected List<String> pathResultFilesTxt = null;
	int[] arrayTotalInstances = new int[sentences.size()*2];
	String[] stringDatasets = new String[sentences.size()*2];


	/**
     * <p>
	 * Constructor
	 * </p>
	 * @param	sentences Total of sentences for RunKeel.xml
	 * @param	experimentType Type of experiment, clasifficacion or regression
	 */
	public EducationalISReport(ArrayList<Element> sentences, int experimentType)
	{				
		super(sentences, experimentType);
		for(int i=0; i<sentences.size()*2; i++)
			stringDatasets[i] = new String();
		this.calcularTotalInstances();
	}
	

	/**
     * <p>
	 * This method has to invoque for to create the report.
	 * Verify the type of problem, type partition and paths for
	 * to create the report. Read in iterative way the files of
	 * results
	 * </p>
	 */
	public void running()
	{			
		
		if(experimentType == CLASSIFICATION)
		{
			int contPart = 0;
			for (int i=0; i<pathOutputFiles.length; i=i+2)
			{	
				contPart++;
				try 
				{	
					if(i%2==0)
					{
						bw.newLine();
						bw.write("Partition " + (contPart));
						bw.newLine();
						bw.write("================");
						bw.newLine();
						bw.newLine();
					}
				}
				catch (IOException e) 
				{				
					e.printStackTrace();
				}	
				
				for(int p=0; p<2; p++)
				{
					int contInstances = 0;
					int aux = i + p; 
					try 
					{	
						fr = new FileReader(pathOutputFiles[aux]);
						br = new BufferedReader(fr);										
					}
					catch (IOException e) 
					{				
						e.printStackTrace();
					}	
					
					// Seek pointer of filereader
					String cadena = "";
					while(cadena.equals("@data")==false)
					{
						try 
						{
							cadena = br.readLine();
						} catch (IOException e1) {							
							e1.printStackTrace();
						}
					}
					
					while (cadena!=null)
					{							
						try
						{
							cadena = br.readLine();
							if(cadena!=null)
							{
								contInstances++;																
							}
							
						}
						catch (IOException e) 
						{				
							e.printStackTrace();
						}	
					}
					
					try 
					{
						br.close();
					}
					catch (IOException e) 
					{			
						e.printStackTrace();
					}
					
					int porcentaje = (contInstances*100)/arrayTotalInstances[i];
					
					//Training
					if(p==0) 
					{
						try
						{							
							bw.newLine();
							bw.write("Training - Porcent of instances respect to original file: " + porcentaje + "%");
							bw.newLine();
							bw.write("---------------------------------------------------------");
							bw.newLine();
							bw.newLine();
							
							fr = new FileReader(pathOutputFiles[aux]);
							br = new BufferedReader(fr);
							
							String cad2 ="";
							try
							{
								cad2 = br.readLine();
							}
							catch (IOException e) 
							{				
								e.printStackTrace();
							}	
							while(cad2!=null)
							{
								try
								{									
									bw.write(cad2);
									bw.newLine();
									cad2 = br.readLine();
								}	
								catch (IOException e) 
								{				
									e.printStackTrace();
								}								
							}
						}
						catch (IOException e) 
						{				
							e.printStackTrace();
						}	
					}
					else
					{
						try
						{							
							bw.newLine();
							bw.write("Testing - Porcent of instances respect to original file: " + porcentaje + "%");
							bw.newLine();
							bw.write("--------------------------------------------------------");
							bw.newLine();
							bw.newLine();	
							
							fr = new FileReader(pathOutputFiles[aux]);
							br = new BufferedReader(fr);
							
							String cad2 ="";
							try
							{
								cad2 = br.readLine();
							}
							catch (IOException e) 
							{				
								e.printStackTrace();
							}	
							while(cad2!=null)
							{
								try
								{									
									bw.write(cad2);
									bw.newLine();
									cad2 = br.readLine();
								}	
								catch (IOException e) 
								{				
									e.printStackTrace();
								}								
							}
						}
						catch (IOException e) 
						{				
							e.printStackTrace();
						}	
					}
				}//for	
			}//for
			
			try 
			{
				br.close();
				bw.close();
			}
			catch (IOException e) 
			{			
				e.printStackTrace();
			}
		}//if	
		
		else if(experimentType == REGRESSION)
		{
			
		}


	}



	/**
     * <p>
	 * Previous Method to calculate total instances in each partition (training or test)
	 * The config.txt file is inspected
	 * </p>
	 */	
	private void calcularTotalInstances()
	{					
		FileReader fr = null;
		BufferedReader br = null;		
		
		this.calculateStringDatasetFiles();
				
		for(int i=0; i<stringDatasets.length; i++)
		{						
			//File config.txt. Read training file and test file
			try 
			{
				fr = new FileReader(stringDatasets[i]);
				br = new BufferedReader(fr);
			} 
			catch (FileNotFoundException e) 
			{		
				e.printStackTrace();
			}
			
			String cad = "";
			// Seek pointer of filereader
			while(cad.equals("@data")==false)
			{
				try 
				{
					cad = br.readLine();
				} catch (IOException e1) {							
					e1.printStackTrace();
				}
			}
			try 
			{
				cad = br.readLine();
			} 
			catch (IOException e) 
			{			
				e.printStackTrace();
			}
			
			int cont = 0;
			while (cad!=null)
			{
				cont++;
				try 
				{
					cad = br.readLine();
				} 
				catch (IOException e) 
				{			
					e.printStackTrace();
				}			
			}
			
			arrayTotalInstances[i] = cont;
			
			try 
			{
				br.close();
			}
			catch (IOException e) 
			{		
				e.printStackTrace();
			}
		}
	}
	
	
	/**
     * <p>
	 * Method to calculate original dataset path.
     * </p>
	 */
	private void calculateStringDatasetFiles()
	{	
		int counterPos = -1;
		for(int i=0; i<sentences.size(); i++)
		{
			
			int posCadenaScriptFile = 0;
			String stringPath = "";
			//Fichero de configuracion "pos" configxxx.txt
			List line = ((Element)sentences.get(i)).getChildren();
			posCadenaScriptFile = line.size() -3;
			stringPath = ((Element)line.get(posCadenaScriptFile)).getText();
			
			//Path File Config.txt
			FileReader filer = null;
			BufferedReader filebr = null;
			try 
			{
				filer = new FileReader(stringPath);
				filebr = new BufferedReader(filer);
			} 
			catch (FileNotFoundException e) 
			{		
				e.printStackTrace();
			}
					
			String cad = "";
			StringTokenizer st = null;
			
			while (cad!=null)
			{
				if (cad.startsWith("inputData") == true)
				{
					st = new StringTokenizer(cad,"\"");
					st.nextToken();
					counterPos++;
					stringDatasets[counterPos] = st.nextToken();
					st.nextToken();
					counterPos++;
					stringDatasets[counterPos] = st.nextToken();
					break;
				}	
				else
				{
					try 
					{
						cad = filebr.readLine();
					} 
					catch (IOException e) 
					{			
						e.printStackTrace();
					}			
				}
		    }	
			
			try 
			{
				filebr.close();
			}
			catch (IOException e) 
			{		
				e.printStackTrace();
			}
		}
	}	
}