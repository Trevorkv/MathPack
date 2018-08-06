/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.ArrayList;
import java.util.Random;

/**
 * Description: A model of a mathematical matrix and its functionalities
 * @author Trevor
 */
public class Matrix{
    
    private int rowSize;
    private int colSize;
    private double determinant;
    
    private double[][] matrix;

    
//    public Matrix(int row, int col)
//    {
//        rowSize = row;
//        colSize = col;
//        
//        
//        matrix = new double[row][col];
//    }
    
    public Matrix(int row, int col, double[][] matrix)
    {
        rowSize = row;
        colSize = col;
        determinant = initDeterminant();
        
        this.matrix = matrix;
    }
    
    
    
    public double initDeterminant()
    {
        //use getSubMatrix
        Matrix temp = new Matrix(rowSize, colSize, matrix);
        determinant = 1;
        
        if(rowSize == colSize)
        {
            determinant = Double.NaN;
            temp = getRREF();
            determinant = multiplyDiag() * determinant;
        }
        
        return determinant;
    }
    
    
    //Intended for square matrices only
    public double multiplyDiag()
    {
        double product = 1;
        for(int i = 0; i < colSize; i++)
        {
            product *= matrix[i][i];
        }
        
        return product;
    }
    
    
    //search up a way to return randomized generics
    //return a fixed sized matrix with random elements
    /**
     * Description: Returns a matrix with dimension set by row and column and 
     *              random integer elements
     * @param row
     * @param col
     * @return the randomly generated Integer matrix with fixed rows and columns
     */
//    public static Matrix getIntMatrix(int row, int col)
//    {
//        Matrix matrix = new Matrix(row,col);
//        
//        for(int i = 0; i < row*col; i++)
//        {
//            matrix.matrix.set(i, new Random().nextInt());
//        }
//        
//        return matrix;
//    }
//    
//    /**
//     * Description: Returns a matrix with dimension set by row and column and 
//     *              random float elements
//     * @param row
//     * @param col
//     * @return the randomly generated float matrix with fixed rows and columns
//     */
//    public static Matrix getFloatMatrix(int row, int col)
//    {
//        Matrix matrix = new Matrix(row,col);
//        
//        for(int i = 0; i < row*col; i++)
//        {
//            matrix.matrix.set(i, new Random().nextFloat());
//        }
//        
//        return matrix;
//    }
    
    
    public int getColSize()
    {
        return colSize;
    }
    
    public int getRowSize()
    {
        return rowSize;
    }
    
    public double[][] getMatrix()
    {
        return matrix;
    }
    
    
    /**
     * Description: Returns a matrix with dimension set by row and column and 
     *              random double elements
     * @param row
     * @param col
     * @return the randomly generated double matrix with fixed rows and columns
     */
    public static Matrix getRandMatrix(int row, int col)
    {
        Matrix matrix = new Matrix(row,col, new double[row][col]);
        
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                matrix.matrix[i][j] = new Random().nextDouble();
            }
        }
        
        return matrix;
    }
    
    
    /**
     * Description: returns the specified element of the matrix according to its 
     *              row and column
     * @param row
     * @param col
     * @return the specified element according to the row and column coordinates
     */
    public double getElement(int row, int col)
    {
       double ret = Double.MAX_VALUE;
       if(row * col >= 0)
           ret = matrix[row][col];
       return ret;      
    }
    
    
    /**
     * Description: assigns val to the specified row by column element and 
     *              returns true if successful.
     * @param row
     * @param col
     * @param val
     * @return a boolean to determine the success of assigning a new value to 
     *          the matrix element.
     */
    public boolean setElement(int row, int col, double val)
    {
        boolean bool = true;
        
        if(row >= 0 && col >= 0)
            matrix[row][col] = val;
        else
            bool = false;
        
        return bool;
    }

    
    public double[] getRow(int row)
    {
        double[] ret = null;
        
        if(row < rowSize)
            ret = matrix[row];
        
        return ret;
    }
    
    
    public double[] getCol(int col)
    {
        double[] ret = null;
        
        if(col < rowSize)
        {
            ret = new double[rowSize];
            for(int i = 0; i < rowSize; i++)
            {
                ret[i] = matrix[i][col];
            }
        }
        
        return ret;
    }
    
    
    public Matrix getSubMatrix(int rowStart, int rowEnd, int colStart, 
            int colEnd)
    {
        int newRowDim = rowEnd - rowStart;
        int newColDim = colEnd - colStart;
        
        double[][] mat = new double[newRowDim][newColDim];
        
        for(int i = 0; i < newRowDim; i++)
        {
            mat[i] = this.getRow(i);
        }
        
        Matrix ret = new Matrix(rowEnd - rowStart,colEnd - colStart, mat);
        
        return ret;
        
    }
    
    /**
     * Description: returns the Reduced Echelon Form of the current
     *          instance's matrix
     * @return a matrix in Reduced Echelon Form
     */
    public Matrix getRREF()
    {
        Matrix ref = null;
        
        if(this.matrix == null)
            throw new NullPointerException();
        else
        {
            ref = new Matrix(rowSize,colSize, matrix);
            int rowIndex = 0;
            int colIndex = 0;
            
            while(rowIndex < rowSize && colIndex < colSize)
            {
                for(int i = rowIndex; i < rowSize; i++)
                {
                    if(ref.matrix[i][colIndex] != 0)
                    {
                        ref.scale(i, 1.0 / ref.matrix[i][colIndex]);
                        ref.replaceRows(i, -ref.matrix[i][colIndex]);
                        ref.interchange(i, rowIndex);
                        rowIndex++;
                        colIndex++;
                        break;
                    }
                }
            }
        }
        
        return ref;
    }
    
    
//    public Matrix getREF()
//    {
//        Matrix ref = null;
//        
//        if(this.matrix == null)
//            throw new NullPointerException();
//        else
//        {
//            ref = new Matrix(rowSize,colSize, matrix);
//            int rowIndex = 0;
//            int colIndex = 0;
//            
//            while(rowIndex < rowSize && colIndex < colSize)
//            {
//                for(int i = rowIndex; i < rowSize; i++)
//                {
//                    if(ref.matrix[i][colIndex] != 0)
//                    {
//                        ref.scale(i, 1.0 / ref.matrix[i][colIndex]);
//                        ref.replaceRows(i, -ref.matrix[i][colIndex]);
//                        ref.interchange(i, rowIndex);
//                        rowIndex++;
//                        colIndex++;
//                        break;
//                    }
//                }
//            }
//        }
//        
//        return ref;
//    }
    
    
    
    /**
     * Description: replaces multiple rows excluding the source with the sum of 
     *              themselves and the multiple of a the row y a scale
     * @param source
     * @param scale 
     */
    public void replaceRows(int source, double scale)
    {
        for(int i = 0; i < rowSize; i++)
        {
            if(i != source)
                this.replace(source, i, scale);
        }
    }

    
    /**
     * Description: swaps row1 and row2 in the matrix
     * @param row1
     * @param row2 
     */
    public void interchange(int row1, int row2)
    {
        //temporary storage
        double[]first = new double[colSize];
        for(int i = 0; i < colSize; i++)
        {
            first[i] = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = first[i];
        }
        
        this.determinant *= -1;
    
    }
    
    /**
     * Description: replaces the destination row with the sum of itself and the
     *              multiple of the source row by a scale 
     *              of the source
     * @param source
     * @param destination
     * @param scale 
     */
    public void replace(int source, int destination, double scale)
    {
       for(int i = 0; i < colSize; i ++)
        {
            matrix[destination][i] = scale * matrix[source][i];
        } 
       
    }
    
    /**
     * Description: scales the row by a scalar
     * @param row
     * @param scalar 
     */
    public void scale(int row, double scalar)
    {
        for(int i = 0; i < colSize; i ++)
        {
            matrix[row][i] = scalar * matrix[row][i];
        }
        
        determinant /= scalar;
    }

    
    /**
     * Description: returns the number of pivots found in the current instance's
     *              matrix
     * @return the number of pivots found in the current matrix
     */
    public int getPivotCount()
    {
        int pivotCount = -1;
        
        return pivotCount;
    }
    
    
    /**
     * Description: returns true if the current matrix is invertible, false if 
     *              otherwise
     * @return returns a boolean to signify if the matrix is invertible
     */
    public boolean isInvertible()
    {
        boolean invertible = true;
        
        if(getDeterminant() == 0)
            invertible = false;
        
        return invertible;
    }
    
    
    /**
     * Description: returns the Eigen value of the current matrix
     * @return the Eigen value of the matrix.
     */
    public int getEigenVal(double [] vector)
    {
        int eigen = Integer.MAX_VALUE;
        
        return eigen;
    }
    
    
    /**
     * Description: returns the Eigen vector of the the current matrix.
     * @return the Eigen vector as an arraylist.
     */
    public ArrayList getEigenVec(double val)
    {
        ArrayList eigen = null;
        
        return eigen;
    }
    
    
    /**
     * Description: returns the determinant of the matrix.
     * @return the integer determinant of the matrix
     */
    public double getDeterminant()
    {
        return determinant;
    }
       
    
    
    /**
     * Description: returns the Complement of the matrix
     * @return the Complement of the matrix
     */
    public Matrix getComplement()
    {
        Matrix comp = null;
        
        return comp;
    }
    
    
    /**
     * Description: returns matrix that is the basis of the current matrix
     * @return the basis of the current matrix.
     */
    public Matrix getBasis()
    {
        Matrix basis = null;
        //call getRef and stuff
        return basis;
    }
    
    /**
     * Description: returns the transpose of the current matrix
     * @return the transpose of the current matrix
     */
    public Matrix getTranspose()
    {
        Matrix trans = new Matrix(this.colSize, this.rowSize, 
                new double[colSize][rowSize]);
  
        for(int i = 0; i < rowSize; i ++)
        {
            for(int j = 0; j < colSize; j++)
            {
                trans.setElement(j, i, this.matrix[j][i]);
            }
        }
        
        return trans;
    }
    
    
    public static Matrix multiply(Matrix var)
    {
        
    }
    
    public static Matrix add(Matrix var)
    {
        
    }
    
    public static Matrix subtract(Matrix var)
    {
        
    }
    
    
            
    
}//EOC
