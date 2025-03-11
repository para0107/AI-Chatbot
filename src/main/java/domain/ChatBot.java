package domain;

import Classifier.KNNClassifier;
import DatabaseConfig.DatabaseHandler;

import java.util.List;

public class ChatBot {
 private DatabaseHandler database;
 private KNNClassifier knn;
    private int interactionCount = 0;  // Track how often chatbot is used
    private final int retrainThreshold = 5;
    // Retrain after 5 interactions

    public ChatBot()
    {
        database = new DatabaseHandler();
        trainModel();
    }

    // Train KNN model with latest database questions
    private void trainModel() {
        List<String> questions = database.getAllQuestions();
        List<String> responses = database.getAllResponses();
        knn = new KNNClassifier(questions, responses);
    }

    public String getResponse(String userInput) {
        interactionCount++;

        // Predict response using KNN
        String predictedResponse = knn.predict(userInput, 3);
        if (predictedResponse != null) {
            System.out.println("predicted from database with knn");

            return predictedResponse;
        }

        // If no match, fetch from Wikipedia
        String webResponse = WebScraper.scrapeWikipedia(userInput);

        // Store new knowledge
        database.insertResponse(userInput, webResponse);

        // Retrain KNN every X interactions
        if (interactionCount >= retrainThreshold) {
            trainModel();
            interactionCount = 0;
        }
System.out.println("From wikipedia");
        return webResponse;
    }
}

