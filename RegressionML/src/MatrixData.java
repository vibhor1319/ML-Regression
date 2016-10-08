import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MatrixData {

	double [][] matrix ;
	double [][] columnMatrix;
	
	HashMap<String,double[]> dynamic_matrix = new HashMap<String, double[]>();
	HashMap<String,double[]> normalized_dynamic_matrix = new HashMap<String, double[]>();
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
	
	public void convertToNormalizeHashMap()
	{
		for(Entry<String, double[]> each: dynamic_matrix.entrySet())
		{
			String column = each.getKey();
			//double value = Math.sqrt(Arrays.stream(dynamic_matrix.get(column)).sum());
			
		//	double[] normalizedArray = Arrays.stream(dynamic_matrix.get(column)).map(map -> (map / value)).toArray();
			normalized_dynamic_matrix.put(column, normalize_features(dynamic_matrix.get(column)));
			//System.out.println(column + " : "+ Arrays.toString(normalizedArray));
		}
	}
	
	public double[] normalize_features(double[] features) {
		double normalized[] = new double[features.length];
		double normalizationDeno = 0.0;
		for (int i = 0; i < features.length; i++) {
			normalizationDeno = normalizationDeno + Math.pow(features[i], 2);

		}
		System.out.println(normalizationDeno);
		normalizationDeno = Math.sqrt(normalizationDeno);
		System.out.println(normalizationDeno);
		for (int i = 0; i < normalized.length; i++) {
			normalized[i] = features[i] / normalizationDeno;
		}

		return normalized;

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
	
	public double[][] dynamicNormalizedMatrixRowWise(String[] columns) {
		double[][] dynamicMa = new double[columns.length][columnMatrix[0].length];
	
	for(int i=0;i<columns.length;i++)
	{
		dynamicMa[i] = normalized_dynamic_matrix.get(columns[i]);
	}
	
	
	return convertToRow(dynamicMa);
	//return null;
}
	
}
