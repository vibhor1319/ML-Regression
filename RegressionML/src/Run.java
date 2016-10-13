import java.util.Arrays;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//runLasso();
		runNN();
	}
	
	public static void runNN()
	{
		MatrixFormulation mf = new MatrixFormulation();
		// double dataTrain[][] = null, dataTest[][] = null;
		NearestNeighbor reg = new NearestNeighbor();
		MatrixData dataTrain = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/week6/kc_house_data_small_train.csv");

		MatrixData dataTest = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/week6/kc_house_data_small_test.csv");
		MatrixData dataValidation = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/week6/kc_house_data_validation.csv");
		// oute
		dataTrain.convertTocolumn();
		dataTrain.convertToHashMap();
		
		dataValidation.convertTocolumn();
		dataValidation.convertToHashMap();
		
		dataTest.convertTocolumn();
		dataTest.convertToHashMap();

		
		//dataTest.convertToNormalizeHashMap();
		String feature_list[] = {"bedrooms","bathrooms","sqft_living","sqft_lot","floors","waterfront","view","condition",  
		                "grade",  
		                "sqft_above",  
		                "sqft_basement",
		                "yr_built",  
		                "yr_renovated",  
		                "lat",  
		                "long",  
		                "sqft_living15",  
		                "sqft_lot15"};
		
		String my_output = "price";
		double simple_feature_matrix_m[][] = reg.get_numpy_data(dataTrain, feature_list, my_output);
		

		double simple_feature_matrix_validation[][] = reg.get_numpy_data(dataValidation, feature_list, my_output,dataTrain);
		

		double simple_feature_matrix_test[][] = reg.get_numpy_data(dataTest, feature_list, my_output,dataTrain);
		
		double[] distances = reg.compute_distances(simple_feature_matrix_m, simple_feature_matrix_test[2]);
		
		System.out.println(Arrays.stream(distances).min().getAsDouble());
		System.out.println(distances[382]);
		System.out.println(Arrays.toString(simple_feature_matrix_test[382]));
		System.out.println(dataTest.dynamic_matrix.get("price")[382]);
		System.out.println(dataTrain.dynamic_matrix.get("price")[382]);
		
		Pair index[] = reg.compute_k_nearest_neighbors(4,simple_feature_matrix_m,simple_feature_matrix_test[2]);
		//System.out.println(Arrays.toString(distances));
		for (int i = 0; i < index.length; i++) {
			System.out.println("Index: " + index[i].index + " Value: " + index[i].value);
				
		}
		
		double avgValue = reg.compute_distances_k_avg(4,simple_feature_matrix_m,simple_feature_matrix_test[2],dataTrain.dynamic_matrix.get("price"));
		System.out.println(avgValue);
		
		
		double[][] newArray = new double[10][simple_feature_matrix_test[1].length];
		for (int i = 0; i < 10; i++) {
			newArray[i] = simple_feature_matrix_test[i];
		}
		
		
		double[] predictedValue = reg.compute_distances_k_all(10, simple_feature_matrix_m, newArray, dataTrain.dynamic_matrix.get("price"));
		
		System.out.println(Arrays.toString(predictedValue));
		
		for (int i = 1; i <= 16; i++) {
			double[] predicted = reg.compute_distances_k_all(i, simple_feature_matrix_m, simple_feature_matrix_validation, dataTrain.dynamic_matrix.get("price"));
			System.out.println(Arrays.toString(predicted));
			double RSS = Matrix.RSS(predicted, dataValidation.dynamic_matrix.get("price"));
			
			System.out.println("RSS " + RSS + " k "+i);
		}
	}

	public static void runLasso() {
		System.out.println("running lasso");

		MatrixFormulation mf = new MatrixFormulation();
		// double dataTrain[][] = null, dataTest[][] = null;
		LassoRegression reg = new LassoRegression();
		MatrixData dataTrain = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_data.csv");

		MatrixData dataTest = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/kc_house_test_data.csv");
		// oute
		dataTrain.convertTocolumn();
		dataTrain.convertToHashMap();
		dataTest.convertTocolumn();
		dataTest.convertToHashMap();
		//dataTest.convertToNormalizeHashMap();
		dataTrain.convertToNormalizeHashMap();

		String simple_features[] = { "sqft_living", "bedrooms" };
		String my_output = "price";
		double simple_feature_matrix_m[][] = reg.get_numpy_data(dataTrain, simple_features, my_output);
		// double[][] simple_feature_matrix = get_numpy_data(train_data,
		// simple_features, my_output)
		//double[][] simple_test_feature_matrix = reg.get_numpy_data(dataTest, simple_features, my_output);
		//double step_size = 1e-12;
		//int max_iterations = 1000;
		double[] initial_weights = { 0.0, 0.0, 0.0 };

		double l1_penalty = 1e7, tolerance =1.0;
		String[] simple_features_extended = new String[simple_features.length + 1];
		for (int l = 1; l <= simple_features.length; l++) {
			simple_features_extended[l] = simple_features[l - 1];

		}
		simple_features_extended[0] = "constant";
//		double[] simple_weights_low_penalty = reg.regression_gradient_descent(simple_features_extended, dataTrain,
//				simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, step_size, l2_penalty,
//				max_iterations);
		double[] final_weight = reg.lasso_cyclical_coordinate_descent(simple_features_extended, dataTrain, simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, l1_penalty, tolerance);
		System.out.println(Arrays.toString(final_weight));
	}

	public static void runRidge() {
		MatrixFormulation mf = new MatrixFormulation();
		// double dataTrain[][] = null, dataTest[][] = null;
		RidgeRegression reg = new RidgeRegression();
		MatrixData dataTrain = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/week5/kc_house_train_data.csv");

		MatrixData dataTest = mf
				.readCSV("/home/vibhor/coursera/ml regression/javaImplementation/week5/kc_house_test_data.csv");
		// oute
		dataTrain.convertTocolumn();
		dataTrain.convertToHashMap();
		dataTest.convertTocolumn();
		dataTest.convertToHashMap();

		String simple_features[] = { "sqft_living" };
		String my_output = "price";
		double simple_feature_matrix_m[][] = reg.get_numpy_data(dataTrain, simple_features, my_output);
		// double[][] simple_feature_matrix = get_numpy_data(train_data,
		// simple_features, my_output)
		//double[][] simple_test_feature_matrix = reg.get_numpy_data(dataTest, simple_features, my_output);
		double step_size = 1e-12;
		int max_iterations = 1000;
		double[] initial_weights = { 0.0, 0.0 };

		double l2_penalty = 0.0;
		String[] simple_features_extended = new String[simple_features.length + 1];
		for (int l = 1; l <= simple_features.length; l++) {
			simple_features_extended[l] = simple_features[l - 1];

		}
		simple_features_extended[0] = "constant";
		double[] simple_weights_low_penalty = reg.regression_gradient_descent(simple_features_extended, dataTrain,
				simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, step_size, l2_penalty,
				max_iterations);
		l2_penalty = 1e11;
		double[] simple_weights_high_penalty = reg.regression_gradient_descent(simple_features_extended, dataTrain,
				simple_feature_matrix_m, dataTrain.dynamic_matrix.get("price"), initial_weights, step_size, l2_penalty,
				max_iterations);
		System.out.println(Arrays.toString(simple_weights_low_penalty));
		System.out.println(Arrays.toString(simple_weights_high_penalty));
	}

}