import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

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
		md.convertToNormalizeHashMap();
		return md.dynamicNormalizedMatrixRowWise(features_new);

	}
	
	public Pair[] compute_k_nearest_neighbors(int k, double[][] feature_matrix, double[] query)
	{
		//double distances[] = new double[feature_matrix.length];
		Pair[] distances = new Pair[feature_matrix.length];
		for (int i = 0; i < feature_matrix.length; i++) {
			double temp = 0.0;
			for (int j = 0; j < feature_matrix[0].length; j++) {
				temp = temp + Math.pow(feature_matrix[i][j] - query[j], 2);
			}
			distances[i] = new Pair(i,Math.sqrt(temp));
			
		}
		//double sorted_distance[] = distances.clone();
		 Arrays.sort(distances);
		 return Arrays.copyOf(distances, k);
		
//		LinkedHashMap<Double, Double> index = new LinkedHashMap<Double, Double>();
//		for (int i = 0; i < k; i++) {
//			index.put(key, strippedArray[i]);
//		}
		
		
	}
	
	public double[] compute_distances_k_all(int k, double[][] feature_matrix, double[][] query, double[] output)
	{
		double[] distances = new double[query.length];
		for (int i = 0; i < query.length; i++) {
			distances[i] = compute_distances_k_avg(k,feature_matrix,query[i],output);
		}
		return distances;
	}
	
	public double compute_distances_k_avg(int k, double[][] feature_matrix, double[] query, double[] output)
	{
		Pair[] distances = new Pair[feature_matrix.length];
		for (int i = 0; i < feature_matrix.length; i++) {
			double temp = 0.0;
			for (int j = 0; j < feature_matrix[0].length; j++) {
				temp = temp + Math.pow(feature_matrix[i][j] - query[j], 2);
			}
			distances[i] = new Pair(i,Math.sqrt(temp));
			
		}
		//double sorted_distance[] = distances.clone();
		 Arrays.sort(distances);
		 Pair[] sortedDistance =  Arrays.copyOf(distances, k);
		 double avg = 0.0;
		 for (int i = 0; i < sortedDistance.length; i++) {
			avg = avg + output[sortedDistance[i].index];
		}
		 return avg/k;
		 
	}
	
	
	public double[] compute_distances(double[][] feature_matrix,double[] query)
	{
		double distances[] = new double[feature_matrix.length];
		
		for (int i = 0; i < feature_matrix.length; i++) {
			double temp = 0.0;
			for (int j = 0; j < feature_matrix[0].length; j++) {
				temp = temp + Math.pow(feature_matrix[i][j] - query[j], 2);
			}
			distances[i] = Math.sqrt(temp);
			
		}
		return distances;
	}
	
	public double[][] get_numpy_data(MatrixData md, String features[], String output,MatrixData md1) {
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
		md.convertToNormalizeHashMap(md1);
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
