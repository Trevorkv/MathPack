
package math;

import java.util.*;
/**
 * Description: Computer Model of a random experiment 
 * Date: 10/12/2018
 * @author Trevor
 * Revision:    10/17/2018 - Variable Rename - sampleSpace -> sampleSize
 *                         - Variable Added - T[] sampleSpace
 */
public class RandomExperiment<T> {
    private T[] sampleSpace;
    private ArrayList<T[]> events;
    
    /**
     * Default Constructor
     * Date: 10/14/2018
     */
    public RandomExperiment()
    {
        events = new ArrayList<>();
    }

    /**
     * Override Constructor
     * @param events An ArrayList use to hold a list of events and their members
     * Date: 10/14/2018
     */
    public RandomExperiment(T[] sampleSpace, ArrayList<T[]> events)
    {
        this.sampleSpace = sampleSpace;
        this.events = new ArrayList<>(events);
        organizeEvents();
    }
    
    /**
     * Description: Return the sampleSize
     * @return sampleSize is type int
     */
    public int getSampleSize() {
        return this.sampleSpace.length;
    }

    /**
     * Description: Return the events
     * @return events is an ArrayList of generic arrays
     */
    public ArrayList<T[]> getEvents() {
        return events;
    }
    
    /**
     * Description: returns the probability of the given event occurring
     * @param index an int index of a particular event
     * @return prob a Double type that shows a probability of index occurring
     */
    public double getProb(int index)
    {
        double prob = 0;
        T[] event = events.get(index);
        for(int i = 0; i < event.length; i++)
        {
            prob += binSearch(sampleSpace, event[i]) != null ? 1 : 0;
            System.out.println("prob " + prob); 
        }
        
        prob = prob / sampleSpace.length;
        
        return prob;
    }
    //more testing required
    private T binSearch(T[] array, T key)
    {
        System.out.println("Key " + key);
        T ret = null;
        int min = 0;
        int max = array.length;
        int mid = (min + max) / 2;

        while(true)
        {
            System.out.println("min " + min + " max " + max  + " mid " + mid);
            if(key == array[mid])
            {
                ret = array[mid];
                break;
            }
            else if(max == mid || min == mid)
                break;
            else if(array[mid].toString().compareTo(key.toString()) > 0)
            {
                max = mid;
                mid = (min + max) / 2;
            }
            else if(array[mid].toString().compareTo(key.toString()) < 0)
            {
                min = mid;
                mid = (min + max) / 2;
            }
        }
        
        return ret;
    }
    
    /**
     * Description: Orders each event's elements from least to greatest
     * Date: 10/17/2018
     */
    private void organizeEvents()
    {
        organizeEventsHelper(this.sampleSpace);
        
        for (T[] event : events) {
            organizeEventsHelper(event);
        }
    }
    
    /**
     * Description: Orders the Array's elements from least to greatest with 
     *              selection sort
     * @param array a Generic Type
     * Date: 10/17/2018
     */
    private void organizeEventsHelper(T[] array)
    {
        for(int i = 0; i < array.length - 1; i++)
        {
            for(int j = i+1; j < array.length; j++)
            {   
                if(array[i].toString().compareTo(array[j].toString()) == 0)
                {
                    
                }
                else if(array[i].toString().compareTo(array[j].toString()) >
                        0)
                {
                    swapElements(array, i, j);
                    break;
                }
            }
        }
    }
    
    /**
     * Description: Swaps the value from two indices from an array
     * @param array an array of outcomes.
     * @param i an int index
     * @param j an int index
     * Date: 10/17/2018
     */
    private void swapElements(T[] array, int i, int j)
    {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * Description: Swaps two elements in the events ArryList
     * @param i
     * @param j 
     * Date: 10/17/2018
     */
    private void swapEvents(int i, int j)
    {
        T[] temp = events.get(i);
        events.set(i, events.get(j));
        events.set(j, temp);        
    }
    
    /**
     * Description: Returns a String representation of the object.
     * @return ret is an String representation of the Object
     * Date: 10/14/2018
     * Revisions:   10/17/2018 - Format Changed - Added curly braces and commas
     */
    @Override
    public String toString()
    {
        String ret = "Sample Space " + this.sampleSpace.length + "\n";
        
        for(int i = 0; i < events.size(); i++)
        {
            ret += "Event " + i + "\n{";
            for (T element : events.get(i)) {
                ret += element + ",";
            }
            
            ret += "}\n";
        }
        
        return ret;
    }
    
}
