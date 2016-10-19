package regression;
import java.util.Arrays;

import core.Matrix;
import core.MatrixData;

public class RidgeRegression {

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
	
	public double feature_derivative_ridge(double[] error,double[] feature, double weight, double l2_penalty ,boolean feature_is_constant)
	{
		if(feature_is_constant)
		{
			return 2*Matrix.multiply(error, feature);
		}
		else
		{
			return 2*Matrix.multiply(error, feature) + 2*l2_penalty*weight;
		}
		
	}
	
	public double[] regression_gradient_descent(String featureNames[],MatrixData dataTrain,double[][] feature_matrix, double[] output, double[] initial_weights,double step_size, double l2_penalty, double max_iterations)
	{
		int l =0;
		boolean converged = false;
		double final_weights[]= initial_weights;
		while(l< max_iterations)
		{   double predict[]=predict_outcome(feature_matrix, final_weights); // no of observations
		double error[] = Matrix.subtract(predict, output);
		double gRSS = 0.0;
			for (int i = 0; i < initial_weights.length; i++) {
				boolean feature_is_constant = false;
				if(i==0)
				{
					feature_is_constant = true;
				}
				else{
					feature_is_constant = false;
				}
				double derivative=feature_derivative_ridge(error, dataTrain.getDynamic_matrix().get(featureNames[i]), final_weights[i], l2_penalty, feature_is_constant);
				//gRSS = gRSS + Math.pow(derivative, 2);
				//double d = initial_weights[i];
				final_weights[i] = final_weights[i] - step_size*derivative;
			}
//			double gradient_magnitude = Math.sqrt(gRSS);
//			if(gradient_magnitude < tolerance)
//			{
//				converged = true;
//			}
			l++;
		}
		
		return final_weights;
	}
	
	
	
	
	
	
}
