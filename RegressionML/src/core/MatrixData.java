package core;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MatrixData {

	double [][] matrix ;
	public double[][] getColumnMatrix() {
		return columnMatrix;
	}
	public void setColumnMatrix(double[][] columnMatrix) {
		this.columnMatrix = columnMatrix;
	}
	public HashMap<String, double[]> getDynamic_matrix() {
		return dynamic_matrix;
	}
	public void setDynamic_matrix(HashMap<String, double[]> dynamic_matrix) {
		this.dynamic_matrix = dynamic_matrix;
	}
	public HashMap<String, double[]> getNormalized_dynamic_matrix() {
		return normalized_dynamic_matrix;
	}
	public void setNormalized_dynamic_matrix(HashMap<String, double[]> normalized_dynamic_matrix) {
		this.normalized_dynamic_matrix = normalized_dynamic_matrix;
	}
	public HashMap<String, Double> getNormalized_norm_value() {
		return normalized_norm_value;
	}
	public void setNormalized_norm_value(HashMap<String, Double> normalized_norm_value) {
		this.normalized_norm_value = normalized_norm_value;
	}
	double [][] columnMatrix;
	String columnHeaders[];
	HashMap<String,double[]> dynamic_matrix = new HashMap<String, double[]>();
	HashMap<String,double[]> normalized_dynamic_matrix = new HashMap<String, double[]>();
	HashMap<String,Double> normalized_norm_value = new HashMap<String, Double>();
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
			//System.out.println(value);
		//	double[] normalizedArray = Arrays.stream(dynamic_matrix.get(column)).map(map -> (map / value)).toArray();
			normalized_dynamic_matrix.put(column, normalize_features(column,dynamic_matrix.get(column)));
			//System.out.println(column + " : "+ Arrays.toString(normalizedArray));
		}
	}
	
	public double[] normalize_features(String column,double[] features) {
		double normalized[] = new double[features.length];
		double normalizationDeno = 0.0;
		for (int i = 0; i < features.length; i++) {
			normalizationDeno = normalizationDeno + Math.pow(features[i], 2);

		}
		//System.out.println(normalizationDeno);
		normalizationDeno = Math.sqrt(normalizationDeno);
		normalized_norm_value.put(column, normalizationDeno);
		//System.out.println(normalizationDeno);
		for (int i = 0; i < normalized.length; i++) {
			normalized[i] = features[i] / normalizationDeno;
		}

		return normalized;

	}
	
	public double[] normalize_features(String column, double[] features, Double norm)
	{
		double normalized[] = new double[features.length];
		double normalizationDeno = norm;
		
		normalized_norm_value.put(column, normalizationDeno);
		//System.out.println(normalizationDeno);
		for (int i = 0; i < normalized.length; i++) {
			normalized[i] = features[i] / normalizationDeno;
		}

		return normalized;
	}
	
	public void convertToNormalizeHashMap(MatrixData md)
	{
		for(Entry<String, double[]> each: dynamic_matrix.entrySet())
		{
			String column = each.getKey();
			//double value = Math.sqrt(Arrays.stream(dynamic_matrix.get(column)).sum());
			//System.out.println(value);
		//	double[] normalizedArray = Arrays.stream(dynamic_matrix.get(column)).map(map -> (map / value)).toArray();
			normalized_dynamic_matrix.put(column, normalize_features(column,dynamic_matrix.get(column),md.normalized_norm_value.get(column)));
			//System.out.println(column + " : "+ Arrays.toString(normalizedArray));
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
