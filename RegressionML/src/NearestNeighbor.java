import java.util.Arrays;

public class NearestNeighbor {

	public double[][] get_numpy_data(MatrixData md, String features[], String output) {
		double constant_array[] = new double[md.matrix.length];
		Arrays.fill(constant_array, 1);
		md.dynamic_matrix.put("constant", constant_array);
		md.addElement("constant");
		md.normalized_dynamic_matrix.put("constant", normalize_features(constant_array));

		String features_new[] = new String[features.length + 1];
		for (int i = 1; i <= features.length; i++) {
			// String string = features[i];
			features_new[i] = features[i - 1];
		}

		features_new[0] = "constant";

		return md.dynamicNormalizedMatrixRowWise(features_new);

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
	
	
	
	
	
}
