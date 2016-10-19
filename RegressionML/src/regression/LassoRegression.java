package regression;
import java.util.Arrays;
import java.util.OptionalDouble;

import core.Matrix;
import core.MatrixData;
import core.*;


public class LassoRegression {

	public double[][] get_numpy_data(MatrixData md, String features[], String output) {
		double constant_array[] = new double[md.getMatrix().length];
		Arrays.fill(constant_array, 1);
		md.getDynamic_matrix().put("constant", constant_array);
		md.addElement("constant");
		md.getNormalized_dynamic_matrix().put("constant", md.normalize_features("constant",constant_array));

		String features_new[] = new String[features.length + 1];
		for (int i = 1; i <= features.length; i++) {
			// String string = features[i];
			features_new[i] = features[i - 1];
		}

		features_new[0] = "constant";

		return md.dynamicNormalizedMatrixRowWise(features_new);

	}

	public double[] predict_outcome(double[][] feature_matrix, double[] weight) {
		return Matrix.multiply(feature_matrix, weight);

	}

	public double feature_derivative_ridge(double[] error, double[] feature, double weight, double l2_penalty,
			boolean feature_is_constant) {
		if (feature_is_constant) {
			return 2 * Matrix.multiply(error, feature);
		} else {
			return 2 * Matrix.multiply(error, feature) + 2 * l2_penalty * weight;
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

	public double lasso_coordinate_descent_step(MatrixData md, String feature, double[][] feature_matrix,
			double[] output, double[] weights, double l1_penalty,int feature_no) {
		// for (int i = 0; i < weights.length; i++) {
		//
		// }

		double ro_i = 0.0;
		double prediction[] = predict_outcome(feature_matrix, weights);
		ro_i = Matrix.multiply(md.getNormalized_dynamic_matrix().get(feature), Matrix.addColumnWise(Matrix.subtract(output, prediction),
				Matrix.scalarMultiply(md.getNormalized_dynamic_matrix().get(feature), weights[feature_no])));
		double new_weight = 0.0;
		if (feature.equals("constant")) {
			new_weight = ro_i;
		} else if (ro_i < (-l1_penalty / 2)) {
			new_weight = ro_i + (l1_penalty / 2);
		} else if (ro_i > (l1_penalty / 2)) {
			new_weight = ro_i - (l1_penalty / 2);
		} else {
			new_weight = 0.0;
		}

		return new_weight;

	}

	public double[] lasso_cyclical_coordinate_descent(String[] new_features, MatrixData md, double[][] feature_matrix,
			double[] output, double[] initial_weights, double l1_penalty, double tolerance) {
		// pass initial weight as 0
		double[] final_weights = initial_weights.clone();
		boolean converged = false;
		double[] changeWeight = initial_weights.clone();
		Arrays.fill(changeWeight, 0.0);
		while (!converged) {
			for (int i = 0; i < initial_weights.length; i++) {

				double weight = lasso_coordinate_descent_step(md, new_features[i], feature_matrix, output,
						final_weights, l1_penalty,i);
				changeWeight[i] = Math.abs(final_weights[i] - weight);
				final_weights[i] = weight;
				
				

			}
			System.out.println(Arrays.toString(final_weights));

			// $.max(changeWeight);
			OptionalDouble max = Arrays.stream(changeWeight).max();
			double maxValue = 0.0;
			if (max.isPresent()) {
				maxValue = max.getAsDouble();
			} else {
				System.exit(0);
			}
			System.out.println(maxValue);
			
			if(maxValue < tolerance)
			{
				converged = true;
			}
		}

		return final_weights;

	}

}
