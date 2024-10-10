import java.util.*;

public class PrecisionRecallCalculator1 {

    // Method to calculate Precision
    public static double calculatePrecision(Set<String> retrievedDocs, Set<String> relevantDocs) {
        int truePositives = 0;
        for (String doc : retrievedDocs) {
            if (relevantDocs.contains(doc)) {
                truePositives++;
            }
        }
        return retrievedDocs.isEmpty() ? 0.0 : (double) truePositives / retrievedDocs.size();
    }

    // Method to calculate Recall
    public static double calculateRecall(Set<String> retrievedDocs, Set<String> relevantDocs) {
        int truePositives = 0;
        for (String doc : retrievedDocs) {
            if (relevantDocs.contains(doc)) {
                truePositives++;
            }
        }
        return relevantDocs.isEmpty() ? 0.0 : (double) truePositives / relevantDocs.size();
    }

    // Method to calculate F-measure (Harmonic Mean)
    public static double calculateFMeasure(double precision, double recall) {
        return (precision + recall == 0) ? 0.0 : 2 * ((precision * recall) / (precision + recall));
    }

    // Method to calculate E-measure with given beta
    public static double calculateEMeasure(double precision, double recall, double beta) {
        if (precision == 0 || recall == 0) {
            return 1.0;  // If either precision or recall is zero, E-measure is 1 (worst case).
        }
        return 1.0 - (1.0 / (((beta * beta) / recall) + (1.0 / precision)));
    }

    public static void main(String[] args) {
        // Sample input sets (retrieved documents and relevant documents)
        Set<String> retrievedDocs = new HashSet<>(Arrays.asList("doc1", "doc2", "doc3", "doc4", "doc5"));
        Set<String> relevantDocs = new HashSet<>(Arrays.asList("doc1", "doc3", "doc6", "doc7"));

        // Calculate Precision and Recall
        double precision = calculatePrecision(retrievedDocs, relevantDocs);
        double recall = calculateRecall(retrievedDocs, relevantDocs);

        // Calculate F-measure (Harmonic Mean)
        double fMeasure = calculateFMeasure(precision, recall);

        // Calculate E-measure with beta = 1.0 (equal weight to precision and recall)
        double eMeasure = calculateEMeasure(precision, recall, 1.0);

        // Print the results
        System.out.printf("Precision: %.2f\n", precision);
        System.out.printf("Recall: %.2f\n", recall);
        System.out.printf("F-measure (Harmonic Mean): %.2f\n", fMeasure);
        System.out.printf("E-measure (beta=1.0): %.2f\n", eMeasure);
    }
}
