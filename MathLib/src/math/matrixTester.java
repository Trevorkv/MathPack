/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;


/**
 *
 * @author Trevor
 */
public class matrixTester {
    
    Matrix mat = null;
    
    int rDimension;
    
    
    public static void main(String Args[])
    {
        double test[][] = {{1,0,0},{0,0,2}, {1,1,1}};
        System.out.println("Initializing Matrix");
        Matrix me = new Matrix(test);
        System.out.println(me.toString());
        System.out.println("GetREF");
        System.out.println(me.getREF());
        System.out.println("GetRREF");
        System.out.println(me.getRREF());
        System.out.println("Interchange");
        System.out.println(me.interchange(0, 1));
        System.out.println(me.toString());
        System.out.println("Replace");
        System.out.println(me.replace(1, 0, -2));
        System.out.println(me.toString());
        System.out.println("Scale");
        System.out.println(me.scaleRow(1, 2));
        System.out.println(me.toString());
        System.out.println("Transpose");
        System.out.println(me.getTranspose().toString());
        System.out.println("ADD");
        System.out.println(me.add(new Matrix(test)));
        
        System.out.println("Subtract");
        System.out.println(me.subtract(new Matrix(test)));
        System.out.println("Multily scalar");
        System.out.println(me.multiply(5));
        
        System.out.println("Multiply Matrix");
        System.out.println(me.multiply(new Matrix(test)));

    }
   
    
    
}
