import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ConflationAlgorithm {

    // Load stop words to ignore common words
    private static final Set<String> STOP_WORDS = Set.of(
        "a", "an", "and", "the", "of", "in", "on", "to", "with", "that", "is", "was", "for", "by"
    );

    // Main method to execute the conflation process
    public static void main(String[] args) {
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try {
            // Step 1: Read input file and split into sentences
            String text = Files.readString(Path.of(inputFile));
            List<String> sentences = splitIntoSentences(text);

            // Step 2: Conflate sentences by merging similar ones
            List<String> conflatedSentences = conflateSentences(sentences);

            // Step 3: Write the conflated output to a new file
            Files.write(Path.of(outputFile), conflatedSentences);
            System.out.println("Conflated document generated: " + outputFile);

        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // Step 1: Split the text into sentences
    private static List<String> splitIntoSentences(String text) {
        return Arrays.stream(text.split("(?<=[.!?])\\s+")).collect(Collectors.toList());
    }

    // Step 2: Conflate sentences by merging similar ones
    private static List<String> conflateSentences(List<String> sentences) {
        List<String> conflated = new ArrayList<>();
        for (String sentence : sentences) {
            boolean merged = false;
            for (int i = 0; i < conflated.size(); i++) {
                String existingSentence = conflated.get(i);
                if (areSentencesSimilar(sentence, existingSentence)) {
                    conflated.set(i, mergeSentences(sentence, existingSentence));
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                conflated.add(sentence);
            }
        }
        return conflated;
    }

    // Compare sentences using a simple Levenshtein distance-based similarity
    private static boolean areSentencesSimilar(String sentence1, String sentence2) {
        double similarityThreshold = 0.7;
        int maxLength = Math.max(sentence1.length(), sentence2.length());
        int distance = levenshteinDistance(sentence1, sentence2);
        double similarity = 1.0 - (double) distance / maxLength;
        return similarity >= similarityThreshold;
    }

    // Merge two similar sentences by combining their contents
    private static String mergeSentences(String sentence1, String sentence2) {
        Set<String> words1 = extractSignificantWords(sentence1);
        Set<String> words2 = extractSignificantWords(sentence2);

        Set<String> mergedWords = new HashSet<>(words1);
        mergedWords.addAll(words2);

        return String.join(" ", mergedWords) + ".";
    }

    // Extract significant words from a sentence, ignoring stop words
    private static Set<String> extractSignificantWords(String sentence) {
        return Arrays.stream(sentence.toLowerCase().split("\\W+"))
                     .filter(word -> !STOP_WORDS.contains(word))
                     .collect(Collectors.toSet());
    }

    // Levenshtein distance algorithm for comparing similarity between two strings
    private static int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[len1][len2];
    }

    // Cost of substitution for Levenshtein distance
    private static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
}
