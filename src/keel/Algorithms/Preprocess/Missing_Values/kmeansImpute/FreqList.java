/**
 * <p>
 * @author Written by Juli�n Luengo Mart�n 31/12/2005
 * @version 0.3
 * @since JDK 1.5
 * </p>
 */
package keel.Algorithms.Preprocess.Missing_Values.kmeansImpute;
import java.util.*;

/**
 * <p>
 * This class represents a list of frequencies of Strings
 * </p>
 */
public class FreqList {
    protected Vector freqs = null;
    protected int index = 0;
    protected int totalElements = 0;
    
    /**
     * <p>
     * Default constructor. Initializes all to zero and allocates memory.
     * </p>
     */
    public FreqList(){
        freqs = new Vector();
        index = 0;
        totalElements = 0;
    }
    
    /**
     * <p>
     * Adds an item to the list, increasing it frequency by one.
     * </p>
     * @param newElem The elemento to be added
     */
    public void AddElement(String newElem){
        boolean found = false;
        int isAt = -1;
        ValueFreq elem = null;
        for(int i=0;i<freqs.size()&&!found;i++){ //search for an existing element
            elem = (ValueFreq)(freqs.elementAt(i));
            if( newElem.compareTo(elem.getValue())==0){
                found = true;
                isAt = i;
            }
        }
        if(found){ //there was already 1 element
            elem.incFreq(); //add 1 to number of times seen
            freqs.setElementAt(elem, isAt);//store back the increased item, replacing the older one
        } else{//it is the first occurrence
            elem = new ValueFreq(newElem, 1);
            freqs.addElement(elem);
        }
        totalElements++;
    }
    
    /**
     * <p>
     *	Extracts the most common element, i.e. the element with highest frequency
     * </p>
     * @return The most repeated element
     */
    public ValueFreq mostCommon(){
        int isAt = 0;
        ValueFreq elem = null;
        ValueFreq ref = null;
        
        if(freqs.size()>0){
            ref = (ValueFreq)(freqs.elementAt(isAt));
            for(int i=1;i<freqs.size();i++){
                elem = (ValueFreq)(freqs.elementAt(i));
                if(elem.moreFreq(ref)){
                    isAt = i;
                    ref = elem;
                }
            }
            return ref;
        } else
            return null;
    }
    
    /**
     * <p>
     * The number of different elements stored
     * </p>
     * @return The number of different elements
     */
    public int numElems(){
        return freqs.size();
    }
    
    /**
     * <p>
     * The element at position indicated (i.e. the not-stored element inserted in position i) 
     * </p>
     * @param i The index of the element to be retrieved
     * @return The indexed element
     */
    public ValueFreq elementAt(int i){
        return (ValueFreq)(freqs.elementAt(i));
    }
    
    /**
     * <p>
     * Reset the iterator to the beginning of the list.
     * </p>
     */
    public void reset(){
        index = 0;
    }
    
    /**
     * <p>
     * Iterates to the next element in the list
     * </p>
     * @return True if still there are more elements remaining, false if the end of the list has been reached.
     */
    public boolean iterate(){
        index += 1;
        if(index>=freqs.size())
            return false;
        return true;
    }
    
    /**
     * <p>
     * Obtains the element pointed currently by the iterator
     * </p>
     * @return The current element
     */
    public ValueFreq getCurrent(){
        if(index<freqs.size())
            return (ValueFreq)(freqs.elementAt(index));
        else{
            System.out.println("ERROR: Element Out Of Range");
            return null;
        }
    }
    
    /**
     * <p>
     * Test if the iterator is out of the bounds of the list
     * </p>
     * @return True if the iterator is beyond the limit, of false if there are still more elements
     */
    public boolean outOfBounds(){
    	return (index>=freqs.size() || index < 0);
    }
    
    /**
     * <p>
     * The total number of elements stored, i.e. the sum of all the frequencies
     * </p>
     * @return the total number of elements added to this list
     */
    public int totalElems(){
        return totalElements;
    }
}
