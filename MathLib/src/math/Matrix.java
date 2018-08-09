/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * Description: A model of a mathematical matrix and its functionalities
 * @author Trevor Klein Villanueva
 */
public class Matrix{
    
    private int rowSize;
    private int colSize;
    private double determinant;
    
    private double[][] matrix;

    /**
     * Default Constructor
     */
    public Matrix()
    {
        rowSize = 0;
        colSize = 0;        
        matrix = new double[rowSize][colSize];
        determinant = 0;
    }
    
    /**
     * Overloaded Constructor
     * @param row
     * @param col 
     */
    public Matrix(int row, int col)
    {
        rowSize = row;
        colSize = col;        
        matrix = new double[rowSize][colSize];
        determinant = initDet();
    }

    /**
     * Overloaded Constructor
     * @param matrix 
     */
    public Matrix(double[][] matrix)
    {
        rowSize = matrix.length;
        rowSize = matrix[0].length;
        this.matrix = matrix;
        determinant = initDet();
    }
    
    /**
     * Description: returns rowSize
     * @return rowSize Integer that represents the number of rows in the 
                                matrix.
     */
    public int getRowSize()
    {
        return rowSize;
    }
    
    /**
     * Description: returns colSize
     * @return colSize Integer that represents the number of columns in
                        the matrix.
     */
    public int getcolSize()
    {
        return colSize;
    }
    
    /**
     * Description: returns matrix
     * @return matrix A two dimensional array of type double to store 
                      numbers to represent the matrix.
     */
    public double[][] getMatrix()
    {
        return matrix;
    }
    
    /**
     * Description: returns determinant
     * @return determinant A double type representing the determinant of the 
     *          matrix.
     */
    public double getDet()
    {
        return determinant;
    }

    /**
     * Description: assigns a new value to the matrix variable
     * @param matrix a two dimensional array of type double
     */
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        rowSize = this.matrix.length;
        colSize = this.matrix[0].length;
    }
      
    /**
     * Description: swaps row1 and row2 in the matrix
     * @param row1 The index of one of the two rows to be replaced
     * @param row2 The index of one of the two rows to be replaced
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
              multiple of the source row by a scaleRow 
              of the source
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
    public void scaleRow(int row, double scalar)
    {
        for(int i = 0; i < colSize; i ++)
        {
            matrix[row][i] = scalar * matrix[row][i];
        }
        
        determinant /= scalar;
    }
    
    /**
     * Description: calculates and returns the determinant of the current matrix
     *              instance.
     * @return ret A double type that signifies the matrix's determinant.
     */
    private double initDet()
    {
        double ret = Double.NaN;
        Matrix temp;
        
        if(isSquare())
        {
            temp = getRREF();
            ret = temp.matrix[0][0];
            
            for(int i = 1; i < temp.rowSize; i++)
            {
                ret *= temp.matrix[i][i];
            }
        }
        return ret;
    }
    
    /**
     * Description: returns true if the current instance is a square matrix and 
     *              false if otherwise.
     * @return ret Boolean that signifies the condition of whether the current 
     *              instance is a square matrix.
     */
    public boolean isSquare()
    {
        boolean ret = true;
        if(rowSize != colSize)
            ret = false;
        return ret;
    }
    
    /**
     * Description: returns the Reduce Row Echelon form of the current matrix.
     * @return temp a Matrix object
     *///INCOMPLETE : ADD REVERSE SIMPLIFYING ROWS FROM BOTTOM TO TOP, ELSE IT
        //IS JUST A ROW ECHELON FORM.
    public Matrix getRREF()
    {
        Matrix temp = new Matrix(matrix);
        
        temp.getREF();
        
        //Add the last step of algorithm rising row reduction
        
        return temp;
    }

    /**
     * Description : returns the Row Echelon Form of the current matrix 
     *               instance.
     * @return ref A Matrix Object meant to serve as the REF of the current
     *             Matrix instance.
     */
    public Matrix getREF()
    {
        Matrix ref = null;
        
        if(this.matrix == null)
            throw new NullPointerException();
        else
        {
            ref = new Matrix(matrix);
            int rowIndex = 0;
            int colIndex = 0;
            
            while(rowIndex < rowSize && colIndex < colSize)
            {
                for(int i = rowIndex; i < rowSize; i++)
                {
                    if(ref.matrix[i][colIndex] != 0)
                    {
                        ref.scaleRow(i, 1.0 / ref.matrix[i][colIndex]);
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
    
    private void replaceRows(int source, double scale)
    {
        for(int j = 0; j < rowSize; j++)
        {
            if(j != source)
                this.replace(source, j, -scale);
        }
    }
    
    /**
     * Description : returns true if the current Matrix instance is invertible
     * @return ret A boolean to signify the invertible condition of a matrix.
     */
    public boolean isInvertible()
    {
        boolean ret = true;
        
        if(determinant == 0)
            ret = false;
        
        return ret;
    }
    
    /**
     * Description: returns the transpose of the current matrix instance. The 
     *              return matrix will have the dimensions of the original but
     *              reversed( ret.rowSize = original.colSize and
     *              ret.colSize = original.rowSize)
     * @return ret The Matrix object that serves as the transpose of the current
     *             Matrix instance.
     */
    public Matrix getTranspose()
    {
        Matrix ret = new Matrix(colSize, rowSize);
        
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < colSize; j++)
            {
                ret.matrix[j][i] = matrix[i][j];
            }
        }
        
        return ret;
    }
    
    /**
     * Description: returns a new matrix from adding the current matrix instance
     *              and the given argument.
     * @param arg A Matrix object meant to be an operand for the add operation.
     * @return ret A Matrix Object.
     */
    public Matrix add(Matrix arg)
    {
        Matrix ret = null;
        
        if(rowSize == arg.rowSize && colSize == arg.colSize)
        {
            ret = new Matrix(rowSize, colSize); 
            for(int i = 0; i < rowSize; i++)
            {
                for(int j = 0; j < colSize; j++)
                {
                    ret.getMatrix()[i][j] = matrix[i][j] + arg.matrix[i][j];
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Description: returns a new matrix from subtracting the current matrix 
     *              instance and the given argument.
     * @param arg A Matrix object meant to be an operand for the add operation.
     * @return ret A Matrix Object.
     */
    public Matrix subtract(Matrix arg)
    {
        Matrix ret = null;
        
        if(rowSize == arg.rowSize && colSize == arg.colSize)
        {
            ret = new Matrix(rowSize, colSize); 
            for(int i = 0; i < rowSize; i++)
            {
                for(int j = 0; j < colSize; j++)
                {
                    ret.getMatrix()[i][j] = matrix[i][j] - arg.matrix[i][j];
                }
            }
        }
        
        return ret;
    }
    
    /**
     * Description: returns the multiple of the current matrix instance and the 
     *              given matrix argument. Returns null if operation fails.
     * @param arg
     * @return ret A Matrix resultant of the matrix operation
     */
    public Matrix multiply(Matrix arg)
    {
        Matrix ret;
        double[][] newMat = null;
        
        if(arg.rowSize == this.colSize)
        {
            newMat = new double[this.rowSize][arg.colSize];
            for(int i = 0; i <  arg.colSize; i++)
            {
                for(int j = 0; j < arg.rowSize; j++)
                {
                    newMat[j][i] += this.matrix[i][j] * arg.matrix[j][i];
                }
            }
        }
        
        ret = new Matrix(newMat);
        
        return ret;
    }
    
    /**
     * Description : returns the multiple of the matrix by a given scalar
     *               argument.
     * @param arg A double type serving as a scalar multiple of the matrix.
     * @return ret A Matrix Object
     */
    public Matrix multiply(double arg)
    {
        Matrix ret = new Matrix(matrix);
        
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < colSize; j++)
            {
                ret.getMatrix()[i][j] = arg * ret.getMatrix()[i][j];
            }
        }
        
        return ret;
    }
    
    /**
     * Description: returns the string interpretation of the object
     * @return ret A String interpretation of the object
     */
    @Override
    public String toString()
    {
        String ret = "Matrix Info\n";
        
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < colSize; j++)
            {
                ret += matrix[i][j] + " ";
            }
            
            ret += "\n";
        }
        
        ret += "Row Size : " + rowSize;
        ret += "Column Sze : " + colSize;
        ret += "Determinant : " + determinant;
        ret += "Invertible : " + isInvertible();
        
        return ret;
    }     
}//EOC
