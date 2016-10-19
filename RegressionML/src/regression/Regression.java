package regression;
import java.util.Arrays;

import core.Matrix;
import core.MatrixData;
import core.MatrixFormulation;

public class Regression {
	
	double slope;
	double intercept;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatrixFormulation mf = new MatrixFormulation();
		//double dataTrain[][] = null, dataTest[][] = null;
		Regression  reg = new Regression();
		 MatrixData dataTrain = mf.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_train_data.csv");
		
		MatrixData dataTest =  mf.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_test_data.csv");
//	outer:	for(int i = 0 ;i< dataTrain.matrix.length; i++)
////		{
////			for(int j = 0; j < dataTrain.matrix[0].length; j++)
////			{
////				System.out.print(" "+ dataTrain.matrix[i][j]);
////				
////			}
////			break outer;
////			//System.out.println();
////		}
		//System.out.println(data);
		
		int i = reg.columnNo(dataTrain,"sqft_living");
		int j = reg.columnNo(dataTrain, "price");
		dataTrain.convertTocolumn();
		dataTrain.convertToHashMap();
		dataTest.convertTocolumn();
		dataTest.convertToHashMap();
		//double a[] = {0.0,1.0,2.0,3.0,4.0};
		//double b[] = {1.0,2.0,3.0,4.0,5.0};
		//simple_linear_regression(a,b);
//		for(int m =0 ;m<dataTrain.columnMatrix[i].length; m++)
//		{
//			System.out.print(dataTrain.columnMatrix[i][m] + "  ");
//		}
//		//System.out.println(dataTrain.columnMatrix[i]);
		//System.out.println(dataTrain.columnMatrix[j]);
		reg.simple_linear_regression(dataTrain.getColumnMatrix()[i], dataTrain.getColumnMatrix()[j]);
		double specific_value_sqft[] = {2650.0};
		double output[] = reg.get_regression_predictions(specific_value_sqft, reg.intercept,reg.slope);
		System.out.println(Arrays.toString(output));
		double RSS = reg.get_residual_sum_of_squares(dataTrain.getColumnMatrix()[i], dataTrain.getColumnMatrix()[j], reg.slope, reg.intercept);
		System.out.println(RSS);
		double specific_value_price[] = {800000};
		double input_array[] = reg.inverse_regression_predictions(specific_value_price, reg.intercept, reg.slope);
		System.out.println(Arrays.toString(input_array));
		int k = reg.columnNo(dataTrain,"bedrooms");
		

		reg.simple_linear_regression(dataTest.getColumnMatrix()[i], dataTest.getColumnMatrix()[j]);
		//double specific_value_sqft[] = {2650.0};
		//double output[] = reg.get_regression_predictions(specific_value_sqft, reg.intercept,reg.slope);
		//System.out.println(Arrays.toString(output));
		double RSS_sqft = reg.get_residual_sum_of_squares(dataTest.getColumnMatrix()[i], dataTest.getColumnMatrix()[j], reg.slope, reg.intercept);
		System.out.println(RSS_sqft);
		reg.simple_linear_regression(dataTest.getColumnMatrix()[k], dataTest.getColumnMatrix()[j]);
		//double specific_value_sqft[] = {2650.0};
		//double output[] = reg.get_regression_predictions(specific_value_sqft, reg.intercept,reg.slope);
		//System.out.println(Arrays.toString(output));
		double RSS_bedroom = reg.get_residual_sum_of_squares(dataTest.getColumnMatrix()[k], dataTest.getColumnMatrix()[j], reg.slope, reg.intercept);
		System.out.println(RSS_bedroom);
		
		
		//########### Week 2
		int p = reg.columnNo(dataTrain,"bathrooms");
		dataTrain.getDynamic_matrix().put("bedrooms_squared", reg.multiply_column_matrix(dataTrain.getColumnMatrix()[k],dataTrain.getColumnMatrix()[k]));
		
		//reg.multiply_column_matrix(dataTest.columnMatrix[k],dataTest.columnMatrix[k]);
		dataTest.getDynamic_matrix().put("bedrooms_squared", reg.multiply_column_matrix(dataTest.getColumnMatrix()[k],dataTest.getColumnMatrix()[k]));
		
		dataTrain.getDynamic_matrix().put("bed_bath_rooms", reg.multiply_column_matrix(dataTrain.getColumnMatrix()[k],dataTrain.getColumnMatrix()[p]));
		
		//reg.multiply_column_matrix(dataTest.columnMatrix[k],dataTest.columnMatrix[k]);
		dataTest.getDynamic_matrix().put("bed_bath_rooms", reg.multiply_column_matrix(dataTest.getColumnMatrix()[k],dataTest.getColumnMatrix()[p]));
		
			
		dataTrain.getDynamic_matrix().put("log_sqft_living", reg.logArray(dataTrain.getColumnMatrix()[i]));
		
		//reg.multiply_column_matrix(dataTest.columnMatrix[k],dataTest.columnMatrix[k]);
		dataTest.getDynamic_matrix().put("log_sqft_living", reg.logArray(dataTest.getColumnMatrix()[i]));
		
		int latIndex = reg.columnNo(dataTrain,"lat");
		int longIndex = reg.columnNo(dataTrain,"long");
dataTrain.getDynamic_matrix().put("lat_plus_long", reg.add_column_matrix(dataTrain.getColumnMatrix()[latIndex],dataTrain.getColumnMatrix()[longIndex]));
		
		//reg.multiply_column_matrix(dataTest.columnMatrix[k],dataTest.columnMatrix[k]);
		dataTest.getDynamic_matrix().put("lat_plus_long", reg.add_column_matrix(dataTest.getColumnMatrix()[latIndex],dataTrain.getColumnMatrix()[longIndex]));
		
//		System.out.println(reg.meanAverage(dataTest.dynamic_matrix.get("bedrooms_squared")));
//		System.out.println(reg.meanAverage(dataTest.dynamic_matrix.get("bed_bath_rooms")));
//		System.out.println(reg.meanAverage(dataTest.dynamic_matrix.get("log_sqft_living")));
//		System.out.println(reg.meanAverage(dataTest.dynamic_matrix.get("lat_plus_long")));
//		System.out.println("Completed");
//		
		
		String simple_features[] = {"sqft_living"};
				String my_output= "price";
				double simple_feature_matrix[][] = reg.get_numpy_data(dataTrain, simple_features, my_output);
				// each row is a column here
				double initial_weights[] = {-47000.0, 1.0};
				double step_size = 7e-12;
				double tolerance = 2.5e7;
				String[] simple_features_extended = new String[simple_features.length+1];
				for (int l = 1; l <= simple_features.length; l++) {
					simple_features_extended[l]=simple_features[l-1];
					
				}
				simple_features_extended[0] = "constant";
				double final_weights[] =reg.regression_gradient_descent(simple_features_extended,dataTrain,simple_feature_matrix, dataTrain.getDynamic_matrix().get("price"),initial_weights, step_size,tolerance);
		//System.out.println(Arrays.toString(final_weights));
		
		
		
		
		
		//simple_features = ['sqft_living']
			//	my_output= 'price'
				double[][] simple_feature_matrix_test = reg.get_numpy_data(dataTest, simple_features, my_output);
				double[] predictedOutput = reg.predict_outcome(simple_feature_matrix_test, final_weights);
				//System.out.println(Arrays.toString(predictedOutput));
				double RSSTest  = reg.computeRSS(dataTest.getDynamic_matrix().get("price"), predictedOutput);
		System.out.println(RSSTest);
		
		
		
		
		
		// multiple regression now
		

		String simple_features_m[] = {"sqft_living","sqft_living15"};
				String my_output_m= "price";
				double simple_feature_matrix_m[][] = reg.get_numpy_data(dataTrain, simple_features_m, my_output_m);
				// each row is a column here
				double initial_weights_m[] = {-100000.0, 1.0, 1.0};
				double step_size_m = 4e-12;
				double tolerance_m= 1e9;
				String[] simple_features_extended_m = new String[simple_features_m.length+1];
				for (int l = 1; l <= simple_features_m.length; l++) {
					simple_features_extended_m[l]=simple_features_m[l-1];
					
				}
				simple_features_extended_m[0] = "constant";
				double final_weights_m[] =reg.regression_gradient_descent(simple_features_extended_m,dataTrain,simple_feature_matrix_m, dataTrain.getDynamic_matrix().get("price"),initial_weights_m, step_size_m,tolerance_m);
		//System.out.println(Arrays.toString(final_weights_m));
		
		
		
		
		
		//simple_features = ['sqft_living']
			//	my_output= 'price'
				double[][] simple_feature_matrix_test_m = reg.get_numpy_data(dataTest, simple_features_m, my_output_m);
				double[] predictedOutput_m = reg.predict_outcome(simple_feature_matrix_test_m, final_weights_m);
				//System.out.println(Arrays.toString(predictedOutput_m));
				double RSSTest_m  = reg.computeRSS(dataTest.getDynamic_matrix().get("price"), predictedOutput_m);
		System.out.println(RSSTest_m);
		
		
		
		
		
		
		
		/// WEEK 3
		
		
		
		
		
		
		
		
		
	}
	
	public double computeRSS(double[] original, double[] predicted)
	{
		double RSS = 0.0;
		for (int i = 0; i < predicted.length; i++) {
			//double d = predicted[i];
			RSS = RSS + Math.pow(original[i] - predicted[i], 2);
		}
		
		return RSS;
	}
	
	public  double[][] get_numpy_data(MatrixData md, String features[], String output)
	{
		double constant_array[] = new double[md.getMatrix().length];
		Arrays.fill(constant_array, 1);
		md.getDynamic_matrix().put("constant", constant_array);
		md.addElement("constant");
		
		String features_new [] = new String[features.length+1];
		for (int i = 1; i <= features.length; i++) {
			//String string = features[i];
			features_new[i] = features[i-1];
		}
		
		features_new[0] = "constant";
		
		return md.dynamicMatrixRowWise(features_new);
		
	}
	
	public double [] predict_outcome(double[][] feature_matrix, double[] weight)
	{
		return Matrix.multiply(feature_matrix, weight);
		
	}
	
	public double feature_derivative(double[] error,double[] feature)
	{
		return 2*Matrix.multiply(error, feature);
	}
	
	public double[] regression_gradient_descent(String featureNames[],MatrixData dataTrain,double[][] feature_matrix, double[] output, double[] initial_weights,double step_size, double tolerance)
	{
		boolean converged = false;
		double final_weights[]= initial_weights;
		while(!converged)
		{   double predict[]=predict_outcome(feature_matrix, final_weights); // no of observations
		double error[] = Matrix.subtract(predict, output);
		double gRSS = 0.0;
			for (int i = 0; i < initial_weights.length; i++) {
				double derivative=feature_derivative(error, dataTrain.getDynamic_matrix().get(featureNames[i]));
				gRSS = gRSS + Math.pow(derivative, 2);
				//double d = initial_weights[i];
				final_weights[i] = final_weights[i] - step_size*derivative;
			}
			double gradient_magnitude = Math.sqrt(gRSS);
			if(gradient_magnitude < tolerance)
			{
				converged = true;
			}
		}
		
		return final_weights;
	}
	
	public double meanAverage(double input_feature[])
	{
		double mean = 0.0;
		for (int i = 0; i < input_feature.length; i++) {
			mean = mean + input_feature[i];
			
			
		}
		
		return mean/input_feature.length;
	}
	
	public double get_residual_sum_of_squares(double input_feature[],double output[], double slope, double intercept)
	{
		double RSS = 0.0;
		double predicted_output[] = get_regression_predictions(input_feature,intercept,slope);
		for(int i =0;i< input_feature.length;i++)
		{
			RSS = RSS+ Math.pow((output[i] - predicted_output[i]), 2);
		}
		return RSS;
	}
	
	public double[] inverse_regression_predictions(double output[], double intercept, double slope)
	{
		double input_feature[] = new double[output.length];
		
		for(int i = 0;i< output.length;i++)
		{
			input_feature[i] = (output[i] - intercept)/ slope;
		}
		
		return input_feature;
	}
	
	public int columnNo(MatrixData matrix, String columnName)
	{
		int i =0;
		for(i=0;i<matrix.getColumnHeaders().length;i++)
		{
			if(matrix.getColumnHeaders()[i].equals(columnName))
			{
				break;
			}
		}
		return i;
	}
	
	public void simple_linear_regression(double input_feature[], double output[])
	{
		double summation_x = summation(input_feature);
		double summation_y = summation(output);
		double summation_square_x = summation_square(input_feature);
		double summation_multiplied_xy = summation(multiply_column_matrix(input_feature, output));
		int N = input_feature.length;
		
		double slope_w1 = (summation_multiplied_xy - (summation_y * summation_x)/N)/(summation_square_x - (summation_x*summation_x)/N);
		
		double slope_w0 = (summation_y/N) - slope_w1 *(summation_x/N);
		intercept = slope_w0;
		System.out.println(slope_w0);
		slope = slope_w1;
		System.out.println(slope_w1);
		
	}
	
	public double[] get_regression_predictions(double[] input_feature, double intercept, double slope)
	{
		double[] output = new double[input_feature.length] ;
		
		for(int  i= 0;i<input_feature.length ; i++)
		{
			output[i] = intercept  + slope*input_feature[i];
		}
		
		
		return output;
	}
	
	public double[] logArray(double input_feature[])
	{
		double logArray[] =new double[input_feature.length]; 
		for (int i = 0; i < input_feature.length; i++) {
			logArray[i] = Math.log(input_feature[i]);
			
		}
		return logArray;
	}
	
	public double[] multiply_column_matrix(double x[],double y[])
	{
		double multiplied[] = new double[x.length];
		for(int i =0;i< x.length;i++)
		{
			multiplied[i] = x[i]*y[i];
		}
		
		return multiplied;
	}
	
	public double[] add_column_matrix(double x[],double y[])
	{
		double added[] = new double[x.length];
		for(int i =0;i< x.length;i++)
		{
		added[i] = x[i]+y[i];
		}
		
		return added;
	}
	
	public double summation(double array_val[])
	{
		double sum= 0.0;
		for(int i =0;i<array_val.length;i++)
		{
			sum = sum + array_val[i];
		}
		
		return sum;
	}
	
	public double summation_square(double array_val[])
	{
		double sum= 0.0;
		for(int i =0;i<array_val.length;i++)
		{
			sum = sum + array_val[i]*array_val[i];
		}
		
		return sum;
	}

}
