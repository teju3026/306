import java.util.*;

public class PrecisionRecallCalculator {

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

    public static void main(String[] args) {
        // Sample input sets
        Set<String> retrievedDocs = new HashSet<>(Arrays.asList("doc1", "doc2", "doc3", "doc4", "doc5"));
        Set<String> relevantDocs = new HashSet<>(Arrays.asList("doc1", "doc3", "doc6", "doc7"));

        // Calculate Precision and Recall
        double precision = calculatePrecision(retrievedDocs, relevantDocs);
        double recall = calculateRecall(retrievedDocs, relevantDocs);

        // Print the results
        System.out.printf("Precision: %.2f\n", precision);
        System.out.printf("Recall: %.2f\n", recall);
    }
}
