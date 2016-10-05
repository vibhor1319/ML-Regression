import java.util.HashMap;

public class MatrixData {

	double [][] matrix ;
	double [][] columnMatrix;
	
	HashMap<String,double[]> dynamic_matrix = new HashMap<String, double[]>();
	public double[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	public String[] getColumnHeaders() {
		return columnHeaders;
	}
	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}
	String columnHeaders[];
	
	public double[][] convertToRow(double[][] mat)
	{
		double converted[][] = new double[mat[0].length][mat.length];
		
		for(int i = 0; i<mat.length;i++)
		{
			for(int j =0 ;j<mat[0].length ;j++)
			{
				converted[j][i] = mat[i][j];
			}
		}
		//columnMatrix = converted;
		return converted;
	}
	
	
	public double[][] convertTocolumn()
	{
		double converted[][] = new double[matrix[0].length][matrix.length];
		
		for(int i = 0; i<matrix[0].length;i++)
		{
			for(int j =0 ;j<matrix.length ;j++)
			{
				converted[i][j] = matrix[j][i];
			}
		}
		columnMatrix = converted;
		return converted;
	}
	
	public void convertToHashMap()
	{
		for(int i = 0; i < columnMatrix.length;i++)
		{
			dynamic_matrix.put(columnHeaders[i], columnMatrix[i]);
		}
	}
	
	public void addElement(String element)
	{
		columnHeaders = new String[columnHeaders.length+1];
		columnHeaders[columnHeaders.length-1] = element;
	}
	
	public double[][] dynamicMatrix(String columns[])
	{
		double[][] dynamicMa = new double[columns.length][columnMatrix[0].length];
		
		for(int i=0;i<columns.length;i++)
		{
			dynamicMa[i] = dynamic_matrix.get(columns[i]);
		}
		
		
		return dynamicMa;
		
	}
	public double[][] dynamicMatrixRowWise(String[] columns) {
			double[][] dynamicMa = new double[columns.length][columnMatrix[0].length];
		
		for(int i=0;i<columns.length;i++)
		{
			dynamicMa[i] = dynamic_matrix.get(columns[i]);
		}
		
		
		return convertToRow(dynamicMa);
		//return null;
	}
	
}
