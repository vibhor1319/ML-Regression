import java.util.Arrays;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
				MatrixFormulation mf = new MatrixFormulation();
				//double dataTrain[][] = null, dataTest[][] = null;
				RidgeRegression  reg = new RidgeRegression();
				 MatrixData dataTrain = mf.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_train_data.csv");
				
				MatrixData dataTest =  mf.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_test_data.csv");
//			oute
				dataTrain.convertTocolumn();
				dataTrain.convertToHashMap();
				dataTest.convertTocolumn();
				dataTest.convertToHashMap();
				
				String simple_features[] = {"sqft_living"};
					String my_output = "price";
					double simple_feature_matrix_m[][] = reg.get_numpy_data(dataTrain, simple_features, my_output);
						//double[][] simple_feature_matrix = get_numpy_data(train_data, simple_features, my_output)
						double[][] simple_test_feature_matrix = reg.get_numpy_data(dataTest, simple_features, my_output);
						double step_size = 1e-12;
								int max_iterations = 1000;
								double[] initial_weights = {0.0,0.0};
								
								double l2_penalty = 0.0;
								String[] simple_features_extended = new String[simple_features.length+1];
								for (int l = 1; l <= simple_features.length; l++) {
									simple_features_extended[l]=simple_features[l-1];
									
								}
								simple_features_extended[0] = "constant";
							double[] simple_weights_low_penalty =  reg.regression_gradient_descent(simple_features_extended, dataTrain, simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, step_size,l2_penalty,max_iterations);
							 l2_penalty = 1e11;
							double[] simple_weights_high_penalty =  reg.regression_gradient_descent(simple_features_extended, dataTrain, simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, step_size,l2_penalty,max_iterations);
							System.out.println(Arrays.toString(simple_weights_low_penalty));
							System.out.println(Arrays.toString(simple_weights_high_penalty));

	}

}
