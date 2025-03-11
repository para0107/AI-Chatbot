package Classifier;

import java.util.*;

public class KNNClassifier {
    private List<String> questions;
    private List<String> responses;
    private Map<String, Integer> vocabulary;
    private double[][] featureMatrix; //Bag-of-Words

    public KNNClassifier(List<String> questions, List<String> responses) {
        this.questions = questions;
        this.responses = responses;
        this.vocabulary = new HashMap<>();
        preprocessData();
    }

    //Convert text into numerical format
    private void preprocessData() {
        Set<String> vocabularySet = new HashSet<>();
        for (String question : questions) {
            String[] tokens = question.toLowerCase().split("\\s+");
            vocabularySet.addAll(Arrays.asList(tokens));
        }

        int index = 0;
        for (String word : vocabularySet)
            vocabulary.put(word, index++);//each word is corresponding an index


        // Create feature matrix
        featureMatrix = new double[questions.size()][vocabulary.size()];
        for (int i = 0; i < questions.size(); i++) {
            String[] words = questions.get(i).toLowerCase().split("\\s+");
            for (String word : words) {
                if (vocabulary.containsKey(word)) {
                    featureMatrix[i][vocabulary.get(word)] = 1; // Bag of Words
                }
            }

        }
    }
    //KNN predictions
    public String predict(String inputQuestion, int k) {
        double[] inputVector = textToVector(inputQuestion);
        PriorityQueue<double[]> neighbors = new PriorityQueue<>(Comparator.comparingDouble(o -> -o[1]));

        for (int i = 0; i < featureMatrix.length; i++) {
            double similarity = cosineSimilarity(featureMatrix[i], inputVector);

            if (similarity > 0) { // Only add to queue if similarity > 0
                neighbors.add(new double[]{i, similarity});
                if (neighbors.size() > k) {
                    neighbors.poll();
                }
            }
        }

        if (neighbors.isEmpty()) {
            return null; // No similar question found
        }

        double bestIndex = neighbors.poll()[0]; // Get most similar question index
        return responses.get((int) bestIndex);
    }

    private double[] textToVector(String text)
    {
        double[] vector = new double[vocabulary.size()];
        String[] words = text.toLowerCase().split("\\s+");
        for(String word : words)
        {
            if(vocabulary.containsKey(word))
                vector[vocabulary.get(word)]  = 1;
        }
        return vector;

    }
    private double cosineSimilarity(double[] a, double[] b) {
        return dot(a, b) / (magnitude(a) * magnitude(b));
    }

    private double dot(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) sum += a[i] * b[i];
        return sum;
    }

    private double magnitude(double[] vector) {
        double sum = 0;
        for (double v : vector) sum += v * v;
        return Math.sqrt(sum);
    }

}

