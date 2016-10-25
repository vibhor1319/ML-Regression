package core;

import java.util.HashMap;

public class MatrixDataString {
String [][] columnMatrix;
public String[][] getColumnMatrix() {
	return columnMatrix;
}
public void setColumnMatrix(String[][] columnMatrix) {
	this.columnMatrix = columnMatrix;
}
public String[][] getMatrix() {
	return matrix;
}
public void setMatrix(String[][] matrix) {
	this.matrix = matrix;
}
public HashMap<String, String[]> getDynamic_matrix() {
	return dynamic_matrix;
}
public void setDynamic_matrix(HashMap<String, String[]> dynamic_matrix) {
	this.dynamic_matrix = dynamic_matrix;
}
public HashMap<String, String[]> getNormalized_dynamic_matrix() {
	return normalized_dynamic_matrix;
}
public void setNormalized_dynamic_matrix(HashMap<String, String[]> normalized_dynamic_matrix) {
	this.normalized_dynamic_matrix = normalized_dynamic_matrix;
}
public String[] getColumnHeaders() {
	return columnHeaders;
}
public void setColumnHeaders(String[] columnHeaders) {
	this.columnHeaders = columnHeaders;
}
String [][] matrix ;
	HashMap<String,String[]> dynamic_matrix = new HashMap<String, String[]>();
	HashMap<String,String[]> normalized_dynamic_matrix = new HashMap<String, String[]>();
	//HashMap<String,Dou> normalized_norm_value = new HashMap<String, Double>();
	String columnHeaders[];
}
