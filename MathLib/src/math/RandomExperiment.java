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
        
        for (T event1 : event) {
            prob += binSearch(sampleSpace, event1) >= 0 ? 1 : 0; 
        }
        
        prob = prob / sampleSpace.length; 
        return prob;
    }
    
    /**
     * Description: runs a binary search on array to find key. Returns the index
     *              of the key if found or -1 if key is not found.
     * Date: 10/29/2018
     * @param array
     * @param key
     * @return ret an int type storing the index of key or -1 if not found
     */
    private int binSearch(T[] array, T key)
    {
        int ret = -1;
        int min = 0;
        int max = array.length;
        int mid = (min + max) / 2;

        while(true)
        {
            if(key == array[mid])
            {
                ret = mid;
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
        this.sampleSpace = organizeEventsHelper(this.sampleSpace);
        
        for(int i = 0; i < events.size(); i++)
        {
            events.set(i, organizeEventsHelper(events.get(i)));
        }    
    }
    
    /**
     * Description: Runs a modified selelction sort that deletes copies
     * Date: 10/29/2018
     * @param array
     * @return T the sorted array 
     */
    private T[] organizeEventsHelper(T[] array)
    {
        int finLength = array.length;
        
        for(int i = 0; i < finLength - 1; i++)
        {
            for(int j = i+1; j < finLength; j++)
            {                   
                if(array[i].toString().compareTo(array[j].toString()) > 0)
                {
                    swapElements(array, i, j);
                }
                else if(array[i].toString().compareTo(array[j].toString()) == 0)
                {

                    swapElements(array, j, finLength-1);
                    finLength--;
                    j--;
                }  
            }
        }    
        
        array = trimArray(array,finLength);
        return array;
    }
    
    /**
     * Description: Returns a new array with index 0 to length
     * Date: 10/29/2018
     * @param array
     * @param length
     * @return T the new trimmed array
     */
    public T[] trimArray(T[] array, int length)
    {
        T ret[] = (T[])new Object[length];
        
        System.arraycopy(array, 0, ret, 0, length);
        
        return ret;
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
        String ret = formatArray(this.sampleSpace, "Sample Space ");
        ret += "Sample Space Length " + sampleSpace.length + "\n";
        
        for(int i = 0; i < events.size(); i++)
        {
            ret += formatArray(events.get(i), "Event " + i);
        }
        
        return ret;
    }
    
    /**
     * Description: returns a formatted array as a string
     * Date: 10/29/2018
     * @param array
     * @param eventName
     * @return String the string representation of the array
     */
    private String formatArray(T[] array, String eventName)
    {
        String ret = ""
                ;
        if(eventName == null || eventName.equals(""))
            eventName = "Random Event";
        
        ret += eventName + " { ";
        
        for(int i = 0; i < array.length; i++)
        {
            if(i < array.length - 1)
                ret += array[i] + ", ";
            else
                ret += array[i] + " }\n";
        }
        
        return ret;
    }
}
