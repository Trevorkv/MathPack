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
        determinant = 0;
    }

    /**
     * Overloaded Constructor
     * @param matrix 
     */
    public Matrix(double[][] matrix)
    {
        setMatrix(matrix);
        determinant = 0;
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
    public int getColSize()
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
        initDet();
        return determinant;
    }

    /**
     * Description: assigns a new value to the matrix variable
     * @param matrix a two dimensional array of type double
     */
    public void setMatrix(double[][] matrix) 
    {
        try{
            this.matrix = new double[matrix.length][matrix[0].length];
            rowSize = this.matrix.length;
            colSize = this.matrix[0].length;

            for(int i = 0; i < rowSize; i++)
            {
                for(int j = 0; j < colSize; j++)
                {
                    this.matrix[i][j] = matrix[i][j];
                }
            }
        }catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e + "\nInconsistency found in matrix dimensions"
                    + "\nThe remaining elements have been initialized to 0");
        }
    }
      
    /**
     * Description: swaps row1 and row2 in the matrix
     * @param row1 The index of one of the two rows to be replaced
     * @param row2 The index of one of the two rows to be replaced
     * @return temp The resulting Matrix after an interchange row operation.
     */
    public Matrix interchange(int row1, int row2)
    {
        //temporary storage
        Matrix temp = new Matrix(matrix);
        double[]first = new double[colSize];
        
        for(int i = 0; i < colSize; i++)
        {
            first[i] = temp.matrix[row1][i];
            temp.matrix[row1][i] = temp.matrix[row2][i];
            temp.matrix[row2][i] = first[i];
        }
    
        return temp;
    }
    
    /**
     * Description: replaces the destination row with the sum of itself and the
              multiple of the source row by a scaleRow 
              of the source
     * @param source
     * @param destination
     * @param scale 
     * @return temp The resulting Matrix after a replace row  operation.
     */
    public Matrix replace(int source, int destination, double scale)
    {
        Matrix temp = null;
        if(source != destination)
        {
             temp = new Matrix(matrix);
             
             for(int i = 0; i < colSize; i ++)
             {
                 temp.matrix[destination][i] = scale * temp.matrix[source][i] + 
                         temp.matrix[destination][i];
             } 
        }
       
       return temp;
    }
    
    /**
     * Description: scales the row by a scalar
     * @param row
     * @param scalar 
     * @return temp The resulting Matrix after a row scale operation.
     */
    public Matrix scaleRow(int row, double scalar)
    {
        Matrix temp = new Matrix(matrix);
        
        for(int i = 0; i < colSize; i ++)
        {
            temp.matrix[row][i] = scalar * temp.matrix[row][i];
        }
        
        return temp;
    }
    
    /**
     * Description: calculates and returns the determinant of the current matrix
     *              instance.
     * @return ret A double type that signifies the matrix's determinant.
     */
    private void initDet()
    {//consider usig other methods to find the det
        Matrix ref = new Matrix(matrix);
        double det = 0;
        int rowIndex = 0;
        int colIndex = 0;
        int rowComplete = 0;
        double scale = Double.NaN;

        while(rowIndex < rowSize && colIndex < colSize)
        {
            if(ref.matrix[rowIndex][colIndex] != 0)
            {          
                det = 1;
                scale = ref.matrix[rowIndex][colIndex];
                ref = ref.scaleRow(rowIndex, 
                        1.0 / scale);
                det *= scale;
                ref = ref.replaceBottomRows(rowIndex, colIndex);
                
                if(rowIndex != rowComplete)
                {
                    ref = ref.interchange(rowIndex, rowComplete);
                    det *= -1;
                }
            }
            else if(rowIndex < rowSize -1)
            {
                ref = ref.interchange(rowIndex, rowSize-1);
                det *= -1;
            }
            
            rowComplete++;
            rowIndex = rowComplete;
            colIndex++;
        }
        
        determinant = det;
    }
    
    public Matrix getSubMatrix(int startRow, int endRow, int startCol,
            int endCol)
    {
        Matrix temp = new Matrix(endRow-startRow, endCol-startCol);
        
        return temp;
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
     */
    public Matrix getRREF()
    {
        Matrix ref = new Matrix(matrix);
        int rowIndex = 0;
        int colIndex = 0;
        int rowComplete = 0;
        double scale;

        while(rowIndex < rowSize && colIndex < colSize)
        {
            if(ref.matrix[rowIndex][colIndex] != 0)
            {                
                scale = ref.matrix[rowIndex][colIndex];
                ref = ref.scaleRow(rowIndex, 
                        1.0 / scale);
                ref = ref.replaceRows(rowIndex, colIndex);
                
                if(rowIndex != rowComplete)
                    ref = ref.interchange(rowIndex, rowComplete);
            }
            else if(rowIndex < rowSize -1)
                ref = ref.interchange(rowIndex, rowSize-1);
            
            rowComplete++;
            rowIndex = rowComplete;
            colIndex++;
        }
        
        return ref;
    }

    /**
     * Description : returns the Row Echelon Form of the current matrix 
     *               instance.
     * @return ref A Matrix Object meant to serve as the REF of the current
     *             Matrix instance.
     */
    public Matrix getREF()
    {
        Matrix ref = new Matrix(matrix);
        int rowIndex = 0;
        int colIndex = 0;
        int rowComplete = 0;
        double scale;

        while(rowIndex < rowSize && colIndex < colSize)
        {
            if(ref.matrix[rowIndex][colIndex] != 0)
            {          
                scale = ref.matrix[rowIndex][colIndex];
                ref = ref.scaleRow(rowIndex, 
                        1.0 / scale);
                ref = ref.replaceBottomRows(rowIndex, colIndex);
                
                if(rowIndex != rowComplete)
                {
                    ref = ref.interchange(rowIndex, rowComplete);
                }
            }
            else if(rowIndex < rowSize -1)
            {
                ref = ref.interchange(rowIndex, rowSize-1);
            }
            
            rowComplete++;
            rowIndex = rowComplete;
            colIndex++;
        }
        
        return ref;
    }
    
    private Matrix replaceRows(int source, int colLoc)
    {
        Matrix temp = new Matrix(matrix);
        double scale = 0;
        
        for(int j = 0; j < rowSize; j++)
        {
            scale = -temp.matrix[j][colLoc];
            if(j != source)
                temp = temp.replace(source, j, scale);
        }
        
        return temp;
    }
    
    private Matrix replaceBottomRows(int source, int colLoc)
    {
        Matrix temp = new Matrix(matrix);
        double scale = 0;
        
        for(int j = source+1; j < rowSize; j++)
        {
            scale = -temp.matrix[j][colLoc];
            temp = temp.replace(source, j, scale);
        }
        
        return temp;
    }
    
    /**
     * Description : returns the Least Common Denominator from num1 and num2
     * @param num1
     * @param num2
     * @return lcm is a Double signifying the Least Common Denominator
     */
    private double getLCM(double num1, double num2)
    {
        double lcm = 0l;
        double least = num1 <= num2 ? num1 : num2;
        double greatest = least == num1 ? num2 : num1;
        
        if(greatest % least == 0)
            lcm = greatest;
        else
        {
            for(int i = 1; i < greatest; i++)
            {
                if(least * i % greatest == 0)
                {
                    lcm = least * i;
                    break;
                }
            }
        }
        
        return lcm;
    }
    
    /**
     * Description : returns true if the current Matrix instance is invertible
     * @return ret A boolean to signify the invertible condition of a matrix.
     */
    public boolean isInvertible()
    {
        boolean ret = true;
        double det = getDet();
        
        if(det == 0 || Double.isNaN(det))
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
        Matrix ret = null;
        double[][] newMat = null;
        
        if(arg.rowSize == this.colSize)
        {
            newMat = new double[this.rowSize][arg.colSize];
            for(int i = 0; i <  arg.colSize; i++)
            {
                for(int j = 0; j < this.rowSize; j++)
                {
                    newMat[j][i] = multiplyRowHelper(getRow(j), arg.getCol(i));
                }
            }
            
            ret = new Matrix(newMat);
        }
        
        return ret;
    }
    
    private double multiplyRowHelper(double[] row, double [] col)
    {
        double ret = 0;
        
        for(int i = 0; i < row.length; i++)
        {
            ret += row[i] * col[i];
        }
        
        return ret;
    }
    
    private double[] getRow(int index)
    {
        return matrix[index];
    }
    
    private double[] getCol(int index)
    {
        double [] ret = new double[colSize];
        
        for(int i = 0; i < rowSize; i++)
        {
            ret[i] = matrix[i][index];
        }
        
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
        
        ret += "Row Size : " + rowSize + "\n";
        ret += "Column Sze : " + colSize + "\n";
        ret += "Determinant : " + getDet() + "\n";
        ret += "Invertible : " + isInvertible() + "\n";
        
        return ret;
    }     
}//EOC
