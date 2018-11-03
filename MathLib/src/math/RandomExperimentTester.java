/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.ArrayList;

/**
 * Description: A tester class for the RandomExperiment Class
 * @author Trevor
 * Date: 10/14/2018
 */
public class RandomExperimentTester {
    
    public static void main(String args[])
    {
        System.out.println("Running Random Experiment Tester");
        ArrayList<Integer[]> test = new ArrayList();
        Integer [] sample = {1,2,5,4,1,0};
        Integer[] e1 = {1, 1, 1,2};
        Integer[] e2 = {4,1,5};
        test.add(e1);
        test.add(e2);
        
        RandomExperiment<Integer> rand = new RandomExperiment<>(sample, test);
        
        System.out.print(rand.toString());
        
        System.out.println("Probability " + 
                rand.getProb(0));
    }
    
    
}
